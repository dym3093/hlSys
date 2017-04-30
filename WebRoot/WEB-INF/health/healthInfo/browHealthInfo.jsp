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
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>症状信息：</span></div>
		
			<p>
			
				<label>病症： </label>
				<input type="text" value="${healthInfo.disorder}" readonly="readonly"/>
			</p>
		 <p>
				<label>首诊分科：</label>
				<input type="text"  value="${ healthInfo.department }" readonly="readonly"/>
			</p>
			<p>
				<label>所属人群：</label>
				<input type="text" value="<hpin:id2nameDB id="${ healthInfo.crowd }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			
			<%-- <p>
				<label>科室：</label>
				<input type="text" value="<hpin:id2nameDB id="${ healthInfo.department }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>科室明细：</label>
				<input type="text" value="<hpin:id2nameDB id="${ healthInfo.departmentthin }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p> --%>
	    	<p>
				<label>身体部位：</label>
				<input type="text" value="<hpin:id2nameDB id="${ healthInfo.bodyParts }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
		    	<p>
				<label>部位明细：</label>
				<input type="text" value="<hpin:id2nameDB id="${ healthInfo.bodyPartsthin }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			
			
		  <p class="nowrap"><label style="height: 60px;">关键问题：</label>
         <textarea cols="35" ows="2" style="width:570px;" name="healthInfo.mainProblem" readonly="readonly">${healthInfo.mainProblem}</textarea>
     </p> 
		  <p class="nowrap"><label style="height: 60px;">其它可参考的相关症状护理指南：</label>
         <textarea cols="35" ows="2" style="width:570px;" name="healthInfo.treatParams" readonly="readonly">${healthInfo.treatParams}</textarea>
     </p> 		     
		
	
    
		
		   <div class="tip" onclick="showFiled('first')"><span>家庭救护指南：</span></div>
		    <div  id="first" >
			  <textarea class="editor" " rows="20" cols="110" readonly="readonly"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png">${ healthInfo.guideNursing }</textarea>
        </div>
    
         <div class="tip" onclick="showFiled('second')"><span>额外指示：</span></div>
          <div  id="second" >
			  <textarea class="editor" " rows="20" cols="110" readonly="readonly"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png">${ healthInfo.instructions }</textarea>
		  	</div>
		  
		  	 <div class="tip" onclick="showFiled('third')"><span>最后提醒：</span></div>
		  	  	<div  id="third" >
			  <textarea class="editor" " rows="20" cols="110" readonly="readonly"  tools="full" skin="vista" 
		  	upImgUrl="${path}/servlet/xheditorUpload?flag=img" upImgExt="jpg,jpeg,gif,png">${ healthInfo.reminder }</textarea>
        		</div>
	
	
	
		</div>
	
</div>
