package org.hpin.statistics.briefness.util;

import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.ArrayList ;
import java.util.Calendar ;
import java.util.Date ;
import java.util.GregorianCalendar ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;

import javax.servlet.http.HttpServletRequest ;

import org.apache.log4j.Logger ;
import org.hpin.common.util.StaticMethod ;
import org.hpin.statistics.briefness.dao.QueryDAO ;




public class StatUtil {

	private static Logger logger = Logger.getLogger(StatUtil.class);
	/**
	 * @param ParameterMap
	 * @return
	 */
	public static Map ParameterMapToUtilMap(Map ParameterMap)
	{
		Map utilMap = new HashMap();
		Iterator iterator = ParameterMap.keySet().iterator();
		while(iterator.hasNext())
		{
			Object key = iterator.next();
			String value = StaticMethod.nullObject2String(ParameterMap.get(key)!=null?((String[])ParameterMap.get(key))[0]:"");
			utilMap.put(key, value);
		}
		
		return utilMap;
	}
	
	/**
	 * 重构原来生成查询参数Map的方法
	 * author:huwuyin 
	 * date :20131023
	 * @param paramsMap
	 * @return
	 */
	public static Map parameterMap2UtilMap(Map paramsMap){
		Map utilMap = new HashMap();
		Iterator iterator = paramsMap.keySet().iterator();
		while(iterator.hasNext())
		{
			Object key = iterator.next();
			String value = StaticMethod.nullObject2String(paramsMap.get(key)!=null ? paramsMap.get(key) : "");
			utilMap.put(key, value);
		}
		
		return utilMap;
	}
	
	public static String getCpmcFromTree(String morecpId){
		String cpmc="";  
		if(!"".equals(morecpId)){
			String cpsql="SELECT wmsys.wm_concat(cpmc) as cpmc FROM hq_cpmanger where ";
			String[] arr=morecpId.split(",");
			int size=arr.length;
			for(int i=0;i<size;i++){
				String str=arr[i];
			  if(i!=size-1){
				if(str.length()==9){
					    cpsql+="cpmcid='"+str+"' or ";
				}else{
					    cpsql+="cpmcid like '"+str+"%' or ";
				}
			  }else{
				  if(str.length()==9){
						cpsql+="cpmcid='"+str+"'";
				  }else{
						cpsql+="cpmcid like '"+str+"%'";
				  }  
			  }
			}
			QueryDAO dao=new QueryDAO();
			cpmc=dao.getCpmcFormTree(cpsql);
    	}
		return cpmc;
	}
	/**
	 * �޸�request.getParameterMap() �е�Map
	 * @param actionMap
	 * @return
	 * @throws Exception
	 */
	public static Map modActionMap(HttpServletRequest request,Map actionMap) throws Exception
	{
		String type = String.valueOf(actionMap.get("type"));
		String type1 = String.valueOf(actionMap.get("type1"));
		GregorianCalendar bdate = null;
		GregorianCalendar edate = null;
		//������ͳ��
		if("half".equalsIgnoreCase(type))
		{  
			int beginyear = Integer.parseInt(String.valueOf(actionMap.get("beginyear")));
	
			if(actionMap.get("halfselect") != null)
			{
				if("half_one".equalsIgnoreCase(String.valueOf(actionMap.get("halfselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 1 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 6 - 1, 1, 23, 59, 59);
				}
				else if("half_two".equalsIgnoreCase(String.valueOf(actionMap.get("halfselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 7 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 12 - 1, 1, 23, 59, 59);
				}
			}	
				
			Date[] datee = StatUtil.getMonthStartAndEndDate(edate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dfbdate = sdf.format(bdate.getTime());
			String dfedate = sdf.format(datee[1]);
			
			actionMap.put("beginTime", dfbdate);
			actionMap.put("endTime", dfedate);
		}
		//����ͳ��
		if("year".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			String beginyear = String.valueOf(actionMap.get("beginyear"));
			String beginTime=beginyear+"-01-01 00:00:00";
		    String endTime=beginyear+"-12-31 23:59:59";
			actionMap.put("beginTime", beginTime);
			actionMap.put("endTime", endTime);
		}
		//����ͳ��
		if("day".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			String beginday = String.valueOf(actionMap.get("beginday"));
			String beginTime=beginday+" 00:00:00";
		    String endTime=beginday+" 23:59:59";
			actionMap.put("beginTime", beginTime);
			actionMap.put("endTime", endTime);
		}
		//������ͳ��
		if("week".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			int beginyear = Integer.parseInt(String.valueOf(actionMap.get("beginyear")));
			int beginweek = 1;
			if(actionMap.get("beginweek") != null)
			{
				beginweek = Integer.parseInt(String.valueOf(actionMap.get("beginweek")));
			}
			String weekstarttime=getYearWeekFirstDay(beginyear,beginweek)+" 00:00:00";
		    String weekendtime=getYearWeekEndDay(beginyear,beginweek)+" 23:59:59";
			
			actionMap.put("beginTime", weekstarttime);
			actionMap.put("endTime", weekendtime);
		}
		
		//������ͳ��
		if("month".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			int beginyear = Integer.parseInt(String.valueOf(actionMap.get("beginyear")));
			int beginmonth = 1;
			if(actionMap.get("beginmonth") != null)
			{
				beginmonth = Integer.parseInt(String.valueOf(actionMap.get("beginmonth")));
			}
			
			bdate = new GregorianCalendar(beginyear, beginmonth - 1, 1, 00, 00, 00);
			edate = new GregorianCalendar(beginyear, beginmonth - 1, 1, 23, 59, 59);
			Date[] datee = StatUtil.getMonthStartAndEndDate(edate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dfbdate = sdf.format(bdate.getTime());
			String dfedate = sdf.format(datee[1]);
			
			actionMap.put("beginTime", dfbdate);
			actionMap.put("endTime", dfedate);
		}
		
		//������ͳ��
		if("season".equalsIgnoreCase(type))
		{
			int beginyear = Integer.parseInt(String.valueOf(actionMap.get("beginyear")));
			if(actionMap.get("seasonselect") != null)
			{
				if("season_one".equalsIgnoreCase(String.valueOf(actionMap.get("seasonselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 1 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 3 - 1, 1, 23, 59, 59);
				}
				else if("season_two".equalsIgnoreCase(String.valueOf(actionMap.get("seasonselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 4 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 6 - 1, 1, 23, 59, 59);
				}
				else if("season_three".equalsIgnoreCase(String.valueOf(actionMap.get("seasonselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 7 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 9 - 1, 1, 23, 59, 59);
				}
				else if("season_four".equalsIgnoreCase(String.valueOf(actionMap.get("seasonselect"))))
				{
					bdate = new GregorianCalendar(beginyear, 10 - 1, 1, 00, 00, 00);
					edate = new GregorianCalendar(beginyear, 12 - 1, 1, 23, 59, 59);
				}
				
				Date[] datee = StatUtil.getMonthStartAndEndDate(edate);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dfbdate = sdf.format(bdate.getTime());
				String dfedate = sdf.format(datee[1]);
				
				actionMap.put("beginTime", dfbdate);
				actionMap.put("endTime", dfedate);
			}
		}
		if("month2month".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			String beginyear = String.valueOf(actionMap.get("beginyear"));
			String endyear = String.valueOf(actionMap.get("endyear"));
			String beginmonth = String.valueOf(actionMap.get("beginmonth"));
			String endmonth = String.valueOf(actionMap.get("endmonth"));
			String beginTime=beginyear+"-"+beginmonth;
		    String endTime=endyear+"-"+endmonth;
			actionMap.put("beginTime", beginTime);
			actionMap.put("endTime", endTime);
		}
		if("yearmonth2month".equalsIgnoreCase(type))
		{
			//��Ҫ����beginTime �� endTime
			String beginyear = String.valueOf(actionMap.get("beginyear"));
			String beginmonth = String.valueOf(actionMap.get("beginmonth"));
			String endmonth = String.valueOf(actionMap.get("endmonth"));
			String beginTime=beginyear+"-"+beginmonth;
		    String endTime=beginyear+"-"+endmonth;
			actionMap.put("beginTime", beginTime);
			actionMap.put("endTime", endTime);
		}		
		 if("statcpmore".equalsIgnoreCase(type1))
			{
				String[] statcpmores = request.getParameterValues("statcpmore");
				if(statcpmores != null && statcpmores.length>=0)
				{
					String statcpmoreString = "";
					for(int i=0;i<statcpmores.length;i++){
						if(i==statcpmores.length-1){
							statcpmoreString = statcpmoreString + statcpmores[i];
						}else{
							statcpmoreString = statcpmoreString + statcpmores[i]+ ",";
						}
					}
					actionMap.put("statcpmore", statcpmoreString);
				}
			}	
		 if("yearseason".equalsIgnoreCase(type))
			{
			 String beginyear = String.valueOf(actionMap.get("beginyear"));
			 int beginseason = StaticMethod.nullObject2int(actionMap.get("beginseason"));
			 int endseason = StaticMethod.nullObject2int(actionMap.get("endseason"));
			 String beginmonth="";
			 String endmonth="";
			 switch(beginseason)
				{
				case 1 : beginmonth="01";break;
				case 2 : beginmonth="04";break;
				case 3 : beginmonth="07";break;
				case 4 : beginmonth="10";break;
			 }
			 switch(endseason)
				{
				case 1 : endmonth="03";break;
				case 2 : endmonth="06";break;
				case 3 : endmonth="09";break;
				case 4 : endmonth="12";break;
			 }
			    String beginTime=beginyear+"-"+beginmonth;
			    String endTime=beginyear+"-"+endmonth;
				actionMap.put("beginTime", beginTime);
				actionMap.put("endTime", endTime);
			} 
		return actionMap;
	}
	
	
	/**
     * @����     ����ĳ��ĳ�ܵĿ�ʼ����
     * @return  interger
     * @throws ParseException
     */
	private static String getYearWeekFirstDay(int yearNum,int weekNum) throws ParseException {
     
     Calendar cal = Calendar.getInstance();
     cal.set(Calendar.YEAR, yearNum);
     cal.set(Calendar.WEEK_OF_YEAR, weekNum);
     cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
     //�ֱ�ȡ�õ�ǰ���ڵ��ꡢ�¡���
     String tempYear = Integer.toString(yearNum);
     String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
     String tempDay = Integer.toString(cal.get(Calendar.DATE));
     if(Integer.parseInt(tempMonth)<10){
    	 tempMonth="0"+tempMonth;
     }if(Integer.parseInt(tempDay)<10){
    	 tempDay="0"+tempDay;
     }
     String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
     return tempDate;


     
    }
    /**
     * @����     ����ĳ��ĳ�ܵĽ�������
     * @return  interger
     * @throws ParseException
     */
    private static String getYearWeekEndDay(int yearNum,int weekNum) throws ParseException {
     Calendar cal = Calendar.getInstance();
     cal.set(Calendar.YEAR, yearNum);
     cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
     cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //�ֱ�ȡ�õ�ǰ���ڵ��ꡢ�¡���
     String tempYear = Integer.toString(yearNum);
     String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
     String tempDay = Integer.toString(cal.get(Calendar.DATE));
     if(Integer.parseInt(tempMonth)<10){
    	 tempMonth="0"+tempMonth;
     }if(Integer.parseInt(tempDay)<10){
    	 tempDay="0"+tempDay;
     }
     String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
     return tempDate;
    }


	
	
	
	/**
	 * �õ����µĵ�һ������һ��
	 * @param calendar
	 * @return
	 */
	private static Date[] getMonthStartAndEndDate(Calendar calendar) 
	{    
		Date[] dates = new Date[2];
		Date firstDateOfMonth, lastDateOfMonth;
		// �õ����������µĵڼ���
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		// ��ȥdayOfMonth,�õ���һ������ڣ���ΪCalendar�ã����ÿ�µĵ�һ�죬����Ҫ��һ
		calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
		firstDateOfMonth = calendar.getTime();
		// calendar.getActualMaximum(Calendar.DAY_OF_MONTH)�õ�������м���
		calendar.add(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		lastDateOfMonth = calendar.getTime();
		dates[0] = firstDateOfMonth;
		dates[1] = lastDateOfMonth;
		return dates;  
	}
	
	public static Date getMonthEndDate(Calendar calendar)
	{
		return getMonthStartAndEndDate(calendar)[1];
	}
	
	public static Date getMonthStartDate(Calendar calendar)
	{
		return getMonthStartAndEndDate(calendar)[0];
	}
	
	
	
	/**
	 * �滻String,��Ҫ�滻��String��repMap�е�key���滻��ֵ��repMap�е�value
	 * @param str
	 * @param repMap
	 * @param marker
	 * @return
	 * @throws Exception
	 */
	public static String getRepString(String str ,Map repMap, String marker) throws Exception
	{	
		int index1 = str.indexOf(marker);
		while(index1 > 0)
		{
			int index2 = str.indexOf(marker, index1+marker.length());
			if(index2 == -1)
			{
				throw new Exception("û���ҵ���2��"+marker+",��鿴�����㷨�ļ�<query-define>�ڵ��Ƿ���ȷ");
			}
			String repString = str.substring(index1+marker.length(), index2);
			Object tempvalue = repMap.get(repString);
			String valueString = String.valueOf(tempvalue==null?"":tempvalue);
			//valueString 为空替换 和 转取时控值和汇总特殊处理
			if("".equalsIgnoreCase(valueString)||"TOTAL10602".equals(valueString)||"NULL10601".equals(valueString)){
				int andindex = str.lastIndexOf(" and ", index1);
				int orindex = str.lastIndexOf(" or ", index1);
				String and_OR_String = "";
					
				int index = Math.max(andindex, orindex);
				
				int len = index2 + marker.length();
				
				if(str.length() >= (index2 + +marker.length() + 1) && "'".equalsIgnoreCase(str.substring(index2 + +marker.length(),index2 + +marker.length() + 1)))
				{
					len = len + 1;
				}
				
				and_OR_String = str.substring(index, len);
				
				if(and_OR_String.indexOf(" in ") != -1)
				{
					int indexlastOp = str.indexOf(")", index);
					and_OR_String = str.substring(index, indexlastOp + ")".length());
				}
				if(and_OR_String.indexOf(" like ") != -1)
				{
					int indexlastOp = str.indexOf("%'", index);
					and_OR_String = str.substring(index, indexlastOp + "%'".length());
				}
				
				//moduify by yuejq sql截取日期类型特殊处理to_date()要截取到最后的）
				if("filter_and_createTime_GE_T".equalsIgnoreCase(repString)||"filter_and_createTime_LE_T".equalsIgnoreCase(repString)
				    	||"filter_and_recordTime_GE_T".equalsIgnoreCase(repString)||"filter_and_recordTime_LE_T".equalsIgnoreCase(repString)
						||"filter_and_inTime_GE_T".equalsIgnoreCase(repString)||"filter_and_inTime_LE_T".equalsIgnoreCase(repString)
						||"filter_and_in_Time_GE_T".equalsIgnoreCase(repString)||"filter_and_in_Time_LE_T".equalsIgnoreCase(repString)
						||"filter_and_productOnlineDate_GE_T".equalsIgnoreCase(repString)||"filter_and_productOnlineDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_samplingDate_GE_T".equalsIgnoreCase(repString)||"filter_and_samplingDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_eventDate_GE_T".equalsIgnoreCase(repString)||"filter_and_eventDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_importDate_GE_T".equalsIgnoreCase(repString)||"filter_and_importDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_productOnlineDate_GE_T".equalsIgnoreCase(repString)||"filter_and_productOnlineDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_submittedDate_GE_T".equalsIgnoreCase(repString)||	"filter_and_submittedDate_LE_T".equalsIgnoreCase(repString)
						||"filter_and_reportLaunchDate_GE_T".equalsIgnoreCase(repString)||"filter_and_reportLaunchDate_LE_T".equalsIgnoreCase(repString)){
					int indexlastOp = str.indexOf(")", index+marker.length());
					and_OR_String = str.substring(index, indexlastOp + ")".length());
				}
				if("NULL10601".equals(valueString)){
					int indexlastOp = str.lastIndexOf("=", index1);
					index2 = str.indexOf(marker, index1+(marker+"'").length());
					repString = str.substring(indexlastOp,index2+ +"@'".length());
					str = replaceAll(str,repString," is null ");
				}else{
					str = replaceAll(str,and_OR_String,"");
				}
			} 
			else
			{
				str = str.replaceAll(marker+repString+marker, valueString);
			}
			
			index1 = str.indexOf(marker);
		}
		System.out.println(str);
		return str;
	}
	
	public static String getDateURL(String path ,String statID,String excelname)
	{
		return  path + "/statistics/queryaction!performSearchDo.action?" + "statID="+statID;
	}
	
	
	/**
	 * �滻 �ַ�
	 * @param sourceStr
	 * @param matchStr
	 * @param targetStr
	 * @return
	 */
	public static String replaceAll(String sourceStr, String matchStr, String targetStr) 

	{
		StringBuffer s = new StringBuffer(sourceStr);
		int pos = -1;
		while ((pos = s.indexOf(matchStr)) != -1) 
		{
			s.replace(pos, pos + matchStr.length(), targetStr);
		}

		return s.toString(); 
	}
	
	public static void main(String[] args){
		List list1=new ArrayList();
		Map map11= new HashMap();
		Map map12= new HashMap();
		map11.put("s1", "101");
		map11.put("f1", "5");
		map12.put("s1", "102");
		map12.put("f1", "7");
		list1.add(map11);
		list1.add(map12);
		List list2=new ArrayList();
		Map map21= new HashMap();
		Map map22= new HashMap();
		Map map23= new HashMap();
		map21.put("s1", "102");
		map21.put("f2", "4");
		map22.put("s1", "103");
		map22.put("f2", "3");
		map23.put("s1", "104");
		map23.put("f2", "6");
		list2.add(map21);
		list2.add(map22);
		list2.add(map23);
	}
//	public ArrayList execute(String sql) { 
//		ArrayList list = new ArrayList(); 
//		Hashtable table = null; 
//		conn = DBUtil.getConnection(); 
//		try { 
//		stmt = conn.createStatement(); 
//
//		ResultSet rs = stmt.executeQuery(sql); 
//		rsmd=rs.getMetaData(); 
//		int len=rsmd.getColumnCount(); 
//		while(rs.next()){ 
//		table= new Hashtable(); 
//		for(int i=1;i<=len;i++){ 
//		table.put(rsmd.getColumnName(i), rs.getString(i)); 
//
//		} 
//		list.add(table); 
//		} 
//
//		} catch (Exception e) { 
//		e.printStackTrace(); 
//		} 
//
//		return list; 
//		} 

}