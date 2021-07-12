package com.softron.reporting.entity;

import com.softron.datastore.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OR_ATTACHMENTS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReportAttachments extends Auditable<String> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2305056141841275359L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ATTACHMENT_NAME", nullable = false)
    private String attachmentName;

    @Column(name = "ATTACHED_FILE", nullable = false)
    private String attachmentPath;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", nullable = false)
    private Report report;

    @Column(name = "ORG_ID", nullable = false)
    private Long orgId;

    @Column(name = "IS_DRAFT")
    private boolean draft;

    @Version
    private int version;

}
