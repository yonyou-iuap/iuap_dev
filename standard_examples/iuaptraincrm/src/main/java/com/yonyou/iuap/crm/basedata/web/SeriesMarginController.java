package com.yonyou.iuap.crm.basedata.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

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

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesMarginService;
import com.yonyou.iuap.crm.basedata.service.itf.ISeriesService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.ExcelWriterPoiWriter;

/**
 * 
* 车系毛利信息控制类
* @author 
* @date 2016年11月21日
 */
@Controller
@RequestMapping(value = "/bd/seriesMarginCtrl")
public class SeriesMarginController {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ISeriesMarginService  smService;
	
	@Autowired
	private ISeriesService seriesService;
	
	@Autowired
	private ITmVehicleClassifyService vehicleClassService;
	
	@Autowired
	private ITmBrandService brandService;
	
	/**
	 * 查询
	* @author 
	* @date 2016年11月21日
	* @param pageNumber
	* @param pageSize
	* @param sortType
	* @param model
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querypage", method = RequestMethod.POST)
	public @ResponseBody Object loadData(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		int pindex = null== parammap.get("pindex") ? 0 :Integer.valueOf(parammap.get("pindex").toString());
		int psize = null== parammap.get("psize") ? 0 :Integer.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		Page<SeriesMarginExtVO> categoryPage = smService.getSeriesMarginsBypage(parammap, pageRequest);
		for(SeriesMarginExtVO seriesMargin:categoryPage){
			//获取车系编码，车系名称
			SeriesVO series = seriesService.getSeriesById(seriesMargin.getPk_series());
			seriesMargin.setVseriesname(series==null?"":series.getVseriesname());
			seriesMargin.setVseriescode(series==null?"":series.getVseriescode());
			//获取所属类别名称
			TmVehicleClassifyVO vehicleClassVO = vehicleClassService.getTmVehicleClassifyVo(series==null?"":series.getPk_vehicleclassify());
			seriesMargin.setClassname(vehicleClassVO==null?"":vehicleClassVO.getVclassname());
			//获取所属品牌名称
			TmBrandVO brand = brandService.getTmBrandVo(vehicleClassVO==null?"":vehicleClassVO.getPk_brand());
			seriesMargin.setBrandname(brand==null?"":brand.getVbrandname());
		}
		result.put("data", categoryPage);
		result.put("flag", "success");
		result.put("msg", "查询数据成功!");
		
		return result;
	}
	
	/**
	 * 构建查询
	* @author 
	* @date 2016年11月24日
	* @param pageNumber
	* @param pagzSize
	* @param sortType
	* @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("pk_seriesmargin".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pk_seriesmargin");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}
	
	/**
	 * 根据pk判断保存或修改
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveSeriesMarginInfo(@RequestBody SeriesMarginVO entity, ModelMap model, HttpServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();  
//		BeanValidators.validateWithException(validator, entity);
		if(entity.getPk_seriesmargin() == null  || entity.getPk_seriesmargin().trim().length()==0){
			smService.saveEntity(entity);
		}else{
		    smService.updateEntity(entity);
		}
		if(entity != null)
			result.put("SeriesMargin", entity);
		
		result.put("flag", AppTools.SUCCESS);
		return result;
	}
	
	/**
	 * 单个删除  物理删除
	* @author 
	* @date 2016年11月21日
	* @param pk_seriesmargin
	* @param model
	* @param request
	* @return
	* @throws BusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> remove(@RequestParam(value = "pk_seriesmargin",required=true) String pk_seriesmargin, Model model, ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		smService.deleteSeriesMarginById(pk_seriesmargin);
		result.put("flag", AppTools.SUCCESS);
		return result;
	}
	
	
	/**
	 * 物理删除   批量 
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @param model
	* @param request
	* @return
	* @throws AppBusinessException
	 */
	@RequestMapping(value = "/deletedata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> remove(@RequestBody List<SeriesMarginVO> entitys,ServletRequest request) throws AppBusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
			result = smService.removeSeriesMargins(entitys);
			result.put("flag", AppTools.SUCCESS);
		return result;
	}
	
	
	@RequestMapping(value = "/seriesMarginUpload")
	public @ResponseBody Object seriesMarginUpload(
			@RequestParam(value = "fileName", required = false) MultipartFile  fileName,
			ModelMap model,
			HttpServletRequest request) throws AppBusinessException{
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Workbook workBook = Workbook.getWorkbook(fileName.getInputStream());
			Sheet sheet = workBook.getSheet(0);
			int sheetRows = sheet.getRows();
			//
			StringBuffer errorBuf = new StringBuffer();
			//
			List<SeriesMarginVO> voList = new ArrayList<SeriesMarginVO>();
			for(int i=1;i<sheetRows;i++){
				SeriesMarginVO seriesmargin = new SeriesMarginVO();
				boolean checkFlag = true;
				String var1 = sheet.getCell(0,i).getContents();//车系编码
				checkFlag = checkFlag & checkImportColumn(errorBuf,var1,"第"+(i+1)+"车系编码不能为空！",null,null,null,null);
				//
				List<SeriesExtVO> series = seriesService.findListByClause("vseriescode = '"+var1+"' ");
				seriesmargin.setPk_series(series.get(0).getPk_series());
				

				String var2 = sheet.getCell(1,i).getContents();//车系名称
				checkFlag = checkFlag & checkImportColumn(errorBuf,var2,"第"+(i+1)+"车系名称不能为空！",null,null,null,null);

				String var3 = sheet.getCell(2,i).getContents();//所属类别
				checkFlag = checkFlag & checkImportColumn(errorBuf,var3,"第"+(i+1)+"所属类别不能为空！",null,null,null,null);

				String var4 = sheet.getCell(3,i).getContents();//所属品牌
				checkFlag = checkFlag & checkImportColumn(errorBuf,var4,"第"+(i+1)+"所属名称不能为空！",null,null,null,null);

				String var5 = sheet.getCell(4,i).getContents();//年度
				checkFlag = checkFlag & checkImportColumn(errorBuf,var5,"第"+(i+1)+"年度不能为空！",null,null,null,null);
				seriesmargin.setVyear(var5);

				String var6 = sheet.getCell(5,i).getContents();//季度
				checkFlag = checkFlag & checkImportColumn(errorBuf,var6,"第"+(i+1)+"季度不能为空！",null,null,null,null);
				seriesmargin.setVmonth(var6);

				String var7 = sheet.getCell(6,i).getContents();//毛利
				checkFlag = checkFlag & checkImportColumn(errorBuf,var7,"第"+(i+1)+"毛利不能为空！",null,null,null,null);
				seriesmargin.setNmargin(Double.parseDouble(var7));
				voList.add(seriesmargin);
			}
			if(null != voList && voList.size()>0){
				smService.importEntitys(voList);
			}
			result.put("msg", "导入成功!");
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
	* @date 2016年12月1日
	* @param errorBuf
	* @param var
	* @param nullMsg
	* @param comboMap
	* @param comboMsg
	* @param pattern
	* @param regErrorMsg
	* @return 如果校验通过则返回true，否则返回false
	 */
	public boolean checkImportColumn(StringBuffer errorBuf,String var,String nullMsg,
			Map<String,String> comboMap,String comboMsg,
			String pattern,String regErrorMsg){
		boolean returnFalg = true;
		//检查字段是否为空
		if(null==var || var.length()==0){
			errorBuf.append(nullMsg);
			returnFalg = false;
		}else{
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
	 * 根据选择的车系带出所有相关信息
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entities
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/getSeriesDetail", method = RequestMethod.POST)
	public @ResponseBody JSONObject getSeriesDetail(
			@RequestBody Map<String,Object> param, Model model,
			ServletRequest request) throws AppBusinessException {
		String pk_series = "";
		if(null!=param.get("pk_series"))
			pk_series = param.get("pk_series").toString();
		SeriesExtVO tmmodel = seriesService.getSeriesById(pk_series);
		if(null==tmmodel){
			tmmodel = new SeriesExtVO();
		}else{
			TmVehicleClassifyVO vehicleClassVO = vehicleClassService.getTmVehicleClassifyVo(tmmodel==null?"":tmmodel.getPk_vehicleclassify());
			tmmodel.setVehiclename(vehicleClassVO==null?"":vehicleClassVO.getVclassname());
			//获取所属品牌名称
			TmBrandVO brand = brandService.getTmBrandVo(vehicleClassVO==null?"":vehicleClassVO.getPk_brand());
			tmmodel.setBrandname(brand==null?"":brand.getVbrandname());
		}
		//获取所属类别名称
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", AppTools.SUCCESS);
		jsonObject.put("tmmodel", tmmodel);
		return jsonObject;
	}
	
	@RequestMapping(value = "/importDemo", method = RequestMethod.POST)
	public void backTemExport( ModelMap model, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		try {
			//数据导出容器
			List<List<Object>> list=new ArrayList<List<Object>>();
			//设置标题
			List<Object> listTitle = new LinkedList<Object>();
			listTitle.add("车系编码");
			listTitle.add("车系名称");
			listTitle.add("所属类别");
			listTitle.add("所属品牌");
			listTitle.add("年度");
			listTitle.add("季度");
			listTitle.add("毛利");
			list.add(listTitle);

			ExcelWriterPoiWriter.writeExcel(response,list,"车系毛利导入模板","车系毛利导入模板");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
