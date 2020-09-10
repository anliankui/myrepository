package cn.smbms.service.bill;


import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.bill.BillDaoImpl;
import cn.smbms.dao.bill.BillMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.tools.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BillServiceImpl implements BillService {
	SqlSession sqlSession=null;
	private BillDao billDao;
	public BillServiceImpl(){
		billDao = new BillDaoImpl();
	}
	@Override
	public boolean add(Bill bill) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(BillMapper.class).add(bill) > 0)
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
			//在service层进行connection连接的关闭
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill) {
		// TODO Auto-generated method stub
		List<Bill> billList = null;
		System.out.println("query productName ---- > " + bill.getProductName());
		System.out.println("query providerId ---- > " + bill.getProviderId());
		System.out.println("query isPayment ---- > " + bill.getIsPayment());
		try {
			sqlSession= MybatisUtil.openSqlSession();
			billList =sqlSession.getMapper(BillMapper.class).getBillList(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return billList;
	}

	@Override
	public boolean deleteBillById(String delId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(BillMapper.class).deleteBillById(delId) > 0)
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
	public Bill getBillById(String id) {
		// TODO Auto-generated method stub
		Bill bill = null;
		try{
			sqlSession= MybatisUtil.openSqlSession();
			bill =sqlSession.getMapper(BillMapper.class).getBillById(id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			bill = null;
		}finally{
			MybatisUtil.closeSqlSession(sqlSession);
		}
		return bill;
	}

	@Override
	public boolean modify(Bill bill) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			sqlSession= MybatisUtil.openSqlSession();
			if(sqlSession.getMapper(BillMapper.class).modify(bill) > 0)
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
