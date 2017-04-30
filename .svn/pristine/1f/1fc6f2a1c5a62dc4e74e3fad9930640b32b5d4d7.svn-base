<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  function toSave(){
	   $("form.IRForm",navTab.getCurrentPanel()).attr("action","../healthMsg/healthInfoAction!saveHealthDetail.action");
  }
  
   function toExcel(){
	   $("form.IRForm",navTab.getCurrentPanel()).attr("action","../healthMsg/healthInfoAction!toExcel.action");
  }
  
  
      function showFiled1(name) {
        switch (name) {
            case 'first1':
                  $("#first1",navTab.getCurrentPanel()).show();
                  $("#second1",navTab.getCurrentPanel()).hide();
                  $("#third1",navTab.getCurrentPanel()).hide();
                  $("#four1",navTab.getCurrentPanel()).hide();
                break;
            case 'second1':
                 $("#first1",navTab.getCurrentPanel()).hide();
                  $("#second1",navTab.getCurrentPanel()).show();
                  $("#third1",navTab.getCurrentPanel()).hide();
                  $("#four1",navTab.getCurrentPanel()).hide();
             
                break;

            case 'third1':
                 $("#first1",navTab.getCurrentPanel()).hide();
                  $("#second1",navTab.getCurrentPanel()).hide();
                  $("#third1",navTab.getCurrentPanel()).show();
                  $("#four1",navTab.getCurrentPanel()).hide();
            
                break;
            case 'four1':
                  $("#first1",navTab.getCurrentPanel()).hide();
                  $("#second1",navTab.getCurrentPanel()).hide();
                  $("#third1",navTab.getCurrentPanel()).hide();
                  $("#four1",navTab.getCurrentPanel()).show();
         
                break;
            case 'allyes1':
                 $("#first1",navTab.getCurrentPanel()).show();
                  $("#second1",navTab.getCurrentPanel()).show();
                  $("#third1",navTab.getCurrentPanel()).show();
                  $("#four1",navTab.getCurrentPanel()).show();
                break;

            case 'allno1':
                 $("#first1",navTab.getCurrentPanel()).hide();
                  $("#second1",navTab.getCurrentPanel()).hide();
                  $("#third1",navTab.getCurrentPanel()).hide();
                  $("#four1",navTab.getCurrentPanel()).hide();

                break;

        }
        
    }
</script>
<div class="pageContent">
	 <div class="pageFormContent" layoutH="56">
	
	
	                                  <div>
                                        <input type="button" value="全部展开"  onclick="showFiled1('allyes1')" />
                                        <input type="button" value="全部闭合"  onclick="showFiled1('allno1')" />
                                    </div>
   <form method="post" id="healthDetail" class="pageForm required-validate IRForm"  onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/healthMsg/healthInfoAction!saveHealthDetail.action">
   <input type="hidden" name="infoid" value="${ infoid }"/>
   <input type="hidden" name="navTabId" value="${ navTabId }"/>
   <input type="hidden" name="flag" value="${ flag }"/>
   <input type="hidden" name="currentTabId" value = "2" />
    

	 <div class="tip" onclick="showFiled1('first1')"><span>第一级信息</span></div>
	     <div id="first1">
				       <table  class="list nowrap  itemDetail" addButton="添加项"   width="90%">
                  <thead>
         					 <tr>
                                  <th type="hidden" name="items1[#index#].sortId" defaultVal="#index#"  size="5" width="50" fieldClass="required digits">序号</th>
                                  
                                  <th type="text" name="items1[#index#].symptom" size="200" width="1000" fieldClass="required">症状描述</th>
                                  <th type="del"  width="60">删除</th>
         			      </tr>
    						  </thead>
    						  <tbody>
    						        <c:forEach items="${ items1 }" var="items1" varStatus="status">
														<tr class="unitBox">
																										
															
																<td><input type="text"  value="${items1.sortId }"
																	name="items1[${status.count-1}].sortId" 
																	readonly="readonly" />
																</td>
															
															
																<td><input class="required textInput" type="text" 
																			value="${items1.symptom}" name="items1[${status.count-1}].symptom"/>
																</td>
																<td><a class="btnDel" href="javascript:void(0)">删除</a>
																</td>
																</tr>
												</c:forEach>      
    						  </tbody>
   							</table>
			</div>
	
	 <div class="tip" onclick="showFiled1('second1')" ><span>第二级信息</span></div>
	     <div id="second1">
				        <table  class="list nowrap  itemDetail" addButton="添加项"   width="90%">
                  <thead>
         					 <tr>
                                  <th type="hidden" name="items2[#index#].sortId" defaultVal="#index#" size="5" width="50" fieldClass="required digits">序号</th>
                                  
                                  <th type="text" name="items2[#index#].symptom" size="200" width="1000" fieldClass="required">症状描述</th>
                                  <th type="del" width="60">删除</th>
         			      </tr>
    						  </thead>
    						  <tbody>
    						        <c:forEach items="${ items2 }" var="items2" varStatus="status">
														<tr >
																<td><input type="text" size="5" value="${items2.sortId }"
																	name="items2[${status.count-1}].sortId"
																	readonly="readonly" />
																</td>
															
															
																<td><input class="required textInput" type="text" size="200"
																			value="${items2.symptom}" name="items2[${status.count-1}].symptom" />
																</td>
																<td><a class="btnDel " href="javascript:void(0)">删除</a>
																</td>
																</tr>
												</c:forEach>                               
    						  </tbody>
   							</table>				
				
		  	</div>
	
	 <div class="tip" onclick="showFiled1('third1')" ><span>第三级信息</span></div> 
	      	<div id="third1" >
				        <table  class="list nowrap  itemDetail" addButton="添加项"   width="90%">
                  <thead>
         					 <tr>
                                  <th type="hidden" name="items3[#index#].sortId" defaultVal="#index#" size="5" width="50" fieldClass="required digits">序号</th>
                                
                                  <th type="text" name="items3[#index#].symptom" size="200" width="1000" fieldClass="required">症状描述</th>
                                  <th type="del" width="60">删除</th>
         			      </tr>
    						  </thead>
    						  <tbody>
    						        <c:forEach items="${ items3 }" var="items3" varStatus="status">
														<tr >
																<td><input type="text" size="5" value="${items3.sortId }"
																	name="items3[${status.count-1}].sortId"
																	readonly="readonly" />
																</td>
															
															
																<td><input class="required textInput" type="text" size="200"
																			value="${items3.symptom}" name="items3[${status.count-1}].symptom" />
																</td>
																<td><a class="btnDel " href="javascript:void(0)">删除</a>
																</td>
																</tr>
												</c:forEach>                               
    						  </tbody>
   							</table>				
				
			</div>
	 
	 <div class="tip" onclick="showFiled1('four1')" ><span>第四级信息</span></div> 
	      <div id="four1" >
				        <table  class="list nowrap  itemDetail" addButton="添加项"   width="90%">
                  <thead>
         					 <tr>
                                  <th type="hidden" name="items4[#index#].sortId" defaultVal="#index#" size="5" width="50" fieldClass="required digits">序号</th>
                               
                                  <th type="text" name="items4[#index#].symptom" size="200" width="1000" fieldClass="required">症状描述</th>
                                  <th type="del" width="60">删除</th>
         			      </tr>
    						  </thead>
    						  <tbody>
    						        <c:forEach items="${ items4 }" var="items4" varStatus="status">
														<tr >
																<td><input type="text" size="5" value="${items4.sortId }"
																	name="items4[${status.count-1}].sortId"
																	readonly="readonly" />
																</td>
															
															
																<td><input class="required textInput" type="text" size="200"
																			value="${items4.symptom}" name="items4[${status.count-1}].symptom" />
																</td>
																<td><a class="btnDel " href="javascript:void(0)">删除</a>
																</td>
																</tr>
												</c:forEach>                               
    						  </tbody>
   							</table>				
				
			</div>
	 
		
	    <div class="formBar">
			   <ul>
				   <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="toSave()">保存</button></div></div></li>
				   <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="toExcel()">EXCEL导入</button></div></div></li>
			   </ul>
		   </div>	
	 </form> 
	 </div>
</div>
