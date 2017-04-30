<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_hpin_ext.jsp"%>
<script type="text/javascript" src="${path}/scripts/layout/AppSimpleTree.js"></script>
<script type="text/javascript">
	var config = {
			
		/**************
		* Tree Configs 
		**************/
		treeGetNodeUrl:"${path}/hpin/sysDictType!getNodes.action",
		treeRootId:'-1',
		treeRootText:"所有字典",
		ctxMenu:{
			newNode:{text:"新建下级字典项"},
			delNode:{text:"删除这个字典项"}
		},//end of ctxMenu
		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"保存",
				url:"${path}/hpin/sysDictType!xsave.action",
				init:function(){
					AppSimpleTree.setField("parentDictId","dictId");
				}
			},
			getNode : {
				url:"${path}/hpin/sysDictType!xget.action"
			},
			edtNode : {
				btnText:"保存修改",
				url:"${path}/hpin/sysDictType!xedit.action"
			},
			delNode : {
				url:"${path}/hpin/sysDictType!xdelete.action"
			}
		},
		fieldOptions : {
			width:150
		},
		fields : [
	        new Ext.form.TextField({
	            fieldLabel: '字典名称',
	            name: 'sysDictType.dictName',
	            id:'dictName',
	            allowBlank:false
	        }),
	        new Ext.form.TextField({
	            fieldLabel: '字典编码',
	            name: 'sysDictType.dictCode',
	            id:'dictCode'
	        }),
	      new Ext.form.TextArea({
	            fieldLabel: '备注',
	            name: 'sysDictType.dictRemark',
	            id:'dictRemark',
	            grow: true,
	            preventScrollbars:true
	        }),
	        /* Hidden Field
	        */
	        new Ext.form.HiddenField({name:'sysDictType.dictId',id:'dictId'}),
	        new Ext.form.HiddenField({name: 'sysDictType.moduleId',id:'moduleId'}),
	        new Ext.form.HiddenField({name: 'sysDictType.moduleName',id:'moduleName'}),
	        new Ext.form.HiddenField({name: 'sysDictType.id',id:'id'}),
	        new Ext.form.HiddenField({name: 'sysDictType.parentDictId',id:'parentDictId'}),
	        new Ext.form.HiddenField({name: 'sysDictType.sysType',id:'sysType'}),
	        new Ext.form.HiddenField({name: 'sysDictType.leaf',id:'leaf'})
		], // end of fields
		
		/************************
		* Custom onLoad Functions
		*************************/	
		onLoadFunctions:function(){
		}
	}; // end of config
	Ext.onReady(AppSimpleTree.init, AppSimpleTree);
</script>

<div id="headerPanel" class="app-header"><img src="${path}/styles/default/images/header-dict.gif"></div>
<div id="helpPanel" class="app-panel">
	<dl>
		<dt>功能说明</dt>
		<dd>维护系统运行所需的基本数据，这些数据可能因系统的使用对象不同或不同时期而有不同的配置。常用的字典表配置包括：机房配置、故障类型配置、故障紧急程度配置等。通过字典表的配置可使系统的应用更加灵活。</dd>
	</dl>
	<br/>
	<dl>
		<dt>添加一个下级字典项</dt>
		<dd>在树图中的字典项上点击右键，并选择"新建下级字典项</dd>
		<dt>修改一个字典项的信息</dt>
		<dd>在树图中的字典项上点击右键，并选择"修改"</dd>
		<dt>删除字典项</dt>
		<dd>在树图中的字典项上点击右键，并选择"删除这个字典项"</dd>
	</dl>
</div>
<div id="treePanel">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel">
	<div id="formPanel-body" class="app-panel"></div>
</div>
<%@ include file="/common/footer_hpin.jsp"%>
