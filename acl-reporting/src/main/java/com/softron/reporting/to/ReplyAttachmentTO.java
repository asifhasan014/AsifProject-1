package com.softron.reporting.to;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyAttachmentTO {

    private Long id;

    private Boolean submitted;

    private String org;
    
    private Long orgId;

    private LocalDate submittedOn;

    private String name;

    private String path;
    
    private Boolean multiple;

}
