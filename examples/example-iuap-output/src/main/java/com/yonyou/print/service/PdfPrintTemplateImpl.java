package com.yonyou.print.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yonyou.uap.ieop.print.aware.HttpRequestEventAware;
import com.yonyou.uap.ieop.print.template.AbstractFileTemplate;

import nc.ui.pub.print.DefaultDataSource;
import nc.ui.pub.print.IDataSource;
import uap.web.ec.service.print.OrderForm;
import uap.web.ec.service.print.OrderItem;

public class PdfPrintTemplateImpl extends AbstractFileTemplate implements HttpRequestEventAware{
	
	private HttpServletRequest request;
	@Override
	public List<IDataSource> getDataSourceList() {
		List<IDataSource> list = new ArrayList<IDataSource>();

		IDataSource datasource = new DefaultDataSource();
		List<Object> objects = new ArrayList<Object>();
		OrderForm printData = getData();
		objects.add(printData);
		datasource.setObjects(objects);

		list.add(datasource);
		return list;
	}

	@Override
	public String getTemplateName() {
		return request.getServletContext().getRealPath("/templates/order.otf");
	}
	 
	public OrderForm getData() {
		// 通过订单号查询订单数据，这里直接写出了
		OrderForm orderForm = new OrderForm();
		orderForm.setOrderno("20150920001");
		orderForm.setOrderdate("2015-09-12 12:45:45");
		orderForm.setCustomername("刘XX");
		orderForm.setPhoneno("134XXXXXXXX");
		orderForm.setCustomeraddr("北京市海淀区北清路68号用友软件园");
		orderForm.setTotalamount(57.30);

		List<OrderItem> items = new ArrayList<OrderItem>();
		OrderItem item1 = new OrderItem();
		item1.setNo("000001");
		item1.setName("图解CSS3：核心技术与案例实战 ");
		item1.setCount(1);
		item1.setAmount(57.30);
		items.add(item1);

		orderForm.setItems(items);

		return orderForm;
	}

	@Override
	public void setHttpRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
