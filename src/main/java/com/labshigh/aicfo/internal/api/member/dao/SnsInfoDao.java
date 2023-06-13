package com.labshigh.aicfo.internal.api.member.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SnsInfoDao {
    private long uid;
    private long memberUid;
    private String snsName;
    private String snsType;
    private String snsId;
    private String snsProfile;
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime snsConnectDate;

}
