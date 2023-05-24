package com.labshigh.aicfo.internal.api.board.notice.mapper;

import com.labshigh.aicfo.internal.api.board.notice.dao.NoticeDao;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeInsertRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeUpdateRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoticeMapper {
    int count(NoticeRequestModel boardRequestModel);
    int countForAdmin(NoticeRequestModel boardRequestModel);
    int offsetCount(NoticeRequestModel boardRequestModel);
    List<NoticeDao> list(NoticeRequestModel boardRequestModel);
    List<NoticeDao> listForAdmin(NoticeRequestModel boardRequestModel);
    NoticeDao detail(NoticeRequestModel boardRequestModel);
    NoticeDao prev(NoticeRequestModel boardRequestModel);
    NoticeDao next(NoticeRequestModel boardRequestModel);
    int insert(NoticeInsertRequestModel model);
    int update(NoticeUpdateRequestModel model);
    List<NoticeDao> noticeTypeList();
    void updateViewCount(NoticeRequestModel boardRequestModel);
    List<NoticeDao> popupList();
}
