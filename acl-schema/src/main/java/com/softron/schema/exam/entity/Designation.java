package com.softron.schema.exam.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DESIGNATION")
public class Designation implements Serializable {

    private static final long serialVersionUID = -8696916386274990433L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "COUNT_NUMBER_KEYSTROKE")
    private String countNumberKeystroke;
    @Column(name = "TYPING_SPEED_BANGLA", nullable = true)
    private String typingSpeedBangla;
    @Column(name = "EXAM_DURATION_BANGLA", nullable = true)
    private String examDurationBangla;
    @Column(name = "MINIMUM_PERCENTAGE_BANGLA", nullable = true)
    private String minimumPercentageBangla;
    @Column(name = "AVERAGE_PERCENTAGE", nullable = true)
    private String averagePercentage;
    @Column(name = "TYPING_SPEED_ENGLISH", nullable = true)
    private String typingSpeedEnglish;
    @Column(name = "EXAM_DURATION_ENGLISH", nullable = true)
    private String examDurationEnglish;
    @Column(name = "MINIMUM_PERCENTAGE_ENGLISH", nullable = true)
    private String minimumPercentageEnglish;
    @Column(name = "MISTAKE_PERCENTAGE", nullable = true)
    private String mistakePercentage;

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

    public String getCountNumberKeystroke() {
        return countNumberKeystroke;
    }

    public void setCountNumberKeystroke(String countNumberKeystroke) {
        this.countNumberKeystroke = countNumberKeystroke;
    }

    public String getTypingSpeedBangla() {
        return typingSpeedBangla;
    }

    public void setTypingSpeedBangla(String typingSpeedBangla) {
        this.typingSpeedBangla = typingSpeedBangla;
    }

    public String getExamDurationBangla() {
        return examDurationBangla;
    }

    public void setExamDurationBangla(String examDurationBangla) {
        this.examDurationBangla = examDurationBangla;
    }

    public String getMinimumPercentageBangla() {
        return minimumPercentageBangla;
    }

    public void setMinimumPercentageBangla(String minimumPercentageBangla) {
        this.minimumPercentageBangla = minimumPercentageBangla;
    }

    public String getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(String averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    public String getTypingSpeedEnglish() {
        return typingSpeedEnglish;
    }

    public void setTypingSpeedEnglish(String typingSpeedEnglish) {
        this.typingSpeedEnglish = typingSpeedEnglish;
    }

    public String getExamDurationEnglish() {
        return examDurationEnglish;
    }

    public void setExamDurationEnglish(String examDurationEnglish) {
        this.examDurationEnglish = examDurationEnglish;
    }

    public String getMinimumPercentageEnglish() {
        return minimumPercentageEnglish;
    }

    public void setMinimumPercentageEnglish(String minimumPercentageEnglish) {
        this.minimumPercentageEnglish = minimumPercentageEnglish;
    }

    public String getMistakePercentage() {
        return mistakePercentage;
    }

    public void setMistakePercentage(String mistakePercentage) {
        this.mistakePercentage = mistakePercentage;
    }
   
}
