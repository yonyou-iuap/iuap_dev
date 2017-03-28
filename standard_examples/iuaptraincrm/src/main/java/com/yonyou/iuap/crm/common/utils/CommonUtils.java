package com.yonyou.iuap.crm.common.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

	private static final int DEF_DIV_SCALE = 2;

	public static final int FIRST_TEN_DAYS = 1;

	public static final int MIDDLE_TEN_DAYS = 2;

	public static final int LAST_TEN_DAYS = 3;

	// ------------------- price format handlers -------------------
	private static DecimalFormat priceDecimalFormat = new DecimalFormat("#,##0.00");

	private CommonUtils() {

	}

	/** Formats a Double representing a price into a string
	 * @param price The price Double to be formatted
	 * @return A String with the formatted price
	 */
	public static String formatPrice(Double price) {
		if (price == null)
			return "";
		return formatPrice(price.doubleValue());
	}

	/** Formats a double representing a price into a string
	 * @param price The price double to be formatted
	 * @return A String with the formatted price
	 */
	public static String formatPrice(double price) {

		return priceDecimalFormat.format(price);
	}

	// ------------------- percentage format handlers -------------------
	static DecimalFormat percentageDecimalFormat = new DecimalFormat("##0.##%");

	/** Formats a Double representing a percentage into a string
	 * @param percentage The percentage Double to be formatted
	 * @return A String with the formatted percentage
	 */
	public static String formatPercentage(Double percentage) {
		if (percentage == null)
			return "";
		return formatPercentage(percentage.doubleValue());
	}

	/** Formats a double representing a percentage into a string
	 * @param percentage The percentage double to be formatted
	 * @return A String with the formatted percentage
	 */
	public static String formatPercentage(double percentage) {
		return percentageDecimalFormat.format(percentage);
	}

	// ------------------- quantity format handlers -------------------
	static DecimalFormat quantityDecimalFormat = new DecimalFormat("#,##0.###");

	/** Formats an Long representing a quantity into a string
	 * @param quantity The quantity Long to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Long quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/** Formats an int representing a quantity into a string
	 * @param quantity The quantity long to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(long quantity) {
		return formatQuantity((double) quantity);
	}

	/** Formats an Integer representing a quantity into a string
	 * @param quantity The quantity Integer to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Integer quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/** Formats an int representing a quantity into a string
	 * @param quantity The quantity int to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(int quantity) {
		return formatQuantity((double) quantity);
	}

	/** Formats a Float representing a quantity into a string
	 * @param quantity The quantity Float to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Float quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/** Formats a float representing a quantity into a string
	 * @param quantity The quantity float to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(float quantity) {
		return formatQuantity((double) quantity);
	}

	/** Formats an Double representing a quantity into a string
	 * @param quantity The quantity Double to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Double quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/**Formats an double representing a quantity into a string
	 * @param quantity The quantity double to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(double quantity) {
		return quantityDecimalFormat.format(quantity);
	}

	/**precision math addition operation
	 * @param double param1 and double param2 to addition operation
	 * @return A double value
	 */
	public static double add(double param1, double param2) {
		BigDecimal x1 = new BigDecimal(Double.toString(param1));
		BigDecimal x2 = new BigDecimal(Double.toString(param2));
		return x1.add(x2).doubleValue();
	}
	
	/**precision math addition operation
	 * @param float param1 and float param2 to addition operation
	 * @return A float value
	 */
	public static float add(float param1, float param2) {
		BigDecimal x1 = new BigDecimal(Float.toString(param1));
		BigDecimal x2 = new BigDecimal(Float.toString(param2));
		return x1.add(x2).floatValue();
	}

	/**precision math addition operation
	 * @param String param1 and String param2 to addition operation
	 * @return A double value
	 */
	public static double add(String param1, String param2) {
		param1 = makeNull(param1);
		param2 = makeNull(param2);
		BigDecimal x1 = new BigDecimal(param1);
		BigDecimal x2 = new BigDecimal(param2);
		return x1.add(x2).doubleValue();
	}

	/**precision math addition operation
	 * @param list param1 to addition operation
	 * @return A double value
	 */
	public static double add(List param) {
		String n;
		BigDecimal x1;
		BigDecimal x2 = new BigDecimal("0");
		if (param != null && param.size() > 0)
		{
			Iterator iter = param.iterator();
			while (iter.hasNext())
			{
				n = (String) iter.next();
				n = makeNull(n);
				x1 = new BigDecimal(n);
				x2 = x2.add(x1);
			}
		}
		return x2.doubleValue();
	}

	/**precision math addition operation
	 * @param list param1 to addition operation
	 * @return A double value
	 */
	public static double sum(List param) {
		Double n;
		BigDecimal x1;
		BigDecimal x2 = new BigDecimal("0");
		if (param != null && param.size() > 0)
		{
			Iterator iter = param.iterator();
			while (iter.hasNext())
			{
				n = (Double) iter.next();
				x1 = new BigDecimal(n.toString());
				x2 = x2.add(x1);
			}
		}
		return x2.doubleValue();
	}

	/**
	 * precision math subtration operation
	 * @param double param1 and double param2 to subtration operation 
	 * @return A double value
	 */
	public static double sub(double param1, double param2) {
		BigDecimal x1 = new BigDecimal(Double.toString(param1));
		BigDecimal x2 = new BigDecimal(Double.toString(param2));
		return x1.subtract(x2).doubleValue();
	}

	/**
	 * precision math subtration operation
	 * @param String param1 and String param2 to subtration operation 
	 * @return A double value
	 */
	public static double sub(String param1, String param2) {
		param1 = makeNull(param1);
		param2 = makeNull(param2);
		BigDecimal x1 = new BigDecimal(param1);
		BigDecimal x2 = new BigDecimal(param2);
		return x1.subtract(x2).doubleValue();
	}

	/**
	 * precision math multiplication operation
	 * @param double param1 and double param2 to multiplication operation 
	 * @return A double value
	 */
	public static double multiply(double param1, double param2) {
		BigDecimal x1 = new BigDecimal(Double.toString(param1));
		BigDecimal x2 = new BigDecimal(Double.toString(param2));
		return x1.multiply(x2).doubleValue();
	}
	
	/**
	 * precision math multiplication operation
	 * @param float param1 and float param2 to multiplication operation 
	 * @return A float value
	 */
	public static float multiply(float param1, float param2) {
		BigDecimal x1 = new BigDecimal(Float.toString(param1));
		BigDecimal x2 = new BigDecimal(Float.toString(param2));
		return x1.multiply(x2).floatValue();
	}

	/**
	 * precision math multiplication operation
	 * @param String param1 and double String to multiplication operation 
	 * @return A double value
	 */
	public static double multiply(String param1, String param2) {
		param1 = makeNull(param1);
		param2 = makeNull(param2);
		BigDecimal x1 = new BigDecimal(param1);
		BigDecimal x2 = new BigDecimal(param2);
		BigDecimal p = new BigDecimal("10000");
		x1 = x1.multiply(p);
		x1 = x1.multiply(x2);
		x1 = x1.divide(p, 2, BigDecimal.ROUND_HALF_UP);
		return x1.doubleValue();
	}

	public static double div(double param1, double param2) {

		return div(param1, param2, DEF_DIV_SCALE);

	}

	public static double div(String param1, String param2) {
		param1 = makeNull(param1);
		param2 = makeNull(param2);
		return div(param1, param2, DEF_DIV_SCALE);

	}

	/**
	 * precision math divide operation
	 * @param double param1 and double param2 to divide operation 
	 * @param scale decimal digits
	 * @return A double value
	 */
	public static double div(double param1, double param2, int scale) {

		if (scale < 0)
		{

			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal x1 = new BigDecimal(Double.toString(param1));
		BigDecimal x2 = new BigDecimal(Double.toString(param2));
		return x1.divide(x2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * precision math divide operation
	 * @param String param1 and String param2 to divide operation 
	 * @param scale decimal digits
	 * @return A double value
	 */
	public static double div(String param1, String param2, int scale) {

		if (scale < 0)
		{

			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal x1 = new BigDecimal(param1);
		BigDecimal x2 = new BigDecimal(param2);
		return x1.divide(x2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * precision math round operation
	 * @param double param to round operation 
	 * @param scale decimal digits
	 * @return A double value
	 */
	public static double round(double param1, int scale) {

		if (scale < 0)
		{

			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(param1));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	};

	/**
	 * precision math round operation
	 * @param double param to round operation 
	 * @param scale decimal digits
	 * @return A double value
	 */
	public static double round(String param1, int scale) {
		param1 = makeNull(param1);
		if (scale < 0)
		{

			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(param1);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	};

	/**
	 * calculate tax
	 * @param x1
	 * @param rate
	 * @return
	 */
	public static double calculateTax(String x1, String rate) {
		x1 = makeNull(x1);
		rate = makeNull(rate);
		BigDecimal t = new BigDecimal(x1);
		BigDecimal r = new BigDecimal(rate);
		BigDecimal c = new BigDecimal("1");
		BigDecimal p = new BigDecimal("10000");
		c = r.add(c);
		t = t.multiply(p);
		t = t.divide(c, BigDecimal.ROUND_HALF_UP);
		t = t.multiply(r);
		t = t.divide(p, 2, BigDecimal.ROUND_HALF_UP);
		return t.doubleValue();
	}

	public static double toDouble(String s) {
		s = makeNull(s);
		return Double.valueOf(s).doubleValue();
	}

	/**
	 * conversion A String number to Double
	 * @param param1
	 * @return
	 */
	public static Double parseDouble(String s) {
		s = makeNull(s);
		return Double.valueOf(s);
	}

	/**
	 * conversion A String number with bigDecimal doubleValue
	 * @param s
	 * @return
	 */
	public static Double parseDoubleB(String s) {
		s = makeNull(s);
		BigDecimal x = new BigDecimal(s);
		return new Double(x.doubleValue());
	}

	/**
	 * conversion A String number to float value
	 * @param s
	 * @return
	 */
	public static Float parseFloat(String s) {
		s = makeNull(s);
		return Float.valueOf(s);
	}

	/**
	 * conversion A String number to long value
	 * @param s
	 * @return
	 */
	public static Long parseLong(String s) {
		return Long.valueOf(s);
	}

	/**
	 * conversion A String number to integer value
	 * @param s
	 * @return
	 */
	public static Integer parseInteger(String s) {
		s = makeNull(s);
		return Integer.valueOf(s);
	}

	/**
	 * conversion A String number to date type value
	 * @param s
	 * @return
	 */
	public static Date parseDate(String date) {
		DateFormat df = DateFormat.getDateInstance();
		try
		{
			return df.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	// ------------------- null string handlers -------------------
	/** Checks to see if the passed Object is null, if it is returns an empty but non-null string, otherwise calls toString() on the object
	 * @param obj1 The passed Object
	 * @return The toString() of the passed Object if not null, otherwise an empty non-null String
	 */
	public static String makeString(Object obj1) {
		if (obj1 != null)
			return obj1.toString();
		else
			return "";
	}

	/**
	 * Checks to see if the passed string is <code>null</code> string
	 * @param a sring s 
	 * @return an empty non-null String
	 */
	public static String fixedNullString(String s) {
		if (s != null && s.length() > 0)
		{
			if ("null".equalsIgnoreCase(s))
			{
				return "";
			}
			return s;
		}
		else
		{
			return "";
		}

	}
	
	
	public static String fixedUIString(Object o)
	{
		if (o != null)
		{
			if ("null".equalsIgnoreCase(o.toString()))
			{
				return "";
			}
			return o.toString();
		}
		else
		{
			return "";
		}
	}

	/** Checks to see if the passed string is null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String checkNull(String string1) {
		if (string1 != null)
			return string1;
		else
			return "";
	}

	/** Checks to see if the passed string is null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String checkNull(Object obj) {
		if (obj != null)
			return obj.toString();
		else
			return "";
	}

	/** Checks to see if the passed string array is null.
	 * @param s The passed String array
	 * @return if the passed String array if not null then return true.
	 */
	public static boolean paramCheck(String[] s) {
		if (s != null && s.length > 0)
			return true;
		else
			return false;
	}

	/** Checks to see if the passed string array is empty "".
	 * @param s The passed String array
	 * @return if the passed String array if not null then return true.
	 */
	public static boolean paramCheck(String s) {
		if (s != null && !"".equals(s) && !"0".equals(s) && !"0.00".equals(s))
			return true;
		else
			return false;
	}

	/** Checks to see if the passed string is a empty string <code>""</code> or a null string.
	 * @param s The passed String 
	 * @return if the passed String if not A <code>""</code> String then return true.
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static boolean isEmpty(Object str) {
		return str == null || str.toString().length() == 0;
	}

	/** Checks to see if the passed string is null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String makeNull(String string1) {
		if (string1 != null && string1.length() > 0)
			return string1;
		else
			return "";
	}

	/** Checks to see if the passed string is non-null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String trim(String string1) {
		if (string1 != null)
			return string1.trim();
		else
			return "";
	}

	/** 
	 * Replaces all occurances of oldString in mainString with newString
	 * 
	 * @param mainString The original string
	 * @param oldString The string to replace
	 * @param newString The string to insert in place of the old
	 * @return mainString with all occurances of oldString replaced by newString
	 */
	public static String replaceString(String mainString, String oldString, String newString) {
		if (mainString == null)
		{
			return null;
		}
		if (oldString == null || oldString.length() == 0)
		{
			return mainString;
		}
		if (newString == null)
		{
			newString = "";
		}

		int i = mainString.lastIndexOf(oldString);

		if (i < 0)
			return mainString;

		StringBuffer mainSb = new StringBuffer(mainString);

		while (i >= 0)
		{
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * Creates a single string from a List of strings seperated by a delimiter.
	 * 
	 * @param list a list of strings to join
	 * @param delim the delimiter character(s) to use. (null value will join with 
	 * no delimiter)
	 * @return a String of all values in the list seperated by the delimiter
	 */
	public static String join(List list, String delim) {
		if (list == null || list.size() < 1)
			return null;
		StringBuffer buf = new StringBuffer();
		Iterator i = list.iterator();

		while (i.hasNext())
		{
			buf.append((String) i.next());
			if (i.hasNext())
				buf.append(delim);
		}
		return buf.toString();
	}

	/**
	 * Splits a String on a delimiter into a List of Strings.
	 * 
	 * @param str the String to split
	 * @param delim the delimiter character(s) to join on (null will split on whitespace)
	 * @return a list of Strings
	 */
	public static List split(String str, String delim) {
		List splitList = null;
		StringTokenizer st = null;

		if (str == null)
			return splitList;

		if (delim != null)
			st = new StringTokenizer(str, delim);
		else
			st = new StringTokenizer(str);

		if (st != null && st.hasMoreTokens())
		{
			splitList = new ArrayList();

			while (st.hasMoreTokens())
				splitList.add(st.nextToken());
		}
		return splitList;
	}

	/**
	 * Format array of string to A PL/SQL String
	 * @param str
	 * @return A formate string with PL/SQL NOT IN syntax 
	 */
	public static String linkStr(String[] str) {
		String temp = "(";
		for (int i = 0; i < str.length; i++)
		{
			if (i == 0)
			{
				temp = temp + "'" + str[i] + "'";
			}
			else
			{
				temp = temp + "," + "'" + str[i] + "'";
			}
		}
		temp = temp + ")";
		return temp;
	}
	
	/**
	 * 将字符串格式成sql形式
	 * @param str
	 * @param delim
	 * @return
	 */
	public static String strToSql(String str, String delim) {
		StringTokenizer st = null;

		if (str == null)
			return str;

		if (delim != null)
			st = new StringTokenizer(str, delim);
		else
			st = new StringTokenizer(str);
		
		String temp = "(";
		if (st != null && st.hasMoreTokens())
		{			
			while (st.hasMoreTokens()){
				temp = temp + "'" + st.nextToken() + "',";
			}
		}
		if(temp.length()>1){
			temp = temp.substring(0,temp.length()-1) + ")";
		}else{
			temp="";
		}		
		return temp;
	}	

	/**
	 * Formate a Integer num to a string id
	 * @param id
	 * @return A String line number
	 */
	public static String getLineId(int id) {
		String sid = "";
		if (id >= 0 && id <= 9)
		{
			sid = "0" + Integer.toString(id);
		}
		else
		{
			sid = Integer.toString(id);
		}
		return sid;
	}

	/**
	 * parse a double "string"
	 * @param id
	 * @return A string id
	 */
	public static String checkId(String id) {
		String sid = id;
		int i = -1;
		if (id != null && id.length() > 0)
		{
			i = id.indexOf(".");
			if (i != -1)
			{
				sid = id.substring(0, i);
				return sid;
			}
		}
		return sid;
	}

	/**
	 * count the days with compare the firstDate date and the last date 
	 * @param firstDate
	 * @param lastDate
	 * @return A double days
	 */
	public static double compareDateToDays(Date firstDate, Date lastDate) {
		if (firstDate == null || lastDate == null)
			throw new IllegalArgumentException("the paramater is null date.");
		long timeColon1 = firstDate.getTime();
		long timeColon2 = lastDate.getTime();
		long tmpCal = timeColon2 - timeColon1;
		long mm = 24 * 60 * 60 * 1000;
		double days = (double) (tmpCal / mm);
		return Math.abs(days);
	}

	public static boolean checkIsNullStr(String nullStr) {
		boolean ret = false;
		if (nullStr != null && nullStr.length() > 0)
		{
			if (nullStr.equals("null") || "".equals(nullStr.trim()))
			{
				ret = true;
			}
		}
		else
		{
			ret = true;
		}
		return ret;
	}

	/**
	 * whether the part_no is user defined part number
	 * @param partNo
	 * @return true or false
	 */
	public static boolean isUserDefinedPartNo(String partNo) {
		boolean ret = false;
		String marker = null;
		if (partNo != null && !"".equals(partNo))
		{
			if (partNo.length() > 3)
			{
				marker = partNo.substring(0, 3);
				if (marker.equalsIgnoreCase("WZD") || marker.equalsIgnoreCase("DZD"))
				{
					ret = true;
				}
			}
		}
		return ret;
	}

	public static String fixedFormatMoney(String m) {
		String tmp = "";
		if (m != null && m.length() > 0)
		{
			String[] t = m.split(",");
			for (int i = 0; i < t.length; i++)
			{
				tmp += t[i];
			}
			System.out.println(tmp);
		}
		else
		{
			tmp = "0.00";
		}
		return tmp;
	}

	/**
	 * 判断是否为合法的TimeStamp格式字符串
	 * @param s
	 * @return
	 */
	public static boolean isValidTimestamp(String s) {
		SimpleDateFormat df = null;
		try
		{
			df = new SimpleDateFormat("yyyy-MM-dd");
			df.parse(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static boolean isValidDate(String s) {
		SimpleDateFormat df = null;
		try
		{
			df = new SimpleDateFormat("yyyy-MM-dd");
			df.parse(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static Date parseDate(String dateString, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date result = null;
		try
		{
			result = df.parse(dateString);
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static Date parseDateTime(String dateString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		DateFormat df = DateFormat.getDateTimeInstance();
		Date result = null;
		try
		{
			result = df.parse(dateString);
		}
		catch (Exception e)
		{
		}
		return result;
	}

	public static String printDate(Date date) {
		if(date!=null){
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		   return df.format(date);
		}else{
		   return "";
		}

	}

	public static String printDate(String format, Date date) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);

	}

	public static String printDateTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);

	}

	public static String getYearMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);

	}

	public static long getIntervalDays(Date from, Date to) {
		return (to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24);
	}

	public static String getOriginalFileName(String fileName) {
		int dotPlace = fileName.lastIndexOf(".");
		String firstPart = "";
		String secondPart = "";
		if (dotPlace == -1)
		{
			return fileName;
		}
		else
		{
			firstPart = fileName.substring(0, dotPlace);
			secondPart = fileName.substring(dotPlace + 1, fileName.length());
		}
		dotPlace = firstPart.lastIndexOf(".");
		if (dotPlace == -1)
		{
			return secondPart;
		}
		else
		{
			return firstPart.substring(0, dotPlace + 1) + secondPart;
		}
	}

	public static boolean isNullString(String s) {
		return null == s || s.equals("");
	}

	/**
	 * Tests if this date is before the specified date.
	 * @param   firstDate   a date.
	 * @param   when   a date.
	 * @return  return true or false
	 * @exception NullPointerException if <code>firstDate or when</code> is null.
	 */
	public static boolean dateBefore(Date firstDate, Date when) throws Exception {
		if (firstDate == null || when == null)
		{
			throw new Exception("the date param can not be empty!");
		}
		return firstDate.before(when);
	}

	/**
	 * Tests if this date is after the specified date.
	 * @param   firstDate   a date.
	 * @param   when   a date.
	 * @return  return true or false
	 * @exception NullPointerException if <code>firstDate or when</code> is null.
	 */
	public static boolean dateAfter(Date firstDate, Date when) throws Exception {
		if (firstDate == null || when == null)
		{
			throw new Exception("the date param can not be empty!");
		}
		return firstDate.after(when);
	}

	/**
	 * 判断第一日期（firstDate）减去第二个日期（secondDate）的差 是否 大于 多少天（days）
	 * @param firstDate Date - 第一日期
	 * @param secondDate Date - 第二个日期
	 * @param days  double - 天数
	 * @return  int   1表示大于，0表示等于，-1表示小于
	 */
	public static int compareDate(Date firstDate, Date secondDate, double days) throws Exception {
		if (firstDate == null || secondDate == null)
		{
			throw new Exception("日期不能为空");
		}
		long lFirst = firstDate.getTime();
		long lSecond = secondDate.getTime();
		long tmp = lFirst - lSecond;
		long lDays = (long) (days * 24 * 60 * 60 * 1000);
		if (tmp > lDays)
		{
			return 1;
		}
		else
		{
			if (tmp == lDays)
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}

	}

	/**
	 * 根据传入日期来判断该日期是上旬、中旬还是下旬
	 * @param date 
	 * @return 
	 * @throws IllegalArgumentException 
	 */
	public static int get10DaysSpanType(Date date) throws IllegalArgumentException {
		int returnValue = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		if (dayOfMonth >= 1 && dayOfMonth <= 10)
			returnValue = FIRST_TEN_DAYS;
		else if (dayOfMonth > 10 && dayOfMonth <= 20)
			returnValue = MIDDLE_TEN_DAYS;
		else if (dayOfMonth > 20 && dayOfMonth <= 31)
			returnValue = LAST_TEN_DAYS;
		else
			throw new IllegalArgumentException("the paramater is not a valid date.");

		return returnValue;
	}

	/**
	 * 先将传入的字符串转化成时间，然后再根据时间判断属于上、中、下哪个旬
	 * @param dateStr
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static int get10DaysSpanType(String dateStr) throws IllegalArgumentException {
		DateFormat format = DateFormat.getDateInstance();
		Date date;
		try
		{
			date = format.parse(dateStr);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("the paramater is not a valid date string.");
		}
		return get10DaysSpanType(date);
	}

	/**
	 * 取得时间在月份中对应的天数
	 * @param date
	 * @return
	 */
	public static int getMonthDays(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将从fromDate 到 toDate 之间（包括 fromDate 和 toDate)的时间放到一个list中返回
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List getDaysList(Date fromDate, Date toDate) {
		ArrayList list = new ArrayList();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		while (fromDate.before(toDate))
		{
			list.add(fromDate);
			cal.add(Calendar.DATE, 1);
			fromDate = cal.getTime();
		}
		list.add(toDate);
		return list;
	}

	/**
	 * 如果日期是一个月份的第10天、第20天或最后一天，则返回true，否则返回false
	 * @param date
	 * @return
	 */
	public static boolean isLastTenDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DATE))
			return true;
		if (cal.get(Calendar.DATE) == 10)
			return true;
		if (cal.get(Calendar.DATE) == 20)
			return true;
		return false;
	}

	public static Date currentDateTime() {
		return Calendar.getInstance().getTime();
	}

	public static Date getLastDateOfMonth(Date dateTime) {
		Calendar cal1 = Calendar.getInstance();

		cal1.setTime(dateTime);
		Calendar cal2 = new GregorianCalendar(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),
				cal1.getActualMaximum((Calendar.DAY_OF_MONTH)));

		return cal2.getTime();
	}

	public static int getDayOfWeek(Date dateTime) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @param currentDate
	 * @param day
	 * @return Date
	 * @description 日期增加天数
	 */
	public static Date addDay(Date currentDate, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * @param currentDate
	 * @param hour
	 * @return Date
	 * @description 日期时间增加小时
	 */
	public static Date addHour(Date currentDate, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}
	
	/**
	 * 判断是否是负数
	 * @param n
	 * @return
	 */
	public static boolean isMinus(Double n)
	{
		boolean result = false;
		if (n != null && n<0)
		{
			result = true;
		}
		return result;
	}
	
    /**
     * 取数字绝对值
     * @param n
     * @return
     */
	public static Double abs(Double n)
	{
		if (n!=null && n<0)
		{
			return Math.abs(n.doubleValue());
		}
		else if(n!=null && n > 0)
		{
			return n;
		}else{
			return new Double(0.0d);
		}
	}
	
	/**
	 * 显式转换为负长整型值
	 * @param id
	 * @return
	 */
	public static Long toMinus(Long id)
	{
		String key;
		Long l=0L;
		if (id == null)
		{
			throw new IllegalArgumentException("is not a long number");
		}
		key = "-"+id.toString();
		l = new Long(key);
		return l;
	}
	
	/**
	 * 显式转换为负浮点数
	 * @param id
	 * @return
	 */
	public static Double toMinus(Double id)
	{
		String key;
		Double l=0.0;
		if (id == null)
		{
			throw new IllegalArgumentException("is not a double number");
		}
		key = "-"+id.toString();
		l = new Double(key);
		return l;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s)
	{
		boolean ret =true;
		Pattern pattern = Pattern.compile("[0-9+./]");
	    char[] ss = s.toCharArray();
	    for (int i=0;i<ss.length; i++)
	    {
	    	Matcher isNum = pattern.matcher(String.valueOf(ss[i]));
	    	if (!isNum.find())
	    	{
	    		ret = false;
	    		break;
	    	}
	    }
		return ret;
	}

	/**
	 * 判断字符串为浮点型
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str){
		boolean ret = true;
		Pattern pattern = Pattern.compile("[0-9]+(\\.?)[0-9]*");
		if(!pattern.matcher(str).matches()){
			ret=false;
		}
		return ret;
	}
	
	/**
	 * 检查邮箱输入是否正确
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str){
		boolean ret = true;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(check);
		if(!pattern.matcher(str).matches()){
			ret=false;
		}
		return ret;
	}
	
	/**
	 * 检查字符串是否有汉字
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str){
		boolean ret = true;
		String check = "([^\u4E00-\u9FA5]|[\u4E00-\u9FA5])*[\u4E00-\u9FA5]+([^\u4E00-\u9FA5]|[\u4E00-\u9FA5])*";
		Pattern pattern = Pattern.compile(check);
		if(!pattern.matcher(str).matches()){
			ret=false;
		}
		return ret;
	}
	/**
	 * 处理Double类型数据的四舍五入以及小数位精确到第N位
	 * @param d  需要处理的double类型的数据
	 * @param decimalNum  需要精确的小数位数
	 * @return
	 */
	public static String printDouble(double d,int decimalNum){
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(decimalNum);
		numberFormat.setGroupingUsed(false);
		
		return numberFormat.format(d);
	}	
	public static void main(String[] args) {
		System.out.println("fdsfds"+isFloat("fdsfds"));
		System.out.println("123"+isFloat("123"));
		System.out.println("123.3"+isFloat("123.3"));
		System.out.println("123.3fdsf"+hasChinese("123.3"));
		System.out.println("房11价多少22了房间33都是"+hasChinese("房11价多少22了房间33都是"));
		System.out.println("fjdsl反对法fjdlsf"+hasChinese("fjdsl反对法fjdlsf"));
		System.out.println("反对法"+hasChinese("反对法"));
		System.out.println("反对法123"+hasChinese("反对法123"));
		System.out.println("123反对法"+hasChinese("123反对法"));
		System.out.println("00232..13213"+hasChinese("00232..13213"));
	}
	
	public static Float floatValue(Double d){
	    if(d!=null){
	       return (Float)d.floatValue();
	    }
	    return null;
	}
	
	public static Double doubleValue(Float f){
	    if(f!=null){
	       return (Double)f.doubleValue();
	    }
	    return null;
	}
	
	public static String valueOf(Integer integer){
		if(integer != null){
			return String.valueOf(integer);
		}else{
			return null;
		}
	}
	
	public static String formatString(String str){
		String rv = "";
		if(str!=null){
			if(str.contains(";")){
				rv = str.replace(";", "<br>");
			}else{
				rv = str;
			}
		}
		return rv;
	}
	/**
	 * 将字符串格式成sql like 的形式
	 * @param str
	 * @param delim
	 * @param column
	 * @return
	 */
	public static String strToSqlLike(String str, String delim,String column) {
		String [] strArr = null;
		String strLike = null;
		String strNull = " 1 = 1";
		//如果需要处理的字符串为空，返回
		if (str == null||str.trim().length()==0){
			return strNull;
		}
		if(delim==null||delim.trim().length()==0){
			return strNull;
		}else{
			strArr = str.split(delim);
		}
		if(column==null||column.trim().length()==0){
			return strNull;
		}
		StringBuffer sb = new StringBuffer("(");
		//循环生成字符串
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].trim().length()>0){
				sb.append(column);
				sb.append(" like '%");
				sb.append(strArr[i].trim().toUpperCase());
				sb.append("%' or ");
			}
		}
		if(sb.length()>1){
            return sb.append(")").toString().replaceAll(" or \\)",")");
		}else{
			return strNull;
		}
	}
	
	/**
	 * 去随机数
	* TODO description
	* @author 
	* @date 2017年1月10日
	* @param passLenth
	* @return
	 */
	public static String getPass(int passLenth) {

		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyz");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < passLenth; i++) {
			// 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
	/**
	 * double 比较大小
	* @param param1
	* @param param2
	* @return int
	* 返回 -1 :param1<param2
	* 返回    0 :param1=param2
	* 返回    1 :param1>param2
	*/
		
	public static int compareTo(double param1, double param2) {
		BigDecimal x1 = new BigDecimal(Double.toString(param1));
		BigDecimal x2 = new BigDecimal(Double.toString(param2));
		return x1.compareTo(x2);
	}
}
