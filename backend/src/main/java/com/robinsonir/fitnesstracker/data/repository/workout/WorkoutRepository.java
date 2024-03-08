package com.robinsonir.fitnesstracker.data.repository.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {

    boolean existsWorkoutEntityByCustomer(CustomerEntity customer);
    boolean existsWorkoutEntitiesById(Long id);

    Optional<WorkoutEntity> findWorkoutEntityById(Long id);
}
