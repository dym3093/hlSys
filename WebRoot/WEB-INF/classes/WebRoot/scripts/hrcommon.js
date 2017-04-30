var dataResult;
/**
* hr公共js
*/
var hr = {

	/**
	  查找单独的经纪人信息
	*/
	getBroker:function(emplid,path){
		var surl = path+"/um/user!getBroker.action?emplid="+emplid+"&date="+new Date().getTime();
		$.ajax({
			url:surl,
			type:"post",
			async:false,
			dataType:'json',
			success:function(data){
			 	try{
					if(data.isExist){
						dataResult = data;
					}else{
						alert("没有相关经纪人信息");
						return;
						}
					}catch(e){
						alert("没有相关经纪人信息");
						return;
					}
			}
		});
	},
	
	/**
	  查找经纪人的所有相关信息
	*/
	getBrokerAllInfo:function(emplid,path){
		var surl = path+"/um/user!getBrokerAllInfo.action?emplid="+emplid+"&date="+new Date().getTime();
		$.ajax({
			url:surl,
			type:"post",
			async:false,
			dataType:'json',
			success:function(data){
			 	try{
					if(data.isExist){
						dataResult = data;
					}else{
						alert("没有相关经纪人信息");
						 return;
						}
					}catch(e){
						alert("没有相关经纪人信息");
						return;
					}
			}
		});
	},
	
	/**
	  查找自如管家的所有相关信息
	*/
	getFunctionerAllInfo:function(emplid,path){
		var surl = path+"/um/user!getFunctionerAllInfo.action?emplid="+emplid+"&date="+new Date().getTime();
		$.ajax({
			url:surl,
			type:"post",
			async:false,
			dataType:'json',
			success:function(data){
			 	try{
					if(data.isExist){
						dataResult = data;
					}else{
						alert("没有相关人信息");
						 return;
						}
					}catch(e){
						alert("没有相关人信息");
						return;
					}
			}
		});
	},
	
	/**
	  查找单个店组信息
	*/
	getOnlyOrg:function(deptid,path){
		var surl = path+"/um/org!getOnlyOrg.action?deptid="+deptid+"&date="+new Date().getTime();
		$.ajax({
			url:surl,
			type:"post",
			async:false,
			dataType:'json',
			success:function(data){
			 	try{
					if(data.isExist){
						dataResult = data;
					}else{
						alert("没有相关部门信息");
						 return;
						}
					}catch(e){
						alert("没有相关部门信息");
						return;
					}
			}
		});
	},
	
	/**
	  查找店组相关所有信息
	*/
	getOrgAllInfo:function(deptid,path){
		var surl = path+"/um/org!getOrgAllInfo.action?deptid="+deptid+"&date="+new Date().getTime();
		$.ajax({
			url:surl,
			type:"post",
			async:false,
			dataType:'json',
			success:function(data){
			 	try{
					if(data.isExist){
						dataResult = data;
					}else{
						alert("没有相关部门信息");
						 return;
						}
					}catch(e){
						alert("没有相关部门信息");
						return;
					}
			}
		});
	}
}
