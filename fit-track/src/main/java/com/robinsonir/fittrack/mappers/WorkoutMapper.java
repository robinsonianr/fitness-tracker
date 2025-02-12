package com.robinsonir.fittrack.mappers;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.entity.workout.WorkoutEntity;
import com.robinsonir.fittrack.data.repository.workout.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = FitTrackMapperConfig.class)
public interface WorkoutMapper {

    @Mapping(target = "customerId", source = "customer")
    Workout convertWorkoutEntityToWorkout(WorkoutEntity workoutEntity);


    @Mapping(target = "customerId", source = "customer")
    List<Workout> convertWorkoutEntityListToWorkoutList(List<WorkoutEntity> workoutEntities);


    default Long mapCustomerId(CustomerEntity customer) {
        return customer.getId();
    }
}

