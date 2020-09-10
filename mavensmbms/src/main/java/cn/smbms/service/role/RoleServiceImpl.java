package cn.smbms.service.role;


import cn.smbms.dao.role.RoleDao;
import cn.smbms.dao.role.RoleDaoImpl;
import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Role;
import cn.smbms.tools.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RoleServiceImpl implements RoleService {
	
	private RoleDao roleDao;
	SqlSession sqlSession=null;
	public RoleServiceImpl(){
		roleDao = new RoleDaoImpl();
	}
	
	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		List<Role> roleList = null;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			roleList = sqlSession.getMapper(RoleMapper.class).getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return roleList;
	}
	
}
