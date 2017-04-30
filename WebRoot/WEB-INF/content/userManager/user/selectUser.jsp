<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('west-div','center-div','用户管理', '',220);
			// 生成树
			rootNode = new Ext.tree.AsyncTreeNode( {
		      id : '-1',
		      text : '链家地产'
	        });
	        var treeloader = new Ext.tree.TreeLoader( {
		      dataUrl : "org!treeOrg.action"
	         });
	         var tree = new Ext.tree.TreePanel({
		      		renderTo : 'west-div',
					rootVisible : true,
					loader : treeloader,
					frame : false,
					autoWidth : true,
					autoScroll:true,
					border:false,
					height:380,
					root : rootNode
				});
				rootNode.expand();
				tree.on('beforeload', function(node) {
				var hlTreePath=node.attributes.hlTreePath;
			  	tree.loader.dataUrl = 'org!treeOrg.action?hlTreePath=' + hlTreePath;
			});
            tree.on("click", function(node, event) {
	           node.expand();
	           window.mainFrame.location.href='user!listSelectedUser.action?pass_orgId='+node.id+'&nameId=${nameId}&valueId=${valueId}';
            });
		});
		</script>
	</head>
	<body>
		<div id="west-div">

		</div>
		<div id="center-div">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
				scrolling="auto" src="user!listSelectedUser.action?pass_orgId=${rootOrg.id}&nameId=${nameId}&valueId=${valueId}"></iframe>
		</div>
	</body>
</html>