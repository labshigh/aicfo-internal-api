package com.labshigh.aicfo.internal.api.board.mapper;

import com.labshigh.aicfo.internal.api.board.dao.BoardRecommendHistoryDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BoardRecommendHistoryMapper {

  int count(BoardRecommendHistoryDao dao);

  BoardRecommendHistoryDao select(BoardRecommendHistoryDao dao);

  void insert(BoardRecommendHistoryDao dao);

  void delete(BoardRecommendHistoryDao dao);
}
