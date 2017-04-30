$(document).ready(function(){
	if($(".isMember",navTab.getCurrentPanel()).val()=="1010601"){
		 $(".realMem",navTab.getCurrentPanel()).show();
		  $(".tempMem",navTab.getCurrentPanel()).hide();
		  $(".customName",navTab.getCurrentPanel()).addClass("required");
		  $(".productName",navTab.getCurrentPanel()).addClass("required");
		  $(".productCode",navTab.getCurrentPanel()).addClass("required");
		/*  $(".agentPerson",navTab.getCurrentPanel()).addClass("required");
		  $(".agentContact",navTab.getCurrentPanel()).addClass("required");*/
	  }else {
		  $(".realMem",navTab.getCurrentPanel()).hide();
		  $(".tempMem",navTab.getCurrentPanel()).show();
		  $(".customName",navTab.getCurrentPanel()).removeClass("required");
		  $(".productName",navTab.getCurrentPanel()).removeClass("required");
		  $(".productCode",navTab.getCurrentPanel()).removeClass("required");
	/*	  $(".agentPerson",navTab.getCurrentPanel()).removeClass("required");
		  $(".agentContact",navTab.getCurrentPanel()).removeClass("required");*/
	  }
	
	var getValue = true;
	var customName = "";
	var productName = "";
	var belongUnits = "";
	var productCode = "";
	var memberName = "";
	var memberSex = "";
	var documentNum = "";
	var patientName = "";
	var documentType = "";
	var patientSex = "";
	var isPatient = "";  
	var isWounded = "";
	var woundedName = "";
	var woundedSex = "";
	var woundedContact = "";
	var woundedDocumentType = "";
	var woundedDocumentNum = "";
  $(".isMember",navTab.getCurrentPanel()).click(function(){
	  if(getValue){
		    customName = $(".customName",navTab.getCurrentPanel()).val();
			productName = $(".productName",navTab.getCurrentPanel()).val();
			belongUnits = $(".belongUnits",navTab.getCurrentPanel()).val();
			productCode = $(".productCode",navTab.getCurrentPanel()).val();
			memberName = $(".memberName",navTab.getCurrentPanel()).val();
			memberSex = $(".memberSex",navTab.getCurrentPanel()).val();
			documentNum = $(".documentNum",navTab.getCurrentPanel()).val();
			patientName = $(".patientName",navTab.getCurrentPanel()).val();
			documentType = $(".documentType",navTab.getCurrentPanel()).val();
			patientSex = $(".patientSex",navTab.getCurrentPanel()).val();
			isPatient = $(".isPatient",navTab.getCurrentPanel()).val();  
			isWounded = $(".isWounded",navTab.getCurrentPanel()).val();
			woundedName = $(".woundedName",navTab.getCurrentPanel()).val();
			woundedSex = $(".woundedSex",navTab.getCurrentPanel()).val();
			woundedContact = $(".woundedContact",navTab.getCurrentPanel()).val();
			woundedDocumentType = $(".woundedDocumentType",navTab.getCurrentPanel()).val();
			woundedDocumentNum = $(".woundedDocumentNum",navTab.getCurrentPanel()).val();
			getValue = false;
	  }
	  if($(this,navTab.getCurrentPanel()).val()=="1010601"){
		  $(".realMem",navTab.getCurrentPanel()).show();
		  $(".tempMem",navTab.getCurrentPanel()).hide();
		  $(".customName",navTab.getCurrentPanel()).val(customName);
		  $(".productName",navTab.getCurrentPanel()).val(productName);
		  $(".belongUnits",navTab.getCurrentPanel()).val(belongUnits);
		  $(".productCode",navTab.getCurrentPanel()).val(productCode);
		  $(".memberName",navTab.getCurrentPanel()).val(memberName);
		  $(".memberSex",navTab.getCurrentPanel()).val(memberSex);
		  $(".documentNum",navTab.getCurrentPanel()).val(documentNum);
		  $(".patientName",navTab.getCurrentPanel()).val(patientName);
		  $(".documentType",navTab.getCurrentPanel()).val(documentType);
		  $(".patientSex",navTab.getCurrentPanel()).val(patientSex);
		  $(".isPatient",navTab.getCurrentPanel()).val(isPatient);  
		  $(".isWounded",navTab.getCurrentPanel()).val(isWounded);
		  $(".woundedName",navTab.getCurrentPanel()).val(woundedName);
		  $(".woundedSex",navTab.getCurrentPanel()).val(woundedSex);
		  $(".woundedContact",navTab.getCurrentPanel()).val(woundedContact);
		  $(".woundedDocumentType",navTab.getCurrentPanel()).val(woundedDocumentType);
	      $(".woundedDocumentNum",navTab.getCurrentPanel()).val(woundedDocumentNum);
		  $(".customName",navTab.getCurrentPanel()).addClass("required");
		  $(".productName",navTab.getCurrentPanel()).addClass("required");
		  $(".productCode",navTab.getCurrentPanel()).addClass("required");
		 /* $(".agentPerson",navTab.getCurrentPanel()).addClass("required");
		  $(".agentContact",navTab.getCurrentPanel()).addClass("required");*/
	  }else {
		  $(".realMem",navTab.getCurrentPanel()).hide();
		  $(".tempMem",navTab.getCurrentPanel()).show();
		  $(".customName",navTab.getCurrentPanel()).val("");
		  $(".productName",navTab.getCurrentPanel()).val("");
		  $(".belongUnits",navTab.getCurrentPanel()).val("");
		  $(".productCode",navTab.getCurrentPanel()).val("");
		  $(".memberName",navTab.getCurrentPanel()).val("");
		  $(".memberSex",navTab.getCurrentPanel()).val("");
		  $(".documentNum",navTab.getCurrentPanel()).val("");
		  $(".patientName",navTab.getCurrentPanel()).val("");
		  $(".documentType",navTab.getCurrentPanel()).val("");
		  $(".patientSex",navTab.getCurrentPanel()).val("");
		  $(".isPatient",navTab.getCurrentPanel()).val("");  
		  $(".isWounded",navTab.getCurrentPanel()).val("");
		  $(".woundedName",navTab.getCurrentPanel()).val("");
		  $(".woundedSex",navTab.getCurrentPanel()).val("");
		  $(".woundedContact",navTab.getCurrentPanel()).val("");
		  $(".woundedDocumentType",navTab.getCurrentPanel()).val("");
	      $(".woundedDocumentNum",navTab.getCurrentPanel()).val("");
		  $(".customName",navTab.getCurrentPanel()).removeClass("required");
		  $(".productName",navTab.getCurrentPanel()).removeClass("required");
		  $(".productCode",navTab.getCurrentPanel()).removeClass("required");
		 /* $(".agentPerson",navTab.getCurrentPanel()).removeClass("required");
		  $(".agentContact",navTab.getCurrentPanel()).removeClass("required");*/
	  }
  });
 /* $(".isWounded",navTab.getCurrentPanel()).change(function(){
	  if($(this,navTab.getCurrentPanel()).val()=="1010601"){
		  $(".woundedName",navTab.getCurrentPanel()).val($(".memberName",navTab.getCurrentPanel()).val());
		  $(".woundedSex",navTab.getCurrentPanel()).val($(".memberSex",navTab.getCurrentPanel()).val());
		  $(".woundedContact",navTab.getCurrentPanel()).val($(".memberContact",navTab.getCurrentPanel()).val());
		  $(".woundedDocumentType",navTab.getCurrentPanel()).val($(".documentType",navTab.getCurrentPanel()).val());
		  $(".woundedDocumentNum",navTab.getCurrentPanel()).val($(".documentNum",navTab.getCurrentPanel()).val());
	  }else {
		// $(".woundedName",navTab.getCurrentPanel()).val("");
		//  $(".woundedSex",navTab.getCurrentPanel()).val("");
		//  $(".woundedContact",navTab.getCurrentPanel()).val("");
		//  $(".woundedDocumentType",navTab.getCurrentPanel()).val("");
		//  $(".woundedDocumentNum",navTab.getCurrentPanel()).val("");
	  }
  });
  $(".isPatient",navTab.getCurrentPanel()).change(function(){
	  if($(this,navTab.getCurrentPanel()).val()=="1010601"){
		  $(".patientName",navTab.getCurrentPanel()).val($(".memberName",navTab.getCurrentPanel()).val());
		  $(".patientSex",navTab.getCurrentPanel()).val($(".memberSex",navTab.getCurrentPanel()).val());
		  $(".documentType",navTab.getCurrentPanel()).val($(".memDocumentType",navTab.getCurrentPanel()).val());
		  $(".documentNum",navTab.getCurrentPanel()).val($(".memDocumentNum",navTab.getCurrentPanel()).val());
	  }else {
		// $(".patientName",navTab.getCurrentPanel()).val("");
		//  $(".patientSex",navTab.getCurrentPanel()).val("");
		//  $(".documentType",navTab.getCurrentPanel()).val("");
		//  $(".documentNum",navTab.getCurrentPanel()).val("");
	  }
  });*/
});
function newWounded(){
	 if($(".isWounded",navTab.getCurrentPanel()).val()=="1010601"){
		  $(".woundedName",navTab.getCurrentPanel()).val($(".memberName",navTab.getCurrentPanel()).val());
		  $(".woundedSex",navTab.getCurrentPanel()).val($(".memberSex",navTab.getCurrentPanel()).val());
		  $(".woundedContact",navTab.getCurrentPanel()).val($(".memberContact",navTab.getCurrentPanel()).val());
		  $(".woundedDocumentType",navTab.getCurrentPanel()).val($(".documentType",navTab.getCurrentPanel()).val());
		  $(".woundedDocumentNum",navTab.getCurrentPanel()).val($(".documentNum",navTab.getCurrentPanel()).val());
	  }
	 else {
		  $(".woundedName",navTab.getCurrentPanel()).val("");
		  $(".woundedSex",navTab.getCurrentPanel()).val("");
		  $(".woundedContact",navTab.getCurrentPanel()).val("");
		  $(".woundedDocumentType",navTab.getCurrentPanel()).val("");
	      $(".woundedDocumentNum",navTab.getCurrentPanel()).val("");
	  }
}
function newPatient(){
	 if($(".isPatient",navTab.getCurrentPanel()).val()=="1010601"){
		  $(".patientName",navTab.getCurrentPanel()).val($(".memberName",navTab.getCurrentPanel()).val());
		  $(".patientSex",navTab.getCurrentPanel()).val($(".memberSex",navTab.getCurrentPanel()).val());
		  $(".documentType",navTab.getCurrentPanel()).val($(".memDocumentType",navTab.getCurrentPanel()).val());
		  $(".documentNum",navTab.getCurrentPanel()).val($(".memDocumentNum",navTab.getCurrentPanel()).val());
	  }else {
		  $(".patientName",navTab.getCurrentPanel()).val("");
		  $(".patientSex",navTab.getCurrentPanel()).val("");
		  $(".documentType",navTab.getCurrentPanel()).val("");
		  $(".documentNum",navTab.getCurrentPanel()).val("");
	  }
}