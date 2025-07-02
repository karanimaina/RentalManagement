package com.students.tenant_management_systemt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@MappedSuperclass
@NoArgsConstructor
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private Instant createdOn;
    private boolean softDelete;
    private Boolean active;

    @PrePersist
    public void addData() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZoneId zoneId = ZoneId.of("Africa/Nairobi");
        ZonedDateTime kenya = zonedDateTime.withZoneSameInstant(zoneId);
        this.createdOn = kenya.toInstant();
        this.softDelete = false;
    }
}
