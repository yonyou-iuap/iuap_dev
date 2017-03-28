package com.yonyou.iuap.crm.common.ref;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.service.itf.IExtIeopUserService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@RequestMapping({ "/ref/userref" })
@Controller
public class UserRefController extends AbstractGridRefModel implements
		IRefModelRestEx {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IExtIeopUserService cpService;

	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	public UserRefController() {
	}

	@Override
	public RefViewModelVO getRefModelInfo(
			@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("用户");
		refViewModel.setRefCode("psn");
		refViewModel.setRootName("用户");
		refViewModel.setStrHiddenFieldCode(new String[] { "refpk" });
		refViewModel.setStrFieldCode(new String[] { "refcode", "refname" });
		refViewModel.setStrFieldName(new String[] { "用户编码", "用户名称" });
		// refViewModel.setRefCodeNamePK(new String[]{"code","name"});
		return refViewModel;
	}

	public Map<String, Object> getCommonRefData(
			@RequestBody RefViewModelVO paramRefViewModelVO) {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> searchParams = new HashMap<String, Object>();
		// PageRequest pageRequest = new PageRequest(0, 100, new
		// Sort(Direction.ASC, "ts"));
		RefClientPageInfo refClientPageInfo = paramRefViewModelVO
				.getRefClientPageInfo();
		int currPageIndex = refClientPageInfo.getCurrPageIndex();
		String condition = paramRefViewModelVO.getContent();// 获取查询条件
		searchParams.put("condition", condition);
		String[] param = paramRefViewModelVO.getPk_val();
		String deptpk = "";
		for (int i = 0; i < param.length; i++) {
			String parm1 = "";
			parm1 = param[i];
			if (parm1.startsWith("pkdept")) {
				int x = parm1.indexOf("pkdept");
				deptpk = parm1.substring(x + 7, parm1.length());
			}
		}
		searchParams.put("pkdept", deptpk);
		PageRequest pageRequest = new PageRequest(currPageIndex,
				AppTools.REFPAGESIZE, new Sort(Direction.ASC, "ts"));
		Page<ExtIeopUserVO> categoryPage = null;
		try {
			categoryPage = cpService
					.getBdUsersRefBypage(searchParams, pageRequest);
			// RefClientPageInfo refClientPageInfo = new RefClientPageInfo();
			int pageCount = categoryPage.getTotalPages();
			// int currPageIndex = categoryPage.getNumber() + 1;
			// int currPageIndex = categoryPage.getNumber();
			int pageSize = categoryPage.getSize();
			refClientPageInfo.setCurrPageIndex(currPageIndex);
			refClientPageInfo.setPageCount(pageCount);
			refClientPageInfo.setPageSize(pageSize);
			paramRefViewModelVO.setRefClientPageInfo(refClientPageInfo);

			List<Map<String, String>> list = new ArrayList();

			List<ExtIeopUserVO> vos = categoryPage.getContent();
			for (ExtIeopUserVO vo : vos) {
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getLoginName());
				user.put("refname", vo.getName());
				// user.put("creator", vo.getCreator());
				list.add(user);
			}
			result.put("dataList", list);
			result.put("refViewModel", paramRefViewModelVO);
			return result;
		} catch (BusinessException e) {
			logger.error("用户参照获取错误", e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> getIntRefData(String pks) throws Exception {
		String sql = "select id as pk,name as name from ieop_user where id in ("
				+ pks + ");";
		List<Map<String, String>> results = dao.queryForList(sql,
				new MapListProcessor());
		return results;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(
			@RequestBody RefViewModelVO arg0) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = new PageRequest(0, 100, new Sort(
				Direction.ASC, "ts"));
		Page<ExtIeopUserVO> categoryPage = null;
		try {
			String condition = arg0.getContent();// 获取查询条件
			searchParams.put("condition", condition);
			String[] param = arg0.getPk_val();
			String deptpk = "";
			for (int i = 0; i < param.length; i++) {
				String parm1 = "";
				parm1 = param[i];
				if (parm1.startsWith("pkdept")) {
					int x = parm1.indexOf("pkdept");
					deptpk = parm1.substring(x + 7, parm1.length());
				}
			}
			searchParams.put("pkdept", deptpk);
			categoryPage = cpService
					.getBdUsersRefBypage(searchParams, pageRequest);

			List<Map<String, String>> list = new ArrayList();

			List<ExtIeopUserVO> vos = categoryPage.getContent();
			for (ExtIeopUserVO vo : vos) {
				HashMap<String, String> user = new HashMap();
				user.put("refpk", vo.getId());
				user.put("refcode", vo.getLoginName());
				user.put("refname", vo.getName());
				// user.put("creator", vo.getCreator());
				list.add(user);
			}

			return list;
		} catch (BusinessException e) {
			logger.error("用户参照获取错误", e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(@RequestBody RefViewModelVO paramRefViewModelVO) {
		// TODO 自动生成的方法存根
		String[] pks = paramRefViewModelVO.getPk_val();
		List<Map<String, String>> results = new ArrayList();
		try {
			if(pks!=null&&pks.length>0) {
				for (int i = 0; i < pks.length; i++) {
					String pk = pks[i];
					ExtIeopUserVO vo = dao.queryByPK(ExtIeopUserVO.class, pk);
					Map<String, String> map = new HashMap();
					map.put("refpk", vo.getId()); 
					map.put("refcode", vo.getLoginName()); 
					map.put("refname", vo.getName()); 
					results.add(map);
				}
			}
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return results;
	}

}
