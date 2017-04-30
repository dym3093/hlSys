//**************************************
//???????????
//**************************************
function checkboxSwitch_input(checkboxObj,inputObj,defaultInputText){
	if (checkboxObj.checked){
		inputObj.disabled = false;
		inputObj.value = "";
	}
	else {
		inputObj.disabled = true;
		inputObj.value = defaultInputText;
	}
}