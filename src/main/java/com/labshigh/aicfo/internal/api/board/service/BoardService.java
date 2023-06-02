package com.labshigh.aicfo.internal.api.board.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.board.dao.BoardDao;
import com.labshigh.aicfo.internal.api.board.dao.BoardFileDao;
import com.labshigh.aicfo.internal.api.board.dao.BoardRecommendHistoryDao;
import com.labshigh.aicfo.internal.api.board.mapper.BoardFileMapper;
import com.labshigh.aicfo.internal.api.board.mapper.BoardMapper;
import com.labshigh.aicfo.internal.api.board.mapper.BoardRecommendHistoryMapper;
import com.labshigh.aicfo.internal.api.board.model.request.BoardDetailRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardListRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardUpdateRecommendCountRequestModel;
import com.labshigh.aicfo.internal.api.board.model.response.BoardDetailResponseModel;
import com.labshigh.aicfo.internal.api.board.model.response.BoardFileResponseModel;
import com.labshigh.aicfo.internal.api.board.model.response.BoardRecommendHistoryResponseModel;
import com.labshigh.aicfo.internal.api.board.model.response.BoardResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private BoardMapper boardMapper;
  @Autowired
  private BoardFileMapper boardFileMapper;

  @Autowired
  private BoardRecommendHistoryMapper boardRecommendHistoryMapper;

  public ResponseListModel listBoard(BoardListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = boardMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;

    }

    List<BoardResponseModel> list = boardMapper.list(requestModel).stream()
        .map(this::convertBoardResponseModel).collect(
            Collectors.toList());

    result.setList(list);

    return result;
  }


  @Transactional
  public BoardDetailResponseModel detailBoard(BoardDetailRequestModel requestModel) {
    //조회수 업데이트
    BoardDao dao = BoardDao.builder()
        .uid(requestModel.getBoardUid())
        .boardTypeCommonCodeUid(requestModel.getBoardTypeCommonCodeUid())
        .counselKindCommonCodeUid(requestModel.getCounselKindCommonCodeUid())
        .build();

    BoardDao detail = boardMapper.detail(dao);

    if (detail == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (requestModel.isUpdateViewCount()) {
      updateViewCount(dao);
      detail.setViewCount(detail.getViewCount() + 1);
    }

    BoardDetailResponseModel result = convertBoardDetailResponseModel(detail);

    //첨부파일리스트
    BoardFileDao fileDao = BoardFileDao.builder()
        .boardUid(requestModel.getBoardUid())
        .build();
    List<BoardFileResponseModel> boardFileList = boardFileMapper.list(fileDao).stream()
        .map(this::convertBoardFileResponseModel).collect(
            Collectors.toList());
    result.setFileList(boardFileList);

    //추천
    BoardRecommendHistoryDao boardRecommendHistoryDao = boardRecommendHistoryMapper.select(
        BoardRecommendHistoryDao.builder().boardUid(requestModel.getBoardUid())
            .memberUid(requestModel.getMemberUid()).build());

    result.setRecommend(convertBoardRecommendHistoryResponseModel(boardRecommendHistoryDao));

    return result;

  }

  @Transactional
  public void updateRecommendCount(BoardUpdateRecommendCountRequestModel requestModel) {

    BoardRecommendHistoryDao boardRecommendHistoryDao = BoardRecommendHistoryDao.builder()
        .boardUid(requestModel.getBoardUid())
        .memberUid(requestModel.getMemberUid())
        .build();
    //히스토리 데이터 확인
    int count = boardRecommendHistoryMapper.count(boardRecommendHistoryDao);

    if (requestModel.getIncrement() == 1) {

      if (count == 1) {
        throw new ServiceException(Constants.MSG_SCAM_BOARD_OPTINON_DUPLICATE_ERROR);
      }
      //히스토리 등록
      boardRecommendHistoryMapper.insert(boardRecommendHistoryDao);
    } else {
      if (count == 0) {
        throw new ServiceException(Constants.MSG_NO_DATA);
      }
      //히스토리 삭제
      boardRecommendHistoryMapper.delete(boardRecommendHistoryDao);
    }

    BoardDao dao = BoardDao.builder().uid(requestModel.getBoardUid())
        .recommendCount(requestModel.getIncrement()).build();

    boardMapper.updateRecommendCount(dao);

  }

  private void updateViewCount(BoardDao dao) {
    boardMapper.updateViewCount(dao);
  }

  private BoardResponseModel convertBoardResponseModel(BoardDao dao) {
    return BoardResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .title(dao.getTitle())
        .content(dao.getContent())
        .uri(getFileUri(dao.getUri()))
        .viewCount(dao.getViewCount())
        .recommendCount(dao.getRecommendCount())
        .counselKindCommonCodeUid(dao.getCounselKindCommonCodeUid())
        .counselKindName(dao.getCounselKindName())
        .boardTypeCommonCodeUid(dao.getBoardTypeCommonCodeUid())
        .boardTypeName(dao.getBoardTypeName())
        .topFlag(dao.isTopFlag())
        .build();
  }

  private BoardDetailResponseModel convertBoardDetailResponseModel(BoardDao dao) {
    return BoardDetailResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .title(dao.getTitle())
        .content(dao.getContent())
        .uri(getFileUri(dao.getUri()))
        .viewCount(dao.getViewCount())
        .recommendCount(dao.getRecommendCount())
        .counselKindCommonCodeUid(dao.getCounselKindCommonCodeUid())
        .counselKindName(dao.getCounselKindName())
        .boardTypeCommonCodeUid(dao.getBoardTypeCommonCodeUid())
        .boardTypeName(dao.getBoardTypeName())
        .topFlag(dao.isTopFlag())
        .prevUid(dao.getPrevUid())
        .nextUid(dao.getNextUid())
        .build();
  }

  private BoardFileResponseModel convertBoardFileResponseModel(BoardFileDao dao) {
    return BoardFileResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .boardUid(dao.getBoardUid())
        .uri(getFileUri(dao.getUri()))
        .fileName(dao.getFileName())
        .build();
  }

  private BoardRecommendHistoryResponseModel convertBoardRecommendHistoryResponseModel(
      BoardRecommendHistoryDao dao) {
    if (dao == null) {
      return null;
    }
    return BoardRecommendHistoryResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .boardUid(dao.getBoardUid())
        .memberUid(dao.getMemberUid())
        .build();
  }


  private String getFileUri(String uri) {

    if (uri == null) {
      return null;
    }

    return "https://" + s3EndPoint + "/" + s3NftBucket + "/" + uri;
  }

}
