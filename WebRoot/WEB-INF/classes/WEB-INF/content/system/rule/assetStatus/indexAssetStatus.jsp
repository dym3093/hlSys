<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
var tabs = null;
Ext
		.onReady(function() {
			tabs = new Ext.TabPanel(
					{
						renderTo : "tabs",
						width : document.body.clientWidth - 22,
						activeTab : 0,
						defaults : {
							autoHeight : true
						},
						autoHeight : true,
						autoScroll : true,
						frame : true,
						items : [
								{
									title : '房源状态',
									autoScroll : true,
									html : '<iframe name="commonIframe3" onload="height=window.screen.height-300;"  width="100%" scrolling="auto" src="${path}/system/assetStatus!listHouseAssetStatus.action"></iframe>'
								},
								{
									title : '业主状态',
									autoScroll : true,
									autoHeight : true,
									html : '<iframe name="commonIframe1" onload="height=commonIframe1.document.body.scrollHeight+60;"  width="100%" scrolling="auto" src="${path}/system/assetStatus!listOwnerAssetStatus.action"></iframe>'
								},
								{
									title : '客户状态',
									autoScroll : true,
									autoHeight : true,
									html : '<iframe name="commonIframe2" onload="height=commonIframe2.document.body.scrollHeight+60;"  width="100%" scrolling="auto" src="${path}/system/assetStatus!listCustomerAssetStatus.action"></iframe>'
								} ]
					});
			function handleAudit(tab) {
				alert(tab.title + ' was activated.');
			}
		});

function submitAuditForm(_form) {
	if (CheckFormFunction(_form)) {
		_form.submit();
	}
}
</script>

	</head>

	<body>
		<div id="tabs">

		</div>
		</form>
	</body>
</html>
