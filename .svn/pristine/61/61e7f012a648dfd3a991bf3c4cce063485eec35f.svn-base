package org.hpin.statistics.briefness.dao;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionFactory1 {

	 public static Connection getConnection()
	    {
	        Connection con = null;
	        
	        try
	        {
	            //register driver
	        	Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
	            String url =
	                "jdbc:microsoft:sqlserver://211.100.28.203:1433;DatabaseName=4-10-1";

	            String user = "huanqiu";
	            String password = "huanqiu123)";
              con = DriverManager.getConnection(url, user, password);
	            //get connection
	            // con = DriverManager.getConnection("jdbc:informix-sqli://10.101.16.118:1526/eoms35b4:INFORMIXSERVER=ol_wps;NEWCODESET=GBK,8859_1,819;", "informix", "Info#123");
	            //con = DriverManager.getConnection("jdbc:informix-sqli://10.101.9.42:8004/eoms35b4:INFORMIXSERVER=eoms3db;NEWCODESET=GBK,8859_1,819;IFX_LOCK_MODE_WAIT=20;IFX_USE_STRENC=true;", "informix", "informix");
	           
	        }catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	        
	        return con;
	    }
}
