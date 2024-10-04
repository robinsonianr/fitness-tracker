package com.robinsonir.fitnesstracker.data.entity.workout;

import com.robinsonir.fitnesstracker.data.entity.AbstractEntity;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "fit_tracker", name = "workout")
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutEntity extends AbstractEntity {

    @Column(name = "workout_type")
    private String workoutType;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "workout_date")
    private OffsetDateTime workoutDate;

    @Column(name = "exercises")
    private Integer exercises;

    @Column(name = "volume")
    private Integer volume;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

}
