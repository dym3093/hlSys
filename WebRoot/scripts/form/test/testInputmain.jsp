<%@ include file="/common/taglibs.jsp"%>
<%@ include file="testBaseinputmainhtml.jsp"%>
	<!-- accessories -->
	<fieldset>
		<legend>
			accessories
		</legend>
		<web:attachment idList="" idField="accessories" appCode="newBusiness"/>
	</fieldset>
		<!-- sheet info -->
		
	<fieldset>
		<legend>${hpin:a2u('基础数据')}</legend>
		
    <div class="x-form-column">
	   <div class="x-form-item">
	      <label>mainPvmName</label>
		     <div class="x-form-element">
		      <input type="text" name="mainPvmName" id="mainPvmName" 
		         value="${sheetMain.mainPvmName}" alt="allowBlank:false"/>
			 </div>
		 </div>
		 
		<div class="x-form-item">		 
			<label>mainSpecialType</label>
		     <div class="x-form-element">
		     <web:comboBox name="mainSpecialType"  alt="allowBlank:false"
		        id="mainSpecialType" initDicId="1020101"  defaultValue=""/>
		   </div>
		</div>
	</div>
	
	 <div class="x-form-column">
	 <div class="x-form-item">
	   <label>mainPvmType</label>
		    <div class="x-form-element">
		      <web:comboBox name="${sheetPageName}mainPvmType" alt="allowBlank:false"
		         id="${sheetPageName}mainPvmType" initDicId="10110" defaultValue=""/>
			</div>
	 </div>
	 </div>
	 <div class="x-form-item">
		   <label>mainPvmContent</label>
		   <div class="x-form-element">
		     <textarea name="${sheetPageName}mainPvmContent" id="mainPvmContent" ></textarea>
		   </div>
	 </div>
	</fieldset>
	<fieldset>
	  <legend>
			operateRoleName
	 </legend>
	<div class="x-form-item">
	 <label>	 
	   evaluateManager
	</label>
	  <input type="hidden" name="roleId" value="95" />
	</div>
	</fieldset>
	<!-- xbox -->
	<c:if test="${status!=1}">
	<fieldset>
	  <legend>
			toOperateRoleName
	 </legend>
	 
	  <div class="form-xbox" id="treeFields">
	    <input class="btn" type="button" name="trees" id="trees" value="selectRole" onclick="javascript:roleTree.resetRoot('${path}/xtree.do?method=subroleFromDept&systemId=3&roleId=96');">
        <div class="x-form-item"><label>mainPvmContent</label>
		  <div class="x-form-element">
        	<input type="text" name="showd" id="showd" alt="allowBlank:false"> 
		  </div>
		</div>
		<div class="x-form-element">
        	<input type="radio" name="showd2"> 
		</div>
		<div class="x-form-element">
        	<input type="checkbox" name="showd3" id="showd3"> 
		</div>
		<div class="x-form-element">
        	<select id="testsel" alt="allowBlank:false"><option value="">${hpin:a2u('请选择')}</option><option value="2">ss</option></select>
		</div>
		<div class="x-form-element">
        	<web:comboBox name="${sheetPageName}mainPvmTypesss" alt="allowBlank:false"
		         id="${sheetPageName}mainPvmTypesss" initDicId="10110" defaultValue="${sheetMain.mainPvmType}"/>
		</div>
		<input type="hidden" name="${sheetPageName}dealPerformer" id="${sheetPageName}dealPerformer" />
	  </div>
	  
	</fieldset>
	</c:if>
	<br/>
	<div id="otherpage"></div>
	<input type="button" id="test2" value="test2"/>
	<script type="text/javascript">
		Sheet.regBtns([
			{id:'test2',onclick:function(){alert('test2')}}
		]);
	</script>