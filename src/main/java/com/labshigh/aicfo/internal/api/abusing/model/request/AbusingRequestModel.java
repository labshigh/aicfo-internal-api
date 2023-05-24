package com.labshigh.aicfo.internal.api.abusing.model.request;

import com.labshigh.aicfo.core.models.RequestPageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbusingRequestModel extends RequestPageModel {
    private String searchKey;
    private String searchValue;
    private long uid;
}
