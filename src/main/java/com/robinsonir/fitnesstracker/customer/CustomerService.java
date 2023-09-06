package com.robinsonir.fitnesstracker.customer;

import com.robinsonir.fitnesstracker.exception.DuplicateResourceException;
import com.robinsonir.fitnesstracker.exception.RequestValidationException;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    private final CustomerDTOMapper customerDTOMapper;

    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO,
                           CustomerDTOMapper customerDTOMapper,
                           PasswordEncoder passwordEncoder) {
        this.customerDAO = customerDAO;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }


    public CustomerDTO getCustomer(Integer id) {
        return customerDAO.selectCustomerById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerDAO.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.username(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password()),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender());

        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id) {
        if (!customerDAO.existsCustomerById(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id)
            );
        }

        customerDAO.deleteCustomerById(id);
    }

    public void checkIfCustomerExistsOrThrow(Integer customerId) {
        if (!customerDAO.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest) {
        Customer customer = customerDAO.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null &&
                !updateRequest.email().equals(customer.getEmail())) {
            if (customerDAO.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }


        customerDAO.updateCustomer(customer);
    }
}
