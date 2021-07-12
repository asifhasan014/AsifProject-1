package com.softron.schema.exam.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CANDIDATE_EXAM_DETAIL")
public class CandidateExamDetail implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Candidate candidate;
    @JoinColumn(name = "EXAM_ID", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exam exam;
    @Column(name = "KEYBOARD")
    private String keyboard;
    @Column(name = "WORDS_PER_MINUTE", length = 500)
    private String wordsPerMinute;
    @Column(name = "WORD_COUNT", length = 500)
    private String wordCount;
    @Column(name = "ERRORS", length = 500)
    private String errors;
    @Column(name = "pass")
    private Boolean pass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getWordsPerMinute() {
        return wordsPerMinute;
    }

    public void setWordsPerMinute(String wordsPerMinute) {
        this.wordsPerMinute = wordsPerMinute;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

}
