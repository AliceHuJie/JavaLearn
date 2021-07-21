package com.hujie.distributelock.seataDemo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    @Insert("update `product` set count = count -1 where id = 1 ")
    void subProduct();
}
