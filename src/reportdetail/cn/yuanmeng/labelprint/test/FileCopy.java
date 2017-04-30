package cn.yuanmeng.labelprint.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import cn.yuanmeng.labelprint.dao.BaseDao;

public class FileCopy{
	public static String isExists(String uploadSubDirName){
		try {
			BaseDao bd=new BaseDao();
			String sql="SELECT * FROM epr_report_batch WHERE SUBSTR(xlsoldpath,15,8)='"+uploadSubDirName+"'";
			List<HashMap> reportBatcLlist=bd.querySql(sql);
			if(reportBatcLlist.size()>0){
				return "已处理";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "未处理";
	}
	
	public  static void fileFromSorceToDest(String batchno1) {
		BaseDao dao=new BaseDao();
		String sql="select * from EPR_REPORT_BATCH where status='0' and batchno='"+batchno1+"'";
		List<HashMap> list=null;
		try {
			list = dao.querySql(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(HashMap map:list){
			String batchno= (String)map.get("batchno");
			String xlsoldname= (String)map.get("xlsoldname");
			String xlsoldpath= (String)map.get("xlsoldpath");
			String pathgrade= (String)map.get("pathgrade");
			String xlsnewname= (String)map.get("xlsnewname");
			String xlsnewpath= (String)map.get("xlsnewpath");
			
			String source=xlsoldpath+"\\"+xlsoldname;
			String dest=xlsnewpath+"\\"+xlsnewname;
			 File sourcefiles = new File(source); 
			 File destfiles = new File(dest); 
			 
			   try {
				   FileUtils.copyFile(sourcefiles, destfiles);
			     } catch (IOException e) {
				   e.printStackTrace();
			     }
			   
			   copy(xlsoldpath, xlsnewpath)  ; 
			
		}
		
	}
	
	 private  static void copy(String src, String des) {   
		 
	       File file1=new File(src);                                                         
	       File[] fs=file1.listFiles();                                                      
	       File file2=new File(des);                                                         
	       if(!file2.exists()){                                                              
	           file2.mkdirs();                                                               
	       }                                                                                 
	       for (File f : fs) {                                                               
	           if(f.isFile()){     
	        	   if(f.getName().indexOf(".pdf")!=-1){
	                   //fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法     
	                   try {
						FileUtils.copyFile(f, new File(des+"\\"+f.getName()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	   }
	           }                                                                         
	       }                                                                                 
	                                                                                         
	   }                                                                                     
	                                                                                         
	   /**                                                                                   
	    * 文件拷贝的方法                                                                     
	    */                                                                                   
	   private static void fileCopy(String src, String des) {                                
	                                                                                         
	       BufferedReader br=null;                                                           
	       PrintStream ps=null;                                                              
	                                                                                         
	       try {                                                                             
	           br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));       
	           ps=new PrintStream(new FileOutputStream(des));                                
	           String s=null;                                                                
	           while((s=br.readLine())!=null){                                               
	               ps.println(s);                                                            
	               ps.flush();                                                               
	           }                                                                             
	                                                                                         
	       } catch (FileNotFoundException e) {                                               
	           // TODO Auto-generated catch block                                            
	           e.printStackTrace();                                                          
	       } catch (IOException e) {                                                         
	           // TODO Auto-generated catch block                                            
	           e.printStackTrace();                                                          
	       }finally{                                                                         
	                                                                                         
	               try {                                                                     
	                   if(br!=null)  br.close();                                             
	                   if(ps!=null)  ps.close();                                             
	               } catch (IOException e) {                                                 
	                   // TODO Auto-generated catch block                                    
	                   e.printStackTrace();                                                  
	               }                                                                         
	       }                                                                                 
	                                                                                         
	   }                                                                                     

}
