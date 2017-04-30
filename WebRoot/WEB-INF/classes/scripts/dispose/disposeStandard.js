/**
 * @author 胡五音
 * 
 * 配置标准前台Js
 */
Ext.onReady(function(){
	var tabsOfRooms = new Ext.TabPanel({
		renderTo : "tabs",
		width : document.body.clientWidth - 22,
		activeTab : 0 ,
		defaults:{autoHeight: true},
		autoScroll : true ,
		frame : true ,
		items : [{
			title : "客厅" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeStandard!listDisposeStandard.action?filter_and_roomType_EQ_S=1&versionId=${version.id}"/>'
		},{
			title : "厨房" ,
			height : document.body.clientHeight - 60 ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeStandard!listDisposeStandard.action?filter_and_roomType_EQ_S=2&versionId=${version.id}"/>'
		},{
			title : "卫生间" ,
			height : document.body.clientHeight - 60 ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeStandard!listDisposeStandard.action?filter_and_roomType_EQ_S=3&versionId=${version.id}"/>'
		},{
			title : "房间( ≥10㎡ )" ,
			height : document.body.clientHeight - 60 ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeStandard!listDisposeStandard.action?filter_and_roomType_EQ_S=4&versionId=${version.id}"/>'
		},{
			title : "房间( ＜10㎡ )" ,
			height : document.body.clientHeight - 60 ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="disposeStandard!listDisposeStandard.action?filter_and_roomType_EQ_S=5&versionId=${version.id}"/>'
		}]
	});
});