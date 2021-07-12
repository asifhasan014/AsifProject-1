package com.softron.reporting.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OR_REPORT_TYPES")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReportType extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 7726321767532915962L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @NotEmpty(message = "Report type should not be empty.")
    @Size(min = 1, max = 200, message = "Report type should be between 1 to 200 characters.")
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Version
    private int version;

}
