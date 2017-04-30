package org.hpin.common.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 保证代码任何地方都能获取到ApplicationContext
 * @author ybc
 * @since 2016-07-01
 */
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	//获取存储在静态变量中的applicationContext
	public static ApplicationContext getApplicationContext(){
		checkApplicationContext();
		return applicationContext;
	}
	
	//从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz){
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}
	
	private static void checkApplicationContext() {
		if(applicationContext == null){
			throw new IllegalStateException(
					"applicationContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	//清除applicationContext静态变量
	public static void cleanApplicationContext(){
		applicationContext = null;
	}
	
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.cleanApplicationContext();
	}

}
