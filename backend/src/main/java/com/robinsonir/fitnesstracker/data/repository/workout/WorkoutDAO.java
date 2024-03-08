package com.robinsonir.fitnesstracker.data.repository.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;

import java.util.List;
import java.util.Optional;

public interface WorkoutDAO {
    List<WorkoutEntity> selectAllWorkouts();

    Optional<WorkoutEntity> selectWorkoutById(Long id);

    void insertWorkout(WorkoutEntity workoutEntity);

    boolean existsWorkoutEntityById(Long id);

    boolean existsWorkoutEntityByCustomer(CustomerEntity customer);


    void deleteWorkoutById(Long id);
}
