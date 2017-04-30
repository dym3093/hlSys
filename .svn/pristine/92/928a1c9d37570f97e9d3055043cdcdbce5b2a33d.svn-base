<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
function syncPdfContent2Cus(id,tomodifyid,code){
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/unMatch!syncPdfContent2Cus.action",
			data: {"id":id,"tomodifyid":tomodifyid,"code":code},
			success: function(data){
				if(data.result=='success'){
					alert("同步成功！");
					return navTabSearch(this);
				}else{
					alert("同步失败！");
				}
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	
function syncCus2PdfContent(id,tomodifyid,code){
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/unMatch!syncCus2PdfContent.action",
			data: {"id":id,"tomodifyid":tomodifyid,"code":code},
			success: function(data){
				if(data.result=='success'){
					alert("同步成功！");
					return navTabSearch(this);
				}else{
					alert("同步失败！");
				} 	
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
function pdfError(id){
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/unMatch!dealPdfError.action",
			data: {"id":id},
			success: function(data){
				if(data.result=='success'){
					alert("同步成功！");
					return navTabSearch(this);
				}else{
					alert("同步失败！");
				}
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}		
</script>

<div class="pageFormContent" layoutH="56">
  <div class="pageContent">
    <form onsubmit="if(this.action!='${path}/report/unMatch!queryPdfError.action'){this.action ='${path}/report/unMatch!queryPdfError.action';};return navTabSearch(this);" action="${path}/report/unMatch!queryPdfError.action">
      <div class="searchBar">
        <div class="tip"><span>未匹配查询</span></div>
        <table class="pageFormContent">
          <tr>
            <!--<td><span>匹配状态：</span>
              <select name="filter_and_modifystate_EQ_S" rel="iselect">
                <option value="">请选择</option>
                <option value="1">匹配数据失败</option>
                <option value="0">会员信息缺失</option>
              </select></td>-->
              <td>
            	<label>姓名：</label> 
				<input type="text" name="filter_and_pdfusername_LIKE_S" value=""/>
            </td>
            <td>
            	<label>条码：</label> 
				<input type="text" name="filter_and_pdfcode_EQ_S" value=""/>
            </td>
              
            <td><div class="buttonActive">
                <div class="buttonContent">
                  <button type="submit">查询</button>
                </div>
              </div></td>
          </tr>
        </table>
      </div>
    </form>
  </div>
  <div class="tip"><span>PDF错误查询</span></div>
  <div class="panelBar"> 
    <!-- <ul class="toolBar">
			<a href="javascript:void(0)" onclick="downloadAllExcel('')">下载</a>
		</ul> --> 
    <!--<ul class="toolBar">
			<web:exportExcelTag
					pagerFormId="pdfContentForm"
					pagerMethodName="contentsfindByPage"
					actionAllUrl="org.hpin.reportdetail.web.ErpReportFileTaskAction"
					informationTableId="exportExcelTable"
					fileName="detailContent"></web:exportExcelTag>
		</ul>-->
    <jsp:include page="/common/pagination.jsp" />
  </div>
  <table class="table" width="100%" id="exportExcelTable">
    <thead>
      <tr>
        <th width="40">序号</th>
        <th>报告姓名</th>
        <th>报告性别</th>
        <th>报告年龄</th>
        <th>报告条码</th>
        <th>操作</th>
        <th>系统姓名</th>
        <th>系统性别</th>
        <th>系统年龄</th>
        <th>系统条码</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${page.results}" var="bean" varStatus="status">
        <tr beanid="${bean.id}">
          <td align="center">${status.count}</td>
          <td align="center">${bean.pdfusername}</td>
          <td align="center">${bean.pdfusersex}</td>
          <td align="center">${bean.pdfuserage}</td>
          <td align="center">${bean.pdfcode}</td>
          <td align="center"><a target="_blank" href="${fn:replace(bean.pdffilepath,'/home/ftp/transact','http://img.healthlink.cn:8099/ymReport')}">查看</a></td>
          <td style="color:#FF0000" align="center">${bean.cususername}</td>
          <td style="color:#FF0000" align="center">${bean.cususersex}</td>
          <td style="color:#FF0000" align="center">${bean.cususerage}</td>
          <td style="color:#FF0000" align="center">${bean.cuscode}</td>
          <td style="color:#FF0000" align="center">
          	<a href="${path}/report/unMatch!queryModifyInfo.action?id=${bean.id}" target="dialog" width="800" height="600">更改</a>
            
            <!--<a href="javascript:void(0)" onclick="syncPdfContent2Cus('${bean.id}','${bean.cusid}','${bean.pdfcode}')"><span style="color:#000">同步会员信息</span></a>
            <a href="javascript:void(0)" onclick="pdfError('${bean.id}')"><span style="color:#F00">PDF报告错误</span></a>
            <a href="javascript:void(0)" onclick="syncCus2PdfContent('${bean.id}','${bean.pdfid}','${bean.pdfcode}')"><span style="color:#00F">同步PDF信息</span></a>-->
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
