package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.service.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Clear any existing data in the customer table before each test
        customerRepository.deleteAll();
    }

    @Test
    void testAddCustomer() {
        // Create a customer entity
        CustomerEntity customer = new CustomerEntity(
                "John Doe",
                "john.doe@example.com",
                "password123",
                30,
                Gender.MALE
        );

        // Save the customer
        CustomerEntity savedCustomer = customerRepository.save(customer);

        // Validate customer is saved and has an ID
        assertNotNull(savedCustomer.getId());
        assertEquals("John Doe", savedCustomer.getName());
        assertEquals("john.doe@example.com", savedCustomer.getEmail());
        assertEquals(Gender.MALE, savedCustomer.getGender());
    }

    @Test
    void testFindCustomerById() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity(
                "Jane Doe",
                "jane.doe@example.com",
                "securePass",
                28,
                Gender.FEMALE
        );
        customerRepository.save(customer);

        // Fetch the customer by ID
        Optional<CustomerEntity> foundCustomer = customerRepository.findCustomerById(customer.getId());

        // Assert the customer was found and data is correct
        assertTrue(foundCustomer.isPresent());
        assertEquals("Jane Doe", foundCustomer.get().getName());
    }

    @Test
    void testFindCustomerByEmail() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity(
                "Jane Doe",
                "jane.doe@example.com",
                "securePass",
                28,
                Gender.FEMALE
        );
        customerRepository.save(customer);

        // Fetch the customer by ID
        Optional<CustomerEntity> foundCustomer = customerRepository.findCustomerById(customer.getId());

        // Assert the customer was found and data is correct
        assertTrue(foundCustomer.isPresent());
        assertEquals("Jane Doe", foundCustomer.get().getName());
    }

    @Test
    void testExistsByEmail() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity(
                "Alice Johnson",
                "alice.johnson@example.com",
                "alicePass",
                29,
                Gender.FEMALE
        );
        customerRepository.save(customer);

        // Check if the customer exists by email
        boolean exists = customerRepository.existsByEmail("alice.johnson@example.com");
        assertTrue(exists);

        // Check if a non-existent email returns false
        boolean notExists = customerRepository.existsByEmail("nonexistent@example.com");
        assertFalse(notExists);
    }

    @Test
    void testUpdateCustomer() {
        // Arrange: Add a customer to the database
        Long customerId = 1L;
        CustomerEntity existingCustomer = new CustomerEntity(
                "John Doe",
                "john.doe@example.com",
                "hashpassword",
                30,
                Gender.MALE,
                70,
                180,
                65,
                "Running",
                20);
        existingCustomer.setId(customerId);
        customerRepository.save(existingCustomer);

        // Act: Create update request and update the customer
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Jane Doe",
                "jane.doe@example.com",
                28,
                Gender.FEMALE,
                65,
                170,
                60,
                "Cycling",
                18
        );

        customerRepository.updateCustomer(customerId,
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

        // Assert: Check if customer was updated
        Optional<CustomerEntity> updatedCustomerOpt = customerRepository.findCustomerById(customerId);
        assertTrue(updatedCustomerOpt.isPresent());
        CustomerEntity updatedCustomer = updatedCustomerOpt.get();

        assertEquals(updateRequest.name(), updatedCustomer.getName());
        assertEquals(updateRequest.email(), updatedCustomer.getEmail());
        assertEquals(updateRequest.age(), updatedCustomer.getAge());
        assertEquals(updateRequest.gender(), updatedCustomer.getGender());
        assertEquals(updateRequest.weight(), updatedCustomer.getWeight());
        assertEquals(updateRequest.height(), updatedCustomer.getHeight());
        assertEquals(updateRequest.weightGoal(), updatedCustomer.getWeightGoal());
        assertEquals(updateRequest.activity(), updatedCustomer.getActivity());
        assertEquals(updateRequest.bodyFat(), updatedCustomer.getBodyFat());
    }

    @Test
    void testUpdateProfileImageId() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity(
                "Daniel Craig",
                "daniel.craig@example.com",
                "bond007",
                50,
                Gender.MALE
        );
        customerRepository.save(customer);

        // Update the profile image ID
        String newProfileImageId = "img12345";
        customerRepository.updateProfileImageId(newProfileImageId, customer.getId());

        // Fetch the updated customer by ID
        Optional<CustomerEntity> updatedCustomer = customerRepository.findCustomerById(customer.getId());

        // Assert the profile image ID was updated
        assertTrue(updatedCustomer.isPresent());
        assertEquals(newProfileImageId, updatedCustomer.get().getProfileImageId());
    }
}
