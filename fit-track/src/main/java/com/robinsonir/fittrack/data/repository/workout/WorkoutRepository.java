package com.robinsonir.fittrack.data.repository.workout;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.entity.workout.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {

    @Query("select w from WorkoutEntity w")
    List<WorkoutEntity> findAllWorkouts();

    @Query("select w from WorkoutEntity w where w.customer.id = ?1")
    List<WorkoutEntity> findAllWorkoutsByCustomerId(Long customerId);

    @Query("select w from WorkoutEntity w where w.id = ?1")
    Optional<WorkoutEntity> findWorkoutById(Long id);

    boolean existsWorkoutEntityByCustomer(CustomerEntity customer);
}
