$(document).ready(function(){
	$("a[target='navTab']").click(function(){
		if( $(this).attr("encode")==undefined){
			var href = $(this).attr("href");
			var param = getInfo(href,"_S=","&");
			for(var i = 0;i<param.length;i++){
				href = href.replace(param[i],encodeURI(encodeURI(param[i])));
			}
			$(this).attr("encode","encode");
			$(this).attr("href",href);
		}
	
	});
});
function getInfo(source,start,end){
    var oReg=new RegExp(start+".*?"+end,"img");
    var oRegStart=new RegExp(start,"g");
    var oRegEnd=new RegExp(end,"g");

    return source.match(oReg)
                 .join("=")
                 .replace(oRegStart,"")
                 .replace(oRegEnd,"")
                 .split("=");
}