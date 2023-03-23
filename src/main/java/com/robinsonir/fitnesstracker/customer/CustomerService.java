package com.robinsonir.fitnesstracker.customer;

import com.robinsonir.fitnesstracker.exception.DuplicateResourceException;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO,
                           CustomerDTOMapper customerDTOMapper) {
        this.customerDAO = customerDAO;
        this.customerDTOMapper = customerDTOMapper;
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
                customerRegistrationRequest.email(),
                customerRegistrationRequest.password(),
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
}
