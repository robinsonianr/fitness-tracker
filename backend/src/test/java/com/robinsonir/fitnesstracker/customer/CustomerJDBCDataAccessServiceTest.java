package com.robinsonir.fitnesstracker.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerJDBCDataAccessServiceTest {

    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();
    @MockBean
    private JdbcTemplate jdbcTemplate;

    private CustomerJDBCDataAccessService customerService;


    @BeforeEach
    void setUp() {
        // Initialize customerService here using an instance of CustomerJDBCDataAccessService
        customerService = new CustomerJDBCDataAccessService(jdbcTemplate, customerRowMapper);
        // Set up mock data for jdbcTemplate
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John", "john@example.com", "password", 30, Gender.MALE),
                new Customer(2, "Alice", "alice@example.com", "password", 25, Gender.FEMALE)
        );

        when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class)))
                .thenReturn(mockCustomers);
    }

    @Test
    void testSelectAllCustomers() {
        // Call the selectAllCustomers method
        List<Customer> customers = customerService.selectAllCustomers();

        // Verify that the correct number of customers is returned
        assertEquals(2, customers.size());

        // You can add more assertions to verify the content of the returned customers
        // For example, you can check if specific customer details match the expected values.
        // You can also check edge cases and corner scenarios.
    }

    @Test
    void selectCustomerById() {
        // Mock the behavior of jdbcTemplate for a specific customer ID
        when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class), Mockito.eq(1)))
                .thenReturn(Collections.singletonList(
                        new Customer(1, "John", "john@example.com", "password", 30, Gender.MALE)
                ));

        // Call the selectCustomerById method with a specific customer ID
        Optional<Customer> customer = customerService.selectCustomerById(1);

        // Verify that the customer is found
        assertTrue(customer.isPresent());

        // Assert customer details
        assertEquals(1, customer.get().getId());
        assertEquals("John", customer.get().getName());
        assertEquals("john@example.com", customer.get().getEmail());
        assertEquals("password", customer.get().getPassword());
        assertEquals(30, customer.get().getAge());
        assertEquals(Gender.MALE, customer.get().getGender());

        // Verify that the customerRowMapper is used to map the result
        verify(jdbcTemplate).query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class), Mockito.eq(1));
    }

    @Test
    void existsCustomerWithEmail() {
        // Mock the behavior of the existsCustomerWithEmail query
        String existingEmail = "john@example.com";
        String nonExistingEmail = "nonexistent@example.com";

        when(jdbcTemplate.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class), // Assuming your query returns an Integer
                Mockito.eq(existingEmail)))
                .thenReturn(1); // Email exists in the database

        when(jdbcTemplate.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class), // Assuming your query returns an Integer
                Mockito.eq(nonExistingEmail)))
                .thenReturn(0); // Email does not exist in the database

        // Test the existsCustomerWithEmail method
        boolean existsExistingEmail = customerService.existsCustomerWithEmail(existingEmail);
        boolean existsNonExistingEmail = customerService.existsCustomerWithEmail(nonExistingEmail);

        // Verify the results
        assertTrue(existsExistingEmail);
        assertFalse(existsNonExistingEmail);
    }


    @Test
    void existsCustomerById() {
        // Mock the behavior of the existsCustomerById query
        int existingId = 1;
        int nonExistingId = 3; // Assuming this ID does not exist in the mock data

        when(jdbcTemplate.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class), // Assuming your query returns an Integer
                Mockito.eq(existingId)))
                .thenReturn(1); // ID exists in the database

        when(jdbcTemplate.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class), // Assuming your query returns an Integer
                Mockito.eq(nonExistingId)))
                .thenReturn(0); // ID does not exist in the database

        // Test the existsCustomerById method
        boolean existsExistingId = customerService.existsCustomerById(existingId);
        boolean existsNonExistingId = customerService.existsCustomerById(nonExistingId);

        // Verify the results
        assertTrue(existsExistingId);
        assertFalse(existsNonExistingId);
    }

    @Test
    void deleteCustomerById() {
        int customerIdToDelete = 1; // Assuming this ID exists in the mock data

        // Mock the behavior of the jdbcTemplate.update method
        when(jdbcTemplate.update(
                Mockito.anyString(),
                Mockito.eq(customerIdToDelete)))
                .thenReturn(1); // One row affected, indicating successful deletion

        // Call the deleteCustomerById method
        customerService.deleteCustomerById(customerIdToDelete);

        String sql = """
                DELETE
                FROM customer 
                WHERE id = ?
                """;

        // Verify that the jdbcTemplate.update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                sql,
                customerIdToDelete);
    }

    @Test
    void updateCustomer() {
        int customerIdToUpdate = 1; // Assuming this ID exists in the mock data
        Customer updatedCustomer = new Customer(customerIdToUpdate, "UpdatedName", null, null, 35, null);

        // Mock the behavior of the jdbcTemplate.update method
        when(jdbcTemplate.update(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.any()))
                .thenReturn(1); // One row affected, indicating successful update

        // Call the updateCustomer method
        customerService.updateCustomer(updatedCustomer);

        // Verify that the jdbcTemplate.update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                "UPDATE customer SET name = ? WHERE id = ?",
                updatedCustomer.getName(),
                customerIdToUpdate);

        // You can add more assertions here to verify other fields that may have been updated.

    }

    @Test
    void selectCustomerByUsername() {
        String usernameToFind = "john@example.com"; // Assuming this username exists in the mock data

        // Mock the behavior of the jdbcTemplate.query method
        when(jdbcTemplate.query(
                Mockito.anyString(),
                Mockito.any(CustomerRowMapper.class),
                Mockito.eq(usernameToFind)))
                .thenReturn(List.of(
                        new Customer(1, "John", "john@example.com", "password", 30, Gender.MALE)
                ));

        // Call the selectCustomerByUsername method
        Optional<Customer> foundCustomer = customerService.selectCustomerByUsername(usernameToFind);

        // Verify that the correct customer with the given username was found
        assertEquals(true, foundCustomer.isPresent());
        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            assertEquals(1, customer.getId());
            assertEquals("John", customer.getName());
            assertEquals("john@example.com", customer.getEmail());
            // Add more assertions for other customer properties if needed
        }
    }

    @Test
    void updateCustomerProfileImageId() {
        int customerIdToUpdate = 1; // Assuming this customer ID exists in the mock data
        String newProfileImageId = "new-profile-image-id";

        // Call the updateCustomerProfileImageId method
        customerService.updateCustomerProfileImageId(newProfileImageId, customerIdToUpdate);

        // Verify that the update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                """
                        UPDATE customer
                        SET profile_image_id = ?
                        WHERE id = ?
                        """,
                newProfileImageId,
                customerIdToUpdate
        );
    }
}