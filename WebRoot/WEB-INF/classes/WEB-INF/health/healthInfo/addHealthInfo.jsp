<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
 function showFiled(name) {
        switch (name) {
            case 'first':
                  $("#first",navTab.getCurrentPanel()).show();
                  $("#second",navTab.getCurrentPanel()).hide();
                  $("#third",navTab.getCurrentPanel()).hide();
                 
                break;
            case 'second':
                 $("#first",navTab.getCurrentPanel()).hide();
                  $("#second",navTab.getCurrentPanel()).show();
                  $("#third",navTab.getCurrentPanel()).hide();
                
             
                break;

            case 'third':
                 $("#first",navTab.getCurrentPanel()).hide();
                  $("#second",navTab.getCurrentPanel()).hide();
                  $("#third",navTab.getCurrentPanel()).show();
                
            
                break;
            case 'allyes':
                 $("#first",navTab.getCurrentPanel()).show();
                  $("#second",navTab.getCurrentPanel()).show();
                  $("#third",navTab.getCurrentPanel()).show();
                 
                break;

            case 'allno':
                 $("#first",navTab.getCurrentPanel()).hide();
                  $("#second",navTab.getCurrentPanel()).hide();
                  $("#third",navTab.getCurrentPanel()).hide();
                 

                break;

        }
        
    }
</script>
<div class="pageContent">
	                                  <div>
                                        <input type="button" value="全部展开"  onclick="showFiled('allyes')" />
                                        <input type="button" value="全部闭合"  onclick="showFiled('allno')" />
                                    </div>
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/healthMsg/healthInfoAction!saveHealthInfo.action" method="post" novalidate="novalidate">
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<input type="hidden" name="currentTabId" value = "1" />
		<input type="hidden" name="healthInfo.id" value = "${id}" />
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>症状信息：</span></div>
			<p>
				<label>病症：</label>
				<input type="text" name="healthInfo.disorder" class="required"  maxlength="50" size="30"  value="${ healthInfo.disorder }"/>
			</p>
		  <p>
				<label>首诊分科：</label>
				<input type="text" name="healthInfo.department"   maxlength="50" size="30"  value="${ healthInfo.department }"/>
			</p>
		  <p>
				<label>所属人群：</label>
				 <select id="crowd" name="healthInfo.crowd" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107">
				 <option value="${ healthInfo.crowd }"></option>
				 </select>
			</p>
			
			<%-- <p>
				<label>科室：</label>
				 <select id="depart" name="healthInfo.department" class="required" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10105" ref="dthin"  refUrl="${path}/hpin/sysDictType!treeRegionDispose.action?parentId=">
				 <option value="${ healthInfo.department }"></option>
				 </select>
			</p>
      <p>
				<label>科室明细：</label>
				 <select id="dthin"  name="healthInfo.departmentthin"  rel="iselect">
				 <option value="${ healthInfo.departmentthin }"></option>
				 </select>
			</p> --%>
      <p>
				<label>身体部位：</label>
				 <select id="body"  name="healthInfo.bodyParts" class="required" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10106"  ref="thin"  refUrl="${path}/hpin/sysDictType!treeRegionDispose.action?parentId=">
				 <option value="${ healthInfo.bodyParts }"></option>
				 </select>
			</p>
			<p>
				<label>部位明细：</label>
				 <select id="thin"   name="healthInfo.bodyPartsthin" class="required" rel="iselect">
				 <option value="${ healthInfo.bodyPartsthin }"></option>
				 </select>
			</p>
			
			
			
			<p class="nowrap"><label style="height: 60px;">关键问题：</label>
         <textarea cols="50" ows="5" style="width:570px;"  class="required" name="healthInfo.mainProblem">${healthInfo.mainProblem}</textarea>
          <span style="border: 0;vertical-align: bottom;float: left;padding-bottom: 10px;width:300px;">每一个问题以(;)分隔</span>
      </p>	
			<p class="nowrap"><label style="height: 60px;">其它可参考的相关症状护理指南：</label>
         <textarea cols="50" ows="5" style="width:570px;" class="required" name="healthInfo.treatParams">${healthInfo.treatParams}</textarea>
          <span style="border: 0;vertical-align: bottom;float: left;padding-bottom: 10px;width:300px;">每一个问题以(;)分隔</span>
      </p>	
      
      
     
      
	    <div class="tip" onclick="showFiled('first')"><span>家庭救护指南：</span></div>
	    <div id="first">
		    <textarea class="editor" name="healthInfo.guideNursing" rows="20" cols="110"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png" >${ healthInfo.guideNursing }</textarea>
	  <!--upLinkUrl="${path}/servlet/xheditorUpload?flag=zip" upLinkExt="zip,rar,txt,doc,docx,xls,xlsx,pdf" 
		  	upFlashUrl="${path}/servlet/xheditorUpload?flag=swf" upFlashExt="swf"
        upMediaUrl="${path}/servlet/xheditorUpload?flag=media" upMediaExt="wmv,avi,wma,mp3,mid" -->
        </div>
      	   <div class="tip" onclick="showFiled('second')"><span>额外指示：</span></div>
      	   <div id="second">
		    <textarea class="editor" name="healthInfo.instructions" rows="20" cols="110"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png" >${ healthInfo.instructions }</textarea>
		  	   	</div>
		  	
		  		   <div class="tip" onclick="showFiled('third')"><span>最后提醒：</span></div>
		  		   	<div id="third">
		    <textarea class="editor" name="healthInfo.reminder" rows="20" cols="110"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png" >${ healthInfo.reminder }</textarea>
              	</div>
    <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
				</li>
			</ul>
		</div>
			</div>
	</form>
</div>
