package cn.smbms.service.user;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.user.UserDao;
import cn.smbms.dao.user.UserDaoImpl;
import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;
import cn.smbms.tools.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService{
	SqlSession sqlSession=null;

	private UserDao userDao;
	public UserServiceImpl(){
		userDao = new UserDaoImpl();
	}
	@Override
	public boolean add(User user) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			int updateRows = sqlSession.getMapper(UserMapper.class).add(user);

			if(updateRows > 0){
				System.out.println("add success!");
				sqlSession.commit();
			}else{
				System.out.println("add failed!");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				System.out.println("rollback==================");
				sqlSession.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			//在service层进行connection连接的关闭
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}
	@Override
	public User login(String userCode, String userPassword) {
		User user = null;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			user = sqlSession.getMapper(UserMapper.class).getLoginUser(userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}
		
		return user;
	}
	@Override
	public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
		// TODO Auto-generated method stub
		List<User> userList = null;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		System.out.println("currentPageNo ---- > " + currentPageNo);
		System.out.println("pageSize ---- > " + pageSize);
		try {
			sqlSession = MybatisUtil.openSqlSession();
			userList = sqlSession.getMapper(UserMapper.class).getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return userList;
	}
	@Override
	public User selectUserCodeExist(String userCode) {
		// TODO Auto-generated method stub
		Connection connection = null;
		User user = null;
		try {
			connection = BaseDao.getConnection();
			user = userDao.getLoginUser(connection, userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return user;
	}
	@Override
	public boolean deleteUserById(Integer delId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession = MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(UserMapper.class).deleteUserById(delId) > 0)
				flag = true;
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}
	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		User user = null;
		try{
			sqlSession = MybatisUtil.openSqlSession();

			user =sqlSession.getMapper(UserMapper.class).getUserById(id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = null;
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return user;
	}
	@Override
	public boolean modify(User user) {
		// TODO Auto-generated method stub

		boolean flag = false;
		try {
			sqlSession = MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(UserMapper.class).modify(user) > 0)
				flag = true;
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}
	@Override
	public boolean updatePwd(int id, String pwd) {
		// TODO Auto-generated method stub
		boolean flag = false;

		try{
			sqlSession = MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(UserMapper.class).updatePwd(id,pwd) > 0)
				flag = true;
			sqlSession.commit();
		}catch (Exception e) {
			// TODO: handle exception
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}
	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		// TODO Auto-generated method stub
		int count = 0;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		try {
			sqlSession = MybatisUtil.openSqlSession();
			count =sqlSession.getMapper(UserMapper.class).getUserCount(queryUserName,queryUserRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return count;
	}
	
}
