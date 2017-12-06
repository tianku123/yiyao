package com.qm.omp.business.util;

import java.text.DecimalFormat;

public class NumberFormatUtil {

	/**
	 * 保留两位小数
	 * @param d
	 * @return
	 */
	public static Double doubleFormat1(Double d){
		if(d == null){
			return null;
		}
		DecimalFormat format = new DecimalFormat(".##");
		return Double.parseDouble(format.format(d));
	}
	
	public static void main(String[] args) {
		System.out.println(doubleFormat1(2.2222222222222d));
	}
}
