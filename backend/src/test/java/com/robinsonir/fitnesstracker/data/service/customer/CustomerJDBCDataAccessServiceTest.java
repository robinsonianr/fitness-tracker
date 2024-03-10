package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.JDBCDataAccessService;
import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRowMapper;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRowMapper;
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
    private final WorkoutRowMapper workoutRowMapper = new WorkoutRowMapper();

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private JDBCDataAccessService customerService;


    @BeforeEach
    void setUp() {
        // Initialize customerService here using an instance of CustomerJDBCDataAccessService
        customerService = new JDBCDataAccessService(jdbcTemplate, workoutRowMapper, customerRowMapper);
        // Set up mock data for jdbcTemplate
        List<CustomerEntity> mockCustomerEntities = List.of(
                new CustomerEntity(1L, "John", "john@example.com", "password", 30, Gender.MALE),
                new CustomerEntity(2L, "Alice", "alice@example.com", "password", 25, Gender.FEMALE)
        );

        when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class)))
                .thenReturn(mockCustomerEntities);
    }

    @Test
    void testSelectAllCustomers() {
        // Call the selectAllCustomers method
        List<CustomerEntity> customerEntities = customerService.selectAllCustomers();

        // Verify that the correct number of customers is returned
        assertEquals(2, customerEntities.size());

        // You can add more assertions to verify the content of the returned customers
        // For example, you can check if specific customerEntity details match the expected values.
        // You can also check edge cases and corner scenarios.
    }

    @Test
    void selectCustomerById() {
        // Mock the behavior of jdbcTemplate for a specific customerEntity ID
        when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class), Mockito.eq(1L)))
                .thenReturn(Collections.singletonList(
                        new CustomerEntity(1L, "John", "john@example.com", "password", 30, Gender.MALE)
                ));

        // Call the selectCustomerById method with a specific customerEntity ID
        Optional<CustomerEntity> customerEntity = customerService.selectCustomerById(1L);

        // Verify that the customerEntity is found
        assertTrue(customerEntity.isPresent());

        // Assert customerEntity details
        assertEquals(1L, customerEntity.get().getId());
        assertEquals("John", customerEntity.get().getName());
        assertEquals("john@example.com", customerEntity.get().getEmail());
        assertEquals("password", customerEntity.get().getPassword());
        assertEquals(30, customerEntity.get().getAge());
        assertEquals(Gender.MALE, customerEntity.get().getGender());

        // Verify that the customerRowMapper is used to map the result
        verify(jdbcTemplate).query(Mockito.anyString(), Mockito.any(CustomerRowMapper.class), Mockito.eq(1L));
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
        Long existingId = 1L;
        Long nonExistingId = 3L; // Assuming this ID does not exist in the mock data

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
        Long customerIdToDelete = 1L; // Assuming this ID exists in the mock data

        // Mock the behavior of the jdbcTemplate.update method
        when(jdbcTemplate.update(
                Mockito.anyString(),
                Mockito.eq(customerIdToDelete)))
                .thenReturn(1); // One row affected, indicating successful deletion

        // Call the deleteCustomerById method
        customerService.deleteCustomerById(customerIdToDelete);

        String sql = """
                DELETE
                FROM fit_tracker.customer
                WHERE id = ?
                """;

        // Verify that the jdbcTemplate.update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                sql,
                customerIdToDelete);
    }

    @Test
    void updateCustomer() {
        Long customerIdToUpdate = 1L; // Assuming this ID exists in the mock data
        CustomerEntity updatedCustomerEntity = new CustomerEntity(customerIdToUpdate, "UpdatedName", null, null, 35, null);

        // Mock the behavior of the jdbcTemplate.update method
        when(jdbcTemplate.update(
                Mockito.anyString(),
                Mockito.any(),
                Mockito.any()))
                .thenReturn(1); // One row affected, indicating successful update

        // Call the updateCustomer method
        customerService.updateCustomer(updatedCustomerEntity);

        // Verify that the jdbcTemplate.update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                "UPDATE fit_tracker.customer SET name = ? WHERE id = ?",
                updatedCustomerEntity.getName(),
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
                        new CustomerEntity(1L, "John", "john@example.com", "password", 30, Gender.MALE)
                ));

        // Call the selectCustomerByUsername method
        Optional<CustomerEntity> foundCustomer = customerService.selectCustomerByUsername(usernameToFind);

        // Verify that the correct customerEntity with the given username was found
        assertTrue(foundCustomer.isPresent());
        CustomerEntity customerEntity = foundCustomer.get();
        assertEquals(1, customerEntity.getId());
        assertEquals("John", customerEntity.getName());
        assertEquals("john@example.com", customerEntity.getEmail());
        // Add more assertions for other customerEntity properties if needed
    }

    @Test
    void updateCustomerProfileImageId() {
        Long customerIdToUpdate = 1L; // Assuming this customerEntity ID exists in the mock data
        String newProfileImageId = "new-profile-image-id";

        // Call the updateCustomerProfileImageId method
        customerService.updateCustomerProfileImageId(newProfileImageId, customerIdToUpdate);

        // Verify that the update method was called with the correct SQL and parameters
        verify(jdbcTemplate).update(
                """
                        UPDATE fit_tracker.customer
                        SET profile_image_id = ?
                        WHERE id = ?
                        """,
                newProfileImageId,
                customerIdToUpdate
        );
    }
}