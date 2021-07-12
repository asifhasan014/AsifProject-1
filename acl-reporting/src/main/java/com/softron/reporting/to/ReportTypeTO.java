package com.softron.reporting.to;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportTypeTO {

    private Long id;

    private String name;

    private boolean active;

}
