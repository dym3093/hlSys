function setValue(contractId, roomId, index) {
	var articleName = null;
	var articleId = null;
	if ($$('articleId' + index) != null) {
		if ($$('articleId' + index) != 'null'
				&& $$('articleId' + index).selectedIndex > 0) {
			var articleId = $$('articleId' + index).options[$$('articleId' + index).selectedIndex].value;
			articleName = $$('articleId' + index).options[$$('articleId' + index).selectedIndex].text;
		}
		if (articleName == "") {
			articleName = null;
		}
	} else {
		articleName = $$('articleName' + index).value;
	}
	var brandName = $$('brandName' + index).value;
	if (brandName == "") {
		brandName = null;
	}
	var amount = $$('amount' + index).value;
	if (amount == "") {
		amount = null;
	}
	var isGood = getRadioValue('isGood' + index);
	if (isGood == "") {
		isGood = null;
	}
	if ($$('isNew' + index) != null) {
		var isNew = $$('isNew' + index).options[$$('isNew' + index).selectedIndex].value;
		// var isNew = getRadioValue('isNew' + index);
		if (isNew == "") {
			isNew = null;
		}
	}
	if ($$('userMode' + index) != null) {
		var userMode = $$('userMode' + index).options[$$('userMode' + index).selectedIndex].value;
		if (userMode == "") {
			userMode = null;
		}
	}
	var remarkObj = $$('remark' + index);
	if (remarkObj != null) {
		var remark = $$('remark' + index).value;
		if (remark == "") {
			remark = null;
		}
	}
	var value = contractId + "-" + roomId + "-" + articleId + "-" + articleName
			+ "-" + brandName + "-" + amount + "-" + isGood + "-" + isNew + "-"
			+ userMode + "-" + remark;
	$$('valueStrList[' + index + ']').value = value;
}

function getRadioValue(RadioName) {
	var obj;
	obj = document.getElementsByName(RadioName);
	if (obj != null) {
		var i;
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				return obj[i].value;
			}
		}
	}
	return "";
}
