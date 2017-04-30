<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/business/barCodeIssue!saveBarCodeIssue.action" method="post">
        <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />     
        
    <div class="pageFormContent" layoutH="56">
      <div>
        <div class="tip"><span>基因包库存管理信息</span></div>     
        <input name="barCodeIssue.id" type="hidden" value="${ barCodeIssue.id }"/>
        <p>
          <label>公司名称：</label>      
          <input name="barCodeIssue.companyId" type="hidden" value="${ barCodeIssue.companyId }"/>  
          <input type="text" name="barCodeIssue.companyName" class="required" value="${ barCodeIssue.companyName }"  />
        </p>
         <p>
          <label>基因包数量：</label>        
          <input type="text" name="barCodeIssue.barcodeCount" class="required digits" maxlength="6" min="1" value="${ barCodeIssue.barcodeCount }"  />
        </p>
         <p>
          <label>联系人：</label>        
          <input type="text" name="barCodeIssue.contacts" class="required" value="${ barCodeIssue.contacts }"  />
        </p>
         <p>
          <label>联系人电话：</label>        
          <input type="text" name="barCodeIssue.contactsTel" class="required" value="${ barCodeIssue.contactsTel }"  />
        </p>
         <p>
          <label>邮编：</label>        
          <input type="text" name="barCodeIssue.postcode" value="${ barCodeIssue.postcode }" />
        </p>
         <p>
          <label>详细地址：</label>        
          <input type="text" name="barCodeIssue.address" class="required" value="${ barCodeIssue.address }"  />
        </p>
         <p>
          <label>快递单号：</label>        
          <input type="text" name="barCodeIssue.expressNo" class="required" value="${ barCodeIssue.expressNo }"  />
        </p>  
        <p></p>
        <p></p>
         <p>
            <label>发送状态：</label>
               <select id="status"  name="barCodeIssue.status" rel="iselect" >
              	 <option value=1 >派发</option>					   
							</select>
        </p>       
        <p>
        	<label>发出日期：</label>         
           <input type="text" name="barCodeIssue.sendDate" class="date required" size="20" datefmt="yyyy-MM-dd" value="${fn:substring(barCodeIssue.sendDate,0,10) }"/>
          <a class="inputDateButton" href="javascript:;">选择</a>       
        </p>    
         
         <p class="nowrap"><label style="height: 60px;">备注说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" name="barCodeIssue.remark" >${barCodeIssue.remark}</textarea>
        </p>             
          
      </div>
    </div>
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </form>
 </div>