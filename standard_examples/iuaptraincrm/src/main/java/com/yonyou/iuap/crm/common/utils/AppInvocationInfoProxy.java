package com.yonyou.iuap.crm.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.yonyou.iuap.context.InvocationInfoProxy;


/**
 * 可将InvocationInfoProxy的扩展属性在这里设置
 * InvocationInfoProxy.setExtendAttribute
 * @author 
 *
 */
public class AppInvocationInfoProxy extends InvocationInfoProxy{

	/**
	 * 
	* TODO 获取当前用户pk
	* @author 
	* @date 2016年12月9日
	* @return
	 */
	public static String getPk_User() {
		return (String) InvocationInfoProxy.getParameter("pk_user");
	}
	
	/**
	 * 
	* TODO 设置当前用户PK
	* @author 
	* @date 2016年12月9日
	* @return
	 */
	public static void setPk_User(String pk_user) {
		InvocationInfoProxy.setParameter("pk_user",pk_user);
	}
	
	/**
	 * 
	* TODO 获取当前用户显示名称
	* @author 
	* @date 2016年12月9日
	* @return
	 */
	public static String getUserCNName() {
		return (String) InvocationInfoProxy.getParameter("userCNName");
	}
	
	/**
	 * 
	* TODO 设置当前用户显示名称
	* @author 
	* @date 2016年12月9日
	* @param userCNName
	 */
	public static void setUserCNName(String userCNName) {
		String username = "";
		try {
			username = URLDecoder.decode(userCNName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		InvocationInfoProxy.setParameter("userCNName", username);
	}
	
	/**
	 * 
	* TODO 获取当前公司或组织pk
	* @author 
	* @date 2016年12月9日
	* @return
	 */
	public static String getPk_Corp() {
		return (String) InvocationInfoProxy.getParameter("pk_corp");
	}
	
	/**
	 * 
	* TODO 设置当前公司或组织pk
	* @author 
	* @date 2016年12月9日
	* @param pk_corp
	 */
	public static void setPk_Corp(String pk_corp) {
		InvocationInfoProxy.setParameter("pk_corp", pk_corp);
	}
	
	/**
	 * 
	* TODO 获取当前部门pk
	* @author 
	* @date 2016年12月9日
	* @return
	 */
	public static String getPk_Dept() {
		return (String) InvocationInfoProxy.getParameter("pk_dept");
	}
	
	/**
	 * 
	* TODO 设置当前部门pk
	* @author 
	* @date 2016年12月9日
	* @param pk_dept
	 */
	public static void setPk_Dept(String pk_dept) {
		InvocationInfoProxy.setParameter("pk_dept", pk_dept);
	}
}
