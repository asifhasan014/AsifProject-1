package com.softron.reporting.entity;

import com.softron.datastore.Auditable;
import com.softron.datastore.generator.StringPrefixedSequenceIdGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OR_REPORTS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Report extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 3000782839911277490L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportid_seq")
    @GenericGenerator(name = "reportid_seq", strategy = "com.softron.datastore.generator.StringPrefixedSequenceIdGenerator", parameters =
        { @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RP"),
                @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    @Column(name = "REPORT_ID", nullable = false, length = 255)
    private String reportId;

    @Column(name = "REPORT_TYPE", nullable = false)
    private Long type;

    @Column(name = "SUBJECT", nullable = false, length = 200)
    private String subject;

    @Column(name = "REMARKS", length = 400)
    private String remarks;

    @Column(name = "LAST_DATE_OF_SUBMISSION")
    private LocalDate lastDateOfSubmission;

    @Column(name = "MEMO_NO", length = 200)
    private String memoNo;

    @Column(name = "MEMO_DATE")
    private LocalDate memoDate;

    @Column(name = "IS_DRAFT")
    private boolean draft = true;

    @Column(name = "IS_COMPLETED")
    private boolean completed = false;

    @Column(name = "SENDER_ORG_ID", nullable = false, updatable = false)
    private Long sender;

    @ManyToMany
    @JoinColumn(name = "ID")
    private List<ReportOrganization> recipients;

    @ManyToMany
    @JoinColumn(name = "ID")
    private List<ReportOrganization> acks;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportAttachments> attachments;

    @Version
    private int version;

}
