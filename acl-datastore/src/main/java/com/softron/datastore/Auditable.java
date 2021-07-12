package com.softron.datastore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

    private static final long serialVersionUID = -6059233184137743126L;

    @CreatedBy
    @Column(name = "CREATED_BY")
    protected U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    @CreatedDate
    private Date createdAt;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    protected U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_AT")
    @LastModifiedDate
    private Date lastModifiedDate;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
