package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDAO;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTO;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTOMapper;
import com.robinsonir.fitnesstracker.exception.DuplicateResourceException;
import com.robinsonir.fitnesstracker.exception.RequestValidationException;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import com.robinsonir.fitnesstracker.s3.S3Service;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    private final CustomerDTOMapper customerDTOMapper;

    private final PasswordEncoder passwordEncoder;

    private final S3Service s3Service;

    @Setter
    @Value("${s3.bucket.name}")
    private String s3Bucket;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO,
                           CustomerDTOMapper customerDTOMapper,
                           PasswordEncoder passwordEncoder,
                           S3Service s3Service) {
        this.customerDAO = customerDAO;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }


    public CustomerDTO getCustomer(Long id) {
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

        CustomerEntity customerEntity = new CustomerEntity(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password()),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender());

        customerDAO.insertCustomer(customerEntity);
    }

    public void deleteCustomerById(Long id) {
        if (!customerDAO.existsCustomerById(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id)
            );
        }

        customerDAO.deleteCustomerById(id);
    }

    public void checkIfCustomerExistsOrThrow(Long customerId) {
        if (!customerDAO.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
    }

    public void uploadCustomerProfilePicture(Long customerId, MultipartFile file) {
        checkIfCustomerExistsOrThrow(customerId);
        String profileImageId = UUID.randomUUID().toString();

        try {
            s3Service.putObject(
                    s3Bucket,
                    "profile-images/%s/%s".formatted(customerId, profileImageId),
                    file.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }

        customerDAO.updateCustomerProfileImageId(profileImageId, customerId);
    }


    public byte[] getProfilePicture(Long customerId) {
        var customer = customerDAO.selectCustomerById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));

        if (StringUtils.isBlank(customer.profileImageId())) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] profile image not found".formatted(customerId));
        }

        return s3Service.getObject(
                s3Bucket,
                "profile-images/%s/%s".formatted(customerId, customer.profileImageId())
        );
    }

    public void updateCustomer(Long id, CustomerUpdateRequest updateRequest) {
        CustomerEntity customerEntity = customerDAO.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customerEntity.getName())) {
            customerEntity.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null &&
                !updateRequest.email().equals(customerEntity.getEmail())) {
            if (customerDAO.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customerEntity.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customerEntity.getAge())) {
            customerEntity.setAge(updateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }


        customerDAO.updateCustomer(customerEntity);
    }

}
