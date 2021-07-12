package com.softron.common.entity;

import com.softron.core.constants.CommonTableNameConstants;
import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = CommonTableNameConstants.USER_MAPPING_TABLE, uniqueConstraints = @UniqueConstraint(columnNames =
    { "MODULE_ID", "USER_ID", "ORG_ID" }))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class OrgUsersMapping extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 6901635703861167822L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODULE_ID")
    private Long moduleId;
    
    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "USER_ID")
    private String userId;
}
