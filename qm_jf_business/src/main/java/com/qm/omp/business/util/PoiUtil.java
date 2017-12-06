package com.qm.omp.business.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiUtil {

	/**
	 * 获取数据标题单元格样式
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);//设置字体
		style.setWrapText(true);//设置自动换行
		return style;
	}
	
	/**
	 * 获取数据部分单元格样式
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)11);
		style.setFont(font);//设置字体
		style.setWrapText(true);//设置自动换行
		return style;
	}
	
	/**
	 * 获取数据部分单元格样式
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getLeftCellStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)11);
		style.setFont(font);//设置字体
		style.setWrapText(true);//设置自动换行
		return style;
	}
	
	

	/**
	 * 小数变成百分比形式，保留两位小数，小数不够补零
	 */
	public static String retain2DecimalPoint_Percent(Double d){
		if(d==null){
			return "--";
		}
		DecimalFormat fmt1 = new DecimalFormat("#0.00%");
		return fmt1.format(d);
	}
	
	/**
	 * 保留两位小数，小数不够补零
	 */
	public  static String retain2DecimalPoint(Double d){
		if(d==null){
			return "--";
		}
		DecimalFormat fmt1 = new DecimalFormat("##0.00");
		return fmt1.format(d);
	}
	
	/**
	 * 空值转换为零
	 */
	public static double nullToZero(Object obj){
		try {
			if(obj==null){
				return 0d;
			}else{
				return Double.parseDouble(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
		
	}
	
	/**
	 * 非空值返回1
	 */
	public static double notNullReturnOne(Object obj){
		if(obj==null){
			return 0;
		}else{
			return 1;
		}
	}
	
	
	/**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale){
    	if(v2 != 0){         
        	BigDecimal divisor = new BigDecimal(v1);
        	BigDecimal dividend = new BigDecimal(v2);
        	return divisor.divide(dividend, scale, BigDecimal.ROUND_HALF_UP).doubleValue();    
        }
        return 0.00;
    }
    
    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     */
    public static double div(int v1, int v2, int scale){
    	if(v2 > 0){         
    		BigDecimal divisor = new BigDecimal(v1);
    		BigDecimal dividend = new BigDecimal(v2);
    		return divisor.divide(dividend, scale, BigDecimal.ROUND_HALF_UP).doubleValue();    
    	}
    	return 0.00;
    }
    
    /**
     * 加法
     * @param o1
     * @param o2
     * @return
     */
    public static double add(Object o1, Object o2) {
		if(o1==null || "".equals(o1.toString().trim())){
			o1 = 0;
		}
		if(o2==null || "".equals(o2.toString().trim())){
			o2 = 0;
		}
		BigDecimal a = new BigDecimal(o1.toString());
		BigDecimal b = new BigDecimal(o2.toString());
		return a.add(b).doubleValue();
	}
    
    /**
     * 减法
     * @param o1
     * @param o2
     * @return
     */
    public static double sub(Object o1, Object o2){
    	if(o1==null || "".equals(o1.toString().trim())){
			o1 = 0;
		}
		if(o2==null || "".equals(o2.toString().trim())){
			o2 = 0;
		}
    	BigDecimal b = new BigDecimal(o1.toString());
    	BigDecimal b2 = new BigDecimal(o2.toString());
    	return b.subtract(b2).doubleValue();
    }
    
    /**
     * 将颜色值转换为10进制的数据
     * @param hex
     * @return
     */
    public static int convertHexToNumber(String hex){
     int num = 0;
     try{
      num = Integer.parseInt(hex,16);
     }catch(Exception e){
    	 e.printStackTrace();
     }
     return num;
    }
    
}
