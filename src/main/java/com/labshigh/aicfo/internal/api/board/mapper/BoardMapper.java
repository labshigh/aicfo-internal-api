package com.labshigh.aicfo.internal.api.board.mapper;

import com.labshigh.aicfo.internal.api.board.dao.BoardDao;
import com.labshigh.aicfo.internal.api.board.model.request.BoardListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BoardMapper {

  void insert(BoardDao dao);

  void update(BoardDao dao);

  void updateViewCount(BoardDao dao);

  void updateRecommendCount(BoardDao dao);

  void delete(BoardDao dao);

  int count(BoardListRequestModel requestModel);

  List<BoardDao> list(BoardListRequestModel requestModel);

  BoardDao detail(BoardDao dao);

}
