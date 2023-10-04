package com.robinsonir.fitnesstracker.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerDTOMapperTest {

    @Test
    void testMapRow() throws SQLException {
        // Create a mock ResultSet with sample data
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John");
        when(resultSet.getString("email")).thenReturn("john@example.com");
        when(resultSet.getInt("age")).thenReturn(30);
        when(resultSet.getString("gender")).thenReturn("MALE"); // Assuming it's stored as a string
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("profile_image_id")).thenReturn("profile_image_id");

        // Create a CustomerRowMapper
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        // Map the ResultSet to a Customer object
        Customer customer = customerRowMapper.mapRow(resultSet, 0);

        // Assert that the mapped Customer object has the expected values
        assert customer != null;
        assertEquals(1, customer.getId());
        assertEquals("John", customer.getName());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals(30, customer.getAge());
        assertEquals(Gender.MALE, customer.getGender()); // Assuming Gender is an enum
        assertEquals("password", customer.getPassword());
        assertEquals("profile_image_id", customer.getProfileImageId());
    }
}