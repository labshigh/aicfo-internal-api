package com.labshigh.aicfo.internal.api.board.mapper;

import com.labshigh.aicfo.internal.api.board.dao.BoardReplyDao;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BoardReplyMapper {

  void insert(BoardReplyDao dao);

  int count(BoardReplyListRequestModel dao);

  List<BoardReplyDao> list(BoardReplyListRequestModel requestModel);

  void update(BoardReplyDao dao);

  void delete(BoardReplyDao dao);

}
