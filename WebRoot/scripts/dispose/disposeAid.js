/**
 * 根据是否含税的选择情况调整税率输入按钮的显示与否
 * 
 * @author 胡五音
 */

function showOrHiddenTaxRateInput(){
	var isTax = document.getElementsByName("disposeInfo.isTax") ;
	var showOrNo = -1 ;
	for(var i = 0 ; i < isTax.length ; i ++){
		if(isTax[i].checked == true){
			showOrNo = isTax[i].value ;
		}
	}
	if(showOrNo == 1){
		document.getElementById("tax_rate_tr").style.display = "block" ;
	}else{
		document.getElementById("tax_rate_tr").style.display = "none" ;
	}
}