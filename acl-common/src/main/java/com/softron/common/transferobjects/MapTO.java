package com.softron.common.transferobjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapTO {

    private Long id;

    private String name;

    private String nameBn;

    public MapTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public MapTO(Long id, String name, String nameBn) {
        super();
        this.id = id;
        this.name = name;
        this.nameBn = nameBn;
    }

}
