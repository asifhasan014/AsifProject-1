package com.softron.schema.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.datastore.Auditable;

import com.softron.schema.admin.entity.masterdata.QualityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "MODULE_MENUS")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 4788873563154121644L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "LINK")
	private String link;

	@Column(name = "IS_HOME")
	private boolean home;

	@Column(name = "SORT_ORDER")
	private int sortOrder;

	@Column(name = "EXTERNAL", nullable = false)
	private boolean external;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active;
	
	@Column(name = "HIDDEN", nullable = false)
	private boolean hidden;

	@Column(name = "PARENT_MENU_ID")
	private Long parentId;

	@Column(name = "MODULE_ID")
	private Long moduleId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_id")
	private QualityType detail;
}
