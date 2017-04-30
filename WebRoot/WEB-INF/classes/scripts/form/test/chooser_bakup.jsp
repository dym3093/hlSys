<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="org.hpin.base.util.StaticMehtod" %>
<%@page import="org.hpin.commons.system.dict.model.TawSystemDictType" %>
<%@page import="org.hpin.base.util.ApplicationContextHolder" %>
<%@page import="org.hpin.commons.system.dict.service.ITawSystemDictTypeManager" %>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ include file="/common/body.jsp"%>
<style type="text/css">
.x-form-column{width:320px}
.chooser-panle{display:none;padding:10px}
.class-box{padding:5px;background:#eef9eb;}
</style>
<script type="text/javascript">
	Ext.onReady(function(){
		c = new Chooser_sichuan();
	});
</script>
<div id="sheetform">
  <form id="theform" method="post">
	<div ID="idSpecial"></div>
	<br/>
	<input type="button" id="newchooser" value="${hpin:a2u('请选择派发对象')}" onclick="javascript:c.show()" class="btn"/>
	<div id='dept' class="chooser-panle">
		<input type="checkbox" name="deptList" value="{id:1,name:'${hpin:a2u("省公司")}'}" onclick="javascript:c.getDeptEls(this,'single')"/> ${hpin:a2u("省公司")}
		<input type="checkbox" name="deptList" value="{id:2,name:'${hpin:a2u("成都分公司")}'}" onclick="javascript:c.getDeptEls(this,'single')"/> ${hpin:a2u("成都分公司")}
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
		
		out.println(itemName);
		out.println("  <input type=\"checkbox\" value='"+itemCode+"' onclick=\"javascript:c.toggleCls(this)\"/>");
		%>${hpin:a2u("全选")}<%	
		out.println("<div id='div-"+itemCode+"' class='class-box'>");
		
		out.println("<div style=\"display:inline\">");
		
		List listSubClass = _objDictManager.getDictSonsByDictid(itemCode);
		for (Iterator subIt = listSubClass.iterator(); subIt.hasNext();){
			TawSystemDictType subitem = (TawSystemDictType) subIt.next();
			String subitemName = StaticMehtod.null2String(subitem.getDictName());
			String subitemCode = StaticMehtod.null2String(subitem.getDictCode());
			String elemntValue = "{prename:'"+itemName+"',name:'"+subitemName+"',preid:"+itemCode+",id:"+subitemCode+"}";
			out.println("<input type=\"checkbox\" value=\""+elemntValue+"\" id=\"class-id-"+subitemCode+"\" name=\"class-"+itemCode+"\" onclick=\"javascript:c.getCls(this,'single')\"/>"+subitemName);
		}
		out.println("</div>");
		
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
		out.println("<input type=\"checkbox\" value=\""+elemntValue+"\" name=\"businessList\" onclick=\"c.getBusinessList(this,'single')\"/>"+itemName);
	}
%>		
	</div>
	<div id="preview"></div>
  </form>
</div>
<%@ include file="/common/footer_hpin.jsp"%>
