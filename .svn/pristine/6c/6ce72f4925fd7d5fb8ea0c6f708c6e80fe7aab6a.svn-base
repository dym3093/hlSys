<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_hpin_ext_debug.jsp"%>

<form id="frm">

<%--

<!-- ${hpin:a2u('最简单的派发树，默认含有部门/人员树,可选择任意类型')} -->
<web:chooser id="test0" category="[{id:'send',text:'${hpin:a2u('派发')}'}]"/>

--%>


<%-- 
<!-- ${hpin:a2u('自定义面板的派发树')} -->
<web:chooser id="test1"
	category="[{id:'send1',text:'${hpin:a2u('派发')}',childType:'user,subrole',allowBlank:false,limit:-1,vtext:'${hpin:a2u('请选择派发人')}'}]"
	panels="[
		{text:'${hpin:a2u('当前角色')}',dataUrl:'/role/tawSystemSubRoles.do?method=xgetUsersBySubRole&subRoleId=8a1595a71a05c16b011a06ad78ed0025'},
		{text:'${hpin:a2u('当前大角色')}',dataUrl:'/role/tawSystemSubRoles.do?method=xgetUsersByRole&roleId=168'}
	]"
/>
--%>


<%-- 

<!-- ${hpin:a2u('包含已选择数据的派发树')} -->
<web:chooser id="test2"  
	category="[{id:'send2',text:'${hpin:a2u('派发')}',allowBlank:false,vtext:'${hpin:a2u('请选择派发人2')}'}]" 
	data="[
		{id:'8a1595a71a05c16b011a06a4633e0014',nodeType:'subrole',categoryId:'send2'},
		{id:'8a1595a71a05c16b011a06a6a6380017',nodeType:'subrole',categoryId:'send2'}
	]"
/>

--%>



<!-- ${hpin:a2u('工单选择子角色专用派发树，含过滤窗口，可设置组长')} -->
<web:chooser id="test" type="role" roleId="189" flowId="4" config="{showLeader:true,mainPanelTitle:'${hpin:a2u('可选择的子角色')}'}"
	category="[
	 {id:'send',text:'${hpin:a2u('派发')}',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'${hpin:a2u('抄送')}',childType:'user,subrole,dept',limit:2}
	,{id:'audit',text:'${hpin:a2u('审核')}',childType:'user',limit:-1}
	]"
	data="[
		{id:'8a1595a71a05c16b011a06a4633e0014',nodeType:'subrole',categoryId:'send'},
		{id:'8a1595a71a05c16b011a06a6a6380017',nodeType:'subrole',categoryId:'send'}
	]"
/>


<br/>

<script type="text/javascript">
	Ext.onReady(function(){
		//v = new hpin.form.Validation({form:'frm'});
	});
</script>

<br/><br/>
<input type="button" value="role 186" onclick="javascript:chooser_test.setRoleId(186)">
<input type="button" value="role 189" onclick="javascript:chooser_test.setRoleId(189)">
<input type="button" value="setDataUrl" onclick="javascript:chooser_test.setDataUrl('/xtree.do?method=userFromDept',1,1)">
<br/><br/>
<input type="submit" value="submit" id="btn2" class="submit">
</form>
<%@ include file="/common/footer_hpin.jsp"%>
