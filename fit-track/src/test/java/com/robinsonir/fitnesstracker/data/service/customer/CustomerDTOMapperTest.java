package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRowMapper;
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
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("John");
        when(resultSet.getString("email")).thenReturn("john@example.com");
        when(resultSet.getInt("age")).thenReturn(30);
        when(resultSet.getString("gender")).thenReturn("MALE"); // Assuming it's stored as a string
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("profile_image_id")).thenReturn("profile_image_id");

        // Create a CustomerRowMapper
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        // Map the ResultSet to a Customer object
        CustomerEntity customerEntity = customerRowMapper.mapRow(resultSet, 0);

        // Assert that the mapped Customer object has the expected values
        assert customerEntity != null;
        assertEquals(1L, customerEntity.getId());
        assertEquals("John", customerEntity.getName());
        assertEquals("john@example.com", customerEntity.getEmail());
        assertEquals(30, customerEntity.getAge());
        assertEquals(Gender.MALE, customerEntity.getGender()); // Assuming Gender is an enum
        assertEquals("password", customerEntity.getPassword());
        assertEquals("profile_image_id", customerEntity.getProfileImageId());
    }
}