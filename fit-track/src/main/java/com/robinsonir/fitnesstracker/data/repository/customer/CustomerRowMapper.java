package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRowMapper implements RowMapper<CustomerEntity> {
    @Override
    public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setAge(rs.getInt("age"));
        customer.setGender(Gender.valueOf(rs.getString("gender")));
        customer.setProfileImageId(rs.getString("profile_image_id"));

        List<WorkoutEntity> workouts;

        if (rs.getLong("workout_id") > 0 && !rs.wasNull()) {
            // Initialize list to store WorkoutEntity objects
            workouts = new ArrayList<>();

            // Iterate over the result set to retrieve WorkoutEntity data
            do {
                Long workoutId = rs.getLong("workout_id");
                // Check if a workout exists
                WorkoutEntity workout = new WorkoutEntity();
                workout.setId(workoutId);
                workout.setWorkoutType(rs.getString("workout_type"));
                workout.setCalories(rs.getInt("calories"));
                workout.setDurationMinutes(rs.getInt("duration_minutes"));
                // Add the workout to the list
                workouts.add(workout);
            } while (rs.next());

            // Set the list of workouts to the customer
            customer.setCustomerWorkouts(workouts);
        } else {
            customer.setCustomerWorkouts(new ArrayList<>());
        }

        return customer;
    }
}
