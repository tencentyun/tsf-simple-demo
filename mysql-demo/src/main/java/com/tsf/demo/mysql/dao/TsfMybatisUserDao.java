package com.tsf.demo.mysql.dao;

import com.tsf.demo.mysql.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by chazling on 2019/7/16.
 */
@Mapper
public interface TsfMybatisUserDao {

    /**
     * 插入用户信息
     */
    @Insert("INSERT INTO `tsf_user` (`user_name`, `user_token`, `creation_time`) VALUES (#{name}, #{token}, CURRENT_TIMESTAMP) ON DUPLICATE KEY UPDATE `creation_time` = CURRENT_TIMESTAMP")
    void insertUser(@Param("name") String name, @Param("token") String token);

    /**
     * 通过名字查询用户信息
     */
    @Select("SELECT `user_name`, `user_token` FROM `tsf_user` WHERE `user_name` = #{name}")
    User findUserByName(@Param("name") String name);
}
