package com.robinsonir.fitnesstracker.customer;

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
        Customer customer = new Customer("John", "john@example.com", "password", 30, Gender.MALE);
        customerRepository.save(customer);

        // Find the customer by email
        Optional<Customer> foundCustomer = customerRepository.findCustomerByEmail("john@example.com");

        // Assert that the customer was found and its details match
        assertTrue(foundCustomer.isPresent());
        assertEquals("John", foundCustomer.get().getName());
        assertEquals("john@example.com", foundCustomer.get().getEmail());
    }

    @Test
    void testExistsCustomerByEmail() {
        // Create a new customer
        Customer customer = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
        customerRepository.save(customer);

        // Check if a customer with a specific email exists
        boolean exists = customerRepository.existsCustomerByEmail("alice@example.com");

        // Assert that the customer exists
        assertTrue(exists);
    }

    @Test
    void testExistsCustomerById() {
        // Create a new customer
        Customer customer = new Customer("Bob", "bob@example.com", "password", 35, Gender.MALE);
        Customer savedCustomer = customerRepository.save(customer);

        // Check if the customer with a specific ID exists
        boolean exists = customerRepository.existsCustomerById(savedCustomer.getId());

        // Assert that the customer exists
        assertTrue(exists);
    }

    @Test
    void testUpdateProfileImageId() {
        // Create a new customer
        Customer customer = new Customer("Eve", "eve@example.com", "password", 28, Gender.FEMALE);
        Customer savedCustomer = customerRepository.save(customer);

        // Update the profile image ID for the customer
        customerRepository.updateProfileImageId("newImageId", savedCustomer.getId());

        // Fetch the customer again
        Optional<Customer> updatedCustomer = customerRepository.findById(savedCustomer.getId());

        // Assert that the profile image ID was updated
        assertTrue(updatedCustomer.isPresent());
        assertEquals("newImageId", updatedCustomer.get().getProfileImageId());
    }
}
