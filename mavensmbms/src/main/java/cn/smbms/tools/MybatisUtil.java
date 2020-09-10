package cn.smbms.tools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: mybatis
 * @description:
 * @author: alk
 * @create: 2020-08-18 17:44:54
 **/

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        InputStream is=null;
        try {
            is= Resources.getResourceAsStream("mybatis-config.xml");
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        sqlSessionFactory=new SqlSessionFactoryBuilder().build(is);

    }
    public static SqlSession openSqlSession(){
        return sqlSessionFactory.openSession();
    }
    public static void closeSqlSession(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }

}
