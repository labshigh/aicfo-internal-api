package com.labshigh.aicfo.internal.api.board.notice.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.board.notice.dao.NoticeDao;
import com.labshigh.aicfo.internal.api.board.notice.mapper.NoticeMapper;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeInsertRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeUpdateRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.response.NoticeResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Log4j2
@Service
public class NoticeService {
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper boardMapper) {
        this.noticeMapper = boardMapper;
    }

    public ResponseListModel list(NoticeRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();

        int totalCount = noticeMapper.count(requestModel);

        result.setCurrentPage(requestModel.getPage());
        result.setTotalCount(totalCount);
        result.setPageSize(requestModel.getSize());

        if (totalCount < 1) {
            //result.setList(Collections.emptyList());
            return result;
        }

        int offsetCnt = noticeMapper.offsetCount(requestModel);
        requestModel.getOffsetAndRowCount().setRowCount(offsetCnt);
        List<NoticeResponseModel> boardDaoList = noticeMapper.list(requestModel).stream()
                .map(this::convertBoardResponseModel).collect(Collectors.toList());
        result.setList(boardDaoList);
        return result;
    }

    public ResponseListModel listForAdmin(NoticeRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();

        int totalCount = noticeMapper.countForAdmin(requestModel);

        result.setCurrentPage(requestModel.getPage());
        result.setTotalCount(totalCount);
        result.setPageSize(requestModel.getSize());

        if (totalCount < 1) {
            result.setList(Collections.emptyList());
            return result;
        }

        List<NoticeResponseModel> boardDaoList = noticeMapper.listForAdmin(requestModel).stream()
                .map(this::convertBoardResponseModel).collect(Collectors.toList());
        result.setList(boardDaoList);
        return result;
    }

    public NoticeResponseModel detail(NoticeRequestModel requestModel) {
        ResponseModel result = new ResponseModel();
        noticeMapper.updateViewCount(requestModel);
        NoticeDao noticeDao = noticeMapper.detail(requestModel);
//        NoticeDao prevNoticeDao = noticeMapper.prev(requestModel);
//        if(null == prevNoticeDao) {
//            prevNoticeDao.setUid(null);
//            prevNoticeDao.setTitle(null);
//            prevNoticeDao.setViewCount(0);
//        }
//        NoticeDao nextNoticeDao = noticeMapper.next(requestModel);
//        if(null == nextNoticeDao) {
//            nextNoticeDao.setUid(null);
//            nextNoticeDao.setTitle(null);
//            nextNoticeDao.setViewCount(0);
//        }
//        BoardResponseModel boardDao = boardMapper.detail(requestModel);
//        result.setData(noticeMapper.detail(requestModel));
        return NoticeResponseModel.builder()
                .uid(noticeDao.getUid())
                .createdAt(noticeDao.getCreatedAt())
                .updatedAt(noticeDao.getUpdatedAt())
                .title(noticeDao.getTitle())
                .content(noticeDao.getContent())
                .noticeTypeUid(noticeDao.getNoticeTypeUid())
                .deleteFlag(noticeDao.isDeleteFlag())
                .usedFlag(noticeDao.isUsedFlag())
                .viewCount(noticeDao.getViewCount())
                .nameKr(noticeDao.getNameKr())
                .prevUid(noticeDao.getPrevUid())
                .prevTitle(noticeDao.getPrevTitle())
                .prevViewCount(noticeDao.getPrevViewCount())
                .nextUid(noticeDao.getNextUid())
                .nextTitle(noticeDao.getNextTitle())
                .nextViewCount(noticeDao.getNextViewCount())
                .isUpdate(noticeDao.isUpdate())
                .popupFlag(noticeDao.isPopupFlag())
                .sticky(noticeDao.isSticky())
                .stickySort(noticeDao.getStickySort())
                .build();
//        return result;
        //BoardDao
    }

    private NoticeResponseModel convertBoardResponseModel(NoticeDao dao) {
        return NoticeResponseModel.builder()
                .uid(dao.getUid())
                .title(dao.getTitle())
                .content(dao.getContent())
                .createdAt(dao.getCreatedAt())
                .updatedAt(dao.getUpdatedAt())
                .deleteFlag(dao.isDeleteFlag())
                .usedFlag(dao.isUsedFlag())
                .viewCount(dao.getViewCount())
                .noticeTypeUid(dao.getNoticeTypeUid())
                .nameKr(dao.getNameKr())
                .sticky(dao.isSticky())
                .stickySort(dao.getStickySort())
                .popupFlag(dao.isPopupFlag())
                .isUpdate(dao.isUpdate())
                .build();
    }

    @Transactional
    public ResponseModel insert(NoticeInsertRequestModel requestModel) {
        noticeMapper.insert(requestModel);
        ResponseModel result = new ResponseModel();
        result.setData(requestModel.getUid());
        return result;
    }

    @Transactional
    public ResponseModel update(NoticeUpdateRequestModel requestModel) {
        int num = noticeMapper.update(requestModel);
        ResponseModel result = new ResponseModel();
        result.setData(requestModel.getUid());
        return result;
    }

    public ResponseListModel noticeTypeList() {
        ResponseListModel result = new ResponseListModel();


        List<NoticeResponseModel> boardDaoList = noticeMapper.noticeTypeList().stream()
                .map(this::convertBoardResponseModel).collect(Collectors.toList());
        result.setList(boardDaoList);
        return result;
    }

    public ResponseListModel popupNotice() {
        ResponseListModel result = new ResponseListModel();


        List<NoticeResponseModel> boardDaoList = noticeMapper.popupList().stream()
                .map(this::convertBoardResponseModel).collect(Collectors.toList());
        result.setList(boardDaoList);
        return result;
    }

}
