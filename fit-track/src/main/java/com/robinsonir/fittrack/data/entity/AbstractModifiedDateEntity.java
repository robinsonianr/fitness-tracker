package com.robinsonir.fittrack.data.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Audited(withModifiedFlag = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModifiedDateEntity extends AbstractAuditEntity implements UserDetails {

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private OffsetDateTime lastModifiedDate;


    @PrePersist
    @PreUpdate
    public void setLastModifiedDate() {
        this.lastModifiedDate = OffsetDateTime.now();
    }
}
