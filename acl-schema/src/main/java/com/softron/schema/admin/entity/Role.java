package com.softron.schema.admin.entity;

import com.softron.datastore.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 8645976870237473738L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "ROLE_ID")
	private Long id;

	@Column(name = "ROLE_NAME")
	private String name;

	@Column(name = "MODULE_ID")
	private Long moduleId;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Menu> permittedMenus;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active;

	public Role() {
		super();
	}

	public Role(String name) {
		super();
		this.name = name;
	}

}
