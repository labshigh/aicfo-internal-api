package com.labshigh.aicfo.internal.api.board.notice.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NoticeResponseModel
{


    private long uid;
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime createdAt;
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private long noticeTypeUid;
    private boolean deleteFlag;
    private boolean usedFlag;
    private int viewCount;
    private String nameKr;
    private boolean sticky;
    private int stickySort;
    private Long prevUid;
    private String prevTitle;
    private int prevViewCount;
    private Long nextUid;
    private String nextTitle;
    private int nextViewCount;
    private boolean isUpdate;
    private boolean popupFlag;
}
