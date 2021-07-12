package com.softron.reporting.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OR_REPORT_RECEIVERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReportReceiver extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 7726321767532915962L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "REPORT_ID")
    private String reportId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "IS_SUBMITTED")
    private boolean submitted;
    
    @Column(name = "IS_ACKNOWLEDGER")
    private boolean acknowledger;
    
    @Column(name = "SUBMISSION_DATE")
    private LocalDate submittedOn;

}
