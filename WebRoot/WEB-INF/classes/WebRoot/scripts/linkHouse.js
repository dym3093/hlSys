function selectLinkDistrict(districtId, selectId1, selectId2) {
	if (null == districtId || districtId == "") {
		return;
	}
	ajaxForLinkHouse("district_bizcircle", districtId, selectId1);
}

function selectLinkResblock(resblockId, selectId) {
	if (null == resblockId || resblockId == "") {
		return;
	}
	ajaxForLinkHouse("resblock_build", resblockId, selectId);
}

function selectLinkBizcircle(resblockId, selectId) {
	if (null == resblockId || resblockId == "") {
		return;
	}
	ajaxForLinkHouse("bizcircle_resblock", resblockId, selectId);
}

function selectRegion(regionId, selectId) {
	if (null == regionId || regionId == "") {
		return;
	}
	ajaxForRegion(regionId, selectId);
}

function selectRegionSpecial(regionId, selectId){
	if (null == regionId || regionId == "") {
		return;
	}
	ajaxForRegionSpecial(regionId, selectId) ;
}	
function selectRegionForKongKucun(regionId, selectId){
	if (null == regionId || regionId == "") {
		return;
	}
	ajaxForRegionForKongKucun(regionId, selectId) ;
}

function loadResblockByDistrict(districtId , loupan){
	if(null == districtId || '' == districtId){
		return ;
	}
	ajaxForLinkHouse("district_resblock" , districtId , loupan) ;
}
/**
 * 获取商圈 
 */
function ajaxForLinkHouse(type, id, selectId) {
	var result;
	Ext.Ajax.request( {
		url : path + "/build/build!showJsonForLink.action",
		params : {
			type : type,
			id : id
		},
		method : "POST",
		async : false,
		// 成功时回调
		success : function(response, options) {
			var responseArray = Ext.util.JSON.decode(response.responseText);
			var select = document.getElementById(selectId);
			select.innerHTML = " ";
			jsAddItemToSelect(select, "", "");
			for ( var i = 0; i < responseArray.length; i++) {
				jsAddItemToSelect(select, responseArray[i].name,
						responseArray[i].id);
			}
		}
	});
	return result;
}

/**
 * 获取区域 
 */
function ajaxForRegion(id, selectId) {
	var result;
	Ext.Ajax.request( {
		url : path + "/hire/hireContract!showJsonForArea.action",
		params : {
			id : id
		},
		method : "POST",
		async : false,
		// 成功时回调
		success : function(response, options) {
			var responseArray = Ext.util.JSON.decode(response.responseText);
			var select = document.getElementById(selectId);
			select.innerHTML = " ";
			jsAddItemToSelect(select, "", "");
			for ( var i = 0; i < responseArray.length; i++) {
				jsAddItemToSelect(select, responseArray[i].name,
						responseArray[i].id);
			}
		}
	});
	return result;
}

function ajaxForRegionSpecial(id, selectId) {
	var result;
	Ext.Ajax.request( {
		url : path + "/hire/hireContract!showJsonForAreaSpecial.action",
		params : {
			param : id
		},
		method : "POST",
		async : false,
		// 成功时回调
		success : function(response, options) {
			var responseArray = Ext.util.JSON.decode(response.responseText);
			var select = document.getElementById(selectId);
			select.innerHTML = " ";
			jsAddItemToSelect(select, "", "");
			for ( var i = 0; i < responseArray.length; i++) {
				jsAddItemToSelect(select, responseArray[i].name,
						responseArray[i].name);
			}
		}
	});
	return result;
}


function ajaxForRegionForKongKucun(id, selectId) {
	var result;
	Ext.Ajax.request( {
		url : path + "/hire/hireContract!showJsonForAreaSpecial.action",
		params : {
			param : id
		},
		method : "POST",
		async : false,
		// 成功时回调
		success : function(response, options) {
			var responseArray = Ext.util.JSON.decode(response.responseText);
			var select = document.getElementById(selectId);
			select.innerHTML = " ";
			jsAddItemToSelect(select, "", "");
			for ( var i = 0; i < responseArray.length; i++) {
				jsAddItemToSelect(select, responseArray[i].name,
						responseArray[i].code);
			}
		}
	});
	return result;
}
