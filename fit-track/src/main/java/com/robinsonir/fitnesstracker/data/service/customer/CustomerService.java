package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTO;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTOMapper;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import com.robinsonir.fitnesstracker.exception.DuplicateResourceException;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import com.robinsonir.fitnesstracker.s3.S3Service;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
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

    private final CustomerDTOMapper customerDTOMapper;

    private final PasswordEncoder passwordEncoder;

    private final S3Service s3Service;
    private final CustomerRepository customerRepository;
    private final CustomerDataService customerDataService;

    @Setter
    @Value("${s3.bucket.name}")
    private String s3Bucket;

    public CustomerService(CustomerDTOMapper customerDTOMapper,
                           PasswordEncoder passwordEncoder,
                           S3Service s3Service,
                           CustomerRepository customerRepository,
                           CustomerDataService customerDataService) {
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
        this.customerRepository = customerRepository;
        this.customerDataService = customerDataService;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }


    public CustomerDTO getCustomer(Long id) {
        return customerRepository.findCustomerById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerDataService.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerRegistrationRequest.name());
        customerEntity.setEmail(customerRegistrationRequest.email());
        customerEntity.setPassword(passwordEncoder.encode(customerRegistrationRequest.password()));
        customerEntity.setAge(customerRegistrationRequest.age());
        customerEntity.setGender(customerRegistrationRequest.gender());
        customerEntity.setMemberSince(customerRegistrationRequest.memberSince());

        customerRepository.save(customerEntity);
    }


    public void checkIfCustomerExistsOrThrow(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
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

        customerRepository.updateProfileImageId(profileImageId, customerId);
    }


    public byte[] getProfilePicture(Long customerId) {
        var customer = customerRepository.findCustomerById(customerId)
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
        CustomerEntity customerEntity = customerRepository.findCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));

        if (updateRequest.email() != null &&
                !updateRequest.email().equals(customerEntity.getEmail())) {
            if (customerRepository.existsByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customerEntity.setEmail(updateRequest.email());
        }

        customerRepository.updateCustomer(id,
                updateRequest.name(),
                updateRequest.email(),
                updateRequest.age(),
                updateRequest.gender(),
                updateRequest.weight(),
                updateRequest.height(),
                updateRequest.weightGoal(),
                updateRequest.activity(),
                updateRequest.bodyFat()
        );
    }

}
