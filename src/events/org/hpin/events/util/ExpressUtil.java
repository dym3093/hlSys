package org.hpin.events.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 快递接口测试
 * @author Marver
 *2016-1-20上午9:26:36
 */
public class ExpressUtil {
	private final static String expressUrl="http://api.shikexin.com/ws/api/getExpressInf?postid=";
	private final static String appKey="&appKey=edba458ff49cb459a2af07695a5b60b5";
	
	public static String expressQuery(String postid){
		String content="";
		try
		{
			URL url= new URL(expressUrl+postid+appKey);
			URLConnection con=url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			String type = con.guessContentTypeFromStream(urlStream);
			String charSet=null;
			if (type == null){
				type = con.getContentType();
			}
				
			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0){
				return null ;
			}
				
			if(type.indexOf("charset=") > 0){
				charSet = type.substring(type.indexOf("charset=") + 8);
			}

		    byte b[] = new byte[10000];
		    int numRead = urlStream.read(b);
			content = new String(b, 0, numRead);
			while (numRead != -1) {
			    if (numRead != -1) {
			     //String newContent = new String(b, 0, numRead);
			      String newContent = new String(b, 0, numRead, charSet);
			      content = newContent;
			    }
			    numRead = urlStream.read(b);
			 }
			   System.out.println("content:" + content);
			   urlStream.close();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return content;
	}
	public static void main(String[] args) {
		String postid = "700074134800";
		expressQuery(postid);
	}
}
