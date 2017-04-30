/**
 * 登录操作js
 *	
 */

//为js取出的String对象截取尾部的空格
  String.prototype.Trim = function() {
  	return this.replace(/(^\s*)|(\s*$)/g, "");
  };


function submitForm() {
	var j_username = document.getElementById("j_username");
	var j_password = document.getElementById("j_password");
	if (j_username.value == '') {
		alert('用户名不能为空！');
		j_username.focus();
		return false;
	}
	if (j_password.value == '') {
		j_password.focus();
		alert('密码不能为空！');
		return false;
	}
	document.getElementById("_form").submit();	
}

function enterSubmit(_form){
	if(event.keyCode==13){ 
      return submitForm(_form);
    } 
}
