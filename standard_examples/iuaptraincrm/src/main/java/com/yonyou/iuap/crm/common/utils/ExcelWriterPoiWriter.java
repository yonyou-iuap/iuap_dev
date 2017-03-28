package com.yonyou.iuap.crm.common.utils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导出数据Excel工具类
* <p>description：</p>
* @author 
* @created 2016年11月29日 上午8:58:16
* @version 1.0
 */
public class ExcelWriterPoiWriter {

	public static Logger logger = LoggerFactory.getLogger(ExcelWriterPoiWriter.class);

	/**
	 * 根据数据，导出excel
	* TODO description
	* @author 
	* @date 2016年11月29日
	* @param response
	* @param resultList
	* @param sheetName
	* @param fileName
	* @throws Exception
	 */
	public static void writeExcel(HttpServletResponse response,List<List<Object>> resultList, String sheetName, String fileName) throws Exception {
		
		
		OutputStream os = null;
		WritableWorkbook workbook = null;
		
		try {
			//防止为空
			if(null==sheetName || "".equals(sheetName)){
				sheetName = "导出";
			}
			if(null==fileName || "".equals(fileName)){
				fileName = "导出";
			}
			
			fileName = new String(fileName.getBytes(System.getProperty("file.encoding")), "ISO8859-1"); // 导出的文字编码
			// 导出的文字编码
			//fileName = new String(fileName.getBytes("GB2312"), "UTF-8");
			//标题样式居中
			WritableCellFormat midTileCenterFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 10,
				       WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE)); 
			midTileCenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
			midTileCenterFormat.setAlignment(Alignment.CENTRE);
			midTileCenterFormat.setBackground(Colour.SEA_GREEN);
			//内容居中
			WritableCellFormat midContentFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 10,
				       WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE)); 
			midContentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
			midContentFormat.setAlignment(Alignment.CENTRE);
			
			//内容居中
			WritableCellFormat errorContentFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 10,
				       WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE)); 
//			midContentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
			midContentFormat.setAlignment(Alignment.LEFT);
			
			//导出数据
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet(sheetName, 0);//创建可写工作表
			ws.getSettings().setDefaultColumnWidth(14);
			if(null != resultList && resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					if(i>10000){
						ws.addCell(CellFactory(0,i,"最多只能导出10000行数据，请调整查询条件再次导出！",errorContentFormat));
						break;
					}
					List<Object> rowList = resultList.get(i);
					for(int j=0;j<rowList.size();j++){
						if(i==0){
							ws.addCell(CellFactory(j,i,rowList.get(j),midTileCenterFormat));
						}else{
							ws.addCell(CellFactory(j,i,rowList.get(j),midContentFormat));
						}
					}
				}
			}
			workbook.write();
			workbook.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if(null != workbook){
				workbook.close();
			}
			if (os != null) {
				os.flush();
				os.close();
			}
		}
	}
	
	/**
	 * 根据数据类型不同，建立相应的对象
	* TODO description
	* @author 
	* @date 2016年11月29日
	* @param i
	* @param j
	* @param columnVal
	* @param columnFormat
	* @return
	* @throws Exception
	 */
	public static WritableCell CellFactory(int i,int j,Object columnVal,WritableCellFormat columnFormat) throws Exception{
		WritableCell returnCell = null;
		
		if(null == columnVal){
			returnCell = new Label(i,j,"",columnFormat);
		}else{
			if(columnVal.getClass().equals(Long.class)){
				returnCell = new jxl.write.Number(i,j,Long.parseLong(columnVal.toString()),columnFormat);
			}else if(columnVal.getClass().equals(Integer.class)){
				returnCell = new jxl.write.Number(i,j,Integer.parseInt(columnVal.toString()),columnFormat);
			}else if(columnVal.getClass().equals(Double.class)){
				returnCell = new jxl.write.Number(i,j,Double.parseDouble(columnVal.toString()),columnFormat);
			}else if(columnVal.getClass().equals(Float.class)){
				returnCell = new jxl.write.Number(i,j,Float.parseFloat(columnVal.toString()),columnFormat);
			}else if(columnVal.getClass().equals(String.class)){
				returnCell = new Label(i,j,columnVal.toString(),columnFormat);
			}else if(columnVal.getClass().equals(Date.class)){
				returnCell = new jxl.write.DateTime(i,j,(Date)columnVal,columnFormat);
			}else if(columnVal.getClass().equals(BigDecimal.class)){
				returnCell = new jxl.write.Number(i,j,((BigDecimal)columnVal).doubleValue(),columnFormat);
			}else{
				returnCell = new Label(i,j,"",columnFormat);
				System.out.println("columnVal.type="+columnVal.getClass());
			}
		}
		return returnCell;
	}
}
