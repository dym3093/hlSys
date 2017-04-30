<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/business/barCodeBat!saveBarCodeBat.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <input name="barCodeBat.id" type="hidden" value="${ barCodeBat.id }"/>
   
    <div class="pageFormContent" layoutH="56">
      <div>
        <div class="tip"><span>制码信息</span></div>
        <p>
          <label>制码批次号：</label>
          <input type="text" name="barCodeBat.batNO" value="${ barCodeBat.batNO }"  readonly="readonly"/>
        </p>
         <p>
          <label>创建人：</label>
          <input type="text" name="barCodeBat.createUserId" value="${ barCodeBat.createUserId }"  readonly="readonly"/>
        </p>
         <p>
          <label>创建时间：</label>  
          <input type="text" name="barCodeBat.createTime" value="${fn:substring(barCodeBat.createTime,0,19) }"  readonly="readonly"/>
        </p>
        <p>
        	<label>发出日期：</label>
          <input type="text" name="barCodeBat.sendDate" class="date required" size="20" datefmt="yyyy-MM-dd" value="${fn:substring(barCodeBat.sendDate,0,10) }"/>
          <a class="inputDateButton" href="javascript:;">选择</a>
        </p> 
        <p>
          <label>制码数量：</label>        
          <input type="text" name="barCodeBat.batCount" value="${ barCodeBat.batCount }"  readonly="readonly"/>
        </p>
        <p>
          <label>实际生成数量:</label>
           <input type="text" name="barCodeBat.batActualCount" value="${ barCodeBat.batActualCount }"  readonly="readonly"/>
        </p>
     
        <p class="nowrap">
        	<label style="height: 60px;">制码说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" name="barCodeBat.remark">${barCodeBat.remark}</textarea>
        </p>       
       
        <div class="tip"><span>基因包跟踪信息</span></div>
         <p>
         	<label>是否完成封装：</label>
              <select id="status"  name="barCodeBat.status" rel="iselect" >
								<option value=3 <c:if test="${ barCodeBat.status=='3' }">selected="selected"</c:if>>是</option>							
							</select>
        </p> 
         <p>
         	<label>完成日期：</label>
          <input type="text" name="barCodeBat.completeDate" class="date required" size="20" datefmt="yyyy-MM-dd" value="${fn:substring(barCodeBat.completeDate,0,10) }"/>
          <a class="inputDateButton" href="javascript:;">选择</a>
        </p> 
        
        
         <p></p>
         <p class="nowrap"><label style="height: 60px;">跟踪说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" name="barCodeBat.remark1">${barCodeBat.remark1}</textarea>
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