package com.robinsonir.fitnesstracker.data.entity.workout;

import com.robinsonir.fitnesstracker.data.entity.AbstractEntity;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "fit_tracker", name = "workout")
public class WorkoutEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "workout_type")
    private String workoutType;


}
