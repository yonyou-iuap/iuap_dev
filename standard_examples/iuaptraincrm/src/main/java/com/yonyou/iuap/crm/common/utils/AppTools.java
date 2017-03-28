package com.yonyou.iuap.crm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import com.yonyou.iuap.persistence.utils.IDGenerator;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

public class AppTools {

	public static final int FAILED = 0;
	public static final int SUCCESS = 1;
	
	public static final int REFPAGESIZE = 100;
	
	public static String generatePK() {
		// 八位的字符，与租户方案中的schema命名规范保持一致
		String defautlSchema = "iuaptcrm";
		return IDGenerator.generateObjectID(defautlSchema);
	}
	
	/**
	 * 日期规范
	 * yyyy-MM-dd
	 * @param beginmonth
	 * @return
	 * @throws BusinessException
	 */
	public static String generateNextMonth(String beginmonth) throws BusinessException {
		
		Date begin = formatToDate(beginmonth);
		Calendar c = Calendar.getInstance();
		c.setTime(begin);
		c.add(Calendar.MONTH, 1);
		Date nextmonth = c.getTime();
		return formatDateTo(nextmonth);
	}
	
	/**
	 * 日期规范
	 * yyyy-MM-dd
	 * @param beginmonth
	 * @return
	 * @throws BusinessException
	 */
	public static String getMonthBegin(String curmonth) throws BusinessException {
		
		Date curdate = formatToDate(curmonth);
		Calendar c = Calendar.getInstance();
		c.setTime(curdate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date begindate = c.getTime();
		return formatDateTo(begindate);
	}
	
	/**
	 * 日期规范
	 * yyyy-MM-dd
	 * @param beginmonth
	 * @return
	 * @throws BusinessException
	 */
	public static String getMonthEnd(String curmonth) throws BusinessException {
		Date curdate = formatToDate(curmonth);
		Calendar c = Calendar.getInstance();
		c.setTime(curdate);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		Date enddate = c.getTime();
		return formatDateTo(enddate);
	}
	
	/**
	 * 计算结束日期与开始日期的月份差距
	 * 不考虑日期号数大小
	 * @param begindate
	 * @param enddate
	 * @return
	 * @throws BusinessException
	 */
	public static int countMonthDiff(String begindate, String enddate) throws BusinessException {
		Date begin = formatToDate(begindate);
		Date end = formatToDate(enddate);
		if(begin.after(end)){
			throw new BusinessException("开始日期不能大于结束日期");
		}
		Calendar bc = Calendar.getInstance();
		bc.setTime(begin);
		int by = bc.get(Calendar.YEAR);
		int bm = bc.get(Calendar.MONTH);
		int bd = bc.get(Calendar.DATE);
		Calendar ec = Calendar.getInstance();
		ec.setTime(end);
		int ey = ec.get(Calendar.YEAR);
		int em = ec.get(Calendar.MONTH);
		int ed = ec.get(Calendar.DATE);
		int diff = ((ey-by)*12 + (em-bm));
		if(bd>=ed){
			diff = ((ey-by)*12 + (em-bm))-1;
		}
		return diff;
	} 
	
	
	/**
	 * 计算结束日期与开始日期的天数差距
	 * 不考虑时分秒
	 * @param begindate
	 * @param enddate
	 * @return
	 * @throws BusinessException
	 */
	public static int countDayDiff(Date begin, Date end) throws BusinessException {
		   Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(begin);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(end);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return day2 - day1;
	}
	
	
	/**
	 * 计算结束日期与开始日期的天数差距
	 * 考虑时分秒
	 * @param begindate
	 * @param enddate
	 * @return
	 * @throws BusinessException
	 */
	public static int countDayTimeDiff(Date fDate, Date oDate) {
	       if (null == fDate || null == oDate) {
	           return -1;
	       }
	       long intervalMilli = oDate.getTime() - fDate.getTime();
	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	   }

	
	/**
	 * 将字符串按照yyyy-MM-dd格式转换为日期
	 * @param time
	 * @return
	 * @throws BusinessException
	 */
	public static Date formatToDate(String time) throws BusinessException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
	        return sf.parse(time);
		} catch (ParseException e) {
			throw new BusinessException("日期格式错误");
		}
	}
	
	/**
	 * 将日期按照yyyy-MM-dd格式转换为字符串
	 * @param time
	 * @return
	 * @throws BusinessException
	 */
	public static String formatDateTo(Date time) throws BusinessException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(time);
	}
	
	/**
	 * 字符串拼接sql函数
	 * @param list_str
	 * @return
	 */
	public static String getStrs(String[] list_str) {
		String str = "(";
		for(int i = 0;i<list_str.length-1;i++){
			str=str+"'"+list_str[i]+"',";
		}
		str=str+"'"+list_str[list_str.length-1]+"')";
		return str;
	}
	
	/**
	 * 字符串拼接sql函数
	 * @param list_str
	 * @return
	 */
	public static String getStrs(Set<String> keySet) {
		String str = "(";
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			str=str+"'"+iterator.next()+"',";
		}
		str = str.substring(0,str.length()-1)+")";
		return str;
	}
	
	/**
	 * 字符串拼接sql函数
	 * @param list_str
	 * @return
	 */
	public static String getStrs(List<String> list_str) {
		// TODO Auto-generated method stub
		String str = "(";
		for(int i = 0;i<list_str.size()-1;i++){
			str=str+"'"+list_str.get(i)+"',";
		}
		str=str+"'"+list_str.get(list_str.size()-1)+"')";
		return str;
	}
	
	/**
	 * JSONArray转换为List类型，这里需要注意的是对于Timestamp类型转换，对于VO中提供了该类型的，
	 * POST封装在JSONbject中多个参数解析出来的JSONArray的转换；
	 * @param datas
	 * @param entityClass
	 * @return
	 */
	public static <T> List<T> JSONArrayToList(JSONArray datas,Class<T> entityClass){
		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher());
		List<T> list = (List<T>) JSONArray.toCollection(datas, entityClass);
		return list;
	}
	
	/**
	 * 获取某年第一天
	* TODO description
	* @author 
	* @date 2017年1月5日
	* @param year
	* @return
	 */
	public static String getYearFirst(int year){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR,year);
		Date yearFirst = calendar.getTime();
		String yf = sf.format(yearFirst);
		return yf;
	}

	/**
	 * 获取某年最后一天
	* TODO description
	* @author 
	* @date 2017年1月5日
	* @param year
	* @return
	 */
	public static String getYearlast(int year){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR,year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date yearlast = calendar.getTime();
		String yl = sf.format(yearlast);
		return yl;
	}
}
