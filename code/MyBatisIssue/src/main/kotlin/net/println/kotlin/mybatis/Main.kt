package net.println.kotlin.mybatis

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactoryBuilder


/**
 * Created by benny on 3/25/17.
 */
fun main(args: Array<String>) {
    val resource = "net/println/kotlin/mybatis/config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    val sqlSessionFactory = SqlSessionFactoryBuilder().build(inputStream)
    val session = sqlSessionFactory.openSession()
    session.use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val user = mapper.selectUser(1)
        println(user)
    }
}