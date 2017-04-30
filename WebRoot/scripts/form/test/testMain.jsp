<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="org.hpin.base.util.StaticMehtod" %>
<%@page import="org.hpin.commons.system.dict.model.TawSystemDictType" %>
<%@page import="org.hpin.base.util.ApplicationContextHolder" %>
<%@page import="org.hpin.commons.system.dict.service.ITawSystemDictTypeManager" %>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>

  <script type="text/javascript" src="testMain.js"></script>
<%@ include file="/common/body.jsp"%>
<style type="text/css">
.x-form-column{width:320px}
.chooser-panle{display:none;padding:10px}
.class-box{padding:10px;background:#eef9eb}
</style>
<script type="text/javascript">
	var roleTree,v;
	function setDept(){
		var depts = [{text:'dept1',value:'1'},{text:'dept2',value:'2'}];
		roleChooser_dealPorformer.setSortSelect(depts);
	}
	function initPage(){
		//init Tab 
		//var tabs = new Ext.TabPanel('main');
		//tabs.addTab('sheetform', "New Sheet");
		//tabs.activate('sheetform'); 		
		//init Form validation and styles
		v = new valider({form:'theform'});
    	c = new Chooser_sichuan();
		//reg btns
		Sheet.regBtns([
			{id:'method.save',onclick:function(){$('title').value = 'hh11'}}
		]);
	} 
	Ext.onReady(function(){
//	    var el = Ext.get("idSpecial");
//		var mgr = el.getUpdateManager();
//		mgr.loadScripts = true;
//		mgr.update("${path}/scripts/form/test/testInputmain.jsp");
//	    mgr.on("update", initPage);
		c = new Chooser_sichuan();
	});
</script>
<div id="sheetform">
  <form id="theform" method="post">
	<div ID="idSpecial"></div>
	<!-- buttons -->
	<!--
		  <div class="x-form-item">
	      <label>title</label>
		     <div class="x-form-element">
		      <input type="text" name="title" id="title" 
		         value="" alt="allowBlank:false"/>
			 </div>
		 </div>
	<div class="form-btns" id="form-btns">
		<input type="submit" id="method.save" value="test1"/>
		<input type="button" id="testall" onclick="Sheet.clickAllBtns();" value="testAll"/>
		<html:button styleClass="btn" property="method.save" styleId="method.save">
		  <fmt:message key="button.save" />
		</html:button>
	</div>
	<web:chooser type="subRoleChooser" name="dealPorformer" roleId="84" flowName="BusinessDesign"/>
	
	<input type="button" value="test" onclick="javascript:setDept();"/>
	-->
	<br/>
	<input type="button" id="newchooser" value="newchooser" onclick="javascript:c.show()"/>
	<div id='dept' class="chooser-panle">
		<input type="checkbox" name="deptList" value="{id:1,name:'dept1'}" onclick="javascript:getDeptEls(this,'single')"/> ${hpin:a2u("省公司")}
		<input type="checkbox" name="deptList" value="{id:2,name:'dept2'}" onclick="javascript:getDeptEls(this,'single')"/> ${hpin:a2u("成都分公司")}
	</div>

	<div id='class' class="chooser-panle">
<%
	ITawSystemDictTypeManager _objDictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
						.getInstance().getBean("ItawSystemDictTypeManager");
	List listClass = _objDictManager.getDictSonsByDictid("1010307");
	for (Iterator it = listClass.iterator(); it.hasNext();) {
		TawSystemDictType item = (TawSystemDictType) it.next();
		String itemName = StaticMehtod.null2String(item.getDictName());
		String itemCode = StaticMehtod.null2String(item.getDictCode());
		
		out.println("<input type=\"checkbox\" onclick=\"javascript:toggleCls(this)\" value=\""+itemCode+"\"/>"+itemName);
		
		out.println("<div id='div-"+itemCode+"' class='class-box' style=\"display:none\">");
		List listSubClass = _objDictManager.getDictSonsByDictid(itemCode);
		for (Iterator subIt = listSubClass.iterator(); subIt.hasNext();){
			TawSystemDictType subitem = (TawSystemDictType) subIt.next();
			String subitemName = StaticMehtod.null2String(subitem.getDictName());
			String subitemCode = StaticMehtod.null2String(subitem.getDictCode());
			String elemntValue = "{prename:'"+itemName+"',name:'"+subitemName+"',preid:"+itemCode+",id:"+subitemCode+"}";
			out.println("<input type=\"checkbox\" value=\""+elemntValue+"\" id=\"class-id-"+subitemCode+"\" name=\"class-"+itemCode+"\" onclick=\"javascript:getCls(this)\"/>"+subitemName);
		}
		out.println("</div><br/>");
	}
%>
	</div>
		
	<div id='business' class="chooser-panle">
<%
	String initDicId = "10114";

	List list = _objDictManager.getDictSonsByDictid(initDicId);
	for (Iterator it = list.iterator(); it.hasNext();) {
		TawSystemDictType item = (TawSystemDictType) it.next();
		String itemName = StaticMehtod.null2String(item.getDictName());
		String itemCode = StaticMehtod.null2String(item.getDictCode());
		String elemntValue = "{name:'"+itemName+"',id:"+itemCode+"}";
		out.println("<input type=\"checkbox\" value=\""+elemntValue+"\" name=\"businessList\" onclick=\"getBusinessList()\"/>"+itemName);
	}
%>		
	</div>
	<div id="preview"></div>
  </form>
</div>
<%@ include file="/common/footer_hpin.jsp"%>
