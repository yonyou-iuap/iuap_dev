/**
 * 
 */
package com.yonyou.iuap.crm.basedata.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Validator;

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
import org.springside.modules.beanvalidator.BeanValidators;

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.basedata.service.itf.ITmBrandService;
import com.yonyou.iuap.crm.basedata.service.itf.ITmVehicleClassifyService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.DictCode;

/**
 * 
 * 车辆分类控制器
 * 
 * @author 
 * @date 2016年11月21日
 */
@Controller
@RequestMapping(value = "/baseData/vehicleClassify")
public class TmVehicleClassifyController {
	@Autowired
	private ITmVehicleClassifyService tvcService;
	@Autowired
	private ITmBrandService tbService;
	@Autowired
	private Validator validator;

	/**
	 * 保存前台传入的车辆分类实体对象
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param tb
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveVehicleClassify(
			@RequestBody TmVehicleClassifyVO tb, ModelMap model,
			ServletRequest request) throws AppBusinessException {

		BeanValidators.validateWithException(validator, tb);

		Map<String, Object> result = new HashMap<String, Object>();
		if (tb.getPk_vehicleclassify() != null
				&& tvcService
						.getTmVehicleClassifyVo(tb.getPk_vehicleclassify()) != null) {
			tvcService.updateEntity(tb);
		} else {

			tvcService.saveEntity(tb);
		}

		// if(tb != null)
		// result.put("vehicleClassify", tb);

		result.put("flag", AppTools.SUCCESS);
		return result;
	}

	/**
	 * 删除前台传入的车辆分类对象
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JSONObject remove(
			@RequestBody TmVehicleClassifyVO entity, Model model,
			ServletRequest request) throws AppBusinessException {

		if (entity.getPk_vehicleclassify() == null
				|| entity.getPk_vehicleclassify().trim().length() == 0) {
			throw new AppBusinessException("不存在此车辆类别信息,pk为空");
		}
		tvcService.deleteTmVehicleClassifyVo(entity);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 启用前台传入的车辆分类对象
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public @ResponseBody JSONObject start(
			@RequestBody List<TmVehicleClassifyVO> entityLst, Model model,
			ServletRequest request) throws AppBusinessException {

		for (TmVehicleClassifyVO entity : entityLst) {
			entity.setVstatus(DictCode.ALREADY_START_STATUS);
		}

		tvcService.batchUpdateEntity(entityLst);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 停用前台传入的车辆分类对象
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 * @throws AppBusinessException
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public @ResponseBody JSONObject stop(
			@RequestBody List<TmVehicleClassifyVO> entityLst, Model model,
			ServletRequest request) throws AppBusinessException {
		
		for (TmVehicleClassifyVO entity : entityLst) {
			entity.setVstatus(DictCode.ALREADY_STOP_STATUS);
		}
		tvcService.batchUpdateEntity(entityLst);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		return jsonObject;
	}

	/**
	 * 查询品牌信息列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querypage", method = RequestMethod.POST)
	public @ResponseBody Page<TmVehicleClassifyVO> loadData(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		Page<TmVehicleClassifyVO> categoryPage = tvcService
				.getTmVehicleClassifysBypage(parammap, pageRequest);

		return categoryPage;
	}

	/**
	 * 查询品牌信息列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querypageExt", method = RequestMethod.POST)
	public @ResponseBody Page<TmVehicleClassifyExtVO> loadDataExt(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestBody Map<String, Object> parammap, Model model,
			ServletRequest request) throws Exception {
		int pindex = null == parammap.get("pindex") ? 0 : Integer
				.valueOf(parammap.get("pindex").toString());
		int psize = null == parammap.get("psize") ? 0 : Integer
				.valueOf(parammap.get("psize").toString());
		PageRequest pageRequest = buildPageRequest(pindex, psize, "auto");
		Page<TmVehicleClassifyExtVO> categoryPage = tvcService
				.getTmVehicleClassifysExtBypage(parammap, pageRequest);

		return categoryPage;
	}

	/**
	 * 构建查询语句
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ts");
		} else if ("creationtime".equals(sortType)) {
			sort = new Sort(Direction.ASC, "creationtime");
		}
		return new PageRequest(pageNumber, pagzSize, sort);
	}

}
