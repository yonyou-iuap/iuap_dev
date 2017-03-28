package com.yonyou.iuap.crm.subsidy.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

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
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.crm.billcode.service.itf.ICommonComponentsService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.common.utils.ExcelWriterPoiWriter;
import com.yonyou.iuap.crm.enums.entity.AppEnumVO;
import com.yonyou.iuap.crm.enums.service.itf.IAppEnumService;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyFundbackVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyItemsVO;
import com.yonyou.iuap.crm.subsidy.entity.CountrysubsidyVO;
import com.yonyou.iuap.crm.subsidy.service.itf.ICountrysubsidyService;

/**
 * 
* <p>国补申报：</p>
* @author 
* @created 2016年11月21日 上午11:36:40
* @version 1.0
 */
@Controller
@RequestMapping(value = "/subsidy/country")
public class CountrysubsidyController {
	@Autowired
	private ICountrysubsidyService service;
	@Autowired
	private ICommonComponentsService commonService;
	@Autowired
	private IAppEnumService appEnumService;
	
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public @ResponseBody Page<CountrysubsidyVO> loadData(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) throws Exception {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams = Servlets.getParametersStartingWith(request, "search_");
			PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
			Page<CountrysubsidyVO> categoryPage = service.getCountrysubsidyBypage(searchParams, pageRequest);
			return categoryPage;
	}
	
	/**
	 * 获取按钮权限
	* TODO description
	* @author 
	* @date 2016年12月13日
	* @param model
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value="/mainFun",method=RequestMethod.GET)
	public @ResponseBody Object getFunList(
			Model model, ServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> funList = new ArrayList<String>();
			funList.add("新增");
			funList.add("修改");
			result.put("funList", funList);
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		}catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
		return result;  
	}

	/**
	 * 保存
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object saveOrderInfo(
			@RequestBody CountrysubsidyVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为NEW，才会插入新数据
			List<CountrysubsidyItemsVO> itemVOs = mainVo.getCountrysubsidyItems();
			List<CountrysubsidyFundbackVO> fundbackVOs = mainVo.getCountrysubsidyFundback();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			mainVo.setPk_countrysubsidy(AppTools.generatePK());
			mainVo.setStatus(VOStatus.NEW);
			mainVo.setVstatus(DictCode.COUNTRY_SUBSIDY_STATUS_SAVE);//已保存
			mainVo.setVbillno(commonService.generateOrderNo("CS"));//单号生成规则
			mainVo.setPk_org(AppInvocationInfoProxy.getPk_Corp());//组织
			mainVo.setVdeclaredept(AppInvocationInfoProxy.getPk_Dept());//部门
			mainVo.setCreator(AppInvocationInfoProxy.getPk_User());//取登录用户的组织和部门信息
			mainVo.setCreationtime(sdf.format(new Date()));

			
			/*if(null != itemVOs && itemVOs.size()>0){
				for(int i=0;i<itemVOs.size();i++){
					CountrysubsidyItemsVO itemVO = itemVOs.get(i);
					itemVO.setPk_countrysubsidy_items(AppTools.generatePK());
					itemVO.setCreationtime(sdf.format(new Date()));
					itemVO.setCreator(AppInvocationInfoProxy.getPk_User());
				}
			}
			if(null != fundbackVOs && fundbackVOs.size()>0){
				for(int i=0;i<fundbackVOs.size();i++){
					CountrysubsidyFundbackVO fundbackVO = fundbackVOs.get(i);
					fundbackVO.setPk_countrysubsidy(AppTools.generatePK());
					fundbackVO.setCreationtime(sdf.format(new Date()));
					fundbackVO.setCreator(AppInvocationInfoProxy.getPk_User());
				}
			}*/
			
			service.saveCountrysbusidy(mainVo);
			
			
//			mainVo = service.getCountrysubsidyWithPk(pk);
			result.put("msg", "保存成功!");
//			result.put("data", mainVo);
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 修改
	* TODO description
	* @author 
	* @date 2016年11月21日
	* @param mainVo
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Object updateOrderInfo(
			@RequestBody CountrysubsidyVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为NEW，才会插入新数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mainVo.setStatus(VOStatus.UPDATED);
//			mainVo.setStatus(30101001);//需要使用常量类
//			mainVo.setVbillno(vbillno);//单号生成规则
//			mainVo.setPk_org(pk_org);//组织
//			mainVo.setVdeclaredept(vdeclaredept);//部门
			mainVo.setModifier(AppInvocationInfoProxy.getPk_User());//取登录用户的组织和部门信息
			mainVo.setModifiedtime(sdf.format(new Date()));
			service.updateCountrysbusidy(mainVo);
//			mainVo = service.getCountrysubsidyWithPk(pk);
			result.put("msg", "修改成功!");
//			result.put("data", mainVo);
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "修改失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 进入编辑界面，根据外键查询两个子表的信息
	* TODO description
	* @author 
	* @date 2016年11月23日
	* @param pk
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/editItemQuery", method = RequestMethod.GET)
	public @ResponseBody Object editItemQuery(
			@RequestParam String pk, 
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			@RequestParam(value = "fundpage", defaultValue = "0") int pageFundNumber,
			@RequestParam(value = "fundpage.size", defaultValue = "10") int pageFundSize,
			@RequestParam(value = "fundsortType", defaultValue = "auto") String sortFundType,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
//			List<CountrysubsidyItemsVO> itemList = service.queryItemWithFk(pk);
//			List<CountrysubsidyFundbackVO> fundbackList = service.queryFundbackWithFk(pk);
//			result.put("itemList", itemList);
//			result.put("fundbackList", fundbackList);
			
			CountrysubsidyVO mainVO = service.getCountrysubsidyWithPk(pk);
			
			//查询条件，根据外键查询
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("pk_countrysubsidy", pk);
			
			//国补明细分页查询
			PageRequest pageItemRequest = buildPageRequest(pageItemNumber, pageItemSize, "auto");
			Page<CountrysubsidyItemsVO> itemList = service.getCountrysubsidyItemBypage(searchParams, pageItemRequest);
			
			//国补回款明细分页查询			
			PageRequest pageFundRequest = buildPageRequest(pageFundNumber, pageFundSize, "auto");
			Page<CountrysubsidyFundbackVO> fundbackList = service.getCountrysubsidyFundbackBypage(searchParams, pageFundRequest);
			
			result.put("mainVO", mainVO);
			result.put("itemList", itemList);
			result.put("fundbackList", fundbackList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 查看
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param pk
	* @param pageNumber
	* @param pageSize
	* @param sortType
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/viewItemQuery", method = RequestMethod.GET)
	public @ResponseBody Object viewItemQuery(
			@RequestParam String pk, 
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			@RequestParam(value = "fundpage", defaultValue = "0") int pageFundNumber,
			@RequestParam(value = "fundpage.size", defaultValue = "10") int pageFundSize,
			@RequestParam(value = "fundsortType", defaultValue = "auto") String sortFundType,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//查询条件，根据外键查询
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("pk_countrysubsidy", pk);
			
			//国补明细分页查询
			PageRequest pageItemRequest = buildPageRequest(pageItemNumber, pageItemSize, "auto");
			Page<CountrysubsidyItemsVO> itemList = service.getCountrysubsidyItemBypage(searchParams, pageItemRequest);
			
			//国补回款明细分页查询			
			PageRequest pageFundRequest = buildPageRequest(pageFundNumber, pageFundSize, "auto");
			Page<CountrysubsidyFundbackVO> fundbackList = service.getCountrysubsidyFundbackBypage(searchParams, pageFundRequest);
			
			result.put("itemList", itemList);
			result.put("fundbackList", fundbackList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 卡片：车辆明细分页查询
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param pk
	* @param pageItemNumber
	* @param pageItemSize
	* @param sortItemType
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/viewItemPageQuery", method = RequestMethod.GET)
	public @ResponseBody Object viewItemPageQuery(
			@RequestParam String pk, 
			@RequestParam String vvin, 
			@RequestParam(value = "itempage", defaultValue = "0") int pageItemNumber,
			@RequestParam(value = "itempage.size", defaultValue = "10") int pageItemSize,
			@RequestParam(value = "itemsortType", defaultValue = "auto") String sortItemType,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//查询条件，根据外键查询
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("pk_countrysubsidy", pk);
			searchParams.put("vvin", vvin);
			
			//国补明细分页查询
			PageRequest pageItemRequest = buildPageRequest(pageItemNumber, pageItemSize, "auto");
			Page<CountrysubsidyItemsVO> itemList = service.getCountrysubsidyItemBypage(searchParams, pageItemRequest);
			
			result.put("itemList", itemList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 卡片：车辆回款分页查询
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param pk
	* @param pageFundNumber
	* @param pageFundSize
	* @param sortFundType
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/viewFundbackPageQuery", method = RequestMethod.GET)
	public @ResponseBody Object viewFundbackPageQuery(
			@RequestParam String pk, 
			@RequestParam String vvin, 
			@RequestParam(value = "fundpage", defaultValue = "0") int pageFundNumber,
			@RequestParam(value = "fundpage.size", defaultValue = "10") int pageFundSize,
			@RequestParam(value = "fundsortType", defaultValue = "auto") String sortFundType,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//查询条件，根据外键查询
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("pk_countrysubsidy", pk);
			searchParams.put("vvin", vvin);
			
			//国补回款明细分页查询			
			PageRequest pageFundRequest = buildPageRequest(pageFundNumber, pageFundSize, "auto");
			Page<CountrysubsidyFundbackVO> fundbackList = service.getCountrysubsidyFundbackBypage(searchParams, pageFundRequest);
			
			result.put("fundbackList", fundbackList);
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 根据外键删除车辆信息
	* TODO description
	* @author 
	* @date 2016年11月24日
	* @param pk
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/delItemWithPk", method = RequestMethod.POST)
	public @ResponseBody Object delItemWithPk(
			@RequestBody List<CountrysubsidyItemsVO> entitys, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			service.delBatchItem(entitys);
			result.put("msg", "删除成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "删除失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 删除国补申请单信息
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param entitys
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Object deleteOrderInfo(
			@RequestBody List<CountrysubsidyVO> entitys,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//设置状态为NEW，才会插入新数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null != entitys && entitys.size()>0){
				for(CountrysubsidyVO mainVo : entitys){
					mainVo.setModifiedtime(sdf.format(new Date()));
					mainVo.setModifier(AppInvocationInfoProxy.getPk_User());
					mainVo.setStatus(VOStatus.DELETED);
				}
				service.deleteBatchCountrysubsidy(entitys);
			}
			result.put("msg", "删除成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "删除失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 国补申报单完结
	* TODO description
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public @ResponseBody Object finishOrderInfo(
			@RequestBody List<CountrysubsidyVO> entitys,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(null != entitys && entitys.size()>0){
				service.finishBatchCountrysubsidy(entitys);
			}
			result.put("msg", "完结成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "完结失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 批量撤销完结国补申报单
	* TODO description
	* @author 
	* @date 2016年12月1日
	* @param entitys
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/unfinish", method = RequestMethod.POST)
	public @ResponseBody Object unFinishOrderInfo(
			@RequestBody List<CountrysubsidyVO> entitys,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(null != entitys && entitys.size()>0){
				service.unFinishBatchCountrysubsidy(entitys);
			}
			result.put("msg", "撤销完结成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "撤销完结失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	@RequestMapping(value = "/vehicelUpload")
	public @ResponseBody Object vehicelUpload(
			@RequestParam(value = "fileName", required = false) MultipartFile  fileName,
			@RequestParam String pk_countrysubsidy,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Workbook workBook = Workbook.getWorkbook(fileName.getInputStream());
			Sheet sheet = workBook.getSheet(0);
			int sheetRows = sheet.getRows();
			//是否商用车	购车领域	购车城市	车辆运营单位	车辆种类	车辆用途	车辆型号	车辆识别代码	
			//车辆牌照	申请补贴标准（万元）	累计回款金额（万元）	未回款金额（万元）	购买价格（万元）	
			//发票号	发票年	发票月	发票日	行驶证年	行驶证月	行驶证日	应回款日期	超期回款日期	
			//申报状态	申报时间	审批节点	未申报状态	未申报说明	完结原因
			StringBuffer errorBuf = new StringBuffer();
			//检查导入文件的VIN是否重复
			Map<String,String> vinMap = new HashMap<String,String>();
			List<CountrysubsidyItemsVO> itemVOList = new ArrayList<CountrysubsidyItemsVO>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
			/**
			 * 枚举转换
			 */
			Map<String,String> yesOrNo = new HashMap<String,String>();
			List<AppEnumVO> yesOrNoList = appEnumService.queryByVtype(DictCode.BILL_YES_NO_TYPE);
			if(null != yesOrNoList && yesOrNoList.size()>0){
				for(AppEnumVO enumVO : yesOrNoList){
					yesOrNo.put(enumVO.getVname(), enumVO.getVcode());
				}
			}
//			yesOrNo.put("是", "10011001");
//			yesOrNo.put("否", "10011002");
			//申报状态
			Map<String,String> appStatusMap = new HashMap<String,String>();
//			appStatusMap.put("已申报", "20131001");
//			appStatusMap.put("未申报", "20131002");
			List<AppEnumVO> appStatusMapList = appEnumService.queryByVtype(DictCode.COUNTRY_SUBSIDY_APP_STATUS);
			if(null != appStatusMapList && appStatusMapList.size()>0){
				for(AppEnumVO enumVO : appStatusMapList){
					appStatusMap.put(enumVO.getVname(), enumVO.getVcode());
				}
			}
			//未申报状态
			Map<String,String> unAppStatusMap = new HashMap<String,String>();
//			unAppStatusMap.put("未销售给终端", "20141001");
//			unAppStatusMap.put("已销售未上牌", "20141002");
//			unAppStatusMap.put("已上牌无资料", "20141003");
//			unAppStatusMap.put("已上牌有资料", "20141004");
//			unAppStatusMap.put("未交车", "20141005");
			List<AppEnumVO> unAppStatusMapList = appEnumService.queryByVtype(DictCode.COUNTRY_SUBSIDY_UNAPP_STATUS);
			if(null != unAppStatusMapList && unAppStatusMapList.size()>0){
				for(AppEnumVO enumVO : unAppStatusMapList){
					unAppStatusMap.put(enumVO.getVname(), enumVO.getVcode());
				}
			}
			
			for(int i=1;i<sheetRows;i++){
				CountrysubsidyItemsVO tempItemVo = new CountrysubsidyItemsVO();
				tempItemVo.setPk_countrysubsidy(pk_countrysubsidy);
				boolean checkFlag = true;
				String var1 = sheet.getCell(0,i).getContents();//是否商用车
				checkFlag = checkFlag & checkImportColumn(errorBuf,var1,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				tempItemVo.setViscommercial(yesOrNo.get(var1));
				
				String var2 = sheet.getCell(1,i).getContents();//购车领域
				checkFlag = checkFlag & checkImportColumn(errorBuf,var2,"第"+(i+1)+"购车领域不能为空！",null,null,null,null);
				tempItemVo.setVbuydomain(var2);
				
				String var3 = sheet.getCell(2,i).getContents();//购车城市
				checkFlag = checkFlag & checkImportColumn(errorBuf,var3,"第"+(i+1)+"购车城市不能为空！",null,null,null,null);
				tempItemVo.setVbuycity(var3);
				
				String var4 = sheet.getCell(3,i).getContents();//车辆运营单位
				checkFlag = checkFlag & checkImportColumn(errorBuf,var4,"第"+(i+1)+"车辆运营单位不能为空！",null,null,null,null);
				tempItemVo.setVrundept(var4);
				
				String var5 = sheet.getCell(4,i).getContents();//车辆种类
				checkFlag = checkFlag & checkImportColumn(errorBuf,var5,"第"+(i+1)+"车辆种类不能为空！",null,null,null,null);
				tempItemVo.setVvehiclekind(var5);
				
				String var6 = sheet.getCell(5,i).getContents();//车辆用途
				checkFlag = checkFlag & checkImportColumn(errorBuf,var6,"第"+(i+1)+"车辆用途不能为空！",null,null,null,null);
				tempItemVo.setVvehiclepurpose(var6);
				
				String var7 = sheet.getCell(6,i).getContents();//车辆型号
				checkFlag = checkFlag & checkImportColumn(errorBuf,var7,"第"+(i+1)+"车辆型号不能为空！",null,null,null,null);
				tempItemVo.setVvehiclemodel(var7);
				
				String var8 = sheet.getCell(7,i).getContents();//车辆识别代码
				checkFlag = checkFlag & checkImportColumn(errorBuf,var8,"第"+(i+1)+"车辆识别代码不能为空！",null,null,null,null);
				if(null != var8 && var8.length()>0 && null != vinMap.get(var8)){
					errorBuf.append("第"+(i+1)+"车辆识别代码在导入文档中重复！");
					checkFlag = false;
					vinMap.put(var8,var8);
				}
				tempItemVo.setVvin(var8);
				
				String var9 = sheet.getCell(8,i).getContents();//车辆牌照
				checkFlag = checkFlag & checkImportColumn(errorBuf,var9,"第"+(i+1)+"车辆牌照不能为空！",null,null,null,null);
				tempItemVo.setVlicense(var9);
				
				String var10 = sheet.getCell(9,i).getContents();//申请补贴标准（万元）
				boolean var10Boolean= checkImportColumn(errorBuf,var10,"第"+(i+1)+"申请补贴标准（万元不能为空！",null,null,null,null);
				checkFlag = checkFlag & var10Boolean;
				if(var10Boolean){
					tempItemVo.setNsubsidystandard(Double.parseDouble(var10));
					tempItemVo.setNnotback(Double.parseDouble(var10));
				}
				
				String var11 = sheet.getCell(10,i).getContents();//累计回款金额（万元）
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var11,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				tempItemVo.setNtotalback(0d);
				
				String var12 = sheet.getCell(11,i).getContents();//未回款金额（万元）
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var12,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				//通过申请回款金额
				
				String var13 = sheet.getCell(12,i).getContents();//购买价格（万元）
				boolean var13Boolean = checkImportColumn(errorBuf,var13,"第"+(i+1)+"购买价格（万元）不能为空！",null,null,null,null);
				checkFlag = checkFlag & var13Boolean;
				if(var13Boolean){
					tempItemVo.setNpurchaseprice(Double.parseDouble(var13));
				}
				
				String var14 = sheet.getCell(13,i).getContents();//发票号
				checkFlag = checkFlag & checkImportColumn(errorBuf,var14,"第"+(i+1)+"发票号不能为空！",null,null,null,null);
				tempItemVo.setVinvoiceno(var14);
				
				String var15 = sheet.getCell(14,i).getContents();//发票年
				boolean var15Boolean = checkImportColumn(errorBuf,var15,"第"+(i+1)+"发票年不能为空！",null,null,null,null);
				checkFlag = checkFlag & var15Boolean;
				if(var15Boolean){
					tempItemVo.setNinvoiceyear(Integer.parseInt(var15));
				}
				
				String var16 = sheet.getCell(15,i).getContents();//发票月
				boolean var16Boolean = checkImportColumn(errorBuf,var16,"第"+(i+1)+"发票月不能为空！",null,null,null,null);
				checkFlag = checkFlag & var16Boolean;
				if(var16Boolean){
					tempItemVo.setNinvoicemonth(Integer.parseInt(var16));
				}
				
				String var17 = sheet.getCell(16,i).getContents();//发票日
				boolean var17Boolean = checkImportColumn(errorBuf,var17,"第"+(i+1)+"发票日不能为空！",null,null,null,null);
				checkFlag = checkFlag & var17Boolean;
				if(var17Boolean){
					tempItemVo.setNinvoiceday(Integer.parseInt(var17));
				}
				
				String var18 = sheet.getCell(17,i).getContents();//行驶证年
				boolean var18Boolean = checkImportColumn(errorBuf,var18,"第"+(i+1)+"行驶证年不能为空！",null,null,null,null);
				checkFlag = checkFlag & var18Boolean;
				if(var18Boolean){
					tempItemVo.setNdlicenseyear(Integer.parseInt(var18));
				}
				
				String var19 = sheet.getCell(18,i).getContents();//行驶证月
				boolean var19Boolean = checkImportColumn(errorBuf,var19,"第"+(i+1)+"行驶证月不能为空！",null,null,null,null);
				checkFlag = checkFlag & var19Boolean;
				if(var19Boolean){
					tempItemVo.setNdlicensemonth(Integer.parseInt(var19));
				}
				
				String var20 = sheet.getCell(19,i).getContents();//行驶证日
				boolean var20Boolean = checkImportColumn(errorBuf,var20,"第"+(i+1)+"行驶证日不能为空！",null,null,null,null);
				checkFlag = checkFlag & var20Boolean;
				if(var20Boolean){
					tempItemVo.setNdlicenseday(Integer.parseInt(var20));
				}
				
				//String var21 = sheet.getCell(20,i).getContents();//应回款日期=行驶证日期+180天
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var21,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				
				
				//String var22 = sheet.getCell(21,i).getContents();//超期回款日期=行驶证日期+360天
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var22,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				
				String var23 = sheet.getCell(22,i).getContents();//申报状态
				checkFlag = checkFlag & checkImportColumn(errorBuf,var23,null,
						appStatusMap,"第"+(i+1)+"申报状态只能为“已申报”和“未申报”！",null,null);
				
//				String var24 = sheet.getCell(23,i).getContents();//申报时间=当前时间
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var24,null,null,null,
//						"\\d{4}[.]((([0]?[1-9])|(1[0-2])))([.](([0]?[1-9])|([1-2][0-9])|(3[0-1])))?"
//						,"第"+(i+1)+"申报时间格式不正确，应为yyyy.mm.dd！");
				
				String var25 = sheet.getCell(24,i).getContents();//审批节点
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var25,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				tempItemVo.setVapprovepoint(var25);
				
				String var26 = sheet.getCell(25,i).getContents();//未申报状态	
				checkFlag = checkFlag & checkImportColumn(errorBuf,var26,null,
						unAppStatusMap,"第"+(i+1)+"未申报状态只能为“未销售给终端”、“已销售未上牌”、“已上牌无资料”、“已上牌有资料”和“未交车”！",null,null);
				
				String var27 = sheet.getCell(26,i).getContents();//未申报说明	
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var27,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				tempItemVo.setVnotdeclarecomments(var27);
				
				String var28 = sheet.getCell(27,i).getContents();//完结原因
//				checkFlag = checkFlag & checkImportColumn(errorBuf,var28,"第"+(i+1)+"是否商用车不能为空！",null,null,null,null);
				
				if(checkFlag){
					//应回款日期
					String ndlicenseDateStr = var18+"."+var19+"."+var20;
					Date ndlicenseDate = sdf2.parse(ndlicenseDateStr);
					Calendar ca = Calendar.getInstance();
					ca.setTime(ndlicenseDate);
					//加6个月为应回款日期
					ca.add(Calendar.MONTH, 6);
					tempItemVo.setDmustbackdate(sdfDate.format(ca.getTime()));//应回款日期
					//加1年为超期回款日期
					ca.add(Calendar.MONTH, 6);
					tempItemVo.setDoverduedate(sdfDate.format(ca.getTime()));//超期回款日期
					
					tempItemVo.setDdeclaredate(sdf.format(new Date()));//申报时间
					tempItemVo.setVdeclarestatus(appStatusMap.get(var23));//申报状态
					tempItemVo.setVnotdeclarestatus(unAppStatusMap.get(var26));//未申报状态
					tempItemVo.setCreator(AppInvocationInfoProxy.getPk_User());
					tempItemVo.setCreationtime(sdf.format(new Date()));
					tempItemVo.setPk_countrysubsidy_items(AppTools.generatePK());
					itemVOList.add(tempItemVo);
				}
			}
			
			if(null != itemVOList && itemVOList.size()>0){
				String errorMsg = service.importSubsidyItem(itemVOList);
				errorBuf.append(errorMsg);
			}
			if(errorBuf.length()==0){
				errorBuf.append("导入成功！");
			}
			//设置状态为NEW，才会插入新数据
			result.put("msg", errorBuf.toString());
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "导入失败!");
			result.put("flag", "fail");
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 国补申报车辆回款导入
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param fileName
	* @param pk_countrysubsidy
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/fundbackUpload")
	public @ResponseBody Object fundbackUpload(
			@RequestParam(value = "fileName", required = false) MultipartFile  fileName,
			@RequestParam String pk_countrysubsidy,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Workbook workBook = Workbook.getWorkbook(fileName.getInputStream());
			Sheet sheet = workBook.getSheet(0);
			int sheetRows = sheet.getRows();
			//车辆识别代码	车系	所属销售部	申报补贴标准（万元）	应回款日期	实际回款金额（万元）	实际回款日期
			StringBuffer errorBuf = new StringBuffer();
			//检查导入文件的VIN是否重复
			Map<String,String> vinMap = new HashMap<String,String>();
			List<CountrysubsidyFundbackVO> itemVOList = new ArrayList<CountrysubsidyFundbackVO>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
			
			for(int i=1;i<sheetRows;i++){
				CountrysubsidyFundbackVO tempItemVo = new CountrysubsidyFundbackVO();
				tempItemVo.setPk_countrysubsidy(pk_countrysubsidy);
				boolean checkFlag = true;
				String var1 = sheet.getCell(0,i).getContents();//车辆识别代码
				checkFlag = checkFlag & checkImportColumn(errorBuf,var1,"第"+(i+1)+"车辆识别代码不能为空！",null,null,null,null);
				if(null != var1 && null!=vinMap.get(var1)){
					errorBuf.append("第"+(i+1)+"车辆识别代码在导入文件中重复！");
					checkFlag = false;
					vinMap.put(var1, var1);
				}
				
				String var2 = sheet.getCell(5,i).getContents();//实际回款金额（万元）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var2,"第"+(i+1)+"实际回款金额（万元）不能为空！",null,null,
						"[-]?\\d+[.]?\\d*","第"+(i+1)+"实际回款金额（万元）只能为数字，正数表示回款，负数表示红冲！");
				
				String var3 = sheet.getCell(6,i).getContents();//实际回款日期
				checkFlag = checkFlag & checkImportColumn(errorBuf,var3,"第"+(i+1)+"实际回款日期不能为空！",null,null,
						"\\d{4}[.]((([0]?[1-9])|(1[0-2])))([.](([0]?[1-9])|([1-2][0-9])|(3[0-1])))?"
						,"第"+(i+1)+"实际回款日期格式不正确，应为yyyy.mm.dd！");
				
				if(checkFlag){
					tempItemVo.setVvin(var1);
					tempItemVo.setNfactback(Double.parseDouble(var2));
					tempItemVo.setDfactbackdate(sdf.format(sdf2.parse(var3)));
					tempItemVo.setCreator(AppInvocationInfoProxy.getPk_User());
					tempItemVo.setCreationtime(sdf.format(new Date()));
					tempItemVo.setPk_countrysubsidy_fundback(AppTools.generatePK());
					itemVOList.add(tempItemVo);
				}
			}
			
			if(null != itemVOList && itemVOList.size()>0){
				String importMsg = service.importSubsidyFundback(itemVOList);
				errorBuf.append(importMsg);
			}
			
			if(errorBuf.length()==0){
				errorBuf.append("导入成功！");
			}
			//设置状态为NEW，才会插入新数据
			result.put("msg", errorBuf.toString());
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "导入失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());			
		}
        return result;  
	}
	
	/**
	 * 检查导入字段是否为空，是否合法
	* TODO description
	* @author 
	* @date 2016年11月30日
	* @param errorBuf
	* @param var
	* @param nullMsg  存在为空提示信息时，才判断是否为空
	* @param comboMap 存在下拉框信息时，才判断是否下拉数据
	* @param comboMsg
	* @param pattern  存在正则表达式是，才判断数据有效性
	* @param regErrorMsg
	* @return 如果校验通过则返回true，否则返回false
	 */
	public boolean checkImportColumn(StringBuffer errorBuf,String var,String nullMsg,
			Map<String,String> comboMap,String comboMsg,
			String pattern,String regErrorMsg){
		boolean returnFalg = true;
		//检查字段是否为空
		if(null != nullMsg && nullMsg.length()>0 && (null==var || var.length()==0)){
			errorBuf.append(nullMsg);
			returnFalg = false;
		}else if(null!=var && var.length()>0){
			//如果是枚举值，检查是否在范围内
			if(null != comboMap && null==comboMap.get(var)){
				errorBuf.append(comboMsg);
				returnFalg = false;
			}
			//如果存在正则表达式，则根据表达式校验是否通过
			if(null != pattern && pattern.length()>0){
				// 创建 Pattern 对象
			    Pattern r = Pattern.compile(pattern);	
			    if(!r.matcher(var).matches()){
			    	errorBuf.append(regErrorMsg);
			    	returnFalg = false;
			    }
			}
		}
		return returnFalg;
	}
	
	/**
	 * 国补申报车辆信息导出
	* TODO description
	* @author 
	* @date 2016年11月29日
	* @param entitys
	* @param model
	* @param request
	* @param response
	* @throws BusinessException
	 */
	@RequestMapping(value = "/vehicelDownload", method = RequestMethod.POST)
	public void vehicelDownload(
//			@RequestBody List<CountrysubsidyItemsVO> entitys,
			@RequestParam String pkVehicleIds,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		try {
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//标题
			List<Object> listTitle = new LinkedList<Object>();
			//购车领域	购车城市	车辆运营单位	车辆种类	车辆用途	车辆型号	车辆识别代码	车辆牌照	
			//申请补贴标准（万元）	累计回款金额（万元）	未回款金额（万元）	购买价格（万元）	发票号	发票年	
			//发票月	发票日	行驶证年	行驶证月	行驶证日	应回款日期	超期回款日期	申报状态	申报时间	
			//审批节点	未申报状态	未申报说明
			listTitle.add("是否商用车");
			listTitle.add("购车领域");
			listTitle.add("购车城市");
			listTitle.add("车辆运营单位");
			listTitle.add("车辆种类");
			listTitle.add("车辆用途");
			listTitle.add("车辆型号");
			listTitle.add("车辆识别代码");
			listTitle.add("车辆牌照");
			listTitle.add("申请补贴标准（万元）");
			listTitle.add("累计回款金额（万元）");
			listTitle.add("未回款金额（万元）");
			listTitle.add("购买价格（万元）");
			listTitle.add("发票号");
			listTitle.add("发票年");
			listTitle.add("发票月");
			listTitle.add("发票日");
			listTitle.add("行驶证年");
			listTitle.add("行驶证月");
			listTitle.add("行驶证日");
			listTitle.add("应回款日期");
			listTitle.add("超期回款日期");
			listTitle.add("申报状态");
			listTitle.add("申报时间");
			listTitle.add("审批节点");
			listTitle.add("未申报状态");
			listTitle.add("未申报说明");
			listTitle.add("完结原因");
			list.add(listTitle);
			/**
			 * 枚举转换
			 */
			Map<String,String> yesOrNo = new HashMap<String,String>();
			List<AppEnumVO> yesOrNoList = appEnumService.queryByVtype(DictCode.BILL_YES_NO_TYPE);
			if(null != yesOrNoList && yesOrNoList.size()>0){
				for(AppEnumVO enumVO : yesOrNoList){
					yesOrNo.put(enumVO.getVcode(),enumVO.getVname());
				}
			}
			
			if(null != pkVehicleIds && pkVehicleIds.length()>0){
				pkVehicleIds = pkVehicleIds.replaceAll(",", "','");
				String sql = " and pk_countrysubsidy_items in ('"+pkVehicleIds+"')";
				List<CountrysubsidyItemsVO> exportList = service.queryItemWithContiditon(sql);
				if(null != exportList && exportList.size()>0){
					for(int i = 0;i<exportList.size();i++){
						//购车领域	购车城市	车辆运营单位	车辆种类	车辆用途	车辆型号	车辆识别代码	车辆牌照	
						//申请补贴标准（万元）	累计回款金额（万元）	未回款金额（万元）	购买价格（万元）	发票号	发票年	
						//发票月	发票日	行驶证年	行驶证月	行驶证日	应回款日期	超期回款日期	申报状态	申报时间	
						//审批节点	未申报状态	未申报说明
						CountrysubsidyItemsVO tempBean = exportList.get(i);
						List<Object> tempList = new LinkedList<Object>();
						tempList.add(yesOrNo.get(tempBean.getViscommercial()));//是否商用车
						tempList.add(tempBean.getVbuydomain());//购车领域
						tempList.add(tempBean.getVbuycity());//购车城市
						tempList.add(tempBean.getVrundept());//车辆运营单位
						tempList.add(tempBean.getVvehiclekind());//车辆种类
						tempList.add(tempBean.getVapprovepoint());//车辆用途
						tempList.add(tempBean.getVvehiclemodel());//车辆型号
						tempList.add(tempBean.getVvin());//车辆识别代码
						tempList.add(tempBean.getVlicense());//车辆牌照
						tempList.add(tempBean.getNsubsidystandard());//申请补贴标准（万元）
						tempList.add(tempBean.getNtotalback());//累计回款金额（万元）
						tempList.add(tempBean.getNnotback());//未回款金额（万元）
						tempList.add(tempBean.getNpurchaseprice());//购买价格（万元）
						tempList.add(tempBean.getVinvoiceno());//发票号
						tempList.add(tempBean.getNinvoiceyear());//发票年
						tempList.add(tempBean.getNinvoicemonth());//发票月
						tempList.add(tempBean.getNinvoiceday());//发票日
						tempList.add(tempBean.getNdlicenseyear());//行驶证年
						tempList.add(tempBean.getNdlicensemonth());//行驶证月
						tempList.add(tempBean.getNdlicenseday());//行驶证日
						tempList.add(tempBean.getDmustbackdate());//应回款日期
						tempList.add(tempBean.getDoverduedate());//超期回款日期
						tempList.add(tempBean.getVdeclarestatus());//申报状态
						tempList.add(tempBean.getDdeclaredate());//申报时间	
						tempList.add(tempBean.getVapprovepoint());//审批节点
						tempList.add(tempBean.getVnotdeclarestatus());//未申报状态
						tempList.add(tempBean.getVnotdeclarecomments());//未申报说明
						tempList.add("");//完结原因
						list.add(tempList);
					}
				}
			}
			
			
			ExcelWriterPoiWriter.writeExcel(response,list,"国补申报车辆信息","国补申报车辆信息");
		} catch (Exception e) {
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 国补申报单导出
	* TODO description
	* @author 
	* @date 2017年1月19日
	* @param model
	* @param request
	* @param response
	* @throws BusinessException
	 */
	@RequestMapping(value = "/countrySublidDownload", method = RequestMethod.POST)
	public void countrySublidDownload(
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		try {
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams = Servlets.getParametersStartingWith(request, "search_");
			List<CountrysubsidyVO> countrySublidList = service.getCountrysubsidyByList(searchParams);
			
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//标题
			List<Object> listTitle = new LinkedList<Object>();
			//国补申报单号	企业申报文号	录入日期	申报组织	申报部门	申报法人	政府批文文号	
			//申报城市	资料是否齐全	资料是否审核	状态	创建时间	创建人	修改时间	修改人
			listTitle.add("国补申报单号");
			listTitle.add("企业申报文号");
			listTitle.add("录入日期");
			listTitle.add("申报组织");
			listTitle.add("申报部门");
			listTitle.add("申报法人");
			listTitle.add("政府批文文号");
			listTitle.add("申报城市");
			listTitle.add("资料是否齐全");
			listTitle.add("资料是否审核");
			listTitle.add("状态");
			listTitle.add("创建时间");
			listTitle.add("创建人");
			listTitle.add("修改时间");
			listTitle.add("修改人");
			list.add(listTitle);
			/**
			 * 枚举转换
			 */
			Map<String,String> yesOrNo = new HashMap<String,String>();
			List<AppEnumVO> yesOrNoList = appEnumService.queryByVtype(DictCode.BILL_YES_NO_TYPE);
			if(null != yesOrNoList && yesOrNoList.size()>0){
				for(AppEnumVO enumVO : yesOrNoList){
					yesOrNo.put(enumVO.getVcode(),enumVO.getVname());
				}
			}
			//申报状态
			Map<String,String> appStatusMap = new HashMap<String,String>();
			List<AppEnumVO> appStatusMapList = appEnumService.queryByVtype(DictCode.COUNTRY_SUBSIDY_STATUS_TYPE);
			if(null != appStatusMapList && appStatusMapList.size()>0){
				for(AppEnumVO enumVO : appStatusMapList){
					appStatusMap.put(enumVO.getVcode(),enumVO.getVname());
				}
			}

			
			if(null != countrySublidList && countrySublidList.size()>0){
				for(int i = 0;i<countrySublidList.size();i++){
					//国补申报单号	企业申报文号	录入日期	申报组织	申报部门	申报法人	政府批文文号	
					//申报城市	资料是否齐全	资料是否审核	状态	创建时间	创建人	修改时间	修改人
					CountrysubsidyVO tempBean = countrySublidList.get(i);
					List<Object> tempList = new LinkedList<Object>();
					tempList.add(tempBean.getVbillno());//国补申报单号
					tempList.add(tempBean.getVdeclareno());//企业申报文号
					tempList.add(tempBean.getDentrydate());//录入日期
					tempList.add(tempBean.getPk_org());//申报组织
					tempList.add(tempBean.getVdeclaredept());//申报部门
					tempList.add(tempBean.getVdeclarelegal());//申报法人
					tempList.add(tempBean.getVgovernmentappno());//政府批文文号
					tempList.add(tempBean.getVdeclarecity());//申报城市
					tempList.add(yesOrNo.get(tempBean.getVisdatacomplete()));//资料是否齐全
					tempList.add(yesOrNo.get(tempBean.getVisdataapprove()));//资料是否审核
					tempList.add(appStatusMap.get(tempBean.getVstatus()));//状态
					tempList.add(tempBean.getCreationtime());//创建时间
					tempList.add(tempBean.getCreator());//创建人
					tempList.add(tempBean.getModifiedtime());//修改时间
					tempList.add(tempBean.getModifier());//修改人
					list.add(tempList);
				}
			}
			
			
			ExcelWriterPoiWriter.writeExcel(response,list,"国补申报单信息","国补申报单信息");
		} catch (Exception e) {
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 国补申报车辆回款模板下载
	* TODO description
	* @author 
	* @date 2016年12月13日
	* @param model
	* @param request
	* @param response
	* @throws BusinessException
	 */
	@RequestMapping(value = "/fundbackDownload", method = RequestMethod.POST)
	public void vehicleFundbackDownload(
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		try {
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//标题
			List<Object> listTitle = new LinkedList<Object>();
			listTitle.add("车辆识别代码");
			listTitle.add("车系");
			listTitle.add("所属销售部");
			listTitle.add("申报补贴标准（万元）");
			listTitle.add("应回款日期");
			listTitle.add("实际回款金额（万元）");
			listTitle.add("实际回款日期");
			list.add(listTitle);
			
			ExcelWriterPoiWriter.writeExcel(response,list,"国补申报车辆回款信息","国补申报车辆回款信息");
		} catch (Exception e) {
//			logger.error("保存出错!",e);
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 获取人员基本信息
	* TODO description
	* @author 
	* @date 2016年12月12日
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/infoDefault", method = RequestMethod.GET)
	public @ResponseBody Object getUserDefaultInfo(
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("pkCorp", AppInvocationInfoProxy.getPk_Corp());//组织
			result.put("pkDept", AppInvocationInfoProxy.getPk_Dept());//部门
			result.put("pkUser", AppInvocationInfoProxy.getPk_User());//用户
			result.put("pk_countrysubsidy", AppTools.generatePK());//主键
			result.put("msg", "查询成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "查询失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
        return result;  
	}
	
	/**
	 * 创建分页请求
	* TODO description
	* @author 
	* @date 2016年11月28日
	* @param pageNumber
	* @param pagzSize
	* @param sortType
	* @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "creationtime");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "creationtime");
		} else if ("pk_projectapply".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_countrysubsidy");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
	
}
