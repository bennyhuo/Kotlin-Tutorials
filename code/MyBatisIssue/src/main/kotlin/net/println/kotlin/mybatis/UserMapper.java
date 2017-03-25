package net.println.kotlin.mybatis;

/**
 * Created by benny on 3/25/17.
 */
//@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM userinfo WHERE id = #{id}")
    User selectUser(int id);
}