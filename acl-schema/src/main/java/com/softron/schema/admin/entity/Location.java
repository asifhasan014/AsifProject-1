package com.softron.schema.admin.entity;

import com.softron.datastore.Auditable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATIONS")
public class Location extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 3959998697771773513L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME_ENGLISH", nullable = false)
    private String nameEng;

    @Column(name = "NAME_BANGLA", nullable = false)
    private String nameBang;

    @Column(name = "ABBR_ENGLISH")
    private String abbrEng;

    @Column(name = "ABBR_BANGLA")
    private String abbrBang;

    @Column(name = "KEYWORD")
    private String keyWord;

    @Column(name = "SERIAL_NO")
    private int serialNo;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "PARENT_ID")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameBang() {
        return nameBang;
    }

    public void setNameBang(String nameBang) {
        this.nameBang = nameBang;
    }

    public String getAbbrEng() {
        return abbrEng;
    }

    public void setAbbrEng(String abbrEng) {
        this.abbrEng = abbrEng;
    }

    public String getAbbrBang() {
        return abbrBang;
    }

    public void setAbbrBang(String abbrBang) {
        this.abbrBang = abbrBang;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
