package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerDTOMapper implements Function<CustomerEntity, CustomerDTO> {

    private static List<WorkoutDTO> mapWorkoutsToDTOs(List<WorkoutEntity> workouts) {
        return workouts.stream()
                .map(workout -> new WorkoutDTO(
                        workout.getId(),
                        workout.getCustomer().getId(),
                        workout.getWorkoutType(),
                        workout.getExercises(),
                        workout.getCalories(),
                        workout.getDurationMinutes(),
                        workout.getVolume(),
                        workout.getWorkoutDate()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO apply(CustomerEntity customerEntity) {
        return new CustomerDTO(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getGender(),
                customerEntity.getAge(),
                customerEntity.getWeight(),
                customerEntity.getHeight(),
                customerEntity.getWeightGoal(),
                customerEntity.getActivity(),
                customerEntity.getBodyFat(),
                customerEntity.getMemberSince(),
                customerEntity.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                mapWorkoutsToDTOs(customerEntity.getCustomerWorkouts()),
                customerEntity.getUsername(),
                customerEntity.getProfileImageId()
        );
    }
}
