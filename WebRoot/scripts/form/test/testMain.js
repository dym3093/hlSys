/**
 * @author mios
 */
 
var Chooser_sichuan = function(){
	var dlg;
	//选择框
	dlg = new Ext.LayoutDialog(Ext.id(), {
    	autoCreate:true,modal:true,width:500,height:400,
    	shadow:false,minWidth:300,minHeight:300,proxyDrag: true,
    	
		center: {
			autoScroll:true,
			titlebar: true
		},
		east:{
			autoScroll:true,
			titlebar: false,
			tabPosition: 'top'
		}
		
    });
	
	dlg.setTitle('选择框');
	dlg.addKeyListener(27, dlg.hide, dlg);
    dlg.addButton('确定',this.getPreview,this);
    dlg.setDefaultButton(dlg.addButton('关闭', dlg.hide, dlg));
    this.dlg = dlg;
    
	var layout = dlg.getLayout();
    layout.beginUpdate();
    layout.add('east', new Ext.ContentPanel('dept', {title: '部门',autoCreate:true}));
    layout.add('east', new Ext.ContentPanel('class', {title: '专业和系统',autoCreate:true}));
    layout.add('east', new Ext.ContentPanel('business', {title: '厂家',autoCreate:true}));
    layout.endUpdate();
    
    this.deptRecords = [];
	this.businessRecords = [];
	this.classRecords = new Ext.util.MixedCollection();
}
Chooser_sichuan.prototype = {
	show:function(){
		this.dlg.show();
	},
	hide:function(){
		this.dlg.hide();
	},
	getPreview:function(){
		if(this.deptRecords.length==0){ alert("请选择部门");return;}
		if(this.classRecords.getCount()==0){ alert("请选择专业和系统");return;}
		if(this.businessRecords.length==0){ alert("请选择厂家");return;}
		Ext.get('preview').dom.innerHTML = "";
		for (var c0 = 0; c0 < this.deptRecords.length; c0++){
			for (var c1 = 0; c1 < this.classRecords.length; c1++){
				for (var c2 = 0; c2 < this.businessRecords.length; c2++){
					var classitem = hpin.JSON.decode(this.classRecords.get(c1).value);
					var name = this.deptRecords[c0].name+"-"+classitem.prename+"-"+classitem.name+"-"+this.businessRecords[c2].name;
					var jsonValue = "{dept:"+this.deptRecords[c0].id
						+",class1:"+classitem.preid
						+",class2:"+classitem.id
						+",class3:"+this.businessRecords[c2].id+"}";
					Ext.get('preview').dom.innerHTML += "<br/><input type='checkbox' name='roleClass' checked='checked' value='"+jsonValue+"'/>"+name;
				}
			}
		}
		this.hide();
	},
	getDeptEls : function(el,mode){
		var deptEls = document.getElementsByName('deptList');
		this.deptRecords.length = 0;
		if(mode=="single"){
			var oldStatus = el.checked;
			Ext.each(deptEls,function(item){
				item.checked = false;
			});
			el.checked = oldStatus;
		}
		Ext.each(deptEls,function(item){
			if(item.checked){
				this.deptRecords.push(hpin.JSON.decode(item.value));
			}
		},this);	
	},
	getBusinessList : function(el,mode){
		var els = document.getElementsByName('businessList');
		this.businessRecords.length = 0;
		if(mode=="single"){
			var oldStatus = el.checked;
			Ext.each(els,function(item){
				item.checked = false;
			});
			el.checked = oldStatus;
		}
		Ext.each(els,function(item){
			if(item.checked){
				this.businessRecords.push(hpin.JSON.decode(item.value));
			}
		},this)
	},
	toggleCls : function(el){
		Ext.each(document.getElementsByName('class-'+el.value),function(cels){
			cels.checked = el.checked;
			this.getCls(cels);
		},this);
	},
	getCls : function(el, mode){
		if(el.checked){
			if(mode=="single"){
				this.classRecords.each(function(item){
					item.checked = false;
				})
				this.classRecords.clear();
			}
			this.classRecords.add(el.id,el);
		}
		else{
			this.classRecords.removeKey(el.id);
		}
	}
};



