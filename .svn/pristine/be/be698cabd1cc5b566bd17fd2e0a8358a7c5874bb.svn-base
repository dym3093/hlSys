/**
 * @author : 胡五音
 * 
 * 配置物品管理前台js
 * 
 */
Ext.onReady(function(){
	var tabsOfDisposeInfo = new Ext.TabPanel({
		renderTo : "tabs",
		width : document.body.clientWidth - 22,
		activeTab : 0 ,
		defaults:{autoHeight: true},
		autoScroll : true ,
		frame : true ,
		items : [{
			title : "家电配置物品" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeItemCount!listHomeAppDisposeItemCount.action"/>'
		},{
			title : "家具配置物品" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeItemCount!listFurnitureDisposeItemCount.action"/>'
		},{
			title : "家居配置物品" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeItemCount!listHabitatDisposeItemCount.action"/>'
		},{
			title : "锁具配置物品" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeItemCount!listLockDisposeItemCount.action"/>'
		}]
	});
});