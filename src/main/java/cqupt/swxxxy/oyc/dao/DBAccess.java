package cqupt.swxxxy.oyc.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * ͨ�������ļ���ȡSqlSession
 * 
 * @author Administrator
 * 
 */
public class DBAccess {
	private static SqlSession sqlSession = null;
	private static DBAccess dBAccess = null;

	private DBAccess() {
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("/conf/mybatis.xml");
			// ͨ�������ļ�����һ��SqlSessionFactory
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			// ͨ��sqlSessionFactory��һ�����ݿ�Ự
			SqlSession newSqlSession = sqlSessionFactory.openSession();
			sqlSession = newSqlSession;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DBAccess getDBAccess() {
		if (dBAccess == null) {
			dBAccess = new DBAccess();
		}
		return dBAccess;
	}
	
	public static SqlSession getSqlSession(){
		getDBAccess(); 
		return sqlSession;
	}
}
