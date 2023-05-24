package com.labshigh.aicfo.internal.api.item.mapper;

import com.labshigh.aicfo.internal.api.item.dao.BasicItemDao;
import com.labshigh.aicfo.internal.api.item.dao.ItemDao;
import com.labshigh.aicfo.internal.api.item.model.request.ItemListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemMapper {

  void insert(ItemDao dao);

  void updateItem(ItemDao dao);

  void updateSort();

  void updateSortByAdmin(ItemDao dao);

  List<ItemDao> list(ItemListRequestModel itemListRequestModel);

  void insertBuyItem(ItemDao dao);

  List<BasicItemDao> selectBasicItem();

}
