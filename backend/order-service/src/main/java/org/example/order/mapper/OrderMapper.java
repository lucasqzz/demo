package org.example.order.mapper;

import org.apache.ibatis.annotations.*;
import org.example.order.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("""
            SELECT id, user_id, total_amount, status, items_json, created_at, updated_at
            FROM t_order
            WHERE id = #{id}
            """)
    Order findById(Long id);

    @Select("""
            SELECT id, user_id, total_amount, status, items_json, created_at, updated_at
            FROM t_order
            WHERE user_id = #{userId}
            ORDER BY created_at DESC
            """)
    List<Order> findByUserId(Long userId);

    @Insert("""
            INSERT INTO t_order (user_id, total_amount, status, items_json)
            VALUES (#{userId}, #{totalAmount}, #{status}, #{itemsJson})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);
}
