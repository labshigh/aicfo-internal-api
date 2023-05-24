package com.labshigh.aicfo.internal.api.board.notice.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDao {

    private Long uid;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime createdAt;
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private long noticeTypeUid;
    private boolean deleteFlag;
    private boolean usedFlag;
    private int viewCount;
    private String nameKr;
    private String imageContent;
    private String sourceUrl;
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
