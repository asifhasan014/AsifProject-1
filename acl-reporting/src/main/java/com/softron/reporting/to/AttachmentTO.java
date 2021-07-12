package com.softron.reporting.to;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentTO {

    private Long id;

    private String attachmentName;

    private String attachmentPath;
    
    private boolean draft;

}
