package com.softron.schema.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CANDIDATE")
public class Candidate implements Serializable {

	private static final long serialVersionUID = -8696916386274990433L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "FATHER_NAME")
	private String fatherName;
	@Column(name = "MOTHER_NAME")
	private String motherName;
	@Column(name = "ROLL_NO", nullable = false)
	private String rollNo;
	@JsonIgnore
	@JoinColumn(name = "EXAMSESSION_ID", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ExamSession examSession;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public ExamSession getExamSession() {
		return examSession;
	}

	public void setExamSession(ExamSession examSession) {
		this.examSession = examSession;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

}
