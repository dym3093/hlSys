(function($) {
$.tags = function(data) {
}
  
$.tags.settings = {
	tag_menu_off : {color: "#006666", textDecoration: "none"},
	tag_menu_on : {color: "#FF6600", textDecoration: "underline"},
	tags_html  : '<div class="tabs">\
					<div class="tabsHeader">\
						<div class="tabsHeaderContent">\
							<ul id="tagMenu">\
							</ul>\
						</div>\
					</div>\
					<table id="frameTable" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#C6E3DE"> \
					   <tr> \
					   <td id="frameContainer"></td> \
					   </tr> \
					</table> \
				</div>'
		
};

$.fn.tags = function(tagConfig){
	if(tagConfig){
	
		$(this).append($.tags.settings.tags_html);
		
		var activeTag = tagConfig.activeTag ? tagConfig.activeTag : 0;
		var offset = tagConfig.marginTop ? tagConfig.marginTop : 0;
		var client = document.compatMode == 'BackCompat' ? document.body : document.documentElement;
		var offsetHeight = client.clientHeight; 
		$("#frameContainer").css("height",(offsetHeight-offset)+"px");
		
		var frameTable = document.getElementById("frameTable");
		frameTable.width = client.clientWidth;
		
		var tags = tagConfig.tags;
		$.tags.settings.currentTab = tags[activeTag].id;
		
		for(var i=0;i<tags.length;i++){
			var tag = tags[i];
			var color = tag.disabled?"#CECCBE":"#006666";
			$("#tagMenu").append('<li id="'+tag.id+'"><a href="javascript:;"><span>'+tag.name+'</span></a></li>');
			
			$("#"+tag.id).attr("id",tag.id)
			.attr("status",tag.disabled+"")
			.attr("url",tag.url)
			.click(function(){
				if($(this).attr("status")=="true"){
					return;
				}
				$("#"+$.tags.settings.currentTab).removeClass("selected");
				$(this).addClass("selected");
				var tagid = $(this).attr("id");
				var iframeObj = $("#"+tagid+"_frame");
				
				$("#"+$.tags.settings.currentTab+"_frame").hide();
				if(iframeObj.size()==0){
					$("#frameContainer").append('<iframe id="'+$(this).attr('id')+'_frame" name="iframe" frameborder="0" src="'+$(this).attr('url')+'" style="border:0px;width:100%;height:100%"></iframe>');
				}else{
					iframeObj.show();
				}
				$.tags.settings.currentTab = tagid;
			});
		}
		
		$("#"+$.tags.settings.currentTab).click();
		$("#"+$.tags.settings.currentTab).addClass("selected");
		
		$(window).resize(function(){
			var client = document.compatMode == 'BackCompat' ? document.body : document.documentElement;
			offsetHeight = client.clientHeight; 
			$("#frameContainer").css("height",(offsetHeight-offset)+"px");
			var frameTable = document.getElementById("frameTable");
			frameTable.width = client.clientWidth;
		});
		
	}
}
})(jQuery);

