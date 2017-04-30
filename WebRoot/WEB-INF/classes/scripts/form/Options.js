/*
 */

hpin.form.Options = {
	/**
	 * 为指定的select元素添加一个option,不指定value值时，value为""
	 * param String select 对象select元素的id
	 * param String text 要添加的option项的文本
	 * param String value 要添加的option项的值
	 */
	add : function(select,text,value){
		var o = $(select);
		if(!value)value="";
		o.options[o.length] = new Option(text, value, false, false);
	},
	/**
	 * 搜索指定的select元素中是否有指定value的option项
	 * 返回 true (存在选项) false(不存在选项)
	 * param String select 对象select元素的id
	 * param String value 要搜索的option项的值
	 */
	findValue : function(select,value){
		var s = $(select);
		if(!s) return false;
		for(var i=(s.options.length-1);i>=0;i--){
			if(s.options[i].value==value){
				return true;
			}
		}
		return false;
	},
	/**
	 * 搜索指定的select元素中是否有指定text的option项
	 * 返回 true (存在选项) false(不存在选项)
	 * param String select 对象select元素的id
	 * param String text 要搜索的option项的文本
	 */
	findText : function(select,text){
		var s = $(select);
		if(!s)return false;
		for(var i=(s.options.length-1);i>=0;i--){
			if(s.options[i].text==text){
				return true;
			}
		}
		return false;
	},
	/**
	 * 删除指定value的select元素的option项
	 * 如果没有value参数，效果为删除该select当前选择项
	 * param String select 对象select元素的id
	 * param String value 要删除的option项的值
	 */
	del : function(select,value){
		var from = $(select);
		if(arguments[1]){
			for(var i=(from.options.length-1);i>=0;i--){
				var o=from.options[i];
				if(o.value==value){from.options[i] = null;}
			}
		}
		else{
			if(from.value=="")return;
			from.options[from.selectedIndex] = null;
			
		}
		from.selectedIndex = 0;
	},
	/**
	 * 用JSON数据填充指定的select
	 * param String select 指定的select元素id
	 * param Array a 包含JSON数据的数组，形如[{text:"a",value:"1"},{text:"b",value:"2"}...]
	 * param Boolean isKeepFirst 是否保留第一个选项
	 */
	loadJSON : function(select,a,isKeepFirst){
		$(select).length = isKeepFirst ? 1 : 0;
		if(a && a.length){
		  a.each(function(o,i){
			hpin.form.Options.add(select,o.text,o.value);
		  });	
		}
	}
}
