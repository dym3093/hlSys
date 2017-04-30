<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/default/ext-adpter.css"></link>
		<title>部门管理</title>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('tagNav','center-div','', '',220);
			// 生成部门人员树
			rootNode = new Ext.tree.AsyncTreeNode( {
		      id : '0',
		      text : '部门人员树'
	        });
	        var treeloader = new Ext.tree.TreeLoader( {
		      dataUrl : "../assign/assignPriv!userTree.action"
	         });
	         tree = new Ext.tree.TreePanel( {
		      		renderTo : 'dept-div',
					rootVisible : true,
					loader : treeloader,
					frame : false,
					autoScroll:true,
					autoWidth:true,
					border:false,
					root : rootNode
				});
			rootNode.expand();
	        // 单击树节点时在左边框架显示此菜单的子菜单集合,如果是非叶子节点则显示子菜单，如果是叶子节点则显示操作集合
            tree.on("click", function(node, event) {
            	if(node.id==0){
            		return false;
            	}
	           //node.expand();
	           var assignType;
	           if(node.leaf == 0){
	           		assignType = 1;
	           }else{
	           		assignType = 2;
	           }
	           var url = '${path}/assign/assignPriv!modifyPriv.action?assignType='+assignType+'&ownerId=' + node.id;
	           window.mainFrame.location.href = url;
            });
            
            // 生角色成树
			rootNode2 = new Ext.tree.AsyncTreeNode( {
		      id : '0',
		      text : '角色树'
	        });
	        var treeloader2 = new Ext.tree.TreeLoader( {
		      dataUrl : "../assign/assignPriv!getRoles.action"
	         });
	         tree2 = new Ext.tree.TreePanel( {
		      		renderTo : 'role-div',
					rootVisible : true,
					loader : treeloader2,
					frame : false,
					autoScroll:true,
					autoWidth:true,
					border:false,
					root : rootNode2
				});
				rootNode.expand();
	        // 单击树节点时在左边框架显示此菜单的子菜单集合,如果是非叶子节点则显示子菜单，如果是叶子节点则显示操作集合
            tree2.on("click", function(node, event) {
            	if(node.id==-1){
            		return false;
            	}
	           //node.expand();
	           var url = '${path}/assign/assignPriv!modifyPriv.action?assignType=3&ownerId=' + node.id;
	           window.mainFrame.location.href = url;
            });
		});
		</script>
	</head>
	<body>
		<div id="tagNav" style="height:100%;overflow:auto;" >
			<div id="dept-div"></div>
			<div id="role-div"></div>
		</div>
		<div id="center-div">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"></iframe>
		</div><span></span><span class="cssPropName editable">overflow</span><span class="cssColon">:&nbsp;</span><span class="cssPropValue editable">scroll</span><span class="cssSemi">;</span>
	</body>
</html>