package com.labshigh.aicfo.internal.api.abusing.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AbusingResponseModel {
    private long uid;
    private long memberUid;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime createdAt;

    private String email;
    private String nickname;
    private int abusingCnt;
    private String walletAddress;
    private boolean usedFlag;
    private String memo;
    private String adminName;
}
