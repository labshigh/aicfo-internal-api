package com.labshigh.aicfo.internal.api.abusing.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbusingRestRequestModel {
    private long uid;
    private long memberUid;
    private boolean usedFlag;
    private String memo;
    private long adminUid;

}
