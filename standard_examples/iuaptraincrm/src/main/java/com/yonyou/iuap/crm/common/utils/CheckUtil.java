package com.yonyou.iuap.crm.common.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

public class CheckUtil {

	/**
	 * 校验集合是NULL还是空集合
	 * 
	 * @param list
	 *            :需要校验的集合
	 * @return true:null或者空集合,false:有值的集合
	 */
	public static boolean NullOrEmpty(List list) {
		if (null != list && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/***
	 * 校验字符串是NULL还是""
	 * 
	 * @param str
	 *            :需要校验的字符串
	 * @return true:NULL或者"",false:有值的字符串
	 */
	public static boolean NullOrEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 校验对象数组是否是NULL
	 * 
	 * @param str
	 *            :需要校验的对象
	 * @return true:NULL,false:对象不为空
	 */
	public static boolean NullOrEmpty(Object[] obj) {
		if (null != obj && obj.length > 0) {
			return false;
		} else {
			return true;
		}
	}

	/***
	 * 校验对象是否是NULL
	 * 
	 * @param str
	 *            :需要校验的对象
	 * @return true:NULL,false:对象不为空
	 */
	public static boolean NullOrEmpty(Object obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否是浮点数
	 * 
	 * @param str
	 *            :校验对象
	 * @return true:是,false:不是
	 */
	public static boolean isMoney(String str) {
		try {
			Double s = new Double(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是否是整数数
	 * 
	 * @param str
	 *            :校验对象
	 * @return true:是,false:不是
	 */
	public static boolean isNumber(String str) {
		try {
			Long s = new Long(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是否是中文
	 * 
	 * @param str
	 *            :校验对象
	 * @return true:是,false:不是
	 */
	public static boolean ischinese(String str) {
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 校验中英混合字符长度(中文字符长度*3)
	 * 
	 * @param str
	 *            :字符
	 * @param size
	 *            :最大长度
	 * @return true:通过,false:未通过
	 */
	public static boolean eqlength(String str, long size) {
		long tl = 0;
		for (int itemp = 0; itemp < str.length(); itemp++) {
			if (ischinese(str.substring(itemp, itemp + 1))) {
				tl += 3;
			} else {
				tl += 1;
			}
		}
		if (tl > size) {
			return false;
		}
		return true;
	}

	/**
	 * 校验中英混合字符长度(中文字符长度*3)
	 * 
	 * @param str
	 *            :字符
	 * @param size
	 *            :最大长度
	 * @param isNull
	 *            :是否校验为空
	 * @return true:通过,false:未通过
	 */
	public static boolean eqlength(String str, long size, boolean isNull) {
		long tl = 0;

		// 是否校验为空
		if (isNull) {
			if (CheckUtil.NullOrEmpty(str)) {
				return false;
			}
		}

		for (int itemp = 0; null != str && itemp < str.length(); itemp++) {
			if (ischinese(str.substring(itemp, itemp + 1))) {
				tl += 3;
			} else {
				tl += 1;
			}
		}
		if (tl > size) {
			return false;
		}
		return true;
	}

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static boolean isEquals(Object obj1, Object obj2) {
		if (obj1 == obj2) {
			return true;
		}
		if (obj1 != null && obj1.equals(obj2) || obj2 != null
				&& obj2.equals(obj1)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查导入字段是否为空，是否合法
	 * 
	 * @author taoweifeng
	 * @date 2016年12月26日
	 * @param errorBuf
	 * @param var
	 * @param nullMsg
	 * @param comboMap
	 * @param comboMsg
	 * @param pattern
	 * @param regErrorMsg
	 * @param size
	 * @param sizeErrorMsg
	 * @return
	 */
	public static boolean check(StringBuffer errorBuf, String var,
			String nullMsg, Map<String, String> comboMap, String comboMsg,
			String pattern, String regErrorMsg, long size, String sizeErrorMsg) {
		boolean returnFalg = true;
		// 检查字段是否为空
		if (null != nullMsg && nullMsg.length() > 0
				&& (null == var || var.length() == 0)) {
			errorBuf.append(nullMsg);
			returnFalg = false;
		} else if (null != var && var.length() > 0) {
			// 如果是枚举值，检查是否在范围内
			if (null != comboMap && null == comboMap.get(var)) {
				errorBuf.append(comboMsg);
				returnFalg = false;
			}
			// 如果存在正则表达式，则根据表达式校验是否通过
			if (null != pattern && pattern.length() > 0) {
				// 创建 Pattern 对象
				Pattern r = Pattern.compile(pattern);
				if (!r.matcher(var).matches()) {
					errorBuf.append(regErrorMsg);
					returnFalg = false;
				}
			}
			if (size != 0) {
				long tl = 0;
				for (int itemp = 0; itemp < var.length(); itemp++) {
					if (ischinese(var.substring(itemp, itemp + 1))) {
						tl += 3;
					} else {
						tl += 1;
					}
					if (tl > size) {
						errorBuf.append(sizeErrorMsg);
						returnFalg = false;
					}
				}
			}
		}
		return returnFalg;
	}

	/**
	 * Map实例化VO
	 * 
	 * @author taoweifeng
	 * @date 2016年11月22日
	 * @param clazz
	 * @param map
	 * @return
	 */
	// Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
	public static void mapToBean(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			System.out.println("Map转换实体类 Error " + e);
		}
	}
}
