package com.qm.omp.business.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


/** 
* @ClassName: SMSSender 
* @Description: TODO  发送 短信
* @author henryfang1@163.com   
* @date 2012-9-9 下午03:51:34 
*  
*/ 

  
public class SMSSender {
	private Logger  logger = Logger.getLogger(SMSSender.class);
	
	
	//发送传递的参数
	//http://www.ztsms.cn:8800/sendXSms.do?username=用户名&password=密码&mobile=手机号码&content=内容&dstime=&productid=产品ID&xh=留空
	private static String username;
	private static String password;
	private static String mobile;
	private static String content;
	private static String dstime;
	private static Integer productid;
	private static String xh;
	private static String sendurl;

	/**   
	 * 创建一个新的实例 SMSSender.   
	 *  助通科技提供的用户名与地址
	 */
	/****
	 * username	用户名（必填）
	 * 	password	密码（必填）
	 * 	mobile	手机号，多个手机号为用半角 , 分开，如13899999999,13688888888(最多100个，必填)
	 * 	content	发送内容（必填）
	 * 	dstime	定时时间，为空时表示立即发送（选填）
	 * 	productid	产品id(必填)
	 * 	xh	扩展号,留空
	 * ***/
	
		
	public SMSSender() {
		username = "1000mu"; //您的用户名
		password = "1000muQM"; //密码
		sendurl = "http://www.ztsms.cn:8800/"; //助通提供的发送地址
		productid = 676767; //产品ID号
		xh = ""; //扩展号留空
		dstime = "";//为空表示立即发送
	}

	public String sendSms(String mobile, String content) {
		String sendUrl = null;
		try {// 否则发到手机乱码
			sendUrl = sendurl + "sendSms.do?username=" + username + "&password="+ password 
			+ "&mobile=" + mobile + "&content="+URLEncoder.encode(content, "UTF-8")
			+ "&productid=" + productid + "&xh="+xh + "&dstime=" + dstime;
		} catch (UnsupportedEncodingException uee) {
			logger.error(uee.toString());
		}

		logger.info("短信内容为------------->:" + content);
		return getUrl(sendUrl);
	}

	
	
	/** 
	 * @Title: getUrl 
	 * @Description: 获取地址
	 * @param urlString
	 * @return    
	 */ 
		
	public String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(15000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		String result = "";
		try {
			result = URLDecoder.decode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/***
	 * 测试地址
	 * 
	 * ***/
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String message = "1号机异常！1号机异常！1号机异常！1号机异常！1号机异常！1号机异常！"+dateFormat.format(new Date());
//		String phone = "13088888888,15288888888,13888888888,13788888888,15088888888";
		String phone = "15295566950";
		String r = new SMSSender().sendSms(phone, message);
		System.out.println(r);
		
		
	}

}
