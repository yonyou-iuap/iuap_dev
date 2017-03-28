package com.yonyou.iuap.crm.dept.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;
import com.yonyou.iuap.crm.dept.service.itf.IBdDeptService;

@Controller
@RequestMapping(value = "/bd/dept")
public class BdDeptController {
	private static Logger logger = LoggerFactory.getLogger(BdDeptController.class);
	@Autowired
	private IBdDeptService bdService;
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody Page<BdDeptVO> loadData(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		Page<BdDeptVO> categoryPage = bdService.getBdDeptsBypage(searchParams,pageRequest);
		return categoryPage;
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("pk_dept".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_dept");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
	
	/**
	 * 加载修改界面查询数据
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/getcontypeInfo", method = RequestMethod.GET)
	public @ResponseBody BdDeptVO getcontypeInfo(@RequestParam(value = "pk_dept",required = true) String pk_dept, Model model, ServletRequest request) throws BusinessException{
		BdDeptVO BterDept = bdService.getBdDept(pk_dept);
		return BterDept;
	}
	
	
	/**
	 * 保存
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JSONObject savecontypeInfo(@RequestBody BdDeptVO entity, ModelMap model, ServletRequest request,String pk_corp) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
		entity.setCreator("test");
		if(null==entity.getPk_dept() || "".equals(entity.getPk_dept())){
			entity.setPk_dept("test");
		}
		entity.setCreationtime(new Date());
		try{
			jsonObject= bdService.saveEntity(entity,pk_corp);
		}catch(Exception e){
			logger.error("查询出错："+e);	
			jsonObject.put("flag", AppTools.FAILED);
		}	
		return jsonObject;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody JSONObject update(@RequestBody BdDeptVO entity,Model model, ServletRequest request,String pk_corp) {
		//JSONObject result = new JSONObject();
		entity.setCreationtime(new Date());
		try{
			return bdService.saveEntityForNormal(entity,pk_corp);
		}
		catch(Exception e){
			logger.error("查询出错："+e);			
		}			
		return null;
	}
	
	@RequestMapping(value = "/psndept", method = RequestMethod.GET)
	public @ResponseBody BdDeptVO psnDept(@RequestParam(value = "", defaultValue = "") String pk_dept,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		BdDeptVO categoryPage = bdService.getBdDept(pk_dept);
		return categoryPage;
	}
	
	@RequestMapping(value = "/queryTree", method = RequestMethod.GET)
	public @ResponseBody JSONObject queryTree(String pk_corp) throws BusinessException{
		JSONObject result = new JSONObject();
		List<BdDeptVO> lists =  bdService.getBdDeptbycorp(pk_corp);
		//List<BdCorpVO> categoryPage = cpService.getBterCorps();
		if(lists != null && !lists.isEmpty()){
			JSONArray oa = new JSONArray();
			for(BdDeptVO vo:lists){
				JSONObject jo = new JSONObject();
				jo.put("pk_dept", vo.getPk_dept());
				jo.put("deptcode", vo.getDeptcode());
				jo.put("deptname", vo.getDeptname());
				jo.put("pk_user", vo.getPk_user());
				jo.put("pkLeader", vo.getPkLeader());
				jo.put("pk_corp", vo.getPk_corp());
				jo.put("canceled", vo.getCanceled());
				jo.put("pkFathedept", vo.getPkFathedept());
				jo.put("deptlevel", vo.getDeptlevel());
				oa.add(jo);
			}
			
			JSONObject data = new JSONObject();//
			data.put("list",oa);
			result.put("data", data);	
			result.put("flag", AppTools.SUCCESS);
			result.put("msg", "success");
		}else{
			result.put("flag", AppTools.FAILED);
			result.put("msg", "部门数据为空");
		}
		return result;
	}
	/**
	 * 删除
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody JSONObject remove(@RequestBody BdDeptVO entity, Model model, ServletRequest request) throws BusinessException {
		if(entity.getPk_dept() == null  || entity.getPk_dept().trim().length()==0){
			throw new BusinessException("pk为空");
		}
		bdService.deleteBdDeptById(entity.getPk_dept());
		JSONObject jsonObject = new JSONObject();  
		jsonObject.put("msg", "success");			
		return jsonObject;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody Map<String,Object> delete(@RequestParam(value = "",required=true) String pk_dept, Model model, ServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		// 删除时校验部门是否引用组织
		String pk = bdService.queryBdDeptByDept(pk_dept);
		List<BdDeptVO> listvo =   bdService.getBdSubDepts(" and pk_fathedept ='"+pk_dept+"'");
		
		if(null!=pk && pk.length()>0){
			result.put("msg", "该部门已引用，不能删除！");
			
		}else{
			if(listvo.size()>0){
				result.put("msg", "该部门有下级节点，不能删除！");
				result.put("flag", AppTools.FAILED);
				return result;				

			}else{
				bdService.deleteBdDeptById(pk_dept);
				result.put("flag", AppTools.SUCCESS);
				return result;				
			}
		}
		return result;
	}
	
	/**
	 * 停用
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody String stopDeptPost(@RequestParam(value = "pk_dept",required=true) String pk_dept, Model model, ServletRequest request) throws BusinessException {
		return bdService.stopBdDept(pk_dept);
	}
	
	/**
	 * 启用
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody String startDeptPost(@RequestParam(value = "pk_dept",required=true) String pk_dept, Model model, ServletRequest request) throws BusinessException {
		return bdService.startBdDept(pk_dept);
	}
	
	@RequestMapping(value = "/queryPageByDept", method = RequestMethod.GET)
	public @ResponseBody Page<BdDeptVO> queryPageByDept(@RequestParam(value = "", defaultValue = "") String pk_dept,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		
		Page<BdDeptVO> categoryPage = bdService.getBdDeptsByDept(searchParams,pageRequest,pk_dept);
		return categoryPage;
	}
	
	@RequestMapping(value = "/queryDept", method = RequestMethod.GET)
	public @ResponseBody Page<BdDeptVO> queryDept(@RequestParam(value = "", defaultValue = "") String pk_dept,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		
		Page<BdDeptVO> categoryPage = bdService.getBdDept(searchParams,pageRequest,pk_dept);
		return categoryPage;
	}
	
	/**
	 * 功能：加载组织树数所有数据
	 * 参数：
	 * 返回：JSONObject
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */	
	@RequestMapping(value = "/queryTrees", method = RequestMethod.GET)
	public @ResponseBody JSONObject queryTrees() throws BusinessException{
		JSONObject result = new JSONObject();
		List<HashMap> lists =  bdService.getBdDeptsListMap("");
		if(lists != null && !lists.isEmpty()){
			JSONObject data = new JSONObject();//
			data.put("list",lists);
			result.put("data", data);	
			result.put("flag", AppTools.SUCCESS);
			result.put("msg", "success");
		}else{
			result.put("flag", AppTools.FAILED);
			result.put("msg", "获取数据为空");
		}
		return result;
	}
	
	@RequestMapping(value = "corppage", method = RequestMethod.GET)
	public @ResponseBody Page<BdCorpVO> corppage(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequestcorp(pageNumber, pageSize,
				sortType);

		
		Page<BdCorpVO> categoryPage =null;
		try {
			categoryPage = bdService.getBdCorpsBypages(searchParams,pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return categoryPage;
	}
	
	
	
	public PageRequest buildPageRequestcorp(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_corp");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 功能：模糊查询
	 */		
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody JSONObject queryParamsBySql(Model model, ServletRequest request,String deptname) throws BusinessException {
		String sql = " and deptname like '"+deptname+"'";
		if(deptname == null || "".equals(deptname)){
			sql = " and deptname is null ";
		}
		JSONObject result = new JSONObject();
		List<BdDeptVO> lists =  bdService.getBdDeptnames(sql);
			if(lists != null && !lists.isEmpty()){
				JSONArray oa = new JSONArray();
				for(BdDeptVO vo:lists){
					JSONObject jo = new JSONObject();
					jo.put("pk_dept", vo.getPk_dept());
					jo.put("deptcode", vo.getDeptcode());
					jo.put("deptname", vo.getDeptname());
					jo.put("pk_user", vo.getPk_user());
					jo.put("pkLeader", vo.getPkLeader());
					jo.put("pk_corp", vo.getPk_corp());
					jo.put("canceled", vo.getCanceled());
					jo.put("pkFathedept", vo.getPkFathedept());
					jo.put("deptlevel", vo.getDeptlevel());
					oa.add(jo);
				}
				
				JSONObject data = new JSONObject();//
				data.put("list",oa);
				result.put("data", data);	
				result.put("flag", AppTools.SUCCESS);
				result.put("msg", "success");
			}else{
				result.put("flag", AppTools.FAILED);
				result.put("msg", "没有该部门");
			}
		return result;
	}
	
}
