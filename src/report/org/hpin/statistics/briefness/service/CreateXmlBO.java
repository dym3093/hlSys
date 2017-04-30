package org.hpin.statistics.briefness.service;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;



public class CreateXmlBO {
	private String path = "";
    private String dirname = "configfiles";
	private String filename = "queryconfig.xml";
	private String queryfilename = "querylistconfig.xml";
    public CreateXmlBO(String path) {
		    this.path = path;
    }
    public Logger logger = Logger.getLogger(this.getClass());
    public Map GetStatXml(String config_id) {
        String title = "";
        String url = "";
        String sql = "";
        String sqltotal = "";
        String params = "";
        String paramsflag = "";
        Map map=new HashMap();
         try {
          SAXBuilder builder = new SAXBuilder();
          HttpServletRequest request = ServletActionContext.getRequest();
          Document doc = builder.build(new File(request.getRealPath("/")+ filename));

          Element nes = doc.getRootElement(); //
          List statList = nes.getChildren("query");

          for (int i = 0; i < statList.size(); i++) {
            String config_id_xml = ( (Element) statList.get(i)).getAttributeValue("config_id");
            System.out.println("config_id_xml : " + config_id_xml);
            if (config_id_xml.equals(config_id)) {
            	//title = ( (Element) statList.get(i)).getChildText("title");
            	//url = ( (Element) statList.get(i)).getChildText("url");
            	List sqlsList = ( (Element)statList.get(i)).getChildren("sqls");
            	for (int j = 0; j < sqlsList.size(); j++) {
            		sql = ( (Element) sqlsList.get(j)).getChildText("sql");
                	//sqltotal = ( (Element) sqlsList.get(j)).getChildText("sqltotal");
                	params = ( (Element) sqlsList.get(j)).getChildText("params");
                	paramsflag = ( (Element) sqlsList.get(j)).getChildText("paramsflag");
            	}
            	map.put("title", title);
            	//map.put("url", url);
                map.put("sql", sql);
                //map.put("sqltotal", sqltotal);
                map.put("params", params);
                map.put("paramsflag", paramsflag);
               
                break;
             }
          }
        
       
        }
        catch (Exception e) {}
        return map;
      }
	
    @SuppressWarnings("deprecation")
	public Map GetListXml(String config_id,String field) {
        
        String sqltotal = "";
        String sql = "";
        String summary = "";
        String params = "";
        String paramsflag = "";
        Map map=new HashMap();
         try {
          SAXBuilder builder = new SAXBuilder();
          HttpServletRequest request = ServletActionContext.getRequest() ;
          Document doc = builder.build(new File(    request.getRealPath("/")+ filename));

          Element nes = doc.getRootElement(); //
          List statList = nes.getChildren("query");

          for (int i = 0; i < statList.size(); i++) {
            String config_id_xml = ( (Element) statList.get(i)).getAttributeValue(
                "config_id");
            if (config_id_xml.equals(config_id)) {
            	List sqlsList = ( (Element)statList.get(i)).getChildren("sqls");
            	for (int j = 0; j < sqlsList.size(); j++) {
            		List listsqlList = ( (Element)sqlsList.get(j)).getChildren("listsql");
            		for(int t=0;t<listsqlList.size();t++){
            			String field_xml = ( (Element) listsqlList.get(t)).getAttributeValue("field");
            			if(field_xml.equals(field)){
            				 sql = ( (Element) listsqlList.get(t)).getChildText("sql");
            				 sqltotal = ( (Element) listsqlList.get(t)).getChildText("sqltotal");
            			}
            			
            		}
            		summary = ( (Element) sqlsList.get(j)).getChildText("summary");
                	params = ( (Element) sqlsList.get(j)).getChildText("params");
                	paramsflag = ( (Element) sqlsList.get(j)).getChildText("paramsflag");
            	}
            	
                map.put("sql", sql);
                map.put("sqltotal", sqltotal);
                map.put("summary", summary);
                map.put("params", params);
                map.put("paramsflag", paramsflag);
               
                break;
             }
          }
        
       
        }
        catch (Exception e) {}
        return map;
      }
	
	
 public Map GetQueryXml(String config_id) {
        
        String sqltotal = "";
        String sql = "";
        String params = "";
        String paramsflag = "";
        Map map=new HashMap();
         try {
          SAXBuilder builder = new SAXBuilder();
          Document doc = builder.build(new File(path + "WEB-INF/" + dirname + "/" +
        		  queryfilename));

          Element nes = doc.getRootElement(); //
          List statList = nes.getChildren("query");

          for (int i = 0; i < statList.size(); i++) {
            String config_id_xml = ( (Element) statList.get(i)).getAttributeValue(
                "config_id");
            if (config_id_xml.equals(config_id)) {
            	List sqlsList = ( (Element)statList.get(i)).getChildren("sqls");
            	for (int j = 0; j < sqlsList.size(); j++) {
            		sql = ( (Element) sqlsList.get(j)).getChildText("sql");
            		sqltotal = ( (Element) sqlsList.get(j)).getChildText("sqltotal");
            		params = ( (Element) sqlsList.get(j)).getChildText("params");
                	paramsflag = ( (Element) sqlsList.get(j)).getChildText("paramsflag");
            	}
            	
                map.put("sql", sql);
                map.put("sqltotal", sqltotal);
                map.put("params", params);
                map.put("paramsflag", paramsflag);
               
                break;
             }
          }
        
       
        }
        catch (Exception e) {}
        return map;
      }
    
    
    public Map getParams(Map sqlParams,String params,String paramsflag,Map actionMap){
     if(!"".equals(params)){
    	String[] arr1=params.split(",");
    	for(int i=0;i<arr1.length;i++){
    		String str1=arr1[i].trim();
    		String value1=(String)actionMap.get(str1);
    		sqlParams.put(str1,value1);
    	}
     }
     if(!"".equals(paramsflag)){
    	String[] arr2=paramsflag.split(",");
    	for(int j=0;j<arr2.length;j++){
    		String str2=arr2[j];
    		String value2=this.getArrToString((String)actionMap.get(str2));
    		sqlParams.put(str2,value2);
    	}
     }
    	return sqlParams;
    }
	
    public  String  getArrToString( String temp){
			   String ss="";
			   String st="";
			   if(!"".equals(temp) && temp!=null){
			   String[] arr= temp.split(",");
			   for(int i=0;i<arr.length;i++){
			      String str= arr[i];
			      ss+="'"+str+"',";
			   }
			     st= ss.substring(0,ss.length()-1);
			   }
			    return st;
			 }
		
	
}

