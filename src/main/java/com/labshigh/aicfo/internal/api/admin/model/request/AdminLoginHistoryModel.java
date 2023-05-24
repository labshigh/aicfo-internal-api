package com.labshigh.aicfo.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginHistoryModel {
    String ip;
    Long adminUid;
    boolean successFlag;
    String adminId;
}
