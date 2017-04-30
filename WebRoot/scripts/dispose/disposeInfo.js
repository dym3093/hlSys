/**
 * @author : 胡五音
 * 
 * 配置基本信息前台js
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
			title : "家电配置信息" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listHomeAppDisposeInfo.action"/>'
		},{
			title : "家电配件" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listHomeAppAccessories.action"/>'
		},{
			title : "家具配置信息" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listFurnitureDisposeInfo.action"/>'
		},{
			title : "家具配送" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listFurnitureDistribution.action"/>'
		},{
			title : "家居配置信息" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listHabitatDisposeInfo.action"/>'
		},{
			title : "空间优化" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listSpaceUpgradeDisposeInfo.action"/>'
		},{
			title : "宽带" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listBroadBandDisposeInfo.action"/>'
		},{
			title : "服务" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeInfo!listServiceDisposeInfo.action"/>'
		}]
	});
});