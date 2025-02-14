package com.robinsonir.fittrack.audit;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(schema = "fit_tracker", name = "revinfo")
@RevisionEntity(FitTrackRevisionListener.class)
public class FitTrackRevisionEntity {

    // TODO: add fields rev, revtstmp, and username
    @Id
    @SequenceGenerator(name = "sequence_gen", sequenceName = "fit_tracker.hibernate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_gen")
    @RevisionNumber
    @Column(name = "rev")
    private Long id;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private Long timestamp;

    @Column(name = "username")
    private String username;

    @Transient
    public Date getRevisionDate() {
        return new Date(this.timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FitTrackRevisionEntity that = (FitTrackRevisionEntity) o;
        return id.equals(that.id) &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp);
    }

    @Override
    public String toString() {
        return "DefaultRevisionEntity(id = " + this.id + ", revisionDate = " + DateFormat.getDateTimeInstance().format(this.getRevisionDate()) + ")";
    }
}
