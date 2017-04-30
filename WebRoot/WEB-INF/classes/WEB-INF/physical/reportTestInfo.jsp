<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}

table .DIYTable{
	
}

table{
	margin:5px;  /* 外边距50px */
	border-collapse:collapse; /* 合并为单一的边框线 */
	width:100%;
}

table td.tableTh{
	 font-weight:900;
}

table.DIYTable td{
	padding:10px;border:1px solid #C0C0C0;
}

table.DIYTable02 td{
	padding:5px;border:1px solid #C0C0C0;
}

/*个性化体检项目总结td格式*/
table td .tdTableSun01{
	border:1px solid #C0C0C0;
	width: 30%;
}

table td .tdTableSun02{
	border:1px solid #C0C0C0;
	width: 70%;
}

</style>

<script type="text/javascript" language="javascript">
	
$(document).ready(function(){
});
	

/* function reportInfo(geneCode){
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		data:{"geneCode":geneCode},
		url: "${path}/physical/phyReport!getReportInfo.action",
		success: function(data){
			$("#diseaseTable").empty();
			$("#projectTable").empty();
			var s= eval("("+data+")");
			var diseaseMap = s.diseaseMap;
			var projectMap = s.projectMap;
			var table1 = "";
			var table2 = "";
			for(var key1 in diseaseMap){
				//alert("key--"+key1+",value--"+diseaseMap[key1]);
				table1=table1+"<tr><td align='center'>"+key1+"</td>"+"<td align='center'>"+diseaseMap[key1]+"</td></tr>";
			}
			for(var key2 in projectMap){
				table2=table2+"<tr><td align='center'width='200'>"+key2+"</td>"+"<td align='center' width='800' style='word-break:break-all'>"+projectMap[key2]+"</td></tr>";
			}
			//将节点插入到下拉列表中
			$("#diseaseTable").append(table1);
			$("#projectTable").append(table2);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
} */

</script>
<div class="pageContent" style="height:575px;overflow-x:hidden;">
	<div>
		<div class="tip"><span><b>基因检测评估风险较高系统项目及个性化建议检查分析：</b></span></div>
		<div>
		<table class="DIYTable">
			<thead>
				<tr >
	                <td align="center" class="tableTh">项目分类</td>
					<td align="center" class="tableTh">较高风险项目及评级</td>
					<td align="center" class="tableTh">目前体检建议</td>
				</tr>
			</thead>
			<tbody id="diseaseTable">
				<c:forEach items="${diseaseMap}" var="diseaseKey">
					<tr><td align="center">${diseaseKey.key}</td>
							<c:forEach items="${diseaseKey.value}" var="diseaseValue">
							<td align="center">
								<c:forEach items="${diseaseValue}" var="diseaseSun">
									
										${diseaseSun}<br/>
									
								</c:forEach>
							</td>
							</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<br/><br/>
		<div class="tip"><span><b>您的常规体检项目：</b></span></div>
		<table class="DIYTable">
			<thead>
				<tr class="tableTh">
	                <td align="center" class="tableTh">项目分类</td>
					<td align="center" class="tableTh">检查内容及意义</td>
				</tr>
			</thead>
			<c:if test="${sex=='男'}">
				<tbody>
					<tr>
						<td align="center">一般</td>
						<td align="center">身高、体重、血压、脉搏、体重指数</td>
					</tr>
					<tr>
						<td align="center">内科</td>
						<td align="center">心血管系统、呼吸系统、消化系统</td>
					</tr>
					<tr>
						<td align="center">外科</td>
						<td align="center">皮肤、浅表淋巴结、甲状腺、脊柱及四肢关节、肛门指诊、前列腺</td>
					</tr>
					<tr>
						<td align="center">眼科</td>
						<td align="center">色觉、眼睑、眼球、角膜、前房、瞳孔、眼底、彩色照相、裂隙灯检查、视力、眼压测定等</td>
					</tr>
					<tr>
						<td align="center">耳鼻喉科</td>
						<td align="center">外耳道、鼓膜、听力、鼻腔、鼻窦、扁桃体、间接喉镜检查</td>
					</tr>
					<tr>
						<td align="center">血常规</td>
						<td align="center">红细胞、白细胞、血红蛋白、血小板等</td>
					</tr>
					<tr>
						<td align="center">肝功三项</td>
						<td align="center">血清谷丙转氨酶测定（ALT）、血清谷草转氨酶测定（GOT）、血清γ-谷氨酰基转移酶测定（GGT）</td>
					</tr>
					<tr>
						<td align="center">血糖</td>
						<td align="center">空腹葡萄糖测定</td>
					</tr>
					<tr>
						<td align="center">血脂</td>
						<td align="center">甘油三酯</td>
					</tr>
					<tr>
						<td align="center">肾功三项</td>
						<td align="center">血清尿素氮、血清肌酐、血清尿酸</td>
					</tr>
					<tr>
						<td align="center">尿常规</td>
						<td align="center">尿糖、尿白细胞、尿红细胞、尿蛋白等</td>
					</tr>
					<tr>
						<td align="center">便常规</td>
						<td align="center">性状、大便颜色、大便红细胞计数、血、大便白细胞计数、大便虫卵、黏液、大便脓细胞、其他</td>
					</tr>
					<tr>
						<td align="center">消化系超声</td>
						<td align="center">肝脏、胆囊、胰腺、脾脏、肾脏</td>
					</tr>
					<tr>
						<td align="center">胸透</td>
						<td align="center">胸部透视</td>
					</tr>
					<tr>
						<td align="center">心电图</td>
						<td align="center">电脑12导联心电图</td>
					</tr>
				</tbody>
			</c:if>
			<c:if test="${sex=='女'}">
				<tbody>
					<tr>
						<td align="center">一般</td>
						<td align="center">身高、体重、血压、脉搏、体重指数</td>
					</tr>
					<tr>
						<td align="center">内科</td>
						<td align="center">心血管系统、呼吸系统、消化系统</td>
					</tr>
					<tr>
						<td align="center">外科</td>
						<td align="center">皮肤、浅表淋巴结、甲状腺、脊柱及四肢关节、肛门指诊</td>
					</tr>
					<tr>
						<td align="center">眼科</td>
						<td align="center">色觉、眼睑、眼球、角膜、前房、瞳孔、眼底、彩色照相、裂隙灯检查、视力、眼压测定等</td>
					</tr>
					<tr>
						<td align="center">耳鼻喉科</td>
						<td align="center">外耳道、鼓膜、听力、鼻腔、鼻窦、扁桃体、间接喉镜检查</td>
					</tr>
					<tr>
						<td align="center">妇科</td>
						<td align="center">月经史、孕、产、手术史、外阴、阴道、分泌物、宫颈、宫体、附件</td>
					</tr>
					<tr>
						<td align="center">血常规</td>
						<td align="center">红细胞、白细胞、血红蛋白、血小板等</td>
					</tr>
					<tr>
						<td align="center">肝功三项</td>
						<td align="center">血清谷丙转氨酶测定（ALT）、血清谷草转氨酶测定（GOT）、血清γ-谷氨酰基转移酶测定（GGT）</td>
					</tr>
					<tr>
						<td align="center">血糖</td>
						<td align="center">空腹葡萄糖测定</td>
					</tr>
					<tr>
						<td align="center">血脂</td>
						<td align="center">甘油三酯</td>
					</tr>
					<tr>
						<td align="center">肾功三项</td>
						<td align="center">血清尿素氮、血清肌酐、血清尿酸</td>
					</tr>
					<tr>
						<td align="center">尿常规</td>
						<td align="center">尿糖、尿白细胞、尿红细胞、尿蛋白等</td>
					</tr>
					<tr>
						<td align="center">便常规</td>
						<td align="center">性状、大便颜色、大便红细胞计数、血、大便白细胞计数、大便虫卵、黏液、大便脓细胞、其他</td>
					</tr>
					<tr>
						<td align="center">消化系超声</td>
						<td align="center">肝脏、胆囊、胰腺、脾脏、肾脏</td>
					</tr>
					<tr>
						<td align="center">胸透</td>
						<td align="center">胸部透视</td>
					</tr>
					<tr>
						<td align="center">心电图</td>
						<td align="center">电脑12导联心电图</td>
					</tr>
				</tbody>
			</c:if>
		</table>
		<br/><br/>
		<div class="tip"><span><b>您的个性化体检项目总结：</b></span></div>
		<table class="DIYTable">
			<thead>
				<tr>
	                <td align="center" class="tableTh">项目分类</td>
					<td align="center" class="tableTh">检查内容及意义</td>
				</tr>
			</thead>
			<tbody id="projectTable">
				<c:forEach items="${projectMap}" var="projectKey">
					
					<c:forEach items="${projectKey}" var="projectKeySun">
					<tr>
						<td align="center">${projectKeySun.key}</td>
						<c:if test="${projectKeySun.key=='血液检验'}">
							<td align="center">
							<c:forEach items="${projectKeySun.value}" var="projectValueList">
								<c:forEach items="${projectValueList}" var="projectValueListMap">
									<c:if test="${projectValueListMap.key!='other'&&projectValueListMap.key!='six'}">
									<table class="DIYTable02">
										<tr>
											<td align="center" class='tdTableSun01'>${projectValueListMap.key}</td>
											<td align="center" class='tdTableSun02'>${projectValueListMap.value}</td>
										</tr>
									</table>	
									</c:if>
								</c:forEach>
							</c:forEach>
							</td>
						</c:if>
						<c:if test="${projectKeySun.key!='血液检验'}">
							<td align="center">
								<c:forEach items="${projectKeySun.value}" var="projectValueList">
									<c:forEach items="${projectValueList}" var="projectValueListMap">
										<c:if test="${projectValueListMap.key=='other'||projectValueListMap.key=='six'}">
											${projectValueListMap.value}
										</c:if>
									</c:forEach>
								</c:forEach>
							</td>
						</c:if>
					</tr>
					</c:forEach>
					
						<%-- 
						<c:if test="${projectKey.key!='血液检验'}">
							<c:forEach items="${projectKey.value}" var="projectValueList">
								<c:forEach items="${projectValueList.value}" var="projectValueMap">
									<c:if test="${projectValueMap.key=='other'||projectValueMap.key=='sex'}">
										<td align="center">${projectValueMap.value}</td>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:if> --%>
						
						<%-- <c:forEach items="${projectKey.value}" var="projectValue">
							<td>${projectValue}</td>
						</c:forEach> --%>
					
				</c:forEach>
			</tbody >
		</table>
	</div>
</div>
