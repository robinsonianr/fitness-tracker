package com.robinsonir.fittrack.api;

import com.robinsonir.fittrack.data.repository.workout.Workout;
import com.robinsonir.fittrack.data.service.workout.WorkoutCreationRequest;
import com.robinsonir.fittrack.data.service.workout.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("{workoutId}")
    public Workout getWorkout(@PathVariable("workoutId") Long workoutId) {
        return workoutService.getWorkout(workoutId);
    }

    @GetMapping("log/{customerId}")
    public List<Workout> getAllWorkoutsCustomerById(@PathVariable("customerId") Long customerId) {
        return workoutService.getAllWorkoutsByCustomerId(customerId);
    }

    @PostMapping
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreationRequest request) {
        workoutService.addWorkout(request);
        return ResponseEntity.ok().build();

    }
}
