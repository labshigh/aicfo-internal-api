package com.labshigh.aicfo.internal.api.board.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.board.dao.BoardDao;
import com.labshigh.aicfo.internal.api.board.dao.BoardReplyDao;
import com.labshigh.aicfo.internal.api.board.mapper.BoardMapper;
import com.labshigh.aicfo.internal.api.board.mapper.BoardReplyMapper;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyDeleteRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyInsertRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyListRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyUpdateRequestModel;
import com.labshigh.aicfo.internal.api.board.model.response.BoardReplyResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class BoardReplyService {

  @Autowired
  private BoardReplyMapper boardReplyMapper;
  @Autowired
  private BoardMapper boardMapper;

  public ResponseListModel listBoardReply(BoardReplyListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = boardReplyMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<BoardReplyResponseModel> list = boardReplyMapper.list(requestModel).stream()
        .map(this::convertBoardReplyResponseModel).collect(
            Collectors.toList());

    result.setList(list);

    return result;

  }

  @Transactional
  public BoardReplyResponseModel insert(BoardReplyInsertRequestModel requestModel) {

    //게시판 있는지 체크
    BoardDao board = boardMapper.detail(BoardDao.builder().uid(requestModel.getBoardUid()).build());
    if (board == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    BoardReplyDao dao = BoardReplyDao.builder()
        .content(requestModel.getContent())
        .boardUid(requestModel.getBoardUid())
        .memberUid(requestModel.getMemberUid())
        .build();

    boardReplyMapper.insert(dao);

    return convertBoardReplyResponseModel(dao);

  }

  @Transactional
  public BoardReplyResponseModel updateById(BoardReplyUpdateRequestModel requestModel) {
    BoardReplyDao dao = BoardReplyDao.builder()
        .uid(requestModel.getUid())
        .content(requestModel.getContent())
        .boardUid(requestModel.getBoardUid())
        .memberUid(requestModel.getMemberUid())
        .build();

    boardReplyMapper.update(dao);

    return convertBoardReplyResponseModel(dao);
  }

  @Transactional
  public void deleteById(BoardReplyDeleteRequestModel requestModel) {
    BoardReplyDao dao = BoardReplyDao.builder()
        .uid(requestModel.getUid())
        .boardUid(requestModel.getBoardUid())
        .memberUid(requestModel.getMemberUid())
        .build();
    boardReplyMapper.delete(dao);
  }


  private BoardReplyResponseModel convertBoardReplyResponseModel(BoardReplyDao dao) {
    return BoardReplyResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .content(dao.getContent())
        .boardUid(dao.getBoardUid())
        .memberUid(dao.getMemberUid())
        .nickname(dao.getNickname())
        .phoneNumber(dao.getPhoneNumber())
        .email(dao.getEmail())
        .build();
  }

}
