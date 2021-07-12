/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mozahid
 */
public enum OfficeTypeEnum {

    MINISTRY("Ministry", 1L, OrgTypeEnum.MINISTRY.name()),
    DIVISION("Division", 1L, OrgTypeEnum.MINISTRY.name()),
    DEPARTMENT("Department", 2L, OrgTypeEnum.DEPARTMENT.name()),
    DIRECTORATE("Directorate", 2L, OrgTypeEnum.DEPARTMENT.name()),
    DIVISIONAL("Divisional", 3L, OrgTypeEnum.DIVISIONAL.name()),
    DEPUTYCOMMOFFICE("DeputyCommissionOffice", 3L, OrgTypeEnum.DIVISIONAL.name()),
    AUTONOMOUS("Autonomous", 4L, OrgTypeEnum.AUTONOMOUS.name()),
    CORPORATION("Corporation", 4L, OrgTypeEnum.AUTONOMOUS.name()),
    COMMISSION("Commission", 5L, OrgTypeEnum.COMMISSION.name());

    private String value;

    private Long id;

    private String orgName;

    private OfficeTypeEnum() {
    }

    private OfficeTypeEnum(String value, Long id, String orgName) {
        this.value = value;
        this.id = id;
        this.orgName = orgName;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public static List<OfficeTypeEnum> getAllOfficeType() {
        return Arrays.asList(OfficeTypeEnum.values());
    }
    static Map<Long, List<OfficeTypeEnum>> map = new HashMap<>();
    static Map<String, List<OfficeTypeEnum>> map1 = new HashMap<>();

    public static List<OfficeTypeEnum> getAllOfficeTypeById(Long id) {
        return map.get(id);
    }

    public static List<OfficeTypeEnum> getAllOfficeTypeByName(String orgName) {
        return map1.get(orgName);
    }

    static {
        for (OfficeTypeEnum officeTypeEnum : OfficeTypeEnum.values()) {
            if (map.containsKey(officeTypeEnum.getId())) {
                map.get(officeTypeEnum.getId()).add(officeTypeEnum);
            } else {
                List<OfficeTypeEnum> list = new ArrayList<>();
                list.add(officeTypeEnum);
                map.put(officeTypeEnum.getId(), list);
            }
        }

        for (OfficeTypeEnum officeTypeEnum : OfficeTypeEnum.values()) {
            if (map1.containsKey(officeTypeEnum.getOrgName())) {
                map1.get(officeTypeEnum.getOrgName()).add(officeTypeEnum);
            } else {
                List<OfficeTypeEnum> list = new ArrayList<>();
                list.add(officeTypeEnum);
                map1.put(officeTypeEnum.getOrgName(), list);
            }
        }
    }
    
    public static OfficeTypeEnum from(String value) {
    	for(OfficeTypeEnum type: OfficeTypeEnum.values()) {
    		if(type.getOrgName().equals(value)) {
    			return type;
    		}
    	}
    	throw new IllegalArgumentException("Invalid OfficeTypeEnum: " + value);
    }

}
