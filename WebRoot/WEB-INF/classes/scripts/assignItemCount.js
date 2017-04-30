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
			title : "用户权限管理" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="assignAction!listUser.action?pass_orgId=-100"/>'
		},{
			title : "角色权限管理" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="assignAction!listRole.action"/>'
		},{
			title : "部门权限管理" ,
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="'+ (document.body.clientHeight - 60)  +'" src="assignAction!listOrg.action"/>'
		}]
	});
});