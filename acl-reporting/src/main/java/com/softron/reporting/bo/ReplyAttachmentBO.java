package com.softron.reporting.bo;

import java.time.LocalDate;

public interface ReplyAttachmentBO {

    Long getId();

    Boolean getSubmitted();

    String getOrg();
    
    Long getOrgId();

    LocalDate getSubmittedOn();

    String getName();

    String getPath();

}
