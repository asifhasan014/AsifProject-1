package com.softron.reporting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softron.core.constants.AppConstants;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportTO {

    @JsonProperty(value = "id")
    private String reportId;

    @NotNull(message = "Report type is mandatory.")
    private Long type;

    private String typeText;

    @NotEmpty(message = "Subject can not be empty.")
    @Size(min = 1, max = 200, message = "Subject should be between 1 to 200 characters.")
    private String subject;

    @Size(max = 400, message = "Subject can not be more than 200 characters.")
    private String remarks;

    @JsonProperty(value = "subDate")
    @JsonFormat(pattern = AppConstants.DATE_FORMAT)
    private LocalDate lastDateOfSubmission;

    private String memoNo;

    @JsonProperty(value = "memoDt")
    private String memoDate;

    private Long sender;

    private String senderName;

    private List<Long> recipients;

    private List<Long> acks;

    private boolean draft;

    private boolean sent;
    
    private boolean completed;
    
    private boolean onlyAck;

}
