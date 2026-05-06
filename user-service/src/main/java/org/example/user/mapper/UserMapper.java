package org.example.user.mapper;

import org.apache.ibatis.annotations.*;
import org.example.user.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("""
            SELECT id, username, password, email, phone, created_at, updated_at
            FROM t_user
            WHERE id = #{id}
            """)
    User findById(Long id);

    @Select("""
            SELECT id, username, password, email, phone, created_at, updated_at
            FROM t_user
            WHERE username = #{username}
            """)
    User findByUsername(String username);

    @Select("""
            SELECT id, username, email, phone, created_at, updated_at
            FROM t_user
            """)
    List<User> findAll();

    @Insert("""
            INSERT INTO t_user (username, password, email, phone)
            VALUES (#{username}, #{password}, #{email}, #{phone})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("""
            UPDATE t_user
            SET email = #{email}, phone = #{phone}, updated_at = NOW()
            WHERE id = #{id}
            """)
    int update(User user);
}
