package org.example.product.mapper;

import org.apache.ibatis.annotations.*;
import org.example.product.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("""
            SELECT id, name, description, price, stock, created_at, updated_at
            FROM t_product
            WHERE id = #{id}
            """)
    Product findById(Long id);

    @Select("""
            SELECT id, name, description, price, stock, created_at, updated_at
            FROM t_product
            """)
    List<Product> findAll();

    @Insert("""
            INSERT INTO t_product (name, description, price, stock)
            VALUES (#{name}, #{description}, #{price}, #{stock})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Update("""
            UPDATE t_product
            SET name = #{name}, description = #{description}, price = #{price}, stock = #{stock}, updated_at = NOW()
            WHERE id = #{id}
            """)
    int update(Product product);

    @Update("""
            UPDATE t_product
            SET stock = stock - #{quantity}, updated_at = NOW()
            WHERE id = #{productId} AND stock >= #{quantity}
            """)
    int reduceStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
