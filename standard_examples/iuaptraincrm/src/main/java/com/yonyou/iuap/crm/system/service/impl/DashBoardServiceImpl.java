package com.yonyou.iuap.crm.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.system.service.itf.IDashBoardService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Service
public class DashBoardServiceImpl implements IDashBoardService {
	@Autowired
	private AppBaseDao appBaseDao;
	
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;
	
	@Override
	public Map<String,Object> queryDashBoard() throws AppBusinessException {
		// TODO 自动生成的方法存根
		Map<String,Object> map = new HashMap();
		try {
			//PO订单数
//			List<ProPoorderInfoVO> polist = appBaseDao.findListByClause(ProPoorderInfoVO.class, "1=1");
//			map.put("pocounts", null==polist?0:polist.size());
			//立项申请数
//			List<ProjectApplyVO> prolist = appBaseDao.findListByClause(ProjectApplyVO.class, "1=1");
//			map.put("procounts", null==polist?0:prolist.size());
			//待审核立项申请数
//			List<ProjectApplyVO> proAuditlist = appBaseDao.findListByClause(ProjectApplyVO.class, "vstatus='"+DictCode.PROJECT_AUDIT_STATUS+"'");
//			map.put("proauditcounts", null==proAuditlist?0:proAuditlist.size());
			//车辆数
			List<TmCustomerVehicleVO> vehiclelist = appBaseDao.findListByClause(TmCustomerVehicleVO.class, "1=1");
			map.put("vehiclecounts", null==vehiclelist?0:vehiclelist.size());
			//客户数
			List<TmCustomerinfoVO> customerlist = appBaseDao.findListByClause(TmCustomerinfoVO.class, "1=1");
			map.put("customercounts", null==customerlist?0:customerlist.size());
			//待执行发运申请（状态<>已保存）
//			List<TmTransportApplyVO> translist = appBaseDao.findListByClause(TmTransportApplyVO.class, "vstatus<>'"+DictCode.APPLY_SAVED_STATUS+"'");
//			map.put("transcountsa", null==translist?0:translist.size());
			//待接车发运申请（状态=已确认）
//			List<TmTransportApplyVO> translistb = appBaseDao.findListByClause(TmTransportApplyVO.class, "vstatus='"+DictCode.APPLY_EXECUTED_STATUS+"'");
//			map.put("transcountsb", null==translistb?0:translistb.size());
			//待交付发运申请（状态=已接车）
//			List<TmTransportApplyVO> translistc = appBaseDao.findListByClause(TmTransportApplyVO.class, "vstatus='"+DictCode.APPLY_CONFIRM_STATUS+"'");
//			map.put("transcountsc", null==translistc?0:translistc.size());
			//获取本月第一天和最后一天
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			  Calendar cal = Calendar.getInstance(); 
			  cal.setTime(new Date()); 
			  cal.set(Calendar.DAY_OF_MONTH, 1); 
			  String fday = format.format(cal.getTime());
			  cal.roll(Calendar.DAY_OF_MONTH, -1);  
			  String lday = format.format(cal.getTime());
			String condition = " creationtime >='"+fday+"' and creationtime <= '"+lday+"'";
			//本月新增线索
//			List<TrackInfoVO> newtracklist = appBaseDao.findListByClause(TrackInfoVO.class, condition);
//			map.put("newtracks", null==newtracklist?0:newtracklist.size());
			//本月新增客户
			List<TmCustomerinfoVO> newcuslist = appBaseDao.findListByClause(TmCustomerinfoVO.class, condition);
			map.put("newcustomers", null==newcuslist?0:newcuslist.size());
			//本月新增充电站
//			List<SaleChargeStationVO> chargelist = appBaseDao.findListByClause(SaleChargeStationVO.class, condition);
//			map.put("newcharges", null==chargelist?0:chargelist.size());
			
			//消息提醒
//			List<TmAlertVO> alertlist = appBaseDao.findListByClause(TmAlertVO.class, "1=1");
//			String sql = "select * from tm_alert order by ts desc limit 2";
//			List<TmAlertVO> alertlist = baseDao.queryByClause(TmAlertVO.class, sql);
//			map.put("alertlist", alertlist);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return map;
	}
}
