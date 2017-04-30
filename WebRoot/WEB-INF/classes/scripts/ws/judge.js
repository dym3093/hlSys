function createSubmit(e){
if(e=="jk"){
$(".branchRule",navTab.getCurrentPanel()).val($(".ifFullTime",navTab.getCurrentPanel()).val());
if($(".ifFullTime",navTab.getCurrentPanel()).val()=="Y"){
	$(".operateName",navTab.getCurrentPanel()).val("全职医生");
	$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
	$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
}else if($(".ifFullTime",navTab.getCurrentPanel()).val()=="N"){
	$(".operateName",navTab.getCurrentPanel()).val("兼职医生");
	$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name2",navTab.getCurrentPanel()).val());
	$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id2",navTab.getCurrentPanel()).val());
	}
	else {
		$(".operateName",navTab.getCurrentPanel()).val("会员需求受理");						
	}
	}else if(e=="gh"){
	var mainOrderType = $(".mainOrderType",navTab.getCurrentPanel()).val();
	var ifNeedDoctor = $(".ifNeedDoctor",navTab.getCurrentPanel()).val();
	if(mainOrderType=="A"&&ifNeedDoctor=="N"){
		$(".operateName",navTab.getCurrentPanel()).val("直接预约挂号");
		$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
		$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
		$(".branchRule",navTab.getCurrentPanel()).val(mainOrderType);
	}else if(mainOrderType=="B"&&ifNeedDoctor=="N"){
		$(".operateName",navTab.getCurrentPanel()).val("指定预约");
		$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name2",navTab.getCurrentPanel()).val());
		$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id2",navTab.getCurrentPanel()).val());
		$(".branchRule",navTab.getCurrentPanel()).val(mainOrderType);
		}else if(ifNeedDoctor=="C"){
			$(".operateName",navTab.getCurrentPanel()).val("医生分诊");
			$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name3",navTab.getCurrentPanel()).val());
			$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id3",navTab.getCurrentPanel()).val());
			$(".branchRule",navTab.getCurrentPanel()).val(ifNeedDoctor);
		}else {
			$(".operateName",navTab.getCurrentPanel()).val("会员需求受理");						
		}
		}else if(e=="cz"){
			var ifDoctor = $(".ifDoctor",navTab.getCurrentPanel()).val();
			if(ifDoctor=="1010601"){
				$(".operateName",navTab.getCurrentPanel()).val("需要医生提供指导");
				$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name2",navTab.getCurrentPanel()).val());
				$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id2",navTab.getCurrentPanel()).val());
				$(".branchRule",navTab.getCurrentPanel()).val("Y");
			}else if(ifDoctor=="1010602"){
					$(".operateName",navTab.getCurrentPanel()).val("直接启动服务");
					$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
					$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
					$(".branchRule",navTab.getCurrentPanel()).val("N");
				}else {
					$(".operateName",navTab.getCurrentPanel()).val("会员需求受理");
				}
				}else if(e=="zy"){
	var transType = $(".transType",navTab.getCurrentPanel()).val();
	if(transType=="103080101"){
		$(".operateName",navTab.getCurrentPanel()).val("保险");
		$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
		$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
		$(".branchRule",navTab.getCurrentPanel()).val("A");
	}else if(transType=="103080102"){
			$(".operateName",navTab.getCurrentPanel()).val("遗体");
			$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name2",navTab.getCurrentPanel()).val());
			$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id2",navTab.getCurrentPanel()).val());
			$(".branchRule",navTab.getCurrentPanel()).val("B");
	}else if(transType=="103080103"){
		$(".operateName",navTab.getCurrentPanel()).val("个案");
		$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name3",navTab.getCurrentPanel()).val());
		$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id3",navTab.getCurrentPanel()).val());
		$(".branchRule",navTab.getCurrentPanel()).val("C");
		}else {
			$(".operateName",navTab.getCurrentPanel()).val("会员需求受理");
		}
		}
	else if(e=="rw"){
	$(".parti_1",navTab.getCurrentPanel()).val("[{'id':'"+$(".toOrgId",navTab.getCurrentPanel()).val()+"','name':'"+$(".toOrgName",navTab.getCurrentPanel()).val()+"','typeCode':'person'}]");
	}else{
	}
}
function saveDraft(e){
	$(".actDid",navTab.getCurrentPanel()).val("draft");
	$(".opName",navTab.getCurrentPanel()).val("\u4fdd\u5b58\u8349\u7a3f");
	$(".required",navTab.getCurrentPanel()).removeClass("required");
if(e=="df"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsAdvance_saveDraft.action");
}else if(e=="jk"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsHealthAdvice_saveDraft.action");
}else if(e=="gh"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsRegistration_saveDraft.action");
}else if(e=="jj"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsEmerServer_saveDraft.action");
}else if(e=="cz"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsDoctorVisit_saveDraft.action");
}else if(e=="hb"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsUnderwriting_saveDraft.action");
}else if(e=="zy"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsTransport_saveDraft.action");
}else if(e=="zx"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsSpecial_saveDraft.action");
}else if(e=="rw"){
	$("form.createForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsCommonTask_saveDraft.action");
}
}
function doAbolished(e){
if(e=="df"){
	$("form.pageForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsAdvance_doAbolished.action");
}else if(e=="jk"){
	$("form.pageForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsHealthAdvice_doAbolished.action");
}else if(e=="rw"){
	$("form.pageForm",navTab.getCurrentPanel()).attr("action","../worksheet/WsCommonTask_doAbolished.action");
}
}
function onDfSubmit(e){
var opT = $("select.operateType",navTab.getCurrentPanel()).val();
$(".saveOpType",navTab.getCurrentPanel()).val(opT);
if(e!=null&&e!="cancel"&&e!="visit"&&e!="close"){
if(e=="ifAdvance"||e=="advanceType"||e=="ifHelp"||e=="follow"||e=="costCheck"){
$(".branchRule",navTab.getCurrentPanel()).val(opT);
}
}
if(e=="ifAdvance"){
		if(opT=="PAY"){
				$(".operateName",navTab.getCurrentPanel()).val("确认垫付");
			}else if(opT=="HELP"){
				$(".operateName",navTab.getCurrentPanel()).val("协助理赔");
				}
			else{$(".operateName",navTab.getCurrentPanel()).val("撤单");
			}
	}
if(e=="advanceType"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("垫付形式为担保函");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("垫付形式为现金");
			}
			else {$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
	}
if(e=="ifHelp"){
		if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("需要协助理赔");
		}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("不需要协助") ;
		}
		else {$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}															
		}
if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
if(e=="sendLetter"||e=="cash"){
		if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("垫付完毕");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("撤销垫付");
			}
		else {$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
		}
	}
if(e=="follow"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("服务跟踪");
		}else if(opT=="LETTER"||opT=="CASH"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else {$(".operateName",navTab.getCurrentPanel()).val("调整垫付额度或垫付形式");
		}
	}
if(e=="account"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("结算");
		}
		else{ $(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
		}	
	}
if(e=="collect"){
	if(opT=="ONE"||opT=="TWO"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else {
			$(".operateName",navTab.getCurrentPanel()).val("收集理赔单据并进行分类");
		}
	}
if(e=="costCheck"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("先行结算");
		}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("提交理赔材料");
		}
		else{ $(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
		}
	}
if(e=="firstAccount"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("先行结算完成");
		}
		else {$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}	
	}
if(e=="commit"){
	if(opT=="ONE"||opT=="TWO"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else {$(".operateName",navTab.getCurrentPanel()).val("确认提交");
		}
	}
if(e=="returnPay"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("完成回款");
		}
		else {$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
if(e=="clubAccount"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("跟会员结算完成");
		}
		else{ $(".operateName",navTab.getCurrentPanel()).val("驳回上一级");		
		}
	}
if(e=="check"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("审核通过");
		}
		else{ $(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
		}
	}
if(e=="visit"){
	if(opT=="Y"){
		$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){
		$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}
		else {
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
if(e=="close"){
	if(opT=="reject"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{
		$(".operateName",navTab.getCurrentPanel()).val("关单");
		}
	}
navTab.closeCurrentTab("menu_402881e63e21f4c9013e21fa53330005");
}
function onJkSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="partTime"||e=="fullTime"){
	$(".branchRule",navTab.getCurrentPanel()).val(opT);
	}
	if(e=="partTime"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("协调员记录兼职医生咨询结果");
			}else $(".operateName",navTab.getCurrentPanel()).val("撤单");		
		}
	if(e=="fullTime"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("全职医生提供咨询并记录结果");
			}else $(".operateName",navTab.getCurrentPanel()).val("撤单");		
		}
	if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
	if(e=="visit"){
	
			if(opT=="Y"){ 
				$(".operateName",navTab.getCurrentPanel()).val("需要回访");
			}else if(opT=="N"){ 
				$(".operateName",navTab.getCurrentPanel()).val("无需回访");
			}else{
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881e63e39cce9013e3a0481b20004");
}
function onGHSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="dis"){
	$(".branchRule",navTab.getCurrentPanel()).val(opT);
	}
	if(e=="triage"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("分诊完毕");
			}else{}		
		}
	if(e=="dis"){
		if(opT=="A"){
			$(".operateName",navTab.getCurrentPanel()).val("挂号分配至坐席预约挂号");
			}else if(opT=="B"){
				$(".operateName",navTab.getCurrentPanel()).val("指定挂号");	
			}else if(opT=="C"){
				$(".operateName",navTab.getCurrentPanel()).val("撤单");	
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="order"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("预约完毕");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("提交未完成挂号");	
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="appoint"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("预约完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="notice"){
		if(opT=="A"){
			$(".operateName",navTab.getCurrentPanel()).val("重新挂号至坐席预约");
			}else if(opT=="B"){
				$(".operateName",navTab.getCurrentPanel()).val("重新挂号至指定预约");	
			}else if(opT=="C"){
				$(".operateName",navTab.getCurrentPanel()).val("通知完毕");
			}else if(opT=="D"){
				$(".operateName",navTab.getCurrentPanel()).val("下一步结算");
			}
		}
	if(e=="settle"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("结算完成");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="visit"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
			}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
			}
			else {
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f18fb18013f19182b900004");
}
function onJJSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="groupBy"){
		var ifPartDoctor =  $(".ifPartDoctor",navTab.getCurrentPanel()).val();
		if($(".serveType",navTab.getCurrentPanel()).val()=="103050101"){
			if(ifPartDoctor=="1010602"){
			$(".branchRule",navTab.getCurrentPanel()).val("N");
			$(".operateName",navTab.getCurrentPanel()).val("全职医生提供急救指导");
			$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name2",navTab.getCurrentPanel()).val());
			$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id2",navTab.getCurrentPanel()).val());
			$("select.operateType",navTab.getCurrentPanel()).val("N");
			}else {
				$(".branchRule",navTab.getCurrentPanel()).val("Y");
				$(".operateName",navTab.getCurrentPanel()).val("兼职医生提供急救指导");
				$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
				$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
				$("select.operateType",navTab.getCurrentPanel()).val("Y");
			}
		}else{
			$(".branchRule",navTab.getCurrentPanel()).val("Y");
			$(".operateName",navTab.getCurrentPanel()).val("完成受理");
			$(".realRoleName",navTab.getCurrentPanel()).val($(".temp_name1",navTab.getCurrentPanel()).val());
			$(".realRoleId",navTab.getCurrentPanel()).val($(".temp_id1",navTab.getCurrentPanel()).val());
			$("select.operateType",navTab.getCurrentPanel()).val("Y");
		}
	}
	if(e=="guide"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("启动服务");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("撤单");	
			}
		}
	if(e=="follow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("跟踪完毕");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("再次受理");	
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="visit"){
		
		if(opT=="Y"){ 
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){ 
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}else{
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f1dd69f013f1ddf62370005");
}
function onCZSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="startServer"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("确认启动");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("撤单");	
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="guide"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("启动服务");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("撤单");	
			}
		}
	if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="THREE"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
	if(e=="follow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("跟踪完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("返回启动服务");	
			}
		}
	if(e=="settle"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("结算完成");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="check"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("审核完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="visit"){
		
		if(opT=="Y"){ 
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){ 
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}else{
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f1dd69f013f1de5d9b2000d");
}
function onHBSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="plan"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("安排完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="notice"){
		if(opT=="A"){
			$(".operateName",navTab.getCurrentPanel()).val("通知完毕派单");
			}else if(opT=="B"){
			$(".operateName",navTab.getCurrentPanel()).val("通知变更");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("撤单");
			}
		}
	if(e=="sendSheet"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("派单完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="serveEnd"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("服务结束");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="oneday"){
		if(opT=="A"){
			$(".operateName",navTab.getCurrentPanel()).val("提前一天通知完毕");
			}else if(opT=="B"){
			$(".operateName",navTab.getCurrentPanel()).val("通知变更");
			}else if(opT=="C"){
				$(".operateName",navTab.getCurrentPanel()).val("撤单");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="onehour"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("前一小时确认完毕");
		}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("撤单");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="THREE"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
	if(e=="follow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("跟踪完毕");
			}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("返回服务安排");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="settle"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("结算完成");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="collect"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("收集理赔单据并进行分类");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="costCheck"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("费用审核完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="check"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("审核完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="visit"){
		
		if(opT=="Y"){ 
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){ 
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}else{
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f1dd69f013f1deab8dd0015");
}
function onZYSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="duty"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("医生判断符合要求");
			$(".parti_1",navTab.getCurrentPanel()).val(pati);
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("医生判断不符合撤单");	
			}
		}
	if(e=="feed"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("反馈");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="ifImpl"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("医生确认需要实施");
		}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("不需要实施撤单");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="start"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("坐席启动服务");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="lpStart"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("理赔启动服务");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="lpfollow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("理赔服务跟踪");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="follow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("坐席服务跟踪");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="settle"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("完成结算");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="THREE"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
	if(e=="collect"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("需要提交理赔资料");
		}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("不需要提交理赔资料");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="commit"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("提交材料");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="return"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("理赔回款完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="check"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("审核完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="visit"){
		
		if(opT=="Y"){ 
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){ 
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}else{
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f1dd69f013f1def59a7001d");
}
function onZXSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="commit4s"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("收集资料完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="ifFit"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("符合条件");
			}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("不符合条件撤单");
			}else if(opT=="CHANGE"){
				$(".operateName",navTab.getCurrentPanel()).val("服务变更");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("补充资料");
			}
		}
	if(e=="change"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("服务变更");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="provider"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("确认服务提供商");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="connect"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("跟会员沟通完毕");
			}else if(opT=="N"){
			$(".operateName",navTab.getCurrentPanel()).val("会员取消服务");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="plan"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("服务安排完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="serveEnd"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("服务结束");
		}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("服务变更");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="oneday"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("提前一天通知完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="onehour"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("前一小时确认完毕");
		}else if(opT=="N"){
				$(".operateName",navTab.getCurrentPanel()).val("撤单");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="cancel"){
		if(opT=="ONE"||opT=="TWO"||opT=="THREE"||opT=="OTHER"){
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
		else{ 
			$(".operateName",navTab.getCurrentPanel()).val("撤单");
		}
	}
	if(e=="follow"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("跟踪完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");	
			}
		}
	if(e=="settle"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("结算完成");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="collect"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("收集理赔单据并进行分类");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="check"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("审核完毕");
			}else{
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="visit"){
		
		if(opT=="Y"){ 
			$(".operateName",navTab.getCurrentPanel()).val("需要回访");
		}else if(opT=="N"){ 
			$(".operateName",navTab.getCurrentPanel()).val("无需回访");
		}else{
		$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
		}
	}
	if(e=="collectData"){
		if(opT=="Y"){
			$(".operateName",navTab.getCurrentPanel()).val("收集材料完毕");
			}
			else{ 
				$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
		}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881ee3f1dd69f013f1df2dbef0025");
}
function onRwSubmit(e){
	var opT = $("select.operateType",navTab.getCurrentPanel()).val();
	$(".saveOpType",navTab.getCurrentPanel()).val(opT);
	if(e=="dealer"){
	var pati = "[{'id':'"+$(".toOrgId",navTab.getCurrentPanel()).val()+"','name':'"+$(".toOrgName",navTab.getCurrentPanel()).val()+"','typeCode':'person'}]";
		if(opT=="trans"){
		$(".operateName",navTab.getCurrentPanel()).val("移交");
		$(".parti_2",navTab.getCurrentPanel()).val(pati);
		}else{
			$(".operateName",navTab.getCurrentPanel()).val("处理完成");
			$(".parti_1",navTab.getCurrentPanel()).val(pati);
		}
	}
	if(e=="close"){
		if(opT=="reject"){
			$(".operateName",navTab.getCurrentPanel()).val("驳回上一级");
			}
			else{
			$(".operateName",navTab.getCurrentPanel()).val("关单");
			}
		}
	navTab.closeCurrentTab("menu_402881f03e21837a013e2186dca90006");
}
function chooseType(e){
		var opT = $("select.operateType",navTab.getCurrentPanel()).val();
			if(e=="double"){
				if(opT=="NO"||opT=="reject"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
					
				}else if(opT=="HELP"||opT=="N"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					
					}
				else{
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();	
					
				}
			}
			if(e=="ifHelp"){
				if(opT=="reject"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
					
				}else if(opT=="N"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					
					}
				else{
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();	
					
				}
			}
			if(e=="cancel"){
				if(opT=="cancel"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();		
				}
			}
			if(e=="single"){
				if(opT=="reject"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();	
					
				}else{
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					
				}
			}
			if(e=="follow"||e=="order"){
				if(opT=="Y"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					
				}else if(opT=="N"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					}
				else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();	
					
				}
			}
			if(e=="collect"||e=="commit"||e=="partTime"||e=="fullTime"||e=="appoint"){
				if(opT=="Y"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos2",navTab.getCurrentPanel()).show();	
					$(".chos1",navTab.getCurrentPanel()).hide();
					
				}
			}
			if(e=="close"){
				if(opT=="reject"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();	
				}else{
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
				}
			}
			if(e=="visit"){
				if(opT=="reject"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
				}else if(opT=="Y"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}
			}
			if(e=="jkvisit"){
				if(opT=="Y"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}else if(opT=="N"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
				}
			}
			if(e=="dealer"){
				if(opT=="trans"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();	
					
				}else{
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					
				}
			}
			if(e=="dis"){
				if(opT=="A"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="B"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="C"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).show();
					
				}
			}
			if(e=="notice"){
				if(opT=="A"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="B"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="C"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).show();
				}
			}
			if(e=="hbnotice"){
				if(opT=="A"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}else if(opT=="B"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
				}
			}
			if(e=="ifFit"){
				if(opT=="Y"){
					$(".chos1",navTab.getCurrentPanel()).show();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="N"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).show();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else if(opT=="CHANGE"){
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).show();
					$(".chos4",navTab.getCurrentPanel()).hide();
				}else{
					$(".chos1",navTab.getCurrentPanel()).hide();
					$(".chos2",navTab.getCurrentPanel()).hide();
					$(".chos3",navTab.getCurrentPanel()).hide();
					$(".chos4",navTab.getCurrentPanel()).show();
				}
			}
		}
function chosObj() {
	if($(".remitType",navTab.getCurrentPanel()).val()=="103010302"){
		$("#to_his",navTab.getCurrentPanel()).hide();
		$("#to_exc",navTab.getCurrentPanel()).show();				

	}else{
		$("#to_his",navTab.getCurrentPanel()).show();
		$("#to_exc",navTab.getCurrentPanel()).hide();	
		}
}
function choosDoctorType() {
	if($(".ifDoctor",navTab.getCurrentPanel()).val()=="1010602"||$(".ifDoctor",navTab.getCurrentPanel()).val()==""){
	if($(".serverType",navTab.getCurrentPanel()).val()=="103060101"){
		$(".ch_doctor",navTab.getCurrentPanel()).show();
		$(".en_doctor",navTab.getCurrentPanel()).hide();
		
	}else{
		$(".ch_doctor",navTab.getCurrentPanel()).hide();
		$(".en_doctor",navTab.getCurrentPanel()).show();	
		}
	$(".tostart",navTab.getCurrentPanel()).show();
	$(".toguide",navTab.getCurrentPanel()).hide();
	}else{
		$(".ch_doctor",navTab.getCurrentPanel()).hide();
		$(".en_doctor",navTab.getCurrentPanel()).hide();
		$(".tostart",navTab.getCurrentPanel()).hide();
		$(".toguide",navTab.getCurrentPanel()).show();
	}
}
function needOtherData(){ 
	if($("input.ifotherdata",navTab.getCurrentPanel()).attr("checked")=="checked"){
		$(".othDatadiv",navTab.getCurrentPanel()).show();
	}else {
		$(".othDatadiv",navTab.getCurrentPanel()).hide();
		$(".otherData",navTab.getCurrentPanel()).val("");
	}

}
function ifothercost(){ 
	if($(".ifOtherCost",navTab.getCurrentPanel()).val()=="1010601"){
		$(".intoaccount",navTab.getCurrentPanel()).show();
	}else {
		$(".intoaccount",navTab.getCurrentPanel()).hide();
	}

}