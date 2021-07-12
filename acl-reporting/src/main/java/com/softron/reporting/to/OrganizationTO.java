package com.softron.reporting.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationTO {

    private Long id;

    private String nameEng;

    private String nameBang;

    private String webAddress;

    private String telephone;

    private String address;

    private String email;

    private String remarks;

    private boolean active;

    private Long orgType;

    private Long parentId;

    private String parentOrgName;

    private String orgTypeName;

}
