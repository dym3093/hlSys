<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_hpin_form.jsp"%>
<style type="text/css">
.testDiv{
	margin:10px 0px;
}

</style>
<script type="text/javascript">
	Ext.onReady(function(){   
		v = new valider({form:'theform',vbtn:'method.save'});
	},this);
	
	function disFields(id){
		$(id).disabled = true;
		//$(id).hide();
	}
</script>

<form id="theform">
	
	<fieldset>
		<legend>${hpin:a2u('文件上传的显示')}</legend>
		<web:attachment idList="" idField="${sheetPageName}accessories" appCode="newBusiness" alt="allowBlank:false"/>
	</fieldset>
	<fieldset>
		<legend>${hpin:a2u('文件上传的显示')}</legend>
		<iframe id='IFrame2' name='IFrame2' class='uploadframe' frameborder='0' scrolling='auto' 
		src="/hpin/accessories/pages/upload.jsp?appId=newBusiness&filelist=&idField=mainDemandAccessories">	
		</iframe>
	</fieldset>
	
	<div id="test">
	<input type="button" value="submit" id="method.save" class="btn"/>
	</div>
</form>

<%@ include file="/common/footer_hpin.jsp"%>
