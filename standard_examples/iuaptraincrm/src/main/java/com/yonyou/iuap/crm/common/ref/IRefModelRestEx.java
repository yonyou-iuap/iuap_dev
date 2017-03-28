package com.yonyou.iuap.crm.common.ref;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IRefModelRestEx {

	
	@RequestMapping(value={"/getIntRefData"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	@ResponseBody
	public abstract List<Map<String,String>> getIntRefData(@RequestParam String pks) throws Exception;
	
}
