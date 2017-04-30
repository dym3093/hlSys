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
<p>${hpin:a2u('在表单中隐藏/显示某几个表单域')}</p>
test1 = ${param.test1}<br/>
test2 = ${param.test2}<br/>
test3 = ${param.test3}<br/>
test4 = ${param.test4}<br/>
<br/>
<form id="theform">
	
	<fieldset>
		<legend>${hpin:a2u('v.dis 单独屏蔽/显示某个表单域')}</legend>
		<div class="x-form-item"><label>test1</label>
			<div class="x-form-element">
			<input type="text" name="test1" id="test1" value="" alt="allowBlank:false"/>
			</div>	
		</div>
		<div class="testDiv">
			<input type="button" value="v.dis('test1',true)" onclick="v.dis('test1',true)" class="btn"/>
			<input type="button" value="v.dis('test1',false)" onclick="v.dis('test1',false)" class="btn"/>		
		</div>
	</fieldset>
	
	<fieldset>
		<legend>${hpin:a2u('v.disable/v.enable 屏蔽/显示某几个表单域
		或v.disabelArea/v.enableArea屏蔽/显示一个区域')}</legend>
	<div id="testarea">
		<div class="x-form-item"><label>test2</label>
			<div class="x-form-element">
			<input type="text" name="test2" id="test2" value="" alt="allowBlank:false"/>
			</div>	
		</div>
		<div class="x-form-item"><label>test3</label>
			<div class="x-form-element">
			<select name="test3" id="test3"><option>select</option></select>
			</div>	
		</div>
		<div class="x-form-item"><label>test4</label>
			<div class="x-form-element">
			<textarea name="test4" id="test4" alt="allowBlank:false"></textarea>
			</div>	
		</div>
	</div>
		<div class="testDiv">
			<input type="button" value="v.disable('test2','test3','test4')" 
				onclick="v.disable('test2','test3','test4')" class="btn"/>
			<input type="button" value="v.hide('test2','test3','test4')" 
				onclick="v.hide('test2','test3','test4')" class="btn"/>
			<input type="button" value="v.enable('test2','test3','test4')" 
				onclick="v.enable('test2','test3','test4')" class="btn"/>
			<br/><br/>
			<input type="button" value="v.disableArea('testarea')" 
				onclick="v.disableArea('testarea')" class="btn"/>
			<input type="button" value="v.disableArea('testarea',true)" 
				onclick="v.disableArea('testarea',true)" class="btn"/>
			<input type="button" value="v.enableArea('testarea')" 
				onclick="v.enableArea('testarea')" class="btn"/>
		</div>
	</fieldset>
	
	<div id="test">
	<input type="button" value="submit" id="method.save" class="btn"/>
	</div>
</form>

<%@ include file="/common/footer_hpin.jsp"%>
