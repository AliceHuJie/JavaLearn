package com.hujie.distributelock.seataDemo.mapper;

import com.hujie.distributelock.seataDemo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    @Insert("insert `order` value (11, 4) ")
    void addOrder(@Param(value = "order") Order order);
}
