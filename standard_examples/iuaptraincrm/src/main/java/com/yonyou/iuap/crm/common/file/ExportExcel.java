package com.yonyou.iuap.crm.common.file;

import java.io.OutputStream;
import java.util.Collection;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
//import org.apache.poi.hssf.usermodel.HSSFComment;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFPatriarch;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel<T> {
  
    @SuppressWarnings("unchecked")
    public void exportExcel(String title,String[] columns, String[] headers,
            Collection<T> dataset, OutputStream out, String pattern)
    {
        // 声明工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成表格
//        HSSFSheet sheet = workbook.createSheet(title);
//        // 设置表格默认列宽度为15个字节
//        sheet.setDefaultColumnWidth((short) 15);
//        // 生成表头样式
//        HSSFCellStyle headstyle = workbook.createCellStyle();
//        // 设置样式
//        headstyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        headstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 生成表头字体
//        HSSFFont headfont = workbook.createFont();
//        headfont.setColor(HSSFColor.VIOLET.index);
//        headfont.setFontHeightInPoints((short) 12);
//        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        // 把字体应用到当前的样式
//        headstyle.setFont(headfont);
//        // 生成表体样式
//        HSSFCellStyle bodystyle = workbook.createCellStyle();
//        bodystyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//        bodystyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        bodystyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        bodystyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        bodystyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        bodystyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        bodystyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        bodystyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        // 生成表体字体
//        HSSFFont bodyfont = workbook.createFont();
//        bodyfont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        bodystyle.setFont(bodyfont);
//        // 声明一个画图的顶级管理器
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//        // 定义注释的大小和位置
//        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,  
//                0, 0, 0, (short) 4, 2, (short) 6, 5));  
//        // 设置注释内容
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("leno");
//        // 产生表格标题行
//        HSSFRow row = sheet.createRow(0);
//        for (short i = 0; i < headers.length; i++)
//        {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellStyle(headstyle);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//        Iterator<T> it = dataset.iterator();
//        int index = 0;
//        while (it.hasNext()){
//        	index++;
//            row = sheet.createRow(index);
//            T t = (T) it.next();
//            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
////            Field[] columns = t.getClass().getDeclaredFields();
//            for (short i = 0; i < columns.length; i++)
//            {
//                HSSFCell cell = row.createCell(i);
//                cell.setCellStyle(bodystyle);
//                String fieldName = columns[i];
//                String getMethodName = "get"
//                		+ fieldName.substring(0, 1).toUpperCase()
//                        + fieldName.substring(1);
//                try{
//                    Class tCls = t.getClass();
//                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
//                    Object value = getMethod.invoke(t, new Object[]{});
//                    // 判断值的类型后进行强制类型转换
//                    String textValue = null;
//                    if (value instanceof byte[]){  
//                        // 有图片时，设置行高为60px;  
//                        row.setHeightInPoints(60);  
//                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
//                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
//                        byte[] bsValue = (byte[]) value;  
//                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
//                                1023, 255, (short) 6, index, (short) 6, index);  
//                        anchor.setAnchorType(2);  
//                        patriarch.createPicture(anchor, workbook.addPicture(  
//                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));  
//                    }else{
//                        // 其它数据类型都当作字符串简单处理
//                        textValue = value.toString();  
//                    }  
//                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
//                    if (textValue != null){  
//                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
//                        Matcher matcher = p.matcher(textValue);
//                        if (matcher.matches()){
//                            // 是数字当作double处理  
//                            cell.setCellValue(Double.parseDouble(textValue));  
//                        }else{
//                            HSSFRichTextString richString = new HSSFRichTextString(  
//                                    textValue);  
//                            HSSFFont font3 = workbook.createFont();  
//                            font3.setColor(HSSFColor.BLUE.index);  
//                            richString.applyFont(font3);  
//                            cell.setCellValue(richString);  
//                        }
//                    }
//                }
//                catch (SecurityException e){  
//                    e.printStackTrace();  
//                }  
//                catch (NoSuchMethodException e){  
//                    e.printStackTrace();  
//                }  
//                catch (IllegalArgumentException e){  
//                    e.printStackTrace();  
//                }  
//                catch (IllegalAccessException e){  
//                    e.printStackTrace();  
//                }  
//                catch (InvocationTargetException e){
//                    e.printStackTrace();
//                }
//                finally{
//                    // 清理资源  
//                }
//            }
//        }  
//        try{  
//            workbook.write(out);  
//            
//        }  
//        catch (IOException e){  
//            e.printStackTrace();  
//        }  
  
    }
}
