package cn.smbms.service.provider;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.bill.BillDaoImpl;
import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.dao.provider.ProviderDaoImpl;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.tools.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
	SqlSession sqlSession=null;
	private ProviderDao providerDao;
	private BillDao  billDao;
	public ProviderServiceImpl(){
		providerDao = new ProviderDaoImpl();
		billDao = new BillDaoImpl();
	}
	@Override
	public boolean add(Provider provider) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(ProviderMapper.class).add(provider) > 0)
				flag = true;
			sqlSession.commit();
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
			//在service层进行连接的关闭
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}

	@Override
	public List<Provider> getProviderList(String proName, String proCode) {
		// TODO Auto-generated method stub
		List<Provider> providerList = null;
		System.out.println("query proName ---- > " + proName);
		System.out.println("query proCode ---- > " + proCode);
		try {
			sqlSession= MybatisUtil.openSqlSession();
			providerList = sqlSession.getMapper(ProviderMapper.class).getProviderList(proName,proCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return providerList;
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
	 * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 */
	@Override
	public int deleteProviderById(String delId) {
		// TODO Auto-generated method stub
		int billCount = -1;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			billCount = sqlSession.getMapper(BillMapper.class).getBillCountByProviderId(delId);
			if(billCount == 0){
				sqlSession.getMapper(ProviderMapper.class).deleteProviderById(delId);
			}
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			billCount = -1;
			try {
				sqlSession.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return billCount;
	}

	@Override
	public Provider getProviderById(String id) {
		// TODO Auto-generated method stub
		Provider provider = null;
		try{
			sqlSession= MybatisUtil.openSqlSession();
			provider = sqlSession.getMapper(ProviderMapper.class).getProviderById(id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			provider = null;
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return provider;
	}

	@Override
	public boolean modify(Provider provider) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(ProviderMapper.class).modify(provider) > 0)
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
}
