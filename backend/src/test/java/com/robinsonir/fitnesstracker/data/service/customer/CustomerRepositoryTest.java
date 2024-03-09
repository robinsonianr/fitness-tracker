package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Clear any existing data in the customer table before each test
        customerRepository.deleteAll();
    }

    @Test
    void testSaveAndFindCustomerByEmail() {
        // Create a new customer
        CustomerEntity customerEntity = new CustomerEntity("John", "john@example.com", "password", 30, Gender.MALE);
        customerRepository.save(customerEntity);

        // Find the customer by email
        Optional<CustomerEntity> foundCustomer = customerRepository.findCustomerByEmail("john@example.com");

        // Assert that the customer was found and its details match
        assertTrue(foundCustomer.isPresent());
        assertEquals("John", foundCustomer.get().getName());
        assertEquals("john@example.com", foundCustomer.get().getEmail());
    }

    @Test
    void testExistsCustomerByEmail() {
        // Create a new customer
        CustomerEntity customerEntity = new CustomerEntity("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
        customerRepository.save(customerEntity);

        // Check if a customer with a specific email exists
        boolean exists = customerRepository.existsCustomerByEmail("alice@example.com");

        // Assert that the customer exists
        assertTrue(exists);
    }

    @Test
    void testExistsCustomerById() {
        // Create a new customer
        CustomerEntity customerEntity = new CustomerEntity("Bob", "bob@example.com", "password", 35, Gender.MALE);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);

        // Check if the customer with a specific ID exists
        boolean exists = customerRepository.existsCustomerById(savedCustomerEntity.getId());

        // Assert that the customer exists
        assertTrue(exists);
    }

    @Test
    void testUpdateProfileImageId() {
        // Create a new customer
        CustomerEntity customerEntity = new CustomerEntity("Eve", "eve@example.com", "password", 28, Gender.FEMALE);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);

        // Update the profile image ID for the customer
        customerRepository.updateProfileImageId("newImageId", savedCustomerEntity.getId());

        // Fetch the customer again
        Optional<CustomerEntity> updatedCustomer = customerRepository.findById(savedCustomerEntity.getId());

        // Assert that the profile image ID was updated
        assertTrue(updatedCustomer.isPresent());
        assertEquals("newImageId", updatedCustomer.get().getProfileImageId());
    }
}
