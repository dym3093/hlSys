function removeLoading(){				
	var loading = Ext.get('loading');
	var mask = Ext.get('loading-mask');
	mask.setOpacity(.7);
	mask.shift({
		xy:loading.getXY(),
		width:loading.getWidth(),
		height:loading.getHeight(), 
		remove:true,
		duration:1,
		opacity:.3,
		easing:'bounceOut',
		callback : function(){
			loading.fadeOut({duration:.2,remove:true});
		}
	});
}
window.onerror = removeLoading;

//框架
var Mainframe = function(){
		return {
			init : function(){				
				var layout = new Ext.BorderLayout(document.body, {
					north: {
						split:false,
						initialSize: 65
					},
					center: {
	                    autoScroll:false
		            }
				});
				
				layout.beginUpdate();
				layout.add('north', new Ext.ContentPanel('header', {fitToFrame:true}));
				layout.add('center', new Ext.ContentPanel('mainFrame',{fitToFrame:true}));
				layout.endUpdate();
				
				var page = window.location.href.split('#')[1];
            	if(page){
            	  this.loadDoc(page);
            	}
            			
				if(Ext.isSafari || Ext.isOpera){
					layout.layout();
				}			
				
				removeLoading();

			},
			loadDoc : function(url){
				window.frames['mainFrame'].location.href = url;
        	}
		};
}();

Mainframe.init();
