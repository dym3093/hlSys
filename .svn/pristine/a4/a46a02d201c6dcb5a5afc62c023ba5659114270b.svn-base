<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("input[type=checkbox]",navTab.getCurrentPanel()).click(function(){
		var inputobj = $(this).parent().find("input[type=hidden]").eq(0);
		inputobj.val("");
		$(this).parent().find("input[type=checkbox]").each(function() {
            if(!!$(this).attr("checked")){
					inputobj.val(inputobj.val()+","+$(this).val());
			}
        });
	});	
});
</script>
<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/resource/customerLink!saveCustomerLink.action" method="post" novalidate="novalidate">
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>客户联系人信息</span></div>
			<p>
				<label>客户全称：</label>
				<input type="hidden" name="customerLink.customerId" value="${ customer.id }" bringbackname="customer.id"/>
				<input type="text" class="required" bringBackName="customer.customerName" value="${ customer.customerName }" readonly="readonly"/>
				<a class="btnLook" href="${ path }/resource/customer!lookUpCustomerAll.action" lookupGroup="customer">查找带回</a>	
			</p>
			<p>
				<label>姓名：</label>
				<input class="required" type="text" name="customerLink.linkMan" maxlength="50" size="30"/>
			</p>
			<p>
				<label>性别：</label> 
				 <select id="sex" name="customerLink.sex" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=10112">
				 </select>
			</p>
			<p>
				<label>职务：</label>
				<input class="" type="text" name="customerLink.position" maxlength="50" size="30"/>
			</p>
			<p>
				<label>所属部门：</label>
				<input type="text" name="customerLink.department" maxlength="50" size="30"/>
			</p>
			<p>
				<label>座机：</label>
				<input type="text" class="textInput tel" name="customerLink.tel" maxlength="20" size="30" />
			</p>
			<p>
				<label>手机：</label>
				<input type="text" class="textInput phone" name="customerLink.phone" maxlength="20" size="30" />
			</p>
			<p>
				<label>传真：</label>
				<input type="text" class="textInput" name="customerLink.fax" maxlength="30" size="30" />
			</p>
			<p>
				<label>Email：</label>
				<input type="text" class="textInput email" name="customerLink.email" maxlength="80" size="30" />
			</p>
			<p>
				<label>QQ：</label>
				<input type="text" class="textInput digits" name="customerLink.qq" maxlength="20" size="30" />
			</p>
			<p>
				<label>个人账号：</label>
				<input type="text" name="customerLink.account" maxlength="32" size="30"/>
			</p>
			<p>
				<label>户名：</label>
				<input type="text" name="customerLink.personName" maxlength="50" size="30"/>
			</p>
			<p>
				<label>开户行：</label>
				<input type="text" name="customerLink.bank" maxlength="50" size="30"/>
			</p>
			<p class="nowrap">
				<label>与远盟对接职能：</label>
				<input type="checkbox" value="1010301" class="functions">数据对接人
				<input type="checkbox" value="1010302" class="functions">结算对接人
				<input type="checkbox" value="1010303" class="functions">垫付对接人
				<input type="checkbox" value="1010304" class="functions">业务对接人
				<input type="checkbox" value="1010305" class="functions">综合对接人
				<input type="checkbox" value="1010306" class="functions">紧急联系人
				<input type="hidden" name="customerLink.functions">
			</p>
			<p class="nowrap">
			<label>备注：</label>
			<textarea name="customerLink.remark" cols="100" rows="2" maxlength="500"></textarea>
		     </p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
