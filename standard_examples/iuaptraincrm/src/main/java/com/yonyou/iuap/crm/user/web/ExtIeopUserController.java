package com.yonyou.iuap.crm.user.web;

import java.util.Date;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.exception.RestException;
import com.yonyou.uap.ieop.security.service.IRoleService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.CommonUtils;
import com.yonyou.iuap.crm.common.utils.MailSenderInfo;
import com.yonyou.iuap.crm.common.utils.SimpleMailSender;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;
import com.yonyou.iuap.crm.psn.service.BdPsnService;
import com.yonyou.iuap.crm.user.entity.ExtIeopRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.repository.itf.IExtIeopUserDao;
import com.yonyou.iuap.crm.user.service.itf.IExtIeopUserService;

@Controller
@RequestMapping(value = "/system/user")
public class ExtIeopUserController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final int HASH_INTERATIONS = 1024;

	@Autowired
	private IExtIeopUserService cpService;
	
	@Autowired
	private IRoleService roleService;	
	@Autowired
	private IExtIeopUserDao dao;
	@Autowired
	private BdPsnService psnService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "querypage", method = RequestMethod.GET)
	public @ResponseBody Page<ExtIeopUserVO> page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);

		
		Page<ExtIeopUserVO> categoryPage =null;
		try {
			categoryPage = cpService.getBdUsersBypage(searchParams,pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return categoryPage;
	}
	@RequestMapping(value = "queryArea", method = RequestMethod.POST)
	public @ResponseBody Page<ExtIeopUserVO> page(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,@RequestBody Map map,Model model, ServletRequest request) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(map!=null && !map.isEmpty()){
			for(Iterator<String> it = map.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				if("page".equalsIgnoreCase(key)){
					pageNumber = (int) map.get(key);
				}else if("page.size".equalsIgnoreCase(key)){
					pageSize = (int) map.get(key);
				}else if("sortType".equalsIgnoreCase(key)){
					sortType = (String) map.get(key);
				}else if("params".equalsIgnoreCase(key)){
					Map params = (Map) map.get(key);
					for(Iterator<String> search  = params.keySet().iterator(); search.hasNext(); ){
						String searchKey = search.next();
						String searchValue = (String) params.get(searchKey);
						if(searchValue!=null && !StringUtils.isEmpty(searchValue)){
							searchParams.put("LIKE_"+searchKey, searchValue);
						}
					}
				}
			}
		}
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Page<ExtIeopUserVO> rolePage=null;
		try {
			rolePage = cpService.getBdUsersBypage(searchParams, pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return rolePage;
	}
	
	
	public PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 加载修改界面查询数据
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/getcontypeInfo", method = RequestMethod.GET)
	public @ResponseBody ExtIeopUserVO getcontypeInfo(@RequestParam(value = "id",required = true) String id, Model model, ServletRequest request) throws BusinessException{
		ExtIeopUserVO bdUser = cpService.getBdUser(id);
		return bdUser;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JSONObject savecontypeInfo(@RequestBody ExtIeopUserVO entity, ModelMap model, ServletRequest request) throws BusinessException, RestException {
		JSONObject jsonObject = new JSONObject(); 
		String code=entity.getLoginName();
		String name=entity.getName();
		String wherestr = "";
		if(null!=entity.getId() && entity.getId().length()>0){ // 
			wherestr = " and (login_name ='"+code+"' or name='"+name+"')  and id <>'"+entity.getId()+"'";
		}else{
			wherestr = " and (login_name ='"+code+"' or name='"+name+"') ";
		}
		List<ExtIeopUserVO> listvo = dao.findByCode(wherestr);
		for(ExtIeopUserVO pvo:listvo){
			String message = "";
			if(code.equals(pvo.getLoginName().trim())){
				message+="用户编码重复！";
			}
			if(name.equals(pvo.getLoginName().trim())){
				message+="用户名称重复！";
			}
			if(null!=message && message.length()>0){
				jsonObject.put("flag", AppTools.FAILED);
				jsonObject.put("msg", message);
				return jsonObject;				
			}
		}
		if(entity.getId() == null  || entity.getId().trim().length()==0){
			String pk = cpService.saveEntity(entity);
			jsonObject.put("flag", AppTools.SUCCESS);
			jsonObject.put("msg", pk);
			return jsonObject;
		}else{
			ExtIeopUserVO categoryPage =cpService.getBdUser(entity.getId());
			entity.setSalt(categoryPage.getSalt());
			entity.setPassword(categoryPage.getPassword());
			entity.setDr(categoryPage.getDr());
			entity.setLocked(categoryPage.getLocked());
			entity.setPsnseal(categoryPage.getPsnseal());
			entity.setUserattr(categoryPage.getUserattr());
			entity.setCreator(categoryPage.getCreator());
			entity.setCreationtime(categoryPage.getCreationtime());
			entity.setModifier(InvocationInfoProxy.getUserid());
			entity.setModifiedtime(new Date());
			String msg = cpService.updateEntity(entity);
			jsonObject.put("flag", AppTools.SUCCESS);
			jsonObject.put("msg", entity.getId());
			return jsonObject;
		}
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody JSONObject savecontypeInfoupdate(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
			String msg = cpService.updateEntitynum(entity);
			jsonObject.put("msg", msg);			
		return jsonObject;
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody JSONObject remove(@RequestBody ExtIeopUserVO entity, Model model, ServletRequest request) throws BusinessException {
		if(entity.getId() == null  || entity.getId().trim().length()==0){
			throw new BusinessException("pk为空");
		}
		cpService.deleteBdUserById(entity.getId());
		JSONObject jsonObject = new JSONObject();  
		jsonObject.put("flag", AppTools.SUCCESS);		
		return jsonObject;
	}
	@RequestMapping(value = "/removelist", method = RequestMethod.POST)
	public @ResponseBody JSONObject removelist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
			String msg = cpService.removelist(entity);
			jsonObject.put("flag", AppTools.SUCCESS);		
		return jsonObject;
	}
	
	/**
	 * 锁定
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/lockedstop", method = RequestMethod.POST)
	public @ResponseBody String stopBdUserPost(@RequestParam(value = "id",required=true) String id, Model model, ServletRequest request) throws BusinessException {
		return cpService.stopBdUser(id);
	}
	@RequestMapping(value = "/lockedstoplist", method = RequestMethod.POST)
	public @ResponseBody JSONObject stopBdUserPostlist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();
		int locked=0;
		int psntel=0;
		
		if (entity.size()>0){
			locked= entity.get(0).getLocked();
			psntel= entity.get(0).getPsnseal();
			if(psntel==0){
				jsonObject.put("msg", "用户停用状态下不能锁定");		
				jsonObject.put("flag", AppTools.FAILED);	
				return jsonObject;

			}
		}
		
		for(int i=1;i<entity.size();i++){
			int lockedn= entity.get(i).getLocked();
			int psnteln= entity.get(i).getPsnseal();
			if(psnteln==0){
				jsonObject.put("msg", "用户停用状态下不能锁定");		
				jsonObject.put("flag", AppTools.FAILED);	
				return jsonObject;

			}
			if(lockedn!=locked){
				jsonObject.put("msg", "用户状态不一致不能修改");		
				jsonObject.put("flag", AppTools.FAILED);	
				return jsonObject;

			}
			
		}
			
		if(locked==1){
			String msg = cpService.stopBdUserlist(entity);
			jsonObject.put("msg", "锁定成功");		
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
		}
		if(locked==0){
			jsonObject.put("msg", "解锁成功");		
			String msg = cpService.startBdUserlist(entity);
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
		}
		
		return jsonObject;
	}
	
	/**
	 * 解锁
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/lockedstart", method = RequestMethod.POST)
	public @ResponseBody String startuserPost(@RequestParam(value = "id",required=true) String id, Model model, ServletRequest request) throws BusinessException {
		return cpService.startBdUser(id);
	}
	@RequestMapping(value = "/lockedstartlist", method = RequestMethod.POST)
	public @ResponseBody JSONObject startuserPostlist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
			String msg = cpService.startBdUserlist(entity);
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
	}
	@RequestMapping(value = "/updateattr", method = RequestMethod.GET)
	public @ResponseBody String startuserPostup(@RequestParam(value = "userattr",required=true) String userattr, Model model, ServletRequest request) throws BusinessException {
		String id =AppInvocationInfoProxy.getPk_User();
		if(id==null){
			id="e8cf288b-88cd-4412-86bd-e89a067e6eb4";
		}		return cpService.updateattr(id,userattr);
	}
	/**
	 * 停用
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/psnsealstop", method = RequestMethod.POST)
	public @ResponseBody String stopBdUserPostsd(@RequestParam(value = "id",required=true) String id, Model model, ServletRequest request) throws BusinessException {
		return cpService.stopBdUsersd(id);
	}
	@RequestMapping(value = "/psnsealstoptlist", method = RequestMethod.POST)
	public @ResponseBody JSONObject stopBdUserPostsdlist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject(); 
		int locked=0;
		int psntel=0;
		
		if (entity.size()>0){
			locked= entity.get(0).getLocked();
			psntel= entity.get(0).getPsnseal();
		}
		
		for(int i=1;i<entity.size();i++){
			int lockedn= entity.get(i).getLocked();
			int psnteln= entity.get(i).getPsnseal();
			
			if(psnteln!=psntel){
				jsonObject.put("msg", "用户状态不一致不能修改");		
				jsonObject.put("flag", AppTools.FAILED);	
				return jsonObject;

			}
			
		}
		
		if(psntel==1){
			String msg = cpService.stopBdUsersdlist(entity);
			jsonObject.put("msg", "停用成功");		
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
		}
		if(psntel==0){
			String msg = cpService.startBdUsersdlist(entity);
			jsonObject.put("msg", "启用成功");		
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
		}
		
		return jsonObject;
	}
	@RequestMapping(value = "/updatepasswordlist", method = RequestMethod.POST)
	public @ResponseBody JSONObject updatepasswordlist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
		
			String msg = cpService.startUpdatepassword(entity);
			jsonObject.put("msg", "修改密码成功，新密码为"+msg);			
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
	}


	@RequestMapping(value = "/psnsealstart", method = RequestMethod.POST)
	public @ResponseBody String startuserPostsd(@RequestParam(value = "id",required=true) String id, Model model, ServletRequest request) throws BusinessException {
		return cpService.startBdUsersd(id);
	}
	@RequestMapping(value = "/psnsealstartlist", method = RequestMethod.POST)
	public @ResponseBody JSONObject startuserPostsdlist(@RequestBody List<ExtIeopUserVO> entity, ModelMap model, ServletRequest request) throws BusinessException {
		JSONObject jsonObject = new JSONObject();  
			String msg = cpService.startBdUsersdlist(entity);
			jsonObject.put("flag", AppTools.SUCCESS);		
			return jsonObject;
	}
	
	
	@RequestMapping(value = "/queryByCodeOrName", method = RequestMethod.POST)

	 public @ResponseBody Page<ExtIeopUserVO> queryByCodeOrNamenum(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request,@RequestBody ExtIeopUserVO entity) throws Exception {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
			 String login_name=entity.getLoginName();
				String name=entity.getName();
				String corp=entity.getPkCorp();
			Page<ExtIeopUserVO> categoryPage = cpService.getBdUsersBypagenum(searchParams,pageRequest,login_name,name,corp);
			return categoryPage;
		}
	/**
	 * 功能：查询
	 * 参数：userid
	 * 返回：Map<String,Object>
	 * 日期：2016-06-03
	 * 作者：ytq
	 * @throws BusinessException 
	 */		
	@RequestMapping(value = "/queryRoleByUserid", method = RequestMethod.POST)
	public @ResponseBody Page<ExtIeopUserRoleVO> queryRoleByUserid(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request,@RequestBody ExtIeopUserVO entity) throws Exception {
//		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
//		Map<String, Object> searchParams = new HashMap<String, Object>();

		 String Id=entity.getId();
		List<ExtIeopUserRoleVO> listvo = cpService.queryByUserID(Id) ;
		
		return new PageImpl(listvo);
//		return listvo;	
	}
	
	//获取未分配该角色的所有用户
	@RequestMapping(value = "/queryUnassignUsers/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ExtIeopUserVO> queryUnassignUsers(@PathVariable("id") String id) throws BusinessException {
		List<ExtIeopUserVO> results= cpService.queryUnassignUsers(id);
		return results;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryroleuserpage", method = RequestMethod.GET)
	public @ResponseBody Page<ExtIeopUserVO> userpage(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("_");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				if(columnName.equals("loginName")){
					columnName = "login_name";
				}else if(columnName.equals("name")){
					columnName = "name";
				}else{
					columnName = "pk_corp";
				}
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if(value != null && StringUtils.isNotBlank(value.toString())){
					condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator)? "%"+value + "%" : value;
					sqlParameter.addParam(value);
				}
			}
		}

		
		Page<ExtIeopUserVO> categoryPage =null;
		try {
			categoryPage = cpService.queryUnassignUsers2(sqlParameter,pageRequest);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return categoryPage;
	}
	@RequestMapping(value = "/queryByCodeOrName", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> queryByCodeOrName(@RequestParam(value = "",required=true) String oldpassword,@RequestParam(value = "",required=true) String newpassword,@RequestParam(value = "",required=true) String newpasswordr, Model model, ServletRequest request)  {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			String id =AppInvocationInfoProxy.getPk_User();
			if(id==null){
				id="e8cf288b-88cd-4412-86bd-e89a067e6eb4";
			}
			ExtIeopUserVO categoryPage=cpService.getBdUser(id);
			String salt=categoryPage.getSalt();
			String password=categoryPage.getPassword();

//			byte[] srtbyte = salt.getBytes();
//			byte[] hashPassword = Digests.sha1(oldpassword.getBytes(), salt, HASH_INTERATIONS);
//			Encodes.encodeHex(hashPassword);
//			byte[] salt = Digests.generateSalt(SALT_SIZE);
//			entity.setSalt(Encodes.encodeHex(salt));
			byte[] hashPassword = Digests.sha1(oldpassword.getBytes(), Encodes.decodeHex(categoryPage.getSalt()), HASH_INTERATIONS);
			String passwordnum=	Encodes.encodeHex(hashPassword);
			if(!passwordnum.equals(password)){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "原密码不正确");
				return result;
			}
			if(oldpassword.equals(newpassword)){
				result.put("flag", AppTools.FAILED);
				result.put("msg", "新密码不能与旧密码一致");
				return result;
			}
			byte[] hashPasswordnum = Digests.sha1(newpassword.getBytes(), Encodes.decodeHex(categoryPage.getSalt()), HASH_INTERATIONS);
	
			cpService.updatepassword(id,Encodes.encodeHex(hashPasswordnum));
		}
		catch(Exception e){
//			result.put("flag", AppTools.Failed);
		}
		result.put("flag", AppTools.SUCCESS);
//		result.put("msg", "原密码不正确");
		return result;
	}
	
	@RequestMapping(value = "/queryUnassignRoles/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ExtIeopRoleVO> queryUnassignRoles(@PathVariable("id") String id) throws BusinessException {
		List<ExtIeopRoleVO> results= cpService.queryUnassignRoles(id);
		return results;
	}
	
	/**
	 * 修改密码
	* TODO description
	* @author 
	* @date 2017年1月10日
	* @param oldpwd
	* @param newpwd
	* @param model
	* @param request
	* @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
	public @ResponseBody Object modifyPwd(
			@RequestParam(value = "oldpwd") String oldpwd,
			@RequestParam(value = "newpwd") String newpwd,
			Model model, ServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			ExtIeopUserVO userVO = cpService.getBdUser(AppInvocationInfoProxy.getPk_User());
			
			byte[] hashPassword = Digests.sha1(oldpwd.getBytes(), Encodes.decodeHex(userVO.getSalt()), HASH_INTERATIONS);
			String checkPwd = Encodes.encodeHex(hashPassword);
			
			if(!checkPwd.equals(userVO.getPassword())){
				result.put("msg", "修改密码失败：原密码填写错误，请重新填写!");
				result.put("flag", "fail");
			}else{
				byte[] newPasswordByte = Digests.sha1(newpwd.getBytes(), Encodes.decodeHex(userVO.getSalt()), HASH_INTERATIONS);
				String newPwdInput = Encodes.encodeHex(newPasswordByte);
				userVO.setPassword(newPwdInput);
				cpService.updateEntity(userVO);
				result.put("msg", "修改密码成功!");
				result.put("flag", "true");
			}
			
		}catch (Exception e) {
			result.put("msg", "修改密码失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
		return result;  
	}
	
	
	@RequestMapping(value = "/findpwd", method = RequestMethod.POST)
	public @ResponseBody Object findpwd(
			@RequestParam(value = "userNameFind") String userNameFind,
			@RequestParam(value = "releNameFind") String releNameFind,
			@RequestParam(value = "emailFind") String emailFind,
			Model model, ServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<ExtIeopUserVO> userVOList = cpService.getBdUserByCodeAndName(userNameFind,null,null);
			if(null != userVOList && userVOList.size()==1){
				ExtIeopUserVO userVo = userVOList.get(0);
				if(!releNameFind.equals(userVo.getName())){
					result.put("msg", "账号信息、姓名、邮箱不正确，请重新填写!");
					result.put("flag", "fail");
				}else {
					String pkPsndoc = userVo.getPkPsndoc();
					if(null != pkPsndoc && pkPsndoc.length()>0){
						BdPsndocVO psnVo = psnService.queryPsnById(pkPsndoc);
						if(emailFind.equals(psnVo.getEmail())){
							//生成8位随机数
							String pskey = CommonUtils.getPass(8);
							//修改密码
							byte[] newPasswordByte = Digests.sha1(pskey.getBytes(), Encodes.decodeHex(userVo.getSalt()), HASH_INTERATIONS);
							String newPwdInput = Encodes.encodeHex(newPasswordByte);
							userVo.setPassword(newPwdInput);
							cpService.updateEntity(userVo);
							//发送邮件
							MailSenderInfo mailInfo = new MailSenderInfo();    
							mailInfo.setMailServerHost("email.yonyou.com");    
							mailInfo.setMailServerPort("25");    
							mailInfo.setValidate(true);    
							mailInfo.setUserName("test@yonyou.com");    
							mailInfo.setPassword("test");//您的邮箱密码    
							mailInfo.setFromAddress("test@yonyou.com");    
							mailInfo.setToAddress(emailFind);    
							mailInfo.setSubject("密码找回 -crm信息系统");    
							mailInfo.setContent("信息系统系统<br>用户名："+userNameFind+"<br>新密码："+pskey+"<br>此为系统邮件，请勿回复！");    
					         //这个类主要来发送邮件   
							SimpleMailSender sms = new SimpleMailSender();   
//					          sms.sendTextMail(mailInfo);//发送文体格式    
							sms.sendHtmlMail(mailInfo);//发送html格式   
							result.put("msg", "密码已经重置并已经发送到您的邮箱，请登录邮箱查看!");
							result.put("flag", "true");
						}else{
							result.put("msg", "账号信息、姓名、邮箱不正确，请重新填写!");
							result.put("flag", "fail");
						}
					}else{
						result.put("msg", "账号信息、姓名、邮箱不正确，请重新填写!");
						result.put("flag", "fail");
					}
				}
			}else{
				result.put("msg", "账号信息、姓名、邮箱不正确，请重新填写!");
				result.put("flag", "fail");
			}
			
		}catch (Exception e) {
			result.put("msg", "修改密码失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
		return result;  
	}
	
}
