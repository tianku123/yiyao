package com.qm.omp.business.web.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解类，用于标识方法的作用和参数
 * @author rh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {
	
	String menuName();//模块名称
	
	String method();//操作方法或作用描述
	
	String[] params();//参数描述
	
	String description();//日志描述
}
