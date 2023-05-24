package com.labshigh.aicfo.internal.api.board.notice.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeUpdateRequestModel {
    private long uid;
    private String title;
    private String content;
    private long noticeTypeUid;
    private boolean usedFlag;
    private boolean deleteFlag;
    private boolean sticky;
    private int stickySort;
    private boolean popupFlag;
}
