<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/warehouse/storeApplication!saveStoreApplication.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <input name="storeApplication.id" type="hidden" value="${ storeApplication.id }"/>

   <div class="pageFormContent" layoutH="56">
      <div>
        <div class="tip"><span>申请信息</span></div>
         <p>
          <label>总公司名称：</label>
          <input name="storeApplication.ownedCompany" type="text" readonly="readonly"  value="${ storeApplication.ownedCompany }" />
        </p>
        <p>
          <label>支公司名称：</label>
          <input name="storeApplication.bannyCompanyName" type="text" readonly="readonly"  value="${ storeApplication.bannyCompanyName }" />
        </p>
        <p>
          <label>收件人：</label>
          <input name="storeApplication.receiveName" type="text" readonly="readonly"  value="${ storeApplication.receiveName }" />
        </p>
          <p>
              <label>项目负责人：</label>
              <input name="storeApplication.remark1" type="text"    readonly="readonly" value="${ storeApplication.remark1 }" />
              <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
          </p>
          <p>
              <label>项目归属：</label>
              <input name="storeApplication.remark2" type="text"   readonly="readonly"  value="${ storeApplication.remark2 }" />
              <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
          </p>
          <p>
              <label>项目编码：</label>
              <input name="storeApplication.remark3" type="text"    readonly="readonly" value="${ storeApplication.remark3 }" />
              <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
          </p>
        <p>
          <label>联系电话：</label>
          <input name="storeApplication.receiveTel" type="text" readonly="readonly"  value="${ storeApplication.receiveTel }" />
        </p>
        <p>
          <label>详细地址:</label>
          <input name="storeApplication.address" type="text" readonly="readonly"  value="${ storeApplication.address }" />
        </p>
       
        <p>
        	 <label>期望日期：</label>
          <input type="text" name="storeApplication.useDate" readonly="readonly"  value="${fn:substring(storeApplication.useDate,0,10) }"/>
          
        </p>        
      <p>
        	 <label>申请人：</label>
          <input name="storeApplication.createUserName" type="text" readonly="readonly"  value="${ storeApplication.createUserName }" />
          
        </p> 
        <p>
              <label>申请编码：</label>
              <input name="storeApplication.batNo" type="text"    readonly="readonly" value="${ storeApplication.batNo }" />
              <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
          </p>
        <p class="nowrap"><label style="height: 60px;">需求说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" readonly="readonly" name="storeApplication.requires">${storeApplication.requires}</textarea>
        </p>  
        
        <div class="tip"><span>处理信息</span></div>
        
       <%--   <p>
        	 <label>处理状态：</label>
             <select  name="storeApplication.status" rel="iselect" >
								<option value="0" <c:if test="${storeApplication.status == '0'}">selected=selected</c:if>>处理中</option>
				        <option value="1" <c:if test="${storeApplication.status == '1'}">selected=selected</c:if>>处理完成</option>
						</select>
         </p> --%>  
        
         <p class="nowrap"><label style="height: 60px;">处理意见：</label>
           <textarea cols="35" ows="2" style="width:570px;" class="required" name="storeApplication.remark">${storeApplication.remark}</textarea>
        </p>  
            
      </div>
    </div>
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">提交</button>
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