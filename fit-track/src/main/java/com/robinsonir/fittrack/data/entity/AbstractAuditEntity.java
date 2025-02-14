package com.robinsonir.fittrack.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity extends AbstractEntity {

    // TODO: implement auditing fields necessary for modification data
    @CreatedDate
    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private OffsetDateTime lastModifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
}
