/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

var TreeTest = function(){
    // shorthand
    var Tree = Ext.tree;
    
    return {
        init : function(){
            // yui-ext tree
            var tree = new Tree.TreePanel({
                el:'tree',
                animate:true, 
                autoScroll:true,
                loader: new Tree.TreeLoader({dataUrl:'../../ext/resource.js'}),
                enableDD:true,
                containerScroll: true,
                dropConfig: {appendOnly:false},
                height:400
            });
       tree.on('beforeload', function(node) {
				tree.loader.dataUrl = '../../ext/resource'+node.id+'.js';
			});
			
      tree.on("click", function(node, event) {
			    tree.loader.dataUrl = '../../ext/resource'+node.id+'.js';
			});
			tree.on('nodedragover', function(e) {
			    return false;
			});
            // add a tree sorter in folder mode
            new Tree.TreeSorter(tree, {folderSort:true});
            
            // set the root node
            var root = new Tree.AsyncTreeNode({
                text: '服务要素', 
                draggable:false, // disable root node dragging
                id:'0'
            });
            tree.setRootNode(root);
            
            // render the tree
            tree.render();
            
            root.expand(false, /*no anim*/ false);
            
            //-------------------------------------------------------------
            
            // YUI tree            
            var tree2 = new Tree.TreePanel({
                el:'tree2',
                animate:true,
                autoScroll:true,
                //rootVisible: false,
                loader: new Ext.tree.TreeLoader({
                    dataUrl:'../../ext/resource_empty.js'
                }),
                containerScroll: true,
                enableDD:true,
                dropConfig: {appendOnly:true},
                height:400
            });
            tree2.on("contextmenu",function(node,e){
                node.select();
                e.preventDefault(); //关闭默认的菜单，以避免弹出两个菜单
                var treeMenu = new Ext.menu.Menu({
				     items : [{
				        text : '删除',
				        handler : function(){
				        	treeMenu.hide();
				        	//执行删除
                  node.remove();
                  Ext.Msg.alert("执行结果：","权限删除成功！");
				        }
				     }]
				});
				treeMenu.show(node.ui.getAnchor());
             });
            tree2.on('beforeload', function(node) {
				tree2.loader.dataUrl = '../../ext/resource_empty.js';
			});
			tree2.on('beforenodedrop', function(e){
			    e.dropNode = TreeTest.copyDropNode(e.dropNode);
			});
			// 拖拽判断，用于处理叶节目不能append的问题
			tree2.on('nodedragover', function(e) {
				var dropNode = e.dropNode;
			    var target = e.target;
			    if(dropNode.parentNode.id != target.id){
			    	return false;
			    }
			    for(var i=0; i < target.childNodes.length; i++){
				   	n = target.childNodes[i];
				   	//如果已存在则返回false
				   	if(n.id == dropNode.id){
				    	return false;
			   		}
			   	}
			   
			    return true;
			});
			tree2.on('nodedrop', function(e){   
		         if(e.point=='append'){
		         	var ids = [];
		         	var node = e.dropNode;
		         	TreeTest.getNodeIds(ids,node);
		         	ids = ids.join(',');
		         	//执行授权
              Ext.Msg.alert("执行结果：","权限分配成功！");
		            //alert('当前"【'+e.dropNode.text+'】"被放到目录"【'+e.target.text+'】"下！');   
		         }
	       });
            
            // add a tree sorter in folder mode
            new Tree.TreeSorter(tree2, {folderSort:true});
            
            // add the root node
            var root2 = new Tree.AsyncTreeNode({
                text: '咨询组合', 
                draggable:false, 
                id:'0'
            });
            tree2.setRootNode(root2);
            tree2.render();
            
            root2.expand(false, /*no anim*/ false);
        },
        copyDropNode: function(node){
			var newNode = new Ext.tree.TreeNode(Ext.apply({}, node.attributes));
			for(var i=0; i < node.childNodes.length; i++){
			   n = node.childNodes[i];
			   if(n){
			    newNode.appendChild(TreeTest.copyDropNode(n));
			   }
			}
			return newNode;
		},
		getNodeIds: function(ids,node){
			ids.push(node.id);
			for(var i=0; i < node.childNodes.length; i++){
			   n = node.childNodes[i];
			   if(n){
			    	TreeTest.getNodeIds(ids,n);
			   }
			}
		}
    };
}();

Ext.EventManager.onDocumentReady(TreeTest.init, TreeTest, true);