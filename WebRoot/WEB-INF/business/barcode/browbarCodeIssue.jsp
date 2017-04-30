<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
    <div class="pageFormContent" layoutH="56">
      <div>
        <div class="tip"><span>基因包库存管理信息</span></div>
         <p>
          <label>创建人：</label>
          <input type="text" name="barCodeIssue.createUserId" value="${ barCodeIssue.createUserId }"  readonly="readonly"/>
        </p>
         <p>
          <label>创建时间：</label>  
          <input type="text" name="barCodeIssue.createTime" value="${fn:substring(barCodeIssue.createTime,0,19) }"  readonly="readonly"/>
        </p>
        
        <p>
          <label>支公司名称：</label>        
          <input type="text" name="barCodeIssue.companyName" value="${ barCodeIssue.companyName }"  readonly="readonly"/>
        </p>
         <p>
          <label>基因包数量：</label>        
          <input type="text" name="barCodeIssue.barcodeCount" value="${ barCodeIssue.barcodeCount }"  readonly="readonly"/>
        </p>
         <p>
          <label>联系人：</label>        
          <input type="text" name="barCodeIssue.contacts" value="${ barCodeIssue.contacts }"  readonly="readonly"/>
        </p>
         <p>
          <label>联系人电话：</label>        
          <input type="text" name="barCodeIssue.contactsTel" value="${ barCodeIssue.contactsTel }"  readonly="readonly"/>
        </p>
         <p>
          <label>邮编：</label>        
          <input type="text" name="barCodeIssue.postcode" value="${ barCodeIssue.postcode }"  readonly="readonly"/>
        </p>
         <p>
          <label>详细地址：</label>        
          <input type="text" name="barCodeIssue.address" value="${ barCodeIssue.address }"  readonly="readonly"/>
        </p>
         <p>
          <label>快递单号：</label>        
          <input type="text" name="barCodeIssue.expressNo" value="${ barCodeIssue.expressNo }"  readonly="readonly"/>
        </p> 
        <p>
           <label>发送接收状态：</label>
              <select  id="status" name="barCodeIssue.status" rel="iselect" >
              	<option value=0 >请选择</option>
						    <option value=1 <c:if test="${ barCodeIssue.status=='1' }">selected="selected"</c:if>>已发出</option> 
								<option value=2 <c:if test="${ barCodeIssue.status=='2' }">selected="selected"</c:if>>已接收</option>							
							</select>
        </p>             
        <p>
        	<label>发出日期：</label>
          <input type="text" name="barCodeIssue.sendDate"  value="${barCodeIssue.sendDate}" readonly="readonly"/>          
        </p>    
         <p>
         	<label>接收日期：</label>
          <input type="text" name="barCodeIssue.receiveDate"  value="${barCodeIssue.sendDate}" readonly="readonly"/>          
        </p> 
        
       
         <p class="nowrap"><label style="height: 60px;">备注说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" name="barCodeIssue.remark" readonly="readonly">${barCodeIssue.remark}</textarea>
        </p>    
        
      </div>
    </div>
 </div>