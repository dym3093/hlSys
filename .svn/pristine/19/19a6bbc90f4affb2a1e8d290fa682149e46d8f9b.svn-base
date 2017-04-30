<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 生成树
			var rootNode = new Ext.tree.AsyncTreeNode({
				text : 'root'
			});

			var treeloader = new Ext.tree.TreeLoader({
				dataUrl : path+'/assign/assignAction!grantResourceTree.action?roleId=${roleId}'
			});
			
			tree = new Ext.tree.TreePanel({
				renderTo : 'west-div',
				rootVisible : false,
				loader : treeloader,
				root : rootNode,
				autoHeight:true,
				border:false,
				bodyBorder:false,
				buttonAlign:'center',
				buttons: [{
			            text: '保存',
			            handler: function(){
			                var grantInfo = '';
			                var selNodes = tree.getChecked();
			                Ext.each(selNodes, function(node){
			                    if(grantInfo.length > 0){
			                    	grantInfo += ',';
			                    }
			           
			                    grantInfo += node.id;
			                })
			              Ext.Ajax.request({
									url : path+'/assign/assignAction!grantResource.action?objectType=${objectType}',
									params : {
			            	            grantInfo : grantInfo,
										roleId:${roleId}
									},
									success : function(response, options) {
										var responseText=response.responseText;
										if(responseText.trim()=='true'){
											Ext.MessageBox.alert('状态', '操作成功',showResult);
											
										}else{
											Ext.MessageBox.alert('状态', '操作失败',showResult);
											 window.close();
										}
										
									},
									failure : function(response, options) {
										Ext.MessageBox.alert('警告', '服务器连接异常，操作失败！');
									},
									waitMsg : '正在提交数据，稍后...'
								});
			               
			            }
			        },{
			            text: '取消',
			            handler: function(){
			                top.closeWindow();
			            }
			        }]
			});
			tree.on('checkchange', function(node, check){ 
			      if(node.hasChildNodes()){
			    	  var childNodeArray=node.childNodes;
				       if(check){
				           node.expand();
				    	   for(var i=0;i<childNodeArray.length ;i++){
				    		  //设置UI状态为未选中状态
				    		   childNodeArray[i].getUI().toggleCheck(true);
				    		 //设置节点属性为未选中状态
				    		   childNodeArray[i].attributes.checked=true;
						   }
					   }else{
						   for(var i=0;i<childNodeArray.length ;i++){
					    		  //设置UI状态为未选中状态
					    		   childNodeArray[i].getUI().toggleCheck(false);
					    		 //设置节点属性为未选中状态
					    		   childNodeArray[i].attributes.checked=false;
							   }
						     
					   }
				    }else if(node.id.indexOf('operation')>=0){
				    	if(check){
				    		parentNode=node.parentNode;
				    		if(parentNode.attributes.checked==false){
				    			//设置UI状态为未选中状态
					    		   parentNode.getUI().toggleCheck(true);
					    		 //设置节点属性为未选中状态
					    		   parentNode.attributes.checked=true;
					    	}
					   }
				  }
			});
						
		});
		function showResult(){
			 window.close();
			}
		</script>
	</head>
	<body>
		<div id="west-div">
		</div>
	</body>
</html>
