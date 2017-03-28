package com.yonyou.iuap.event.local.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.event.local.BusinessEvent;
import com.yonyou.iuap.event.local.BusinessException;
import com.yonyou.iuap.event.local.LocalEventDispatcher;

@Controller
@RequestMapping("/eventlocal")
public class LocalEventController {

	@ResponseBody
	@RequestMapping("/test")
	public Map<String, Object> getEventInfo(@RequestParam(value = "nodeCode", required = false) String nodeCode,
			@RequestParam(value = "ts", required = false) String ts) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BusinessEvent businessEvent = new BusinessEvent("USER", "ADD_BEFORE", "tenantCode-000",
				System.currentTimeMillis());
		try {
			LocalEventDispatcher.fireEvent(businessEvent);
			returnMap.put("isSuccess", "true");
			returnMap.put("msg", "事件处理成功");
		} catch (BusinessException e) {
			returnMap.put("isSuccess", "false");
			returnMap.put("msg", "事件处理失败");
		}

		return returnMap;
	}
}
