<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/business/barCodeBat!saveBarCodeBat.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <div class="pageFormContent" layoutH="56">
      <div>
        <div class="tip"><span>添加制码信息</span></div>
        <p>
          <label>制码数量：</label>
          <input name="barCodeBat.batCount" type="text" class="required digits" maxlength="6" min="1"  value="${ barCodeBat.batCount }" />
        </p>
        <p>
        	 <label>发出日期：</label>
          <input type="text" name="barCodeBat.sendDate" class="date required" size="20" datefmt="yyyy-MM-dd" value="${fn:substring(barCodeBat.sendDate,0,10) }"/>
          <a class="inputDateButton" href="javascript:;">选择</a>
        </p>
        <p></p>
        <p class="nowrap"><label style="height: 60px;">制码说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" name="barCodeBat.remark">${barCodeBat.remark}</textarea>
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
