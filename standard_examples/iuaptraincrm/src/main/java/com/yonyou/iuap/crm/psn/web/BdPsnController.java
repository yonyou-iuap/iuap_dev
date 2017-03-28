package com.yonyou.iuap.crm.psn.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.psn.service.BdPsnService;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Controller
@RequestMapping(value = "/bd/psn")
public class BdPsnController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BdPsnService cpService;
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;

	/**
	 * 
	 * TODO 功能：根据人员ID查询人员 参数：pk_psndoc
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pk_psndoc
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/queryPsnById", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryPsnById(
			@RequestParam(value = "", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		BdPsndocVO vo = cpService.queryPsnById(pk_psndoc);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		result.put("data", vo);
		return result;
	}

	/**
	 * 
	 * TODO 分页查询
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<BdPsndocVO> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<BdPsndocVO> categoryPage = cpService.getBdDeptsBypage(
				searchParams, pageRequest);
		return categoryPage;
	}

	/**
	 * 
	 * TODO 查询区域
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "queryArea", method = RequestMethod.POST)
	public @ResponseBody Page<BdPsndocVO> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map map, Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (map != null && !map.isEmpty()) {
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				if ("page".equalsIgnoreCase(key)) {
					pageNumber = (int) map.get(key);
				} else if ("page.size".equalsIgnoreCase(key)) {
					pageSize = (int) map.get(key);
				} else if ("sortType".equalsIgnoreCase(key)) {
					sortType = (String) map.get(key);
				} else if ("params".equalsIgnoreCase(key)) {
					Map params = (Map) map.get(key);
					for (Iterator<String> search = params.keySet().iterator(); search
							.hasNext();) {
						String searchKey = search.next();
						String searchValue = (String) params.get(searchKey);
						if (searchValue != null
								&& !StringUtils.isEmpty(searchValue)) {
							searchParams.put("LIKE_" + searchKey, searchValue);
						}
					}
				}
			}
		}
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<BdPsndocVO> rolePage = cpService.getBdDeptsBypage(searchParams,
				pageRequest);
		return rolePage;
	}

	public PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_psndoc");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 
	 * TODO 功能：根据组织查询人员 参数：组织 主键 pk_corp
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pk_corp
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/queryPsnsByCorp", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryPsnsByCorp(
			@RequestParam(value = "pk_corp", required = true) String pk_corp,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BdPsndocVO> volist = cpService.queryPsnByIds("pk_corp", pk_corp);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		result.put("data", volist);
		return result;
	}

	/**
	 * 
	 * TODO 功能：根据组织查询人员 参数：部门主键 pk_dept
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pk_dept
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/queryPsnsByDept", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryPsnsByDept(
			@RequestParam(value = "", required = true) String pk_dept,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BdPsndocVO> volist = cpService.queryPsnByIds("pk_dept", pk_dept);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "success");
		result.put("data", volist);
		return result;
	}

	/**
	 * 
	 * TODO 功能：修改保存 参数：人员vo
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(
			@RequestBody BdPsndocVO entity, Model model, ServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String pk = cpService.savePsnEntity(entity);
		result.put("msg", "success");
		result.put("pk", pk);
		return result;
	}

	/**
	 * 
	 * TODO 功能：新增保存操作 参数：人员vo
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> savePost(
			@RequestBody BdPsndocVO entity, ModelMap model,
			ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String code = entity.getPsncode();
		String name = entity.getPsnname();
		String number = entity.getNumber();
		String pk_psndoc = entity.getPk_psndoc();

		String wherestr = "";
		if (null != pk_psndoc && pk_psndoc.length() > 0) { //
			wherestr = "  (psncode ='" + code + "' or psnname='" + name
					+ "'or number='" + number + "')  and pk_psndoc <>'"
					+ pk_psndoc + "'";
		} else {
			wherestr = "  (psncode ='" + code + "' or psnname='" + name
					+ "'or number='" + number + "') ";
		}
		List<BdPsndocVO> listvo = cpService.queryPsnListByCondition(wherestr);
		for (BdPsndocVO pvo : listvo) {
			String message = "";
			if (code.equals(pvo.getPsncode().trim())) {
				message += "编码重复！";
			}
			if (name.equals(pvo.getPsnname().trim())) {
				message += "名称重复！";
			}
			if (number.equals(pvo.getNumber().trim())) {
				message += "工号重复！";
			}
			if (null != message && message.length() > 0) {
				result.put("flag", AppTools.FAILED);
				result.put("msg", message);
				return result;
			}
		}
		String pk = cpService.savePsnEntity(entity);
		try {
			BdPsndocVO vo = dao.queryByPK(BdPsndocVO.class, pk);
			result.put("ts", vo.getTs());
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (null == pk_psndoc || "".equals(pk_psndoc)) {
			result.put("msg", "保存成功");
		} else {
			result.put("msg", "修改成功");
		}
		result.put("pk", pk);
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	 * TODO 功能：删除操作 参数：人员主键 pk_psndoc
	 * 
	 * @author 
	 * @date 2016年12月6日
	 * @param pk_psndoc
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delete(
			@RequestParam(value = "pk_psndoc", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		cpService.deletePsnById(pk_psndoc);
		result.put("msg", "success");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	 * TODO 功能：人员调动
	 * 
	 * @author 
	 * @date 2016年12月7日
	 * @param pk_psndoc
	 * @param fromcorp
	 * @param fromdept
	 * @param tocorp
	 * @param todept
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/psnFromDeptToDept", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> psnFromDeptToDept(
			@RequestParam(value = "", required = true) String pk_psndoc,
			@RequestParam(value = "", required = true) String fromcorp,
			@RequestParam(value = "", required = true) String fromdept,
			@RequestParam(value = "", required = true) String tocorp,
			@RequestParam(value = "", required = true) String todept,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		BdPsndocVO pvo = cpService.queryPsnById(pk_psndoc);
		pvo.setPk_corp(tocorp);
		pvo.setPk_dept(todept);
		cpService.savePsnEntity(pvo);
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	 * TODO 功能：封存 参数：人员主键 pk_psndoc
	 * 
	 * @author 
	 * @date 2016年12月7日
	 * @param pk_psndoc
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/sealedPsnn", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sealedPsnPost(
			@RequestParam(value = "", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		cpService.sealedPsn(pk_psndoc);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "success");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	 * TODO 功能：解封 参数：人员主键 pk_psndoc
	 * 
	 * @author 
	 * @date 2016年12月7日
	 * @param pk_psndoc
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/unSealedPsnn", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> unSealedPsnPost(
			@RequestParam(value = "", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		cpService.unSealedPsn(pk_psndoc);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "success");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	 * TODO 批量封存人员
	 * 
	 * @author 
	 * @date 2016年12月7日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/sealedPsn", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> stoplist(
			@RequestBody List<BdPsndocVO> entity, ModelMap model,
			ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		int psntel = 0;
		if (entity.size() > 0) {
			psntel = entity.get(0).getPsnseal();
		}

		for (int i = 1; i < entity.size(); i++) {
			int psnteln = entity.get(i).getPsnseal();

			if (psnteln != psntel) {
				result.put("msg", "人员状态不一致不能修改");
				result.put("flag", AppTools.FAILED);
				return result;
			}
		}

		if (psntel == 1) {
			result.put("msg", "人员已是封存状态，不能封存");
			result.put("flag", AppTools.FAILED);
			return result;
		}
		if (psntel == 0) {
			cpService.stoplist(entity);
			result.put("msg", "封存成功");
			result.put("flag", AppTools.SUCCESS);
			return result;
		}
		return result;
	}

	/**
	 * 
	 * TODO 批量解封人员
	 * 
	 * @author 
	 * @date 2016年12月7日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/unSealedPsn", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> startlist(
			@RequestBody List<BdPsndocVO> entity, ModelMap model,
			ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		int psntel = 0;
		if (entity.size() > 0) {
			psntel = entity.get(0).getPsnseal();
		}
		for (int i = 1; i < entity.size(); i++) {
			int psnteln = entity.get(i).getPsnseal();
			if (psnteln != psntel) {
				result.put("msg", "人员状态不一致不能修改");
				result.put("flag", AppTools.FAILED);
				return result;
			}
		}

		if (psntel == 0) {
			result.put("msg", "人员已是正常状态，不能解封");
			result.put("flag", AppTools.FAILED);
			return result;
		}
		if (psntel == 1) {
			cpService.startlist(entity);
			result.put("msg", "解封成功");
			result.put("flag", AppTools.SUCCESS);
			return result;
		}
		return result;
	}

	/**
	 * 
	* TODO 批量删除人员
	* @author 
	* @date 2016年12月7日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/deletelist", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> startuserPostsdlist(
			@RequestBody List<BdPsndocVO> entity, ModelMap model,
			ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		cpService.deletelist(entity);
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	* TODO 修改用户手机号
	* @author 
	* @date 2016年12月7日
	* @param psntel
	* @param pk_psndoc
	* @param model
	* @param request
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/usertel", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> userName(
			@RequestParam(value = "", required = true) String psntel,
			@RequestParam(value = "", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BdPsndocVO> entity = cpService.queryPsnByIds("pk_psndoc",
				pk_psndoc);
		if (null == psntel && "".equals(psntel)) {
			psntel = entity.get(0).getPsntel();
		}
		cpService.updatetel(pk_psndoc, psntel);
		// LoginController.sendMessage("smtp.163.com",
		// "@163.com", "pwd", entity.get(0).getEmail(),
		// "重置密码",
		// ""+entity.get(0).getPsnname()+"你好，你的手机重置为",
		// "text/html;charset=gb2312");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 
	* TODO 修改用户邮箱
	* @author 
	* @date 2016年12月7日
	* @param email
	* @param pk_psndoc
	* @param model
	* @param request
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/useremail", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> usertel(
			@RequestParam(value = "", required = true) String email,
			@RequestParam(value = "", required = true) String pk_psndoc,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BdPsndocVO> entity = cpService.queryPsnByIds("pk_psndoc",
				pk_psndoc);
		if (null == email && "".equals(email)) {
			email = entity.get(0).getEmail();
		}
		cpService.updateemail(pk_psndoc, email);
		// LoginController.sendMessage("smtp.163.com",
		// "@163.com", "pwd", entity.get(0).getEmail(),
		// "重置密码",
		// ""+entity.get(0).getPsnname()+"你好，你的手机重置为",
		// "text/html;charset=gb2312");
		result.put("flag", AppTools.SUCCESS);
		return result;
	}
	
	/**
	 * 
	* TODO 人员调动
	* @author 
	* @date 2016年12月7日
	* @param email
	* @param pk_psndoc
	* @param model
	* @param request
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/savenum", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deftShift(
			@RequestBody JSONObject shiftJson,
			Model model, ServletRequest request) throws AppBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String newCorp = shiftJson.getString("newCorp");
		String newDept = shiftJson.getString("newDept");
		String oldCorp = shiftJson.getString("oldCorp");
		String oldDept = shiftJson.getString("oldDept");
		String pk_psndoc = shiftJson.getString("pk_psndoc");
		cpService.shiftDept(pk_psndoc,oldCorp,oldDept,newCorp,newDept);
		result.put("flag", AppTools.SUCCESS);
		result.put("msg", "调动成功");
		return result;
	}

}
