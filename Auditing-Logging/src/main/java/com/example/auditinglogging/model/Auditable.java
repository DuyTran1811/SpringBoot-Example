package com.example.auditinglogging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {
    @Column(name = "created_date", updatable = false)
    @Temporal(TIMESTAMP)
    @CreatedDate
    @JsonIgnore
    protected Date createdDate;


    @Column(name = "modified_date")
    @LastModifiedDate
    @JsonIgnore
    @Temporal(TIMESTAMP)
    protected Date modifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    protected T createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    protected T modifiedBy;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate == null) {
            createdDate = new Date();
        }
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        if (!(modifiedDate == null)) {
            modifiedDate = new Date();
        }
        this.modifiedDate = modifiedDate;
    }

    public T getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(T createdBy) {
        this.createdBy = createdBy;
    }

    public T getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(T modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
