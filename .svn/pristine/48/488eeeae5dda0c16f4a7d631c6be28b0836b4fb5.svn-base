Main = function() {
	var layout;
	var nt = Ext.tree;

	return {
		init : function() {

			// create the main layout
			layout = new Ext.BorderLayout(document.body, {
				west : {
					split : true,
					initialSize : 200,
					minSize : 150,
					maxSize : 300,
					titlebar : true,
					collapsible : true,
					animate : false,
					autoScroll : true
				},
				center : {
					autoScroll : true,
					tabPosition : 'top',
					closeOnTab : true
				// alwaysShowTabs: true,
				}
			});
			// tell the layout not to perform layouts until we're done adding
			// everything
			layout.beginUpdate();

			layout.add('west', new Ext.ContentPanel('classes', {
				title : '模块浏览器',
				fitToFrame : true
			}));
			layout.add('center', new Ext.ContentPanel('pages', {
				fitToFrame : true
			}));

			layout.endUpdate();

			// 创建导航树
			var treeLoader = new nt.TreeLoader({
				dataUrl : config.treeGetNodeUrl,
				baseAttrs : {
					hrefTarget : 'pages'
				}
			});

			var tree = new nt.TreePanel('classes-body', {
				enableDD : false,
				containerScroll : true,
				lines : true,
				rootVisible : true
			});

			tree.on('beforeappend', function(t, parent, node) {
				var href = node.attributes.href;
				if (node.attributes.href) {					
					//以:开头,则说明要指定端口，需要为其加上scheme和serverName
					if (/^:/.test(node.attributes.href)) {
						node.attributes.href = config.scheme + "://" + config.serverName + node.attributes.href;
					}
					
					//以\开头,则加上hpin.appPath
					if(/^\//.test(node.attributes.href)){
						node.attributes.href = hpin.appPath + node.attributes.href;
					}
				}
			});
			
			//TODO 导航键在IE下不起作用
		    tree.el.on('keypress', function(e){
		        if(e.isNavKeyPress()){
		            e.stopEvent();
		        }
		    });
		    
			treeroot = new nt.AsyncTreeNode({
				id : config.treeRootId,
				allowDrag : false,
				allowDrop : false,
				text : config.treeRootText,
				loader : treeLoader
			});
			tree.setRootNode(treeroot);
			tree.render();
			treeroot.expand();

		},

		loadDoc : function(url) {
			Ext.get('main').dom.src = url;
		}
	};
}();
