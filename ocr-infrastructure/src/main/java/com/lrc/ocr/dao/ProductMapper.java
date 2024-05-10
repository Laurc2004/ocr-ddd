package com.lrc.ocr.dao;


import com.lrc.ocr.po.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author lrc
* @description 针对表【product】的数据库操作Mapper
* @createDate 2024-05-08 17:55:53
* @Entity generator.com.lrc.ocr.po.Product
*/
@Mapper
public interface ProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int queryQuota(Long productId);

    List<Product> selectAll();

    Product queryByProductId(Long productId);
}
