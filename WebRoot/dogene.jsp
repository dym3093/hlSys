<%@ page language="java" import="java.util.*,java.sql.*,java.io.*,cn.yuanmeng.labelprint.test.TestExcelProcess,cn.yuanmeng.labelprint.dao.BaseDao,java.sql.*" pageEncoding="UTF-8"%>
<%@ page import="cn.yuanmeng.labelprint.test.FileCopy" %>
  <%
  String dirPath=request.getParameter("dirPath");
  if(dirPath!=null){ 
	  TestExcelProcess tp=new TestExcelProcess();
	  tp.start(dirPath);
	  out.println("<script>alert('处理结束');location.href='http://gene.healthlink.cn/gene.jsp';</script>");
  }else{
	  out.println("<script>alert('请重新输入');location.href='http://gene.healthlink.cn/gene.jsp';</script>");
  }
  %>
