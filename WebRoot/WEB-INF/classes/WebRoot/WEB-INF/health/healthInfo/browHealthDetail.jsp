<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
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
<div class="pageContent" >
	
	<div class="pageFormContent" layoutH="56">
		                                <div>
                                        <input type="button" value="全部展开"  onclick="showFiled1('allyes1')" />
                                        <input type="button" value="全部闭合"  onclick="showFiled1('allno1')" />
                                    </div>
    <div></div>
	  <div class="tip" onclick="showFiled1('first1')"><span>第一级信息</span></div>
	  <div id="first1" style="display:block">
				    <table class="list"  width="100%">
						<thead>
							<tr>
								<th size="5" width="50">序号</th>
							
								<th size="200" width="1000">症状描述</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ items1 }" var="items1" varStatus="status">
								<tr>
									<td>${items1.sortId }</td>
								
									<td>${items1.symptom}</td>
								</tr>
							</c:forEach>
						 </tbody>
					</table>
		 </div>
		<div class="tip" onclick="showFiled1('second1')" ><span>第二级信息</span></div>
			<div id="second1" style="display:none">
        <table class="list"  width="100%">
						<thead>
							<tr>
								<th size="5" width="50">序号</th>
							
								<th size="200" width="1000">症状描述</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ items2 }" var="items2" varStatus="status">
								<tr class="unitBox">
									<td>${items2.sortId }</td>
								
									<td>${items2.symptom}</td>
								</tr>
							</c:forEach>
						 </tbody>
					</table>				       
				
			</div>
		<div class="tip" onclick="showFiled1('third1')" ><span>第三级信息</span></div>
			 	<div id="third1" style="display:none">
        <table class="list"  width="100%">
						<thead>
							<tr>
								<th size="5" width="50">序号</th>
								
								<th size="200" width="1000">症状描述</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ items3 }" var="items3" varStatus="status">
								<tr class="unitBox">
									<td>${items3.sortId }</td>
								
									<td>${items3.symptom}</td>
								</tr>
							</c:forEach>
						 </tbody>
					</table>				        
				
			</div>
		<div class="tip" onclick="showFiled1('four1')" ><span>第四级信息</span></div>
     <div id="four1" style="display:none">
        <table class="list"  width="100%">
						<thead>
							<tr>
								<th size="5" width="50">序号</th>
							
								<th size="200" width="1000">症状描述</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ items4 }" var="items4" varStatus="status">
								<tr class="unitBox">
									<td>${items4.sortId }</td>
								
									<td>${items4.symptom}</td>
								</tr>
							</c:forEach>
						 </tbody>
					</table>				       
				
			</div>
 
 </div>
</div>
