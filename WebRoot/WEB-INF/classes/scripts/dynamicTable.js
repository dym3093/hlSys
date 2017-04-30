function DynamicTable(id,start,saveRow,handler,templateId,callback){
	this.coreDom = document.getElementById(id);
	this.coreDom.startRow = start;
	this.coreDom.saveRow = saveRow;
	this.coreDom.handlerElem = document.getElementById(handler);
	if(templateId&&templateId!=""){
		this.coreDom.elements = $("#"+templateId).children().clone();
	}else{
		this.coreDom.elements = $(this.coreDom.rows[start]).children().clone();
	}
	
	this.coreDom.add = function(){
		var self = this;
		//添加一行
		var newTr = this.insertRow(this.rows.length);
		//newTr.setAttribute("class" , this.coreDom.rows[start].class) ;
		newTr.setAttribute("className" , this.rows[start].className) ;
		this.elements.each(function(){
			$(newTr).append($(this).clone());
		});
		
		//添加一操作列
		var newTd = newTr.insertCell(newTr.cells.length);
		if(newTr.rowIndex >= this.saveRow + this.startRow){
			var newA = document.createElement("a");
			newA.innerHTML = "删除";
			newA.href = "javascript:void(0)";
			newA.onclick = function(){
				self.remove(this.parentNode.parentNode.rowIndex);
			}
			newTd.appendChild(newA);
		}
		this.refresh();
		
		//执行回调函数
		if(callback != ""){
			eval("("+callback+")");
		}
	}
	this.coreDom.remove = function(i){
		this.deleteRow(i);
		this.refresh();
	}
	this.coreDom.refresh = function(){
		var rexG = /^(.*\[)\d+(\].*)/;
		var rows = this.rows;
		for(var i=0; i<rows.length; i++){
			var cells = rows[i].cells;
			for(var j=0; j<cells.length; j++){
				var children = cells[j].childNodes;
				for(var k=0; k<children.length; k++){
					var elem = children[k];
					if(elem.name){
						elem.name = elem.name.replace(rexG,"$1"+i+"$2");
					}
					if(elem.id){
						elem.id = elem.id.replace(rexG,"$1"+i+"$2");
					}
				}
			}
		}
	}
	this.coreDom.init = function(){
		var self = this;
		this.elements.find(":text").each(function(){
			this.value = "";
		});
		this.elements.find(":hidden").each(function(){
			this.value = "";
		});
		this.elements.find("select").each(function(){
			if(this.options.length>0){
				this.options[0].selected = true;
			}
		});
		this.elements.find(":checkbox").each(function(){
				this.checked = false;
		});
		this.elements.find(":radio").each(function(){
				this.checked = false;
		});
		
		//如果开始的行不是第一行，则第一行为title，添加一列，标题为操作列
		if(this.startRow>0){
			if(this.rows[0].cells[0].nodeName.toUpperCase()=='TD'){
				var titleTd = this.rows[0].insertCell(this.rows[0].cells.length);
				$(titleTd).html("操作");
			}else{
				var titleTh = document.createElement("th");
				$(titleTh).html("操作");
				this.rows[0].appendChild(titleTh);
			}
		}
		
		var firstRow = this.rows[this.startRow];
		//添加一操作列
		var newTd = firstRow.insertCell(firstRow.cells.length);
		//如果指定的添加操作触发对象为空，则第一行保留添加操作，否则由handlerElem对象触发添加操作
		if(!this.handlerElem){
			var newA = document.createElement("a");
			newA.innerHTML = "添加&nbsp;&nbsp;";
			newA.href = "javascript:void(0)";
			newA.onclick = function(){
				self.add();
			}
			newTd.appendChild(newA);
		}else{
			this.handlerElem.onclick = function(){
				self.add();
			}
		}
		if(this.saveRow<=0){
			var newA = document.createElement("a");
			newA.innerHTML = "删除";
			newA.href = "javascript:void(0)";
			newA.onclick = function(){
				self.remove(this.parentNode.parentNode.rowIndex);
			}
			newTd.appendChild(newA);
		}
		
		if(this.rows.length>1){
			for(var i=this.startRow+this.saveRow-1,i=(i>this.rows.length-1?this.rows.length-1:i); i>this.startRow; i--){
				var otherRow = this.rows[i];
				//添加一空列，补齐单元格
				var newTd = otherRow.insertCell(otherRow.cells.length);
			}
			for(var i=this.startRow+this.saveRow,i=(i==this.startRow?this.startRow+1:i); i<this.rows.length; i++){
				var otherRow = this.rows[i];
				//添加一操作列
				var newTd = otherRow.insertCell(otherRow.cells.length);
				var newA = document.createElement("a");
				newA.innerHTML = "删除";
				newA.href = "javascript:void(0)";
				newA.onclick = function(){
					self.remove(this.parentNode.parentNode.rowIndex);
				}
				newTd.appendChild(newA);
			}
		}
	}
	
	this.coreDom.init();
}

function initSortTable(id,options,defaultSort,sortProperty,sortType){
	$("#_form").append("<div id='_sort'><input type='hidden' name='"+sortProperty+"' value='"+sortType+"'></div>");
	for(var item in options){
		var table = document.getElementById(id);
		var titleTrs = table.rows[0];
		var type = "asc";
		if(defaultSort[item]){
			type = defaultSort[item];
		}
		
		if(options[item]==sortProperty && sortType!=''){
			type = sortType=='asc'?'desc':'asc';
			$(titleTrs.cells[item]).addClass(type);
		}else{
			$(titleTrs.cells[item]).addClass('sort');
		}
		$(titleTrs.cells[item]).attr('type',type);
		$(titleTrs.cells[item]).click(function(){
			$("#_sort").remove();
			$("#_form").append("<div id='_sort'><input type='hidden' name='"+options[this.cellIndex]+"' value='"+$(this).attr("type")+"'></div>");
			document.getElementById('pageNum').value = 1;
			document.getElementById('_form').submit();
		});
	}
}