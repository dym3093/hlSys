/**
 * 根据ID获取HTML元素
 * 
 * @param {}
 *            value html元素ID
 * @return {}
 */
function $$(value) {
	return document.getElementById(value);
}

/**
 * 创建form
 * 
 * @return {}
 */
function getNewForm(formName) {
	var form = document.createElement("form");
	form.name = formName;
	document.body.appendChild(form);
	form.method = "post";
	return form;
}

function getForm() {
	var _form = null;
	_form = document.getElementById("searchFormTemp");
	if (null == _form) {
		_form = document.getElementById("searchBackFormTemp");
	}
	if (null == _form) {
		_form = document.getElementById("transferForm");
	}
	if (null == _form) {
		_form = getNewForm("searchFormTemp");
	}
	return _form;
}

function clearSearchForm(thisForm) {
	var elements = thisForm.getElementsByTagName("input");
	for ( var j = 0; j < elements.length; j++) {
		if (elements[j].type == "text" || elements[j].type == "hidden") {
			elements[j].value = "";
		}
	}
	elements = thisForm.getElementsByTagName("select");
	for ( var j = 0; j < elements.length; j++) {
		elements[j].value = "-10";
	}
	if (typeof (thisForm) != "undefined") {
		thisForm.submit();
	}
}

/**
 * 清空Form表单中，可能重复的信息项目
 */
function clearSubmitForm(thisForm){
	var elements = thisForm.getElementsByTagName("input");
	for( var j = 0; j < elements.length; j++) {
		if(elements[j].className == "HWYHWYHWY"){
			thisForm.removeChild(elements[j]) ;
		}
	}
}

/**
 * 提交Form
 * 
 * @param {Object}
 *            _form
 */
function submitForm(_form) {
	if (CheckFormFunction(_form)) {
		_form.submit();
	}
}

/**
 * 提交添加操作
 * 
 * @param {Object}
 *            actionUrl
 */
function addAction(actionUrl) {
	var _form = getForm();
	_form.action = actionUrl;
	_form.submit();
}

/**
 * 修改操作调用的js函数
 * 
 * @param {}
 *            actionUrl 跳转Action
 * @param {}
 *            parameterName checkbox的ID名称
 * @param {}
 *            idName 提交到后台的参数名称
 */
function updateAction(actionUrl, parameterName, idName) {
	var _form = getForm();
	if (null != parameterName) {
		var cbs = document.getElementsByName(idName);
		if (cbs == undefined) {
			alert("请选择一条记录！");
			return;
		}
		var id = null;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				id = cbs[i].value;
			}
		}
		if (selectNum == 0) {
			alert("请选择一条记录！");
			return;
		}
		if (selectNum > 1) {
			alert("只能选择一条记录!");
			return;
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = id;
		_form.appendChild(hiddenInput);
	}
	_form.action = actionUrl;
	_form.submit();
}

/**
 * 合同页面为AMSadmin增加特权，可以修改审核通过的合同
 */
function updateSpecialAction(actionUrl , parameterName , idName){
	var _form = getForm();
	if (null != parameterName) {
		var cbs = document.getElementsByName(idName);
		if (cbs == undefined) {
			alert("请选择一条记录！");
			return;
		}
		var id = null;
		var _tmp = null ;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				_tmp = cbs[i].value;
			}
		}
		var arrays = _tmp.split("-hwy_acewill_hwy-") ;
		id = arrays.length > 0 ? arrays[0] : -100 ;
		var auditState = arrays.length > 1 ? arrays[1] : -100 ;
		if (selectNum == 0) {
			alert("请选择一条记录！");
			return;
		}
		if (selectNum > 1) {
			alert("只能选择一条记录!");
			return;
		}
		if(id == -100){
			alert("信息缺失，请尝试重新登录，或联系管理员！");
			return;
		}
		if(auditState == 2){
			var confirm = confirm = "您选择的合同已经审核通过，确认要进行修改吗？" ;
			var _flag = window.confirm(confirm) ;
		}
		if(_flag || auditState != 2){
			var hiddenInput = document.createElement("input");
			hiddenInput.type = "hidden";
			hiddenInput.name = parameterName;
			hiddenInput.value = id;
			_form.appendChild(hiddenInput);
			_form.action = actionUrl;
			_form.submit();
		}
		
	}
}

/**
 * 合同页面提交的updateAction
 * @param actionUrl
 * @param parameterName
 * @param idName
 */
function updateContractAction(actionUrl, parameterName, idName) {
	var _form = getForm();
	if (null != parameterName) {
		var cbs = document.getElementsByName(idName);
		if (cbs == undefined) {
			alert("请选择一条记录！");
			return;
		}
		var id = null;
		var _tmp = null ;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				_tmp = cbs[i].value;
			}
		}
		var arrays = _tmp.split("-hwy_acewill_hwy-") ;
		id = arrays.length > 0 ? arrays[0] : -100 ;
		var auditState = arrays.length > 1 ? arrays[1] : -100 ;
		if (selectNum == 0) {
			alert("请选择一条记录！");
			return;
		}
		if (selectNum > 1) {
			alert("只能选择一条记录!");
			return;
		}
		if(id == -100){
			alert("信息缺失，请尝试重新登录，或联系管理员！");
			return;
		}
		if(auditState == 2){
			alert("审核通过的合同不允许进行更改操作！");
			return;
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = id;
		_form.appendChild(hiddenInput);
	}
	_form.action = actionUrl;
	_form.submit();
}

/**
 * 合同列表页面调用的查看方法
 */
function updateViewContractAction(actionUrl, parameterName, idName){
	var _form = getForm();
	if (null != parameterName) {
		var cbs = document.getElementsByName(idName);
		if (cbs == undefined) {
			alert("请选择一条记录！");
			return;
		}
		var id = null;
		var _tmp = null ;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				_tmp = cbs[i].value;
			}
		}
		var arrays = _tmp.split("-hwy_acewill_hwy-") ;
		id = arrays.length > 0 ? arrays[0] : -100 ;
		var auditState = arrays.length > 1 ? arrays[1] : -100 ;
		if (selectNum == 0) {
			alert("请选择一条记录！");
			return;
		}
		if (selectNum > 1) {
			alert("只能选择一条记录!");
			return;
		}
		if(id == -100){
			alert("信息缺失，请尝试重新登录，或联系管理员！");
			return;
		}
		if(auditState == 2){
			alert("审核通过的合同不允许进行更改操作！");
			return;
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = id;
		_form.appendChild(hiddenInput);
	}
	_form.action = actionUrl;
	_form.submit();
}

/**
 * 合同列表页面作废合同处理方法
 */
function deleteContractAction(actionUrl, parameterName, idName){
	// 取出删除记录集合
	var idArray = document.getElementsByName(idName);
	var ids = "";
	var isHasPassContract = false ;
	var auditState = 0 ;
	// 遍历每一个集合，删除的记录ID组装成字符串，以,隔开
	for ( var i = 0; i < idArray.length; i++) {
		if (idArray[i].checked) {
			ids = ids + idArray[i].value.split("-hwy_acewill_hwy-")[0] + ",";
			auditState = idArray[i].value.split("-hwy_acewill_hwy-").length > 1 ? idArray[i].value.split("-hwy_acewill_hwy-")[1] : 0 ;
			if(auditState == 2){
				isHasPassContract = true ;
				break ;
			}
		}
	}
	if(isHasPassContract){
		alert("审核通过的合同不允许作废！") ;
		return ;
	}
	// 如果为空字符，则用户没有选择任何记录
	if (ids == "") {
		alert("请选择一条记录");
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var idStrArray = ids.split(",");
	if(confirm=="noConfirm"){
		flag = true;
	}else{
		var confirm = confirm = "确认作废吗？";
		var flag = window.confirm(confirm);
	}
	if (flag) {
		var _form = getForm();
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = ids;
		_form.appendChild(hiddenInput);
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}
/**
 * 合同列表页面作废合同处理方法
 */
function confirmRentRushAction(actionUrl, parameterName, idName){
	// 取出删除记录集合
	var idArray = document.getElementsByName(idName);
	var ids = "";
	var isHasPassContract = false ;
	var auditState = 0 ;
	// 遍历每一个集合，删除的记录ID组装成字符串，以,隔开
	for ( var i = 0; i < idArray.length; i++) {
		if (idArray[i].checked) {
			ids = ids + idArray[i].value.split("-hwy_acewill_hwy-")[0] + ",";
			auditState = idArray[i].value.split("-hwy_acewill_hwy-").length > 1 ? idArray[i].value.split("-hwy_acewill_hwy-")[1] : 0 ;
			if(auditState == 2){
				isHasPassContract = true ;
				break ;
			}
		}
	}
	if(isHasPassContract){
		alert("审核通过的合同不允许作废！") ;
		return ;
	}
	// 如果为空字符，则用户没有选择任何记录
	if (ids == "") {
		alert("请选择一条记录");
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var idStrArray = ids.split(",");
	if(confirm=="noConfirm"){
		flag = true;
	}else{
		var confirm = confirm = "确认分派吗？";
		var flag = window.confirm(confirm);
	}
	if (flag) {
		var _form = getForm();
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = ids;
		_form.appendChild(hiddenInput);
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}
function deleteContractActionOnAudit(actionUrl, parameterName, idName){
	// 取出删除记录集合
	var idArray = document.getElementsByName(idName);
	var ids = "";
	var isHasPassContract = false ;
	var isis = false ; 
	var auditState = 0 ;
	// 遍历每一个集合，删除的记录ID组装成字符串，以,隔开
	for ( var i = 0; i < idArray.length; i++) {
		if (idArray[i].checked) {
			ids = ids + idArray[i].value.split("-hwy_acewill_hwy-")[0] + ",";
			auditState = idArray[i].value.split("-hwy_acewill_hwy-").length > 1 ? idArray[i].value.split("-hwy_acewill_hwy-")[1] : 0 ;
			if(actionUrl.indexOf("payment!deletePayment.action")>=0){
				if(auditState == 1){
					isis = true ;
					break ;
				}
			}else{
				if(auditState == 2){
					isHasPassContract = true ;
					break ;
				}
			}
			
		}
	}
	// 如果为空字符，则用户没有选择任何记录
	if (ids == "") {
		alert("请选择一条记录");
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var idStrArray = ids.split(",");
	if(confirm=="noConfirm"){
		flag = true;
	}else{
		var confirm = confirm = "确认作废吗？";
		var flag = window.confirm(confirm);
	}
	
	if (flag) {
		if(isis){
			alert("您选择的付款单中有已经通过审核的，不能删除！");
			return;
		} else
		if(isHasPassContract){
			var _confirm = confirm = "您选择的合同中有已经通过审核的，确认要作废吗？" ;
			var hasPass2Del = window.confirm(_confirm) ;
		}else{
			hasPass2Del = true ;
		}
		if(hasPass2Del){
			var _form = getForm();
			var hiddenInput = document.createElement("input");
			hiddenInput.type = "hidden";
			hiddenInput.name = parameterName;
			hiddenInput.value = ids;
			_form.appendChild(hiddenInput);
			_form.action = actionUrl;
			_form.submit();
		}else{
			return ;
		}
	} else {
		return;
	}
}


function openContractWinForId(title, url, width, height, parameterName, idName) {
	var cbs = document.getElementsByName(idName);
	if (cbs == undefined) {
		alert("请选择一条记录！");
		return;
	}
	var id = null;
	var selectNum = 0;
	for ( var i = 0; i < cbs.length; i++) {
		if (cbs[i].checked) {
			selectNum = selectNum + 1;
			id = cbs[i].value.split("-hwy_acewill_hwy-")[0];
		}
	}
	if (selectNum == 0) {
		alert("请选择一条记录！");
		return;
	}
	if (selectNum > 1) {
		alert("只能选择一条记录!");
		return;
	}
	if (id.indexOf("?") > 0) {
		url = url + "&id=" + id;
	} else {
		url = url + "?id=" + id;
	}
	var win = showWindow(title, width, height, url, true, true);
	win.show();
}


/**
 * 删除操作调用的js函数
 * 
 * @param {}
 *            actionUrl 跳转Action
 * @param {}
 *            parameterName checkbox的ID名称
 * @param {}
 *            idName 提交到后台的参数名称
 */
function deleteAction(actionUrl, parameterName, idName,confirm) {
	// 取出删除记录集合
	var idArray = document.getElementsByName(idName);
	var ids = "";
	// 遍历每一个集合，删除的记录ID组装成字符串，以,隔开
	for ( var i = 0; i < idArray.length; i++) {
		if (idArray[i].checked) {
			ids = ids + idArray[i].value + ",";
		}
	}
	// 如果为空字符，则用户没有选择任何记录
	if (ids == "") {
		alert("请选择一条记录");
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var idStrArray = ids.split(",");
	if(confirm=="noConfirm"){
		flag = true;
	}else{
		var confirm = confirm = "是否确认删除!";
		var flag = window.confirm(confirm);
	}
	if (flag) {
		var _form = getForm();
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = ids;
		_form.appendChild(hiddenInput);
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}

function getCheckId(idName) {
	var cbs = document.getElementsByName(idName);
	if (cbs == undefined) {
		alert("请选择一条记录！");
		return;
	}
	var id = null;
	var selectNum = 0;
	for ( var i = 0; i < cbs.length; i++) {
		if (cbs[i].checked) {
			selectNum = selectNum + 1;
			id = cbs[i].value;
		}
	}
	if (selectNum == 0) {
		alert("请选择一条记录！");
		return;
	}
	if (selectNum > 1) {
		alert("只能选择一条记录!");
		return;
	}
	return id;
}
/**
 * checkbox全选函数 cAllName是全选checkbox按钮的ID checkboxsName是全选checkbox组的name
 */
function checkAll(thisCheck, checkboxsName) {
	var cabs = thisCheck;
	var cbs = document.getElementsByName(checkboxsName);
	if (cbs == undefined) {
		return;
	}
	if (typeof cbs.length == "undefined") {
		cbs = document.forms[0].id;
		if (cbs.checked && !cbs.checked) {
			cbs.checked = true;
		}
		if (!cbs.checked && cbs.checked) {
			cbs.checked = false;
		}
		return;
	}
	for ( var i = 0; i < cbs.length; i++) {
		if (cabs.checked) {
			cbs[i].checked = true;
		} else {
			(cbs[i].checked) = false;
		}
	}
}
function href(actionUrl) {
	var _form = getForm();
	_form.action = actionUrl;
	_form.submit();
}

function addGrid(obj, tdHtml) {
	var aa = $(obj).parents("table");
	aa.append(tdHtml);
}

function delGrid(obj) {
	$(obj).parents("tr").remove();
}

function openDetailWin(url, width, height, title) {
	if (title == 'null') {
		title = '详细内容';
	}
	var detailWin = showWindow(title, width, height, url, true, true);
	detailWin.show();
}

function openWinForId(title, url, width, height, parameterName, idName) {
	var cbs = document.getElementsByName(idName);
	if (cbs == undefined) {
		alert("请选择一条记录！");
		return;
	}
	var id = null;
	var selectNum = 0;
	for ( var i = 0; i < cbs.length; i++) {
		if (cbs[i].checked) {
			selectNum = selectNum + 1;
			id = cbs[i].value;
		}
	}
	if (selectNum == 0) {
		alert("请选择一条记录！");
		return;
	}
	if (selectNum > 1) {
		alert("只能选择一条记录!");
		return;
	}
	if (id.indexOf("?") > 0) {
		url = url + "&id=" + id;
	} else {
		url = url + "?id=" + id;
	}
	var win = showWindow(title, width, height, url, true, true);
	win.show();
}

/**
 * 对js获取的浮点数做小数截取保留，根据小数点后第pos + 1位的数值对第pos位做四舍五入
 * 如果是整数，则不做操作
 * 参数1：需要转换的数值；参数2：保留位数
 */
function formatFloat(src, pos) {
	if( src==null || src==''){
		return 0;
	}
	return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
}

/**
 * 以下几个JS方法是对select元素进行操作
 * 包含(新增、修改、删除、清空、判断存在等)
 */
//判断select选项中 是否存在Value="paraValue"的Item        
function jsSelectIsExitItem(objSelect, objItemValue) {
	var isExit = false;
	for ( var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == objItemValue) {
			isExit = true;
			break;
		}
	}
	return isExit;
}

//向select选项中 加入一个Item        
function jsAddItemToSelect(objSelect, objItemText, objItemValue) {
	//判断是否存在 ,不存在则追加     
	if (!jsSelectIsExitItem(objSelect, objItemValue)) {
		var varItem = new Option(objItemText, objItemValue);
		objSelect.options.add(varItem);
	}
}

//向select选项中 加入一个Item        
function jsAddItem4HasTitleToSelect(objSelect, objItemText, objItemValue , isHasTitle) {
	if(!isHasTitle){
		jsAddItemToSelect(objSelect, objItemText, objItemValue)
	}else{
		if (!jsSelectIsExitItem(objSelect, objItemValue)) {
		var varItem = new Option(objItemText, objItemValue);
		varItem.setAttribute("title" , objItemText) ;
		objSelect.options.add(varItem);
	}
	}
}

//从select选项中 删除一个Item        
function jsRemoveItemFromSelect(objSelect, objItemValue) {
	//判断是否存在        
	if (jsSelectIsExitItem(objSelect, objItemValue)) {
		for ( var i = 0; i < objSelect.options.length; i++) {
			if (objSelect.options[i].value == objItemValue) {
				objSelect.options.remove(i);
				break;
			}
		}
	}
}

//删除select中选中的项    
function jsRemoveSelectedItemFromSelect(objSelect) {
	var length = objSelect.options.length - 1;
	for ( var i = length; i >= 0; i--) {
		if (objSelect[i].selected == true) {
			objSelect.options[i] = null;
		}
	}
}

//修改select选项中 value="paraValue"的text为"paraText"        
function jsUpdateItemToSelect(objSelect, objItemText, objItemValue) {
	//判断是否存在        
	if (jsSelectIsExitItem(objSelect, objItemValue)) {
		for ( var i = 0; i < objSelect.options.length; i++) {
			if (objSelect.options[i].value == objItemValue) {
				objSelect.options[i].text = objItemText;
				break;
			}
		}
	} else {
		alert("该select中 不存在该项");
	}
}

//设置select中text="paraText"的第一个Item为选中        
function jsSelectItemByValue(objSelect, objItemText) {
	//判断是否存在        
	var isExit = false;
	for ( var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].text == objItemText) {
			objSelect.options[i].selected = true;
			isExit = true;
			break;
		}
	}
}

function jsSelectItemByValue1(objSelect, objItemValue) {
	//判断是否存在        
	var isExit = false;
	for ( var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == objItemValue) {
			objSelect.options[i].selected = true;
			isExit = true;
			break;
		}
	}
}

//清空select的项    
function clearSelectOptions(objSelect) {
	for ( var i = objSelect.options.length - 1; i >= 0; i--) {
		objSelect.options.remove(i);
	}
}

function portalActiveTab(url, title) {
	setActiveTab(url, title)
}

function setActiveTab(url, title) {
	if (top.contentPanel.items.length > 1) {
		top.contentPanel.remove(top.contentPanel.items.length - 1, true);
	}
	var tab = top.contentPanel
			.add( {
				title : title,
				html : '<iframe height="100%" width="100%" scrolling="auto" src="' + url + '"></iframe>',
				closable : true
			});
	top.contentPanel.setActiveTab(1);
}

function updateSelectId(idName) {
	var cbs = document.getElementsByName(idName);
	if (cbs == undefined) {
		alert("请选择一条记录！");
		return null;
	}
	var id = null;
	var selectNum = 0;
	for ( var i = 0; i < cbs.length; i++) {
		if (cbs[i].checked) {
			selectNum = selectNum + 1;
			id = cbs[i].value;
		}
	}
	if (selectNum == 0) {
		alert("请选择一条记录！");
		return null;
	}
	if (selectNum > 1) {
		alert("只能选择一条记录!");
		return null;
	}
	return id;
}

function selectUser(nameId, valueId, emplid, x, y, e) {
	if (y == undefined || y == null && (e != undefined && e != null)) {
		y = getY(e) - 280;
	}
	var width = 800;
	var height = 450;
	win = showWindow('选择用户', width, height, path
			+ '/um/user!listSelectedUser.action?nameId=' + nameId + '&valueId='
			+ valueId + '&emplid=' + emplid, false, true, x, y);
	win.show();
}

function selectOrg(nameId, valueId, x, y, fun) {
	if (y == undefined || y == null) {
		y = getY(e) - 280;
	}
	var width = 800;
	var height = 450;
	var url = path + '/um/org!listSelectedOrg.action?nameId=' + nameId + '&valueId='+ valueId;
	if (fun != undefined && fun != null) {
		url = url + "&fun=" + fun;
	}
	win = showWindow('选择店组', width, height, url, false, true, x, y);
	win.show();
}

/**
 * 根据用户系统号选择用户
 */
function selectUserForCode(codeId, emplid, x, y, e, fun) {
	if (y == undefined || y == null && (e != undefined && e != null)) {
		//y = getY(e) - 180;
		y = e.clientTop ;
	}
	var width = 760;
	var height = 400;
	var url = path + '/um/user!listSelectedUser.action?codeId=' + codeId
			+ '&emplid=' + emplid;
	if (y != undefined && y != null) {
		url = url + "&fun=" + fun;
	}
	win = showWindow('选择用户', width, height, url, false, true, x, y);
	win.show();
}

function getY(e) {
	if (e == undefined || e == null) {
		return;
	}
	y = e.offsetTop;
	while (e = e.offsetParent) {
		y += e.offsetTop;
	}

	return y;
}



/**
 * 将js中Date类型的对象转换为string类型
 * @param formatter
 * @returns 日期字符串
 * 
 * Created By 胡五音
 */
Date.prototype.format = function(formatter)
{
    if(!formatter || formatter == "")
    {
        formatter = "yyyy-MM-dd";
    }
    var year = this.getYear().toString();
    var month = (this.getMonth() + 1).toString();
    var day = this.getDate().toString();
    var yearMarker = formatter.replace(/[^y|Y]/g,'');
    if(yearMarker.length == 2)
    {
        year = year.substring(2,4);
    }    
    var monthMarker = formatter.replace(/[^m|M]/g,'');
    if(monthMarker.length > 1)
    {
        if(month.length == 1) 
        {
            month = "0" + month;
        }
    }    
    var dayMarker = formatter.replace(/[^d]/g,'');
    if(dayMarker.length > 1)
    {
        if(day.length == 1) 
        {
            day = "0" + day;
        }
    }    
    return formatter.replace(yearMarker,year).replace(monthMarker,month).replace(dayMarker,day);    
  };
  
  /**
   * 将js中String类型的日期格式化
   * @param formatter
   * @returns String日期字符串
   * 
   * Created By 胡五音
   */
  String.prototype.Formatter = function(formatter){
	  if(!formatter || formatter == "")
	    {
	        formatter = "yyyy-MM-dd";
	    }
	  	var value = this.replace(/-/g, '/') ;
	  	value = value.substring(0 , value.indexOf(" ", 8)) ;
	  	var date = new Date(value) ;
	  	var year = date.getYear().toString();
	    var month = (date.getMonth() + 1).toString();
	    var day = date.getDate().toString();
	    var yearMarker = formatter.replace(/[^y|Y]/g,'');
	    if(yearMarker.length == 2)
	    {
	        year = year.substring(2,4);
	    }    
	    var monthMarker = formatter.replace(/[^m|M]/g,'');
	    if(monthMarker.length > 1)
	    {
	        if(month.length == 1) 
	        {
	            month = "0" + month;
	        }
	    }    
	    var dayMarker = formatter.replace(/[^d]/g,'');
	    if(dayMarker.length > 1)
	    {
	        if(day.length == 1) 
	        {
	            day = "0" + day;
	        }
	    }    
	    return formatter.replace(yearMarker,year).replace(monthMarker,month).replace(dayMarker,day);
  }
  
//为js取出的String对象截取尾部的空格
  String.prototype.Trim = function() {
  	return this.replace(/(^\s*)|(\s*$)/g, "");
  };
  
  function updateNewAction(actionUrl, parameterName, idName) {
	var _form = getForm();
	var paymentStatus = null;
	var isArreas = null;
	if (null != parameterName) {
		var cbs = document.getElementsByName(idName);
		if (cbs == undefined) {
			alert("请选择一条记录！");
			return;
		}
		var id = null;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				id = cbs[i].value.split("-hwy_acewill_hwy-")[0];
				paymentStatus = cbs[i].value.split("-hwy_acewill_hwy-").length > 1 ? cbs[i].value.split("-hwy_acewill_hwy-")[1] : 0 ;
				if(actionUrl.indexOf("hireContract!modifyPayment.action") >= 0 ){
					paymentIsAudit = cbs[i].value.split("-hwy_acewill_hwy-").length > 2 ? cbs[i].value.split("-hwy_acewill_hwy-")[2] : -1 ;
					if(paymentIsAudit != 2){
						if(paymentStatus == 'shz' || paymentStatus == 'yfk'){
							alert("该付款计划已有付款单不能修改！");
							return;
						}
					}
				}
				if(actionUrl.indexOf("rentContract!modifyPayment.action") >= 0 && (paymentStatus >0) ){
					alert("该收款计划已有收款单不能修改！");
					return;
				}
			}
		}
		if (selectNum == 0) {
			alert("请选择一条记录！");
			return;
		}
		if (selectNum > 1) {
			alert("只能选择一条记录!");
			return;
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = id;
		_form.appendChild(hiddenInput);
	}
	_form.action = actionUrl;
	_form.submit();
}
  
  /**
   * 将数字的金额转换为中文的金额
   */
  function transform(dValue, maxDec){
  	  // 验证输入金额数值或数值字符串：
	  dValue = dValue.toString().replace(/,/g, ""); 
	  dValue = dValue.replace(/^0+/, ""); // 金额数值转字符、移除逗号、移除前导零
	  if (dValue == "") { return "零元整"; } // （错误：金额为空！）
	  else if (isNaN(dValue)) { return "错误：金额不是合法的数值！"; } 
	   
	  var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
	  var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
	  if (dValue.length > 1)
	  {
	  if (dValue.indexOf('-') == 0) { dValue = dValue.replace("-", ""); minus = "负"; } // 处理负数符号“-”
	  if (dValue.indexOf('+') == 0) { dValue = dValue.replace("+", ""); } // 处理前导正数符号“+”（无实际意义）
	  }
	   
	  // 变量定义：
	  var vInt = ""; var vDec = ""; // 字符串：金额的整数部分、小数部分
	  var resAIW; // 字符串：要输出的结果
	  var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
	  var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
	  var zeroCount; // 零计数
	  var i, p, d; // 循环因子；前一位数字；当前位数字。
	  var quotient, modulus; // 整数部分计算用：商数、模数。
	
	  // 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
	  var NoneDecLen = (typeof(maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
	  parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
	  if (parts.length > 1) 
	  {
	  vInt = parts[0]; vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分
	   
	  if(NoneDecLen) { maxDec = vDec.length > 5 ? 5 : vDec.length; } // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
	  var rDec = Number("0." + vDec);  
	  rDec *= Math.pow(10, maxDec); rDec = Math.round(Math.abs(rDec)); rDec /= Math.pow(10, maxDec); // 小数四舍五入
	  var aIntDec = rDec.toString().split('.');
	  if(Number(aIntDec[0]) == 1) { vInt = (Number(vInt) + 1).toString(); } // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
	  if(aIntDec.length > 1) { vDec = aIntDec[1]; } else { vDec = ""; }
	  }
	  else { vInt = dValue; vDec = ""; if(NoneDecLen) { maxDec = 0; } } 
	  if(vInt.length > 44) { return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！"; }
	   
	  // 准备各字符数组 Prepare the characters corresponding to the digits:
	  digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
	  radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
	  bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰" ,"沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
	  decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
	   
	  resAIW = ""; // 开始处理
	   
	  // 处理整数部分（如果有）
	  if (Number(vInt) > 0) 
	  {
	  zeroCount = 0;
	  for (i = 0; i < vInt.length; i++) 
	  {
	  p = vInt.length - i - 1; d = vInt.substr(i, 1); quotient = p / 4; modulus = p % 4;
	  if (d == "0") { zeroCount++; }
	  else 
	  {
	  if (zeroCount > 0) { resAIW += digits[0]; }
	  zeroCount = 0; resAIW += digits[Number(d)] + radices[modulus];
	  }
	  if (modulus == 0 && zeroCount < 4) { resAIW += bigRadices[quotient]; }
	  }
	  resAIW += "元";
	  }
	   
	  // 处理小数部分（如果有）
	  for (i = 0; i < vDec.length; i++) { d = vDec.substr(i, 1); if (d != "0") { resAIW += digits[Number(d)] + decimals[i]; } }
	   
	  // 处理结果
	  if (resAIW == "") { resAIW = "零" + "元"; } // 零元
	  if (vDec == "") { resAIW += "整"; } // ...元整
	  resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负......元角分/整
	  return resAIW;
  }
  
  /**
   * JS日期加减函数
   * days为正，向后累加；为负，向前递减
   * author sky
   * create 2012-07-01
   */
  function dayChange(date , days){
  	//var d = new Date(date) ;
  	var d = newDate(date) ;
  	d.setDate(d.getDate() + days);
  	var m = d.getMonth() + 1 + "" ;
  	m = m.length == 1 ? "0" + m : m ;
  	var _temp_d = (d.getDate() + "").length == 1 ? "0" + d.getDate() : d.getDate() ;
  	var _d = d.getFullYear() + '-' + m + '-' + _temp_d ;
  	return _d ;   
  }
  
  /**
   * 重构new Date()方法，IE7不支持new Date(参数)的日期创建方法
   * author sky
   * create 2012-07-01
   */
  function newDate(str) { 
	str = str.split('-'); 
	var date = new Date(); 
	date.setUTCFullYear(str[0], str[1] - 1, str[2]); 
	date.setUTCHours(0, 0, 0, 0); 
	return date; 
  }
  
  /**
   * 将全角数字转换为半角数字
   * author sky
   * create 2012-07-05
   */
  function convertFullWidthNumber2HalfWidth(value){
  	var strNumber = "１２３４５６７８９０。" ;	//定义全角字符池
  	var numNumber = "1234567890." ;		//定义半角字符池
  	for(var i = 0 ; i < value.length ; i ++){
  		var desPoint = value.substring(i , i + 1 ) ;
  		var desLocation = strNumber.indexOf(desPoint) ;
  		if(desLocation > -1){
  			var newPoint = numNumber.substring(desLocation , desLocation + 1) ;
  			value = value.replace(desPoint , newPoint) ;
  		}
  	}
  	return value ;
  }
  
  
