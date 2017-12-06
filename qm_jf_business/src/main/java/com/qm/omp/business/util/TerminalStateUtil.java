package com.qm.omp.business.util;
/**
 * 异常情况：0：连接正常，1：连接中断，2：纸币箱满，3：硬币箱满，4：打印机缺纸
 * 拼接字符串，保存到一个字段中
 * @author rh
 *
 */
public class TerminalStateUtil {

	/**
	 * 增加状态
	 * @param oldStr
	 * @param str
	 * @return
	 */
	public static String addExceptionStr(String oldStr, String str){
		if(oldStr==null || oldStr.trim().equals("")){
			return str + ",";
		}else{
			if(oldStr.indexOf(str)!=-1){//已经包括该异常代码则直接返回
				return oldStr;
			}else{//没包括则追加
				
				return oldStr + str + ",";
			}
		}
	}
	
	/**
	 * 减少状态
	 * @param oldStr
	 * @param str
	 * @return
	 */
	public static String minusExceptionStr(String oldStr, String str){
		if(oldStr==null || oldStr.trim().equals("")){
			return "";
		}else{
				
				return oldStr.replace(str+",", "");
		}
	}
	
	public static void main(String[] args) {
		String str = addExceptionStr("1,2,3,", "1");
		str = minusExceptionStr(str, "1");
		System.out.println(str);
	}
}
