package com.robinsonir.fitnesstracker.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;
}
