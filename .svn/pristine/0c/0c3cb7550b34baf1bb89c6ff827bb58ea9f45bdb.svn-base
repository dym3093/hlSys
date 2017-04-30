/**
 * @desc 业务部->业务组联动下拉框js脚本
 * @author sky
 * @see 联动下拉框标签 
 */
function loadGroupSelect(path , selId , deptId){
	var groupsel = document.getElementById(selId) ;	
	clearSelectOptions(groupsel) ;                                   
	Ext.Ajax.request({                                               
		url : path + '/um/org!loadBusinessGroupByAjax.action' ,                                                     
		params : {                                                     
			deptId : deptId                                              
		},                                                             
		success : function(response , options){                        
			var jsonArray = Ext.util.JSON.decode(response.responseText) ;
			var groupList = jsonArray.data ; 
			jsAddItemToSelect(groupsel , '' , '') ;                          
			if(groupList.length > 0){                                    
				for(var i = 0 ; i < groupList.length ; i ++){              
					var data = groupList[i] ;                                
					jsAddItemToSelect(groupsel , data.name , data.id ) ;     
				}                                                          
			}                                                            
		}                                                              
	})                                                               
}                                                                  