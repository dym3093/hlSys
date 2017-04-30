package org.hpin.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量表
 * 
 * @author thinkpad
 * 
 */
public class Constant {
   
	public final static String admin_name = "admin"; // 管理员名称

	public final static String administrator_name = "administrator"; // 管理员名称
	
	public final static String AMSadmin_name = "AMSadmin" ;
	
	public static List<String> mealNameList=new ArrayList<String>();
	
	// 要打印的套餐
	public final static List<String> getPrintMealName(){
		if(null!=mealNameList && 0==mealNameList.size()){
			mealNameList.add("p53");
			mealNameList.add("P53");
			mealNameList.add("T017.基础一(男)");
			mealNameList.add("T017.基础一(男) D102.P53");
			mealNameList.add("T018.基础一(女)");
			mealNameList.add("T018.基础一(女) D102.P53");
			mealNameList.add("T019.基础二(男)");
			mealNameList.add("T020.基础二(女)");
			mealNameList.add("T021.基础三(男)");
			mealNameList.add("T022.基础三(女)");
			mealNameList.add(" 基础一");
			/*mealNameList.add("儿童成长");
			mealNameList.add("儿童成长一");20160817周卫东邮件(Fw: Re: 20160817基础一、儿童成长一太保深圳85_85)通知取消打印
			mealNameList.add("儿童成长一+基础三");
			20160818周卫东邮件(回复: 回复: 不需生成打印任务)通知取消打印
			mealNameList.add("北方旧版套餐一");
			mealNameList.add("北方旧版套餐七");
			mealNameList.add("北方旧版套餐三");
			mealNameList.add("北方旧版套餐二");
			mealNameList.add("北方旧版套餐五");
			mealNameList.add("北方旧版套餐六");
			mealNameList.add("北方旧版套餐四");
			mealNameList.add("北方旧版常规二");*/
			mealNameList.add("基础--");
			mealNameList.add("基础1");
			mealNameList.add("基础一");
			mealNameList.add("基础一 （联动）");
			mealNameList.add("基础一(新增)");
			mealNameList.add("基础一(新增）");
			mealNameList.add("基础一(联动)");
			mealNameList.add("基础一+基础三");
			mealNameList.add("基础一+基础三（无代谢）");
			mealNameList.add("基础一+基础二");
			mealNameList.add("基础一+基础二（无代谢）");
			mealNameList.add("基础一平安");
			mealNameList.add("基础一（平安）");
			mealNameList.add("基础一(平安)");
			mealNameList.add("基础一新增");
			mealNameList.add("基础一（新增 ）");
			mealNameList.add("基础一（新增)");
			mealNameList.add("基础一（新增）");
			mealNameList.add("基础一（联动）");
			mealNameList.add("基础三");
			mealNameList.add("基础三(新增)");
			mealNameList.add("基础三+p53");
			mealNameList.add("基础三+P53");
			mealNameList.add("基础三新增");
			mealNameList.add("基础三（新增)");
			mealNameList.add("基础三（新增）");
			mealNameList.add("基础二");
			mealNameList.add("女性套餐二");
			mealNameList.add("特殊基础一");
			mealNameList.add("基础一（捷越）");
			/**@since 2016年11月4日10:54:31 */
			mealNameList.add("精准一");
			mealNameList.add("精准二");
			mealNameList.add("精准三");
			mealNameList.add("精准四");
			mealNameList.add("精准五");
			mealNameList.add("精准六");
			mealNameList.add("精准七");
			mealNameList.add("精准八");
			mealNameList.add("精准九");
			mealNameList.add("精准十");
			
			mealNameList.add("高端一");
			mealNameList.add("高端二");
			mealNameList.add("高端三");
			mealNameList.add("高端四");
			mealNameList.add("高端五");
			mealNameList.add("高端六");
			mealNameList.add("高端七");
			mealNameList.add("高端八");
			mealNameList.add("高端九");
			mealNameList.add("高端十");
			
			mealNameList.add("肿瘤基础0");
			mealNameList.add("肿瘤基础一");
			mealNameList.add("肿瘤基础二");
			mealNameList.add("肿瘤基础三");
			
			mealNameList.add("心脑基础0");
			mealNameList.add("心脑基础一");
			mealNameList.add("心脑基础二");
			mealNameList.add("心脑基础二新增");
			mealNameList.add("心脑基础三");
			
			mealNameList.add("长寿基础一");
			mealNameList.add("长寿基础二");
			mealNameList.add("长寿基础三");
			
		}
		
		return mealNameList;
	}
	
}