/************************************
 * valider
 * @e.g 例子
	Ext.onReady(function(){
		v = new valider({form:"myform",confirmTarget:"confirm.jsp"});
	});
 * @cfg form 表单id
 * @cfg confirmTarget
 ************************************/
function valider(formcfg){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	this.valid = true;
	this.formEl = Ext.get(formcfg.form);
	this.form = this.formEl.dom;
	this.fields = new Ext.util.MixedCollection();
	
	//注册el元素中的所有表单域
	this.regFields = function(el){
		Ext.select('input[type=text],input[type=hidden],textarea,select[alt]',false,el.dom).each(function(item){
			//alert(item.dom.id);
			var fieldId = item.dom.id;
			if(this.fields.containsKey(fieldId)) return;
			var cfg = item.dom.attributes.alt ? eval('({'+item.dom.attributes.alt.nodeValue+'})') : {};
			cfg.valider = this;
			switch(item.dom.tagName){
				case 'INPUT':
					if(item.dom.type=="hidden" && item.dom.attributes.alt){
						if(item.dom.attributes.alt.nodeValue!="") this.regAttach(item.dom,cfg);
					}
					else
						this.regInput(item.dom,cfg);
					break;
				case 'TEXTAREA':
					this.regTextarea(item.dom,cfg);
					break;
				case 'SELECT':
					this.regSelect(item.dom,cfg);
					break;
			}
		},this);
	};
	
	//注册input元素
	this.regInput = function(item,cfg){
		if(cfg.width==null) cfg.width = '180';
		if(cfg.timer){
			cfg.width = 180;
			cfg.readOnly = true;
			this.fields.add(item.id,new Ext.form.TimeField(cfg).applyTo(item));
		}
		else{
			this.fields.add(item.id,new Ext.form.TextField(cfg).applyTo(item));
		}
	};
	//注册附件验证
	this.regAttach = function(item,cfg){
		this.fields.add(item.id,new Ext.form.attachmentField(cfg).applyTo(item));
	};
	//注册textarea元素
	this.regTextarea = function(item,cfg){
		cfg.width = cfg.width==null ? '180px' : cfg.width;
		cfg.height = 100;
		this.fields.add(item.id,new Ext.form.TextArea(cfg).applyTo(item));
	};
	//注册select元素
	this.regSelect = function(item,cfg){
		cfg.width = cfg.width==null ? '180px' : cfg.width;
		try{
			this.fields.add(item.id,new Ext.form.SelectField(cfg).applyTo(item));
		}catch(e){};
	};
	
	/* 在指定id的元素中载入url页面，并注册其中的元素
	 * String id 元素id
	 * String url 插入的页面url
	 * Boolean isUpdate =true不判断元素中是否有内容直接插入
	 */
	this.appendPage = function(id,url,isUpdate){
		var el = Ext.get(id);
		if(!isUpdate && el.dom.innerHTML!="")return;
		var mgr = Ext.get(id).getUpdateManager();
     	mgr.loadScripts = true;
     	mgr.update(url);
     	mgr.on("update", this.regFields,this);
	};
	
	//注册表单中的元素
	this.regFields(this.formEl);
	
	//执行验证和提交
	this.check=function(e){	    
		this.valid = true;
		var firstFocus = null;
		this.fields.each(function(f){
			if(!f.validate()){
			  this.valid = false;
			  if(firstFocus==null)firstFocus = f;
			  if(e){
			    e.stopEvent();
			  }
			  else{
			  	return false;
			  }	  
            }
		},this);
		
		if(typeof formcfg.test == "function"){
			if(!formcfg.test()){
				this.valid = false;
				if(e){
			      e.stopEvent();
			    }
			    else{
			  	  return false;
			    }
			}			
		}
		
		if(this.valid){		
			if(formcfg.showTarget) this.confimTarget(formcfg.showTarget);
			else {
				Ext.get(formcfg.form).mask('数据提交中，请稍候...');
				this.form.submit();
			}
		}
		else if(firstFocus!=null){
			firstFocus.focus(/*是否选择文字*/false);
		}	
	};
	
	//验证所有表单域
	this.checkAllFields=function(){
		this.fields.each(function(f){
			f.validate();
		});	
	};
	//清除所有验证信息
	this.clear=function(){
		this.fields.each(function(f){
			f.clearInvalid();
		});
	}
	//验证单个表单域
	this.checkField=function(key){
		if(this.fields.containsKey)	this.fields.get(key).validate();
	};
	//确认派发角色
	this.confimTarget=function(url){
		var el = Ext.get(formcfg.form);
		el.mask("数据确认中，请稍候");
		var mm = el._maskMsg;
		var mmContent = Ext.get(mm.dom.firstChild);
		var data = Ext.lib.Ajax.serializeForm(this.form);
		mmContent.load({
			method:'POST',
        	url: url,
        	scripts:true,
        	params:data,
        	callback:function(){
        		mm.center(el);
        	}
   		});
   		
		
	};
	//确认派发角色时暂不发送
	this.stopSubmit=function(){
		Ext.get(formcfg.form).unmask();
	};
	//确认派发角色时确认发送
	this.doSubmit=function(btn){
		btn.disabled = true;
		this.form.submit();
	}
	
	//屏蔽/激活或隐藏/显示已注册key的某个元素
	this.dis=function(key,isDisable,isHide){
		if(typeof key == "string" && this.fields.containsKey(key)){
			var f = this.fields.get(key);
			if(isDisable){
				f.clearInvalid();
				if(isHide) 
					f.hide();
				else
					f.disable();
			}
			else{
				f.enable();
				f.show();
				f.validate();
			}
		}
		else{
			Try.these(function(){
				$(key).disabled=isDisable ? true : false;
				if(isDisable && isHide) 
					$(key).hide();
				if(!isDisable)
					$(key).show();						
			});
		}	
	}
	//屏蔽页面中某些表单域
	this.disable=function(){
		Ext.each(arguments,function(key){
			this.dis(key,true);
		},this);
	};
	//屏蔽并隐藏表单域
	this.hide=function(){
		Ext.each(arguments,function(key){
			this.dis(key,true,true);
		},this);
	};
	//激活并显示表单域
	this.enable=function(){
		Ext.each(arguments,function(key){
			this.dis(key,false);
		},this);	
	};	
	//屏蔽页面中某个区域
	this.disableArea=function(id,isHide){
		if(Ext.get(id).dom.innerHTML=='')return;
		Ext.get(id).mask('此时不需填写');
		Ext.select('input,select,textarea',true,id).each(function(f){
			var fieldKey = f.id;
			this.dis(fieldKey,true);
		},this);
		if(isHide) Ext.get(id).setDisplayed(false);
	};
	//激活并显示页面中某个区域
	this.enableArea=function(id){
		Ext.get(id).unmask();
		Ext.get(id).setDisplayed(true);
		Ext.select('input,select,textarea',true,id).each(function(f){
			var fieldKey = f.id;
			this.dis(fieldKey,false);
		},this);
	};
		
	//绑定表单提交事件
	//this.formEl.on('submit',this.check, this);
	Ext.each(formcfg.vbtn.split(","),function(b){
		if(Ext.get(b)) Ext.get(b).on('click',this.check,this);
	},this);
}
