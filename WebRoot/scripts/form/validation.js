/**
 * 验证类
 * 
 * @version 0.2.1 080927
 * @example 在表单项上设置验证信息 
 * 必填： <input type="text" alt="allowBlank:false,vtext:'请输入工单主题'"/> 
 * 下拉框必选：<select alt="allowBlank:false"></select> 
 * "请选择"选项必须是第一个选项，并且其值为"" 
 * 数字： <input type="text" alt="vtype:number"> 
 * 页面自定义验证： 注意必须返回true或false,可以用alert显示验证失败信息 
 *   v = new hpin.form.Validation({form:'theform'});
 *   v.custom = function(){ 
 *   	if($(time1).value.trim()==""){
 *          alert("回复时限不能为空"); return false; 
 *      } 
 *      return true;
 *   }
 * 直接提交不验证：<input type="submit" onclick="v.passing=true">
 *   此时提交只验证maxLength,其他不进行处理
 */

hpin.form.Validation = function(config) {
	hpin.apply(this, config);
	this.vAttr = 'alt';
	this.preCommitQueried = false; // 是否请求过preCommit后台
	this.form = $(this.form);
	this.init();
	this.form.on('submit', this.onSubmit, this);
	this.passing = false; // 设置为true时，本次提交不验证直接提交
}

hpin.form.Validation.prototype = {
	/**
	 * 是否是需要验证的表单项
	 * 
	 * @element el
	 */
	isVElement : function(el) {
		if (el.disabled) {
			return false;
		}
		if (!el[this.vAttr] && !el.getAttribute(this.vAttr)) {
			return false;
		} else {
			var config = this.getConfig(el);
			if (config.allowBlank == false || config.vtype || config.maxLength) {
				return true;
			}
			return false;
		}
	},
	/**
	 * 初始化验证状态，所有含有alt属性，并且不是disabled的元素将被绑定事件
	 * 
	 */
	init : function() {
		for (var i = 0; i < this.form.elements.length; i++) {
			var el = this.form.elements[i];
			if (!this.isVElement(el))
				continue;
			$(el).on('blur', this.validate, this);
		}
	},
	/**
	 * 提交时重新验证所有元素，并提示验证信息
	 * 
	 */
	check : function() {
		var firstInvalid = null, valid = true;
		var els = this.form.elements;
		var fLength = els.length;
		for (var i = 0; i < fLength; i++) {
			var el = els[i];
			if (!this.isVElement(el))
				continue;
			// $(el).on('blur',this.validate,this);
			if (!this.validate(el)) {
				if (valid) {
					firstInvalid = el;
				}
				valid = false;
			}
		}
		if (!valid) {
			this.alertInvalid(firstInvalid);
		}

		// 页面自定义验证

		if (valid && typeof this.custom == "function") {
			if (!this.custom()) {
				valid = false;
			}
		}

		return valid;
	},
	/**
	 * form提交事件，当验证未通过时停止事件
	 * 
	 * @event event
	 * @element form
	 */
	onSubmit : function(form, event) {
		Ext.get(this.form).mask('数据验证中，请稍候...');
		// try{
		if (!this.check()) { // 验证表单
			Ext.get(this.form).unmask();
			hpin.stopEvent(event);
		} else {
			if (this.preCommitUrl && !this.preCommitQueried && !this.passing) { // 显示后台确认信息
				hpin.stopEvent(event);
				var data = Ext.lib.Ajax.serializeForm(this.form);
				Ext.Ajax.request({
					method : 'post',
					url : this.preCommitUrl,
					success : this.handleResponse,
					failure : this.handleFailure,
					scope : this,
					params : data
				});
			}
		}

		this.passing = false;
		// }
		// catch(e){
		// hpin.log("error!",e);
		// hpin.stopEvent(event);
		// }

	},
	/**
	 * 处理后台返回信息，格式为JSON JSON字段： status: 状态标识 0 为通过验证直接提交 1 为等待用户确认 2 为不提交
	 * text:等待用户确认的提示文字 script:需执行的javascript
	 */
	handleResponse : function(x) {
		var o = hpin.JSONDecode(x.responseText);

		if (o.status == 0) {
			this.preCommitQueried = true;
			this.form.submit();
		}

		var arrText = [];
		o.data.each(function(item) {
			arrText.push(item.text);
		});

		if (o.status == 1) {
			if (window.confirm(arrText.join("\n\n"))) {
				o.data.each(function(item) {
					if (typeof item.fn == "function") {
						item.fn();
					}
				});
				this.preCommitQueried = true;
				this.form.submit();
			} else {
				Ext.get(this.form).unmask();
			}
		}
		if (o.status == 2) {
			alert(arrText.join("\n\n"));
			Ext.get(this.form).unmask();
		}
	},
	handleFailure : function() {
		this.preCommitQueried = true;
		hpin.log("handle fail");
		this.form.submit();
	},
	/**
	 * 验证某个表单项
	 * 
	 * 
	 * @event e
	 * @element el
	 */
	validate : function(el, event) {
		var config = this.getConfig(el);
		if (typeof config != 'object') {
			return false;
		}
		if (el.disabled || this.validateValue(el.value, el, config)) {
			this.clearInvalid(el);
			return true;
		}
		return false;
	},
	/**
	 * 验证表单项的值
	 * 
	 */
	validateValue : function(value, el, config) {
		var blankText = "请输入内容";
		if (config.allowBlank === false && !this.passing) {
			if (el.tagName == 'SELECT') {
				blankText = "请至少选择一项";
			}
			if (el.type == 'radio' || el.type == 'checkbox') {

			}
			if (el.value.trim() == "") {
				this.markInvalid(el, blankText);
				return false;
			}
		}
		if (config.vtype && !this.passing) {
			var vt = hpin.form.VTypes;
			if (!vt[config.vtype](value, el, config)) {
				this.markInvalid(el, config.vtext || vt[config.vtype + 'Text']);
				return false;
			}
		}
		if (config.maxLength && parseInt(config.maxLength) > 0
				&& (el.tagName == 'INPUT' || el.tagName == 'TEXTAREA')) {

			var vLen = el.value.length, mLen = parseInt(config.maxLength), tLen = 0;
			for (var i = 0; i < vLen; i++) {
				var intCode = el.value.charCodeAt(i);
				if (intCode >= 0 && intCode <= 128) {
					tLen += 1; // 非中文单个字符长度加 1
				} else {
					tLen += 2; // 中文字符长度则加 2
				}
				if (tLen > mLen) {
					this.markInvalid(el, "字段最多可输入" + mLen + "个英文字符,或"
							+ Math.floor(mLen / 2) + "个中文字符,请减少该字段的字符数量");
					return false;
				}
			}
		}
		return true;
	},
	/**
	 * 提示验证信息,第一个未通过验证的表单项将获得焦点
	 * 
	 * 
	 * @element el 第一个验证未通过的表单项
	 */
	alertInvalid : function(el) {
		var config = this.getConfig(el);
		var info = config.vtext || config.blankText || el.invalidText || '请检查表单项';
		info += "\n\n...\n表单至少有1项不符合要求，请检查表单项。"

		alert(info);

		Ext.get(this.form).unmask();

		setTimeout(function() {
			try {
				el.focus();
			} catch (e) {
			}
		}, 50);
	},
	/**
	 * 将表单项标记为未通过
	 * 
	 * @element el
	 * @string text
	 */
	markInvalid : function(el, text) {
		if (hpin.isIE && el.tagName == "SELECT") {
			$(el.options[0]).addClass('invalid');
		}
		$(el).addClass('invalid');
		$(el).title = el.invalidText = text;
	},
	/**
	 * 将表单项标记为通过
	 * 
	 * @element el
	 */
	clearInvalid : function(el) {
		if (hpin.isIE && el.tagName == "SELECT") {
			$(el.options[0]).removeClass('invalid');
		} else {
			$(el).removeClass('invalid');
		}
		$(el).title = '';
	},
	/**
	 * 获取表单项上的验证设置，一般是在alt属性中设置
	 * 
	 * @element el
	 */
	getConfig : function(el) {
		var cfg = el[this.vAttr] || el.getAttribute(this.vAttr);
		try {
			return eval('({' + cfg + '})');
		} catch (e) {
			return {};
		}
	}
};

hpin.form.VTypes = function() {
	var number = /^[0-9]*$/;
	var email = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
	return {
		'number' : function(v) {
			return number.test(v);
		},
		'numberText' : '请输入数字',

		'moreThen' : function(value, el, config) {
			if (!config.link) {
				return true
			};
			var linkEl = $(config.link);
			hpin.form.Validation.prototype.validate(linkEl);
			return value > linkEl.value;
		},
		'moreThenText' : '',

		'lessThen' : function(value, el, config) {
			if (!config.link) {
				return true
			};
			var linkEl = $(config.link);
			hpin.form.Validation.prototype.validate(linkEl);
			return value < linkEl.value;
		},
		'lessThenText' : '',

		'email' : function(v) {
			return email.test(v);
		},
		'emailText' : '请输入邮件地址'
	}
}();
