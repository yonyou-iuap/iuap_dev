package iuap.ref.controller;

import iuap.ref.environment.ReferRestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.persistence.vo.pub.BusinessException;

@RequestMapping({"/iref_ctr_ex"})
@Controller
public class WebRefControllerEx {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ReferRestTemplate template;
//	private RestTemplate template;
	
	/**
	 * 可以使用自己的dao
	 */
//	@Autowired
//	private BaseDAO dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * args:{refcode:[pks]},{refcode2:[pks]}
	 * refcode[pk:value,pk:value]
	 * return:{refcode:{{pkvalue:namevalue},{pkvalue:namevalue}}},{refcode2:[{code:1,pk:1,name:1},{code:2,pk:2,name:2}]}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/initRefData", method = RequestMethod.GET)
	public @ResponseBody Map<String,Map<String,String>> getIntRefData(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,String> params) throws Exception{
		List<String> refcodes = new ArrayList<String>();
		//1.获取refcodes
		for(String refcode : params.keySet()){
			refcodes.add(refcode);
		}
		
		//2.获取refClass
		List<Map<String, Object>> refvos = this.getUsedRefEntity(refcodes);
		
		//3.查询数据
		Map<String,Map<String,String>> resultmap = new HashMap<String,Map<String,String>>();
		for(Map<String,Object> refvo : refvos){
			String refCode = (String) refvo.get("refcode");
			String refRestUrl = (String) refvo.get("refurl");
			
			String pks = params.get(refCode);
			if(pks!=null && !"".equals(pks)){
				List<Map<String, String>> results = null;
				try {
					if (StringUtils.isNotEmpty(refRestUrl)) {
//						    checkRefServerURL(request);
//						refRestUrl = RefPlatform.getBaseHome() + refRestUrl;
						results = (List<Map<String, String>>) this.template.getForObject(refRestUrl + "getIntRefData.do?pks={pks}", List.class, pks);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("查询参照数据出错",e);
					throw new BusinessException("查询参照数据出错",e);
				}
				Map<String, String> retresults = new HashMap<String, String>();
				for(Map<String,String> result : results){
					String key = result.get("pk");
					String value = result.get("name");
					retresults.put(key, value);
				}
				resultmap.put(refCode, retresults);
			}
		}
		return resultmap;
	}
	
	/**
	 * 提供参照表中数据
	 * @param refcodes
	 * @return
	 */
	private List<Map<String,Object>> getUsedRefEntity(List<String> refcodes){
		//select * from ref_refinfo where refcode in refcodes
		String cond = this.sqlListToString(refcodes);
		String sql = "select refcode,refclass,refurl from ref_refinfo where refcode in ("+cond+")";
		return jdbcTemplate.queryForList(sql);
	}
	
	private String sqlListToString(List<String> conditions){
		StringBuffer cons = new StringBuffer();
		for(String para : conditions){
			cons.append("'"+para + "',");
		}
		int last = cons.lastIndexOf(",");
		String retString = cons.substring(0, last);
		return retString;
	}
	
	/*private void checkRefServerURL(HttpServletRequest request){
	     if (StringUtils.equalsIgnoreCase(RefPlatform.getBaseHome(), "-1")) {
	       String refCtx = "http://" + request.getServerName() + ":" + request.getServerPort();
	      RefPlatform.setBaseHome(refCtx);
	    }
	 }*/
	
}
