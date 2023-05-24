package com.labshigh.aicfo.internal.api.board.notice.model.request;

import com.labshigh.aicfo.core.models.RequestPageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequestModel extends RequestPageModel {
    private String searchKey;
    private String searchValue;

    private long uid;
}
