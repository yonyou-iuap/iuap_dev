package com.yonyou.iuap.crm.ref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.ArrayListProcessor;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.ref.entity.RefinfoVO;

@Service
public class RefinfoService {
	
	@Autowired
	private AppBaseDao baseDao;
	
	public List<RefinfoVO> getRefinfoByMdpk(String pk){
		
		List<Object[]> list = new ArrayList<Object[]>();
		List<RefinfoVO> refinfoList = new ArrayList<RefinfoVO>();
		try {
			list = baseDao.findForList("select * from ref_refinfo where md_entitypk='123456'", new ArrayListProcessor());
			for(Object[] o:list){
				RefinfoVO vo = new RefinfoVO();
				vo.setPk_ref(o[0] ==null?null:o[0].toString());
				vo.setRefname(o[1] ==null?null:o[1].toString());
				vo.setRefcode(o[2] ==null?null:o[2].toString());
				vo.setRefclass(o[3] ==null?null:o[3].toString());
				vo.setRefurl(o[4] ==null?null:o[4].toString());
				vo.setMd_entitypk(o[5] ==null?null:o[5].toString());
				vo.setProductType(o[6] ==null?null:o[6].toString());
				refinfoList.add(vo);
			}
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return refinfoList;
	}

}
