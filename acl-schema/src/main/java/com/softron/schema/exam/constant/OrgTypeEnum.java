/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.schema.exam.constant;

/**
 *
 * @author Mozahid
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mozahid
 */
public enum OrgTypeEnum {

    MINISTRY("Ministry/Division", 1L),
    DEPARTMENT("Department/Directorate", 2L),
    DIVISIONAL("Divisional/DeputyCommOffice", 3L),
    AUTONOMOUS("Autonomous/Corporation", 4L),
    COMMISSION("Commission", 5L);

    private String value;
    private Long id;

    static Map<Long, List<OrgTypeEnum>> map = new HashMap<>();
    static Map<String, List<OrgTypeEnum>> map1 = new HashMap<>();
    static Map<Long, OrgTypeEnum> mapObj = new HashMap<>();
    static public Map<String, OrgTypeEnum> mapObj1 = new HashMap<>();

    private OrgTypeEnum(String value, Long id) {
        this.value = value;
        this.id = id;
    }

    private OrgTypeEnum() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static List<OrgTypeEnum> getAllOrgType() {
        return Arrays.asList(OrgTypeEnum.values());
    }

    public static OrgTypeEnum getOrgTypeById(Long id) {
        return mapObj.get(id);
    }

    public static Map<Long, OrgTypeEnum> getOrgTypeAndId() {
        return OrgTypeEnum.getMap();
    }

    public static Map<Long, OrgTypeEnum> getMap() {
        Map<Long, OrgTypeEnum> map = new HashMap<>();
        for (OrgTypeEnum orgTypeEnum : OrgTypeEnum.values()) {
            map.put(orgTypeEnum.getId(), orgTypeEnum);
        }
        return map;
    }

    static {
        for (OrgTypeEnum orgTypeEnum : OrgTypeEnum.values()) {
            if (map.containsKey(orgTypeEnum.getId())) {
                map.get(orgTypeEnum.getId()).add(orgTypeEnum);
            } else {
                List<OrgTypeEnum> list = new ArrayList<>();
                list.add(orgTypeEnum);
                map.put(orgTypeEnum.getId(), list);
            }
            mapObj.put(orgTypeEnum.getId(), orgTypeEnum);
        }

        for (OrgTypeEnum orgTypeEnum : OrgTypeEnum.values()) {
            if (map1.containsKey(orgTypeEnum.name())) {
                map1.get(orgTypeEnum.name()).add(orgTypeEnum);
            } else {
                List<OrgTypeEnum> list = new ArrayList<>();
                list.add(orgTypeEnum);
                map1.put(orgTypeEnum.name(), list);
            }
            mapObj1.put(orgTypeEnum.name(), orgTypeEnum);
        }
    }

}
