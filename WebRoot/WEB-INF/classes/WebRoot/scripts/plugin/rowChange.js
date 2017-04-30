
var rowChange = {

	 /** 增加行 **/
	 addGrid:function(obj,tdHtmlArray){
		     	var aa = $(obj).parents("table");
		     	var htmlStr = "<tr>";
		     	if(tdHtmlArray&&tdHtmlArray.length>0){
		     	   for(var i=0;i<tdHtmlArray.length;i++){
		     	    htmlStr += "<td><nobr>"+tdHtml+"</nobr></td>";
		     	   }
		     	}
		     	htmlStr +="</tr>";
		     	$(aa).append(htmlStr);
		     },

	/**  删除行  **/	     
 	delGrid:function(obj){
		     	$(obj).parents("tr").remove();
		     }
		     
		     
}