package com.softron.schema.admin.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * User Privilege entity.
 *
 * @author Mozahid
 * @version 1.0
 */
@Entity
@Table(name = "USER_PRIVILEGES")
public class Privilege extends Auditable<String> implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4865992894467088116L;

    /**
     * Privilege id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "PRIVILEGE_ID")
    private Long id;

    /**
     * Privilege name.
     */
    @Column(name = "PRIVILEGE_NAME")
    private String name;

    /**
     * 
     * Constructor.
     *
     */
    public Privilege() {
        super();
    }

    /**
     * 
     * Constructor.
     *
     * @param name
     */
    public Privilege(String name) {
        super();
        this.name = name;
    }

    /**
     * 
     * Getter of {@link #id}.
     * 
     * @return {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * Setter of {@link #id}.
     * 
     * @param id
     *            to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * Getter of {@link #name}.
     * 
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Setter of {@link #name}.
     * 
     * @param name
     *            to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Privilege other = (Privilege) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
