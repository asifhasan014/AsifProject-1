package com.softron.schema.admin.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PASSWORD_RESET_TOKENS")
@Data
public class PasswordResetToken implements Serializable {

	private static final long serialVersionUID = -5252711305055259545L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "USER_ID")
	private UserEntity user;

	@Column(nullable = false)
	private Date expiryDate;
	
	@Column(nullable = false)
	private boolean tokenRedeemed;

	public void setExpiryDate(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiryDate = now.getTime();
	}

	public boolean isExpired() {
		return new Date().after(this.expiryDate);
	}

}
