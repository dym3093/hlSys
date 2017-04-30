<script type="text/javascript">

//dept tree
    	var	deptTreeAction='${path}/xtree.do?method=dept';
    	xbox({
    		btnId:'${sheetPageName}showDept',dlgId:'dlg-dept',
    		treeDataUrl:deptTreeAction,treeRootId:'-1',treeRootText:'Dept',treeChkMode:'single',treeChkType:'dept',
    		showChkFldId:'${sheetPageName}showDept',saveChkFldId:'${sheetPageName}toDeptId'
    	});
 </script>
<%
 String roleId = org.hpin.base.util.StaticMehtod.nullObject2String(request.getAttribute("roleId"));
 System.out.println("roleId===="+roleId);
 %>
		<!-- base info -->
		<fieldset>
        <legend>${hpin:a2u('基本信息')}</legend>
		<table width="640">
		<tr>
		 <td colspan="2">
		   <div class="x-form-item" id="testtr"><label>title</label>
            <div class="x-form-element">
            	<input type="button" name="testbtn" id="testbtn" value="test"/>
                <input type="text" name="title" id="title" value="${sheetMain.title}" alt="width:'500px',allowBlank:false"/>
            </div>
           </div>
		 </td>
		</tr>
		<tr>
		 <td width="320">
		  <div class="x-form-item"><label>${hpin:a2u('名称')}</label>
            <div class="x-form-element">
            <input type="hidden" name="${sheetPageName}id" value="${sheetMain.id}"/>
            <input type="hidden" name="${sheetPageName}sendUserId" value="${sheetMain.sendUserId}"/>
            <input type="hidden" name="${sheetPageName}status" value="${status}"/>
            <input type="hidden" name="${sheetPageName}aiid" value="${taskId}"/>
		    <input type="hidden" name="${sheetPageName}piid" value="${piid}"/>
		    <input type="hidden" name="${sheetPageName}parentSheetName" value="${sheetMain.parentSheetName}"/>
		    <input type="hidden" name="${sheetPageName}parentSheetId" value="${sheetMain.parentSheetId}"/>
		    <input type="hidden" name="activeTemplateId" value="${taskName}"/>
                   <label><web:id2nameDB id="${sheetMain.sendUserId}" beanId="tawSystemUserDao"/>&nbsp;&nbsp;</label>
            </div>
          </div>
		 </td>
		<td>
				<div class="x-form-item"><label>sendDeptName</label>
                  <div class="x-form-element">
                      <input type="hidden" name="${sheetPageName}sendDeptId" value="${sheetMain.sendDeptId}"/>
                        <web:id2nameDB id="${sheetMain.sendDeptId}" beanId="tawSystemDeptDao"/>&nbsp;
                  </div>
               </div>
		</td>
		</tr>
		<tr>
		<td colspan="2">
		        <div class="x-form-item"><label>sendRoleName</label>
                   <div class="x-form-element">
                    <c:if test="${sheetMain.sendRoleId==''}">  
                      <web:roleComboBox name="${sheetPageName}sendRoleId" id="${sheetPageName}sendRoleId" userId="${sheetMain.sendUserId}" roleId="${roleId}" defaultValue="${sheetMain.sendRoleId}"/>     
                    </c:if>
                    <c:if test="${sheetMain.sendRoleId!=''}">
                       <input type="hidden" name="${sheetPageName}sendRoleId" id="${sheetPageName}sendRoleId" value="${sheetMain.sendRoleId}"/>
                       <web:id2nameDB id="${sheetMain.sendRoleId}" beanId="tawSystemSubRoleDao"/>
                     </c:if>
                   </div>
                </div>
		   </td>
		</tr>
		<tr>
		<td >
			<div class="x-form-item"><label>sendContact</label>
            <div class="x-form-element">
                 <input type="text" name="${sheetPageName}sendContact" id="${sheetPageName}sendContact" value="${sheetMain.sendContact}" alt="allowBlank:false"/>
            </div>
        	</div>
		</td>
		<td>
		<div class="x-form-item"><label>toDeptId</label>
			<div class="x-form-element">
				<input type="text"  readonly="readonly" name="${sheetPageName}showDept" id="${sheetPageName}showDept">
				<input type="hidden" name="${sheetPageName}toDeptId" id="${sheetPageName}toDeptId"/>
			</div>
        </div>
		</td>
		</tr>
		<tr>
		<td>
		<div class="x-form-item"><label>acceptLimit</label>
            <div class="x-form-element">
                <input type="text" name="${sheetPageName}acceptLimit" readonly="readonly" value="${hpin:date2String(sheetMain.acceptLimit)}" id="${sheetPageName}acceptLimit" alt="timer:true"/>
            </div>
        </div>
		</td>
		<td>
		        <div class="x-form-item"><label>completeLimit</label>
            <div class="x-form-element">
                <input type="text" name="${sheetPageName}completeLimit" readonly="readonly" id="${sheetPageName}completeLimit" value="${hpin:date2String(sheetMain.completeLimit)}" alt="timer:true"/>
            </div>
        </div>
		</td>
		</tr>
		</table>
    </fieldset>

     <c:if test="${status==1}">
          <fieldset>
            <table width="640">
              <tr>
                 <td width="320">
                   <div class="x-form-item"><label>endUserName</label>
                      <div class="x-form-element">
                       <input type="hidden" name="${sheetPageName}endUserId" value="${endUserId}"/>
                       ${endUserName}&nbsp;
                      </div>
                      </div>
                 </td>
                  <td>
		              <div class="x-form-item"><label>endDeptName</label>
                         <div class="x-form-element">
                            <input type="hidden" name="${sheetPageName}endDeptId" value="${endDeptId}"/>
                           ${endDeptName}&nbsp;
                         </div>
                     </div>
                 </td>
              </tr>
            <tr>
               <td colspan="2">
                  <div class="x-form-item"><label>endRoleName</label>
                    <div class="x-form-element">
                      <input type="hidden" name="${sheetPageName}endRoleId" value="${endRoleId}"/>
                     ${endRoleName}&nbsp;
                    </div>
                  </div>
               </td>
            </tr>
            <tr>
              <td colspan="2">
                  <div class="x-form-item"><label>holdStatisfied</label>
                    <div class="x-form-element">
                      <web:comboBox name="${sheetPageName}holdStatisfied" id="${sheetPageName}holdStatisfied" initDicId="10303" />
                    </div>
                 </div>
              </td>
            </tr>
            <tr>
            <td colspan="2">
             <div class="x-form-item"><label>endResult</label>
              <div class="x-form-element">
                <textarea name="${sheetPageName}endResult" id="${sheetPageName}endResult" " alt="width:'500px',allowBlank:false"></textarea>
              </div>
		  </div>
            </td>
            </tr>
            </table>

       </fieldset>
     </c:if>
     
     <c:if test="${status==-1}">
          <fieldset>
            <legend>holdStatisfied</legend>
            <table width="640">
              <tr>
                 <td width="320">
                   <div class="x-form-item"><label>endUserName</label>
                      <div class="x-form-element">
                       <input type="hidden" name="${sheetPageName}endUserId" value="${endUserId}"/>
                       ${endUserName}&nbsp;
                      </div>
                      </div>
                 </td>
                  <td>
		              <div class="x-form-item"><label>endDeptName</label>
                         <div class="x-form-element">
                            <input type="hidden" name="${sheetPageName}endDeptId" value="${endDeptId}"/>
                           ${sendDeptName}&nbsp;
                         </div>
                     </div>
                 </td>
              </tr>
            <tr>
               <td colspan="2">
                  <div class="x-form-item"><label>endRoleName</label>
                    <div class="x-form-element">
                      <input type="hidden" name="${sheetPageName}endRoleId" value="${endRoleId}"/>
                     ${endRoleName}&nbsp;
                    </div>
                  </div>
               </td>
            </tr>
            <tr>
               <td colspan="2">
                  <div class="x-form-item"><label>cancelReason</label>
                     <div class="x-form-element">               
                       <textarea name="${sheetPageName}endResult" id="${sheetPageName}endResult" " alt="width:'500px',allowBlank:false"></textarea>
                     </div>
		          </div>
               </td>
            </tr>
            </table>
       </fieldset>
     </c:if>