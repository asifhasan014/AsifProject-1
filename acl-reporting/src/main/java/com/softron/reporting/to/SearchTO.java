package com.softron.reporting.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTO {

    private String reportId;

    private Long type;
    
    private String folder;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @JsonIgnore
    private String userName;

    @JsonIgnore
    private Long orgId;

}
