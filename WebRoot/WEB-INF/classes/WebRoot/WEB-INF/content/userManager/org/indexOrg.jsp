<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<title>部门管理</title>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('west-div','center-div','', '',220);
			// 生成树
			rootNode = new Ext.tree.AsyncTreeNode( {
		      id : '-100',
		      text : '部门树',
		      attributes :'hlTreePath:${org.hlTreepath}'
	        });
	        var treeloader = new Ext.tree.TreeLoader( {
		      dataUrl : "org!treeOrgMain.action"
	         });
	         tree = new Ext.tree.TreePanel( {
		      		renderTo : 'west-div',
					rootVisible : true,
					loader : treeloader,
					frame : false,
				
					autoScroll:true,
					autoWidth:true,
					border:false,
					root : rootNode
				});
				rootNode.expand();
				tree.on('beforeload', function(node) {
				  var hlTreePath=node.attributes.hlTreePath;
			      tree.loader.dataUrl = 'org!treeOrg.action?hlTreePath=' + hlTreePath;
		});
	       // tree = createTree('west-div','org!treeOrg.action', '-1', '链家地产', true, null, 0, true,null);
	        // 单击树节点时在左边框架显示此菜单的子菜单集合,如果是非叶子节点则显示子菜单，如果是叶子节点则显示操作集合
            tree.on("click", function(node, event) {
	           node.expand();
	           window.mainFrame.location.href='org!listOrg.action?pass_orgId='+node.id+'&pass_hlTreePath='+node.attributes.hlTreePath;
            });
		});
		</script>
	</head>
	<body>
		<div id="west-div" style = "height: 100%;overflow: auto;">

		</div>
		<div id="center-div">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
				src="org!listOrg.action?pass_orgId=-100"></iframe>
		</div>
	</body>
</html>