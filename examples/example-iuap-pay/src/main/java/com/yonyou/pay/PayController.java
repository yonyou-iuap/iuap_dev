package com.yonyou.pay;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pay")
public class PayController {

	@RequestMapping(value = "/jump", method = RequestMethod.GET)
	public String jump(Model model) {
		return "pay";
	}
}
