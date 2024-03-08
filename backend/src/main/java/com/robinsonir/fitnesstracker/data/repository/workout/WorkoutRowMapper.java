package com.robinsonir.fitnesstracker.data.repository.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorkoutRowMapper implements RowMapper<WorkoutEntity> {
    @Override
    public WorkoutEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkoutEntity workout = new WorkoutEntity();
        workout.setId(rs.getLong("id"));
        workout.setWorkoutType(rs.getString("workout_type"));
        workout.setCalories(rs.getInt("calories"));
        workout.setDurationMinutes(rs.getInt("duration_minutes"));

        // Extract the customer reference ID from the result set
        Long customerId = rs.getLong("customer_id");
        // Create a placeholder CustomerEntity object with the reference ID
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        // Set the CustomerEntity object in the WorkoutEntity
        workout.setCustomer(customer);

        return workout;
    }
}
