<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>	
		<script type="text/javascript">
		var tree = null;
		Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('west-div','center-div','系统菜单管理', '',200);
			// 生成树
			rootNode = new Ext.tree.AsyncTreeNode( {
		      id : 'root0',
		      text : '系统菜单树'
	        });
	        var treeloader = new Ext.tree.TreeLoader( {
		      dataUrl : "resource!treeResource.action"
	         });
	         var tree = new Ext.tree.TreePanel({
		      		renderTo : 'west-div',
					rootVisible : true,
					loader : treeloader,
					frame : false,
					height:document.body.scrollHeight-35,
					autoScroll:true,
					border:false,
					autoWidth:true,
					root : rootNode
				});
				rootNode.expand();
				tree.on('beforeload', function(node) {
			  	tree.loader.dataUrl = 'resource!treeResourceDispose.action?parentId='+node.id;
			});//81773672
            tree.on("click", function(node, event) {
	           node.expand();
 	           if(node.id.indexOf('root') >= 0){
	           		 window.mainFrame.location.href='resource!listResourceModule.action' ;
	           }else if(node.id.indexOf('resource') >= 0){
						window.mainFrame.location.href='resource!listResourceChildren.action?parentId=' + node.id ;
	           }else if(node.id.indexOf('children') >= 0){
					window.mainFrame.location.href='resource!listResourceChildren.action?parentId=' + node.id ;
	           }
            });
		});
		</script>
	</head>
	<body>
		<div id="west-div">
		</div>
		<div id="center-div">
			<c:choose>
				<c:when test="${flag==1}">
					<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
					scrolling="auto" src="resource!listResourceChildren.action?parentId=${parentId}"></iframe>
				</c:when>
				<c:otherwise>
					<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
					scrolling="auto" src="resource!listResourceModule.action"></iframe>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>