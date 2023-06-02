package com.labshigh.aicfo.internal.api.board.mapper;

import com.labshigh.aicfo.internal.api.board.dao.BoardFileDao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BoardFileMapper {

  void insert(BoardFileDao dao);

  void delete(BoardFileDao dao);

  List<BoardFileDao> list(BoardFileDao dao);


}
