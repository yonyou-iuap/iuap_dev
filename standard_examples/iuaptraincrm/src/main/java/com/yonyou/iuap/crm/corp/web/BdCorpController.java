package com.yonyou.iuap.crm.corp.web;

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

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.corp.service.itf.IBdCorpService;

@Controller
@RequestMapping(value = "/bd/corp")
public class BdCorpController {
	private static Logger logger = LoggerFactory.getLogger(BdCorpController.class);
	@Autowired
	private IBdCorpService cpService;
	
	/**
	 * 功能：加载组织树数所有数据
	 * 参数：
	 * 返回：JSONObject
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/queryTree", method = RequestMethod.GET)
	public @ResponseBody JSONObject queryTree() {
		JSONObject result = new JSONObject();
		List<BdCorpVO> lists;
		try {
			lists = cpService.getBdCorps();
			if(lists != null && !lists.isEmpty()){
				JSONArray oa = new JSONArray();
				for(BdCorpVO vo:lists){
					JSONObject jo = new JSONObject();
					jo.put("pk_corp", vo.getPk_corp());
					jo.put("fathercorp", vo.getFathercorp());
					jo.put("unitcode", vo.getUnitcode());
					jo.put("unitname", vo.getUnitname());
					jo.put("modifiedtime", vo.getModifiedtime());
					jo.put("corptype", vo.getCorptype());
					jo.put("creator", vo.getCreator());
					jo.put("isseal", vo.getIsseal());
					jo.put("corplevel", vo.getCorplevel());
					oa.add(jo);
				}
				JSONObject data = new JSONObject();//
				data.put("list",oa);
				result.put("data", data);	
				result.put("flag", AppTools.SUCCESS);
				result.put("msg", "success");
			}else{
				result.put("flag", AppTools.FAILED);
				result.put("msg", "获取数据为空");
			}			
		} catch (Exception e) {
			logger.error("查询出错："+e);
			result.put("flag", AppTools.FAILED);
		}
	
		return result;
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
	public @ResponseBody JSONObject queryTrees(){
		JSONObject result = new JSONObject();
		try{
			List<HashMap> lists =  cpService.getBdCorpsListMap("");
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
		}
		catch(Exception e) {
			logger.error("查询出错："+e);
			result.put("flag", AppTools.FAILED);
		}
		return result;
	}

	/**
	 * 功能：查询组织分页数据
	 * 参数：pageNumber,pageSize,sortType
	 * 返回：Page<BdCorpVO>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */		
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET)
	public @ResponseBody Page<BdCorpVO> queryPage(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		
		Page<BdCorpVO> categoryPage = cpService.getCorpsBypage(searchParams,pageRequest);
		return categoryPage;
	}
	

	/**
	 * 功能：根据组织编码，查询组织分页数据
	 * 参数：pk_corp,pageNumber,pageSize,sortType
	 * 返回：Page<BdCorpVO>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */	
	@RequestMapping(value = "/queryPageByCorp", method = RequestMethod.GET)
	public @ResponseBody Page<BdCorpVO> queryPageByCorp(@RequestParam(value = "", defaultValue = "") String pk_corp,@RequestParam(value = "page", defaultValue = "0")  int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		try{
			return cpService.getCorpsByCorp(searchParams,pageRequest,pk_corp);
		}
		catch(Exception e){
			logger.error("查询出错："+e);			
		}
		return null;
	}	

	/**
	 * 功能：查询组织分页数据
	 * 参数：pageNumber,pageSize,sortType
	 * 返回：Page<BdCorpVO>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws Exception 
	 */		
	@RequestMapping(value = "/queryPages", method = RequestMethod.GET)
	public @ResponseBody Page<BdCorpVO> queryPages(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		try{
			return cpService.getCorpsBypages(searchParams,pageRequest);
		}
		catch(Exception e){
			logger.error("查询出错："+e);			
		}
		return null;
	}	
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("pk_corp".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_corp");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
	
	/**
	 * 功能：根据组织主键查询对应的组织vo
	 * 参数：pk_corp
	 * 返回：BdCorpVO
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/queryCorpById", method = RequestMethod.GET)
	public @ResponseBody BdCorpVO queryCorpById(@RequestParam(value = "",required = true) String pk_corp, Model model, ServletRequest request) {
		try{
			return cpService.getBdCorp(pk_corp);
		}
		catch(Exception e){
			logger.error("查询出错："+e);			
		}			
		return null;
	}

	
	/**
	 * 功能：修改保存操作
	 * 参数：BdCorpVO
	 * 返回：JSONObject
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody JSONObject update(@RequestBody BdCorpVO entity,Model model, ServletRequest request) {
		//JSONObject result = new JSONObject();
		entity.setCreationtime((new Date()).toString());
		try{
			return cpService.saveEntityForNormal(entity);
		}
		catch(Exception e){
			logger.error("查询出错："+e);			
		}			
		return null;
	}
	
	/**
	 * 功能：新增保存操作
	 * 参数：BdCorpVO
	 * 返回：JSONObject
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JSONObject savePost(@RequestBody BdCorpVO entity, ModelMap model, ServletRequest request) {
		JSONObject jsonObject = new JSONObject();  
		entity.setCreator(AppInvocationInfoProxy.getPk_User());
		if(null==entity.getPk_user() || "".equals(entity.getPk_user())){
			entity.setPk_user("test");
		}
		entity.setCreationtime((new Date()).toString());
		try{
			jsonObject= cpService.saveEntity(entity);
		}catch(Exception e){
			logger.error("查询出错："+e);	
			jsonObject.put("flag", AppTools.FAILED);
		}	
		return jsonObject;
	}
	
	
	/**
	 * 功能：删除操作
	 * 参数：pk_corp
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody Map<String,Object> delete(@RequestParam(value = "",required=true) String pk_corp, Model model, ServletRequest request)  {
		Map<String,Object> result = new HashMap<String,Object>();
		// 删除时校验部门是否引用组织
		try{
			String pk = cpService.queryDeptByCorp(pk_corp);
			List<BdCorpVO> listvo =   cpService.getBdSubCorps(" and fathercorp ='"+pk_corp+"'");
			
			if(null!=pk && pk.length()>0){
				result.put("msg", "该组织已引用，不能删除！");
				result.put("flag", AppTools.FAILED);
				return result;

			}else{
				if(listvo.size()>0){
					result.put("msg", "该组织有下级节点，不能删除！");
					result.put("flag", AppTools.FAILED);
					return result;

				}else{
					cpService.deleteBdCorpById(pk_corp);
//					result.put("msg", "success");	
					result.put("flag", AppTools.SUCCESS);
					return result;
				}
			}
		}
		catch(Exception e){
			logger.error("删除出错："+e);	
			result.put("flag", AppTools.FAILED);
		}
		return result;
	}
	
	/**
	 * 功能：停用
	 * 参数：pk_corp
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody JSONObject stopCorpPost(@RequestParam(value = "",required=true) String pk_corp, Model model, ServletRequest request) {
		JSONObject result = new JSONObject(); 	
		try{
			cpService.stopCorp(pk_corp);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("停用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}
	

	/**
	 * 功能：启用
	 * 参数：pk_corp
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */			
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody JSONObject startCorpPost(@RequestParam(value = "",required=true) String pk_corp,@RequestParam(value = "",required=true) String def1, Model model, ServletRequest request){
		JSONObject result = new JSONObject(); 
		try{
			cpService.startCorp(pk_corp,def1);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("启用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}
	/**
	 * 功能：停用
	 * 参数：pk_corp
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */	
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public @ResponseBody JSONObject stopCorpGet(@RequestParam(value = "",required=true) String pk_corp, Model model, ServletRequest request)  {
		JSONObject result = new JSONObject(); 	
		try{
			cpService.stopCorp(pk_corp);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("停用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}
	
	/**
	 * 功能：启用
	 * 参数：pk_corp
	 * 返回：Map<String,Object>
	 * 日期：2016-04-12
	 * 作者：ytq
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public @ResponseBody JSONObject startCorpGet(@RequestParam(value = "",required=true) String pk_corp,@RequestParam(value = "",required=true) String def1, Model model, ServletRequest request)  {
		JSONObject result = new JSONObject(); 
		try{
			cpService.startCorp(pk_corp,def1);
			result.put("flag", AppTools.SUCCESS);
		}catch(Exception e){
			logger.error("启用出错："+e);	
			result.put("flag", AppTools.FAILED);
		}	
		return result;
	}		
		
}
