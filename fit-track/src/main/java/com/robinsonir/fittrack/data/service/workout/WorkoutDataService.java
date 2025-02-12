package com.robinsonir.fittrack.data.service.workout;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.repository.workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutDataService {
    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutDataService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public boolean existsByCustomer(CustomerEntity customer) {
        return this.workoutRepository.existsWorkoutEntityByCustomer(customer);
    }
}
