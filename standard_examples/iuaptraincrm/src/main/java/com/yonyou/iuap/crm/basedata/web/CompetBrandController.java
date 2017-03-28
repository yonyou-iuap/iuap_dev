package com.yonyou.iuap.crm.basedata.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import jxl.Sheet;
import jxl.Workbook;

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
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.Servlets;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetbrandSalesVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.exception.RestException;
import com.yonyou.iuap.crm.basedata.service.itf.ICompetBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmComBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmModelService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.common.utils.ExcelWriterPoiWriter;

/**
 * 竞品信息路由类 主要实现竞品信息的查询、更新、发布、撤销、关闭、保存和删除业务的提交和页面转发功能
 * 
 * @author 
 * @date Nov 24, 2016
 */
@Controller
@RequestMapping(value = "/bd/competBrand")
public class CompetBrandController {
	private static Logger logger = LoggerFactory
			.getLogger(CompetBrandController.class);
	@Autowired
	private ICompetBrandService competService;
	@Autowired
	private ITmComBrandService combrandService;
	@Autowired
	private ITmModelService modelService;
	@Autowired
	private Validator validator;

	/**
	 * 查询方法，带扩展信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querypageExt", method = RequestMethod.POST)
	public @ResponseBody Page<TmCompetBrandExtVO> pageExt (
			@RequestParam(value = "pageIndex", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			ServletRequest request) throws AppBusinessException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams = Servlets.getParametersStartingWith(request,null);
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Page<TmCompetBrandExtVO> categoryPage = null;
		try {
			categoryPage = competService.getCompetBrandsExtBypage(searchParams,
					pageRequest);
			for (TmCompetBrandExtVO tmCompetBrandExtVO : categoryPage) {
				switch (tmCompetBrandExtVO.getVstatus()) {
				case DictCode.COMPETBRAND_CLOSED_STATUS:
					tmCompetBrandExtVO.setShowStatus("已关闭");
					break;
				case DictCode.COMPETBRAND_PUBLISHED_STATUS:
					tmCompetBrandExtVO.setShowStatus("已发布");
					break;
				case DictCode.COMPETBRAND_REVOKED_STATUS:
					tmCompetBrandExtVO.setShowStatus("已撤销");
					break;
				case DictCode.COMPETBRAND_SAVED_STATUS:
					tmCompetBrandExtVO.setShowStatus("已保存");
					break;
				default:
					tmCompetBrandExtVO.setShowStatus("状态异常");
					break;
				}
			}
		} catch (Exception e) {
			throw new AppBusinessException(e.getMessage());
		}
		return categoryPage;
	}

	/**
	 * 查询视图页面数据
	 * 
	 * @author 
	 * @date Dec 27, 2016
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/getView", method = RequestMethod.POST)
	public @ResponseBody TmCompetBrandExtVO getView(
			@RequestBody TmCompetBrandExtVO entity, Model model,
			ServletRequest request) throws AppBusinessException {
		TmComBrandVO tmComBrandVO = combrandService.getTmComBrandVo(entity
				.getVcompetbrand());// 获取竞品品牌信息
		if(tmComBrandVO==null){
			throw new AppBusinessException("竞品品牌不存在！");
		}
		entity.setCombrandname(tmComBrandVO.getVcbrandname());//设置显示的品牌名
		return entity;
	}
	/**
	 * 查询指定车型的销量
	 * 
	 * @author 
	 * @date Dec 27, 2016
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @throws AppBusinessException 
	 */
	@RequestMapping(value = "/getSalesVolume", method = RequestMethod.POST)
	public @ResponseBody Page<TmCompetbrandSalesVO> getSalesVolume(
			@RequestParam(value = "pageIndex", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "vyear") String sortType,
					ServletRequest request) throws AppBusinessException {
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams = Servlets.getParametersStartingWith(request,null);
			PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
			return competService.getCompetBrandSalesVolume(searchParams, pageRequest);
		} catch (AppBusinessException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 集更新和保存于一体
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 * @throws RestException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object save(
			@RequestBody TmCompetBrandExtVO entity, ModelMap model,
			ServletRequest request) throws AppBusinessException, RestException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			BeanValidators.validateWithException(validator, entity);
			String msg = competService.saveEntity(entity);
			result.put("flag", "success");
			result.put("msg", msg);//实质是主键
		} catch (Exception e) {
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 创建分页请求
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "tcp.creationtime");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "tcp.creationtime");
		}else if ("vyear".equals(sortType)) {
			sort = new Sort(Direction.DESC, "vyear");
		}
		
		return new PageRequest(pageNumber, pagzSize, sort);
	}

	/**
	 * 删除竞品信息方法
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Object remove(
			@RequestBody List<TmCompetBrandExtVO> entities, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			competService.deleteCompetBrand(entities);
			result.put("flag", "success");
			result.put("msg", "删除成功!");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "删除失败!");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}

	/**
	 * 发布竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	/*@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public @ResponseBody Object publish(
			@RequestBody List<TmCompetBrandExtVO> entities, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			competService.publishCompetBrand(entities);
			result.put("flag", "success");
			result.put("msg", "发布成功!");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "发布失败!");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}*/

	/**
	 * 撤回竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 *//*
	@RequestMapping(value = "/revoke", method = RequestMethod.POST)
	public @ResponseBody Object revoke(
			@RequestBody List<TmCompetBrandExtVO> entities, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			competService.revokeCompetBrand(entities);
			result.put("flag", "success");
			result.put("msg", "撤销成功!");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "撤销失败!");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}*/

	/**
	 * 关闭竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public @ResponseBody Object close(
			@RequestBody List<TmCompetBrandExtVO> entities, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			competService.closeCompetBrand(entities);
			result.put("flag", "success");
			result.put("msg", "关闭成功!");
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			result.put("msg", "关闭失败!");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据选择的车型带出车型明细
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param model
	 * @param param
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/getModelDetail", method = RequestMethod.POST)
	public @ResponseBody Object getModelDetail(
			@RequestBody Map<String,Object> param, Model model,
			ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String pkModel = "";
			if(null!=param.get("pkmodel"))
				pkModel = param.get("pkmodel").toString();
			TmModelVO tmmodel = modelService.getEntityById(pkModel);
			result.put("flag", "success");
			result.put("tmmodel", tmmodel);
		} catch (AppBusinessException e) {
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
		return result;
	}
	/**
	 * 
	 * 
	 * @author 
	 * @date Dec 20, 2016
	 * @param model
	 * @param request
	 * @param response
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
	public void getTemplate(
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		try {
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//标题
			List<Object> listTitle = new LinkedList<Object>();
			listTitle.add("品牌");
			listTitle.add("车型");
			listTitle.add("公告批次");
			listTitle.add("长（mm）");
			listTitle.add("宽（mm）");
			listTitle.add("高（mm）");
			listTitle.add("整备质量（kg）");
			listTitle.add("额定载客");
			listTitle.add("电机功率（kw");
			listTitle.add("电池形式");
			listTitle.add("最大质量（kg）");
			list.add(listTitle);
			ExcelWriterPoiWriter.writeExcel(response,list,"竞品车型信息","竞品车型信息");
		} catch (Exception e) {
//			logger.error("保存出错!",e);
		}
	}

	/**
	 * 竞品车型导入方法
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @param fileName
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/importData")
	public @ResponseBody Object importData(
			@RequestParam(value = "fileName", required = false) MultipartFile  fileName,
			ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Workbook workBook = Workbook.getWorkbook(fileName.getInputStream());
			Sheet sheet = workBook.getSheet(0);
			int sheetRows = sheet.getRows();
			//车型编码 车型名称 品牌 公告批次 长（mm） 宽（mm） 高（mm） 整备质量（kg） 额定载客 电机额定/最大功率（kW） 电池型式 最大质量（kg）
			StringBuffer errorBuf = new StringBuffer();
			List<TmCompetBrandVO> competbrandVOList = new ArrayList<TmCompetBrandVO>();
//			Set<String> modelNameSet = new HashSet<>();//车型名称集合，检验当前导入的记录是否有重复的
			//获取竞品品牌信息，作为map参考
			Map<String,String> brandMap=competService.getCombrandMap();
			Set<String> brandModelSet = new HashSet<>();//品牌,车型名称set，检验当前导入的记录是否有重复的
			String brandModelPar;//品牌车型对
			for(int i=1;i<sheetRows;i++){
				TmCompetBrandVO tempItemVO = null;
				boolean checkFlag = true;

				String var1 = sheet.getCell(0,i).getContents();//品牌
				checkFlag = checkFlag & checkImportColumn(errorBuf,var1,"第"+(i+1)+"行品牌不能为空！",null,null,null,null);
				//如果录入的品牌不在记录范围内（同时注意去除空格，否则多了空格会显示不存在）
				if (checkFlag && !brandMap.containsKey(var1.trim())) {
					checkFlag=false;
					errorBuf.append("第"+(i+1)+"行品牌不存在！");
				}
				String var2 = sheet.getCell(1,i).getContents();//车型名称
				checkFlag = checkFlag & checkImportColumn(errorBuf,var2,"第"+(i+1)+"行车型名称不能为空！",null,null,null,null);
				//如果品牌和车型的配对重复
				brandModelPar = var1 + var2;
				if (brandModelSet.contains(brandModelPar)) {
					checkFlag = false;
					errorBuf.append("第"+(i+1)+"行车型和品牌与文件已有记录重复！");
				}

			/*	String var3 = sheet.getCell(2,i).getContents();//车型编码
				checkFlag = checkFlag & checkImportColumn(errorBuf,var3,"第"+(i+1)+"行车型编码不能为空！",null,null,null,null);

				//先检查flag是否为true，防范潜在的空指针异常
				if (checkFlag) {
					// 如果车型编码和车型名称相同
					if (var2.equals(var3)) {
						checkFlag=false;
						errorBuf.append("第"+(i+1)+"行车型编码和车型名称相同！");
					}
				}*/
				
				String var4 = sheet.getCell(2,i).getContents();//公告批次
				/*checkFlag = checkFlag & checkImportColumn(errorBuf,var4,"第"+(i+1)+"行公告批次不能为空！",null,null,null,null);*/
				
				String var5 = sheet.getCell(3,i).getContents();//长（mm）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var5,"第"+(i+1)+"行长（mm）不能为空！",null,null,	"\\d+"
						,"第"+(i+1)+"行长（mm）的格式不正确，应为纯整数！");
				
				String var6 = sheet.getCell(4,i).getContents();//宽（mm）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var6,"第"+(i+1)+"行宽（mm）不能为空！",null,null,	"\\d+"
						,"第"+(i+1)+"行宽（mm）的格式不正确，应为纯整数！");
				
				String var7 = sheet.getCell(5,i).getContents();//高（mm）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var7,"第"+(i+1)+"行高（mm）不能为空！",null,null,	"\\d+"
						,"第"+(i+1)+"行高（mm）的格式不正确，应为纯整数！");
						
				String var8 = sheet.getCell(6,i).getContents();//整备质量（kg）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var8,"第"+(i+1)+"行整备质量（kg）不能为空！",null,null,	"([0-9]{1,9})|([0-9]{1,9}\\.[0-9]{1,3})"
						,"第"+(i+1)+"行整备质量（kg）的格式不正确，应为纯数字（允许小数）！");
				
				String var9 = sheet.getCell(7,i).getContents();//额定载客
				checkFlag = checkFlag & checkImportColumn(errorBuf, var9, "第" + (i + 1) + "行额定载客不能为空！", null, null, null
						, null);
				
				String var10 = sheet.getCell(8,i).getContents();//电机额定/最大功率（kW）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var10,"第"+(i+1)+"行电机额定/最大功率（kW）不能为空！",null,null,null,null);
				
				String var11 = sheet.getCell(9,i).getContents();//电池型式
				checkFlag = checkFlag & checkImportColumn(errorBuf,var11,"第"+(i+1)+"行电池型式不能为空！",null,null,null,null);
				
				String var12 = sheet.getCell(10,i).getContents();//最大质量（kg）
				checkFlag = checkFlag & checkImportColumn(errorBuf,var12,"第"+(i+1)+"行最大质量（kg）不能为空！",null,null,	"\\d+"
						,"第"+(i+1)+"行最大质量（kg）的格式不正确，应为纯数字（允许小数）！");
				if(checkFlag){
					brandModelSet.add(var1 + var2);
					tempItemVO=new TmCompetBrandVO();
					tempItemVO.setVcompetbrand(brandMap.get(var1));//设置品牌
					tempItemVO.setVmodelname(var2);//设置车型名称
//					tempItemVO.setVcompetmodel(var3);//设置车型编码
					tempItemVO.setVnoticenum(var4);//设置公告批次
					tempItemVO.setVlength(Integer.parseInt(var5));//设置长（mm）
					tempItemVO.setVwidth(Integer.parseInt(var6));//设置宽（mm）
					tempItemVO.setVheight(Integer.parseInt(var7));//设置高（mm）
					tempItemVO.setNcurbweight(Double.parseDouble(var8));//设置整备质量（kg）
					tempItemVO.setNpasenger(var9);//设置额定载客
					tempItemVO.setVmotorpower(var10);//设置电机额定/最大功率（kW）
					tempItemVO.setVbatteryclass(var11);//设置电池型式
					tempItemVO.setNmaxweight(Double.parseDouble(var12));//设置最大质量（kg）
					competbrandVOList.add(tempItemVO);
				}
			}
			if(competbrandVOList.size() > 0){
				competService.saveEntity(competbrandVOList);
			}
			if(errorBuf.length()==0){
				errorBuf.append("导入成功！");
			}
			result.put("msg", errorBuf.toString());
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "导入失败!");
			result.put("flag", "fail");
		}
        return result;  
	}
	/**
	 * 检查导入字段是否为空，是否合法
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
	private boolean checkImportColumn(StringBuffer errorBuf,String var,String nullMsg,
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
}
