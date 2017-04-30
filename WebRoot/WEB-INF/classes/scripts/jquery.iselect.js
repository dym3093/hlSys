/*
 * Select for Input (for jQuery)
 * 可输入的下拉列表
 * version: 1.0 (2012/05/07)
 * @requires jQuery v1.2 or later
 *
 * Usage:
 *  
 *  jQuery(document).ready(function() {
 *    jQuery('#selectId').iselect() 
 *  })
 *
 *  
 *
 */
(function($) {
	$.iselect = {};
	$.iselect.csses = {
						defaultWidth:193,
						divContainer:{position:'relative',height:'21px',display:'inline-block',margin:'5px 0 5px 5px',border:'none',padding:'0'},
						selectCss:{position:'absolute',top:'0',left:'0',height:'21px',padding:'0',margin:'0'},
						inputCss:{position:'absolute',left:'0',top:'0',height:'17px',lineHeight:'17px',padding:'0 0 0 2px',margin:'0',fontSize:'12px'},
						inputTipCss:{position:'absolute',height:'100px',overflow:'auto',border:'1px #A2BAC0 solid',fontSize:'13px',display:'none',backgroundColor:'#FFFFFF'},
						tipItemCss:{paddingLeft:'3px',cursor:'default',lineHeight:'19px',textAlign:'left'}
					};
	function getTop(e){
		var offset=e.offsetTop;
		if(e.offsetParent!=null) offset+=getTop(e.offsetParent);
		return offset-e.scrollTop;//元素距离文档顶部高度-文档被卷去的高度
	} 
	function getLeft(e){
		var offset=e.offsetLeft;
		if(e.offsetParent!=null) offset+=getLeft(e.offsetParent);
		return offset;
	} 
	$.iselect.showTips = function(selectId,inputId,inputTipId){
		var value = $.trim($("#"+inputId).val());
		$("#"+inputTipId).empty();
		$('#'+inputTipId).css("left",(getLeft($("#"+inputId).parent().get(0)))+"px")
		 				 .css("top",(getTop($("#"+inputId).parent().get(0))+20)+"px");
		$("#"+selectId).find("option").each(function(index,item){
				if(value!='' && item.text.indexOf(value)!=-1){
					$("#"+inputTipId).append("<div oValue='"+item.value+"'>"+item.text+"</div>");
				}else if(value==''||value=='请选择'){
					$("#"+inputTipId).append("<div oValue='"+item.value+"'>"+item.text+"</div>");
				}
		});
		if($("#"+inputTipId).children().length>0){
			$("#"+inputTipId).show();	
		}
		$("#"+inputTipId).children().mouseover(function(){
				$(this).css({backgroundColor:"#316AC5",color:"#FFFFFF"});
		}).mouseout(function(){
				$(this).css({backgroundColor:"#FFFFFF",color:"#000000"});
		}).click(function(){
				$(this).parent().hide();
				if($(this).attr("oValue")!=$("#"+selectId).val()){
					$("#"+selectId).val($(this).attr("oValue")).change();
				}
				//$("#"+inputId).val($(this).html());
		}).css($.iselect.csses.tipItemCss);
	};
	
	function loadNextData(parent){
		var ref = parent.attr("ref");
		var refUrl = parent.attr("refUrl");
		if(ref && refUrl && $("#"+ref).get(0)){
			refUrl = refUrl+parent.val();
			$.post(refUrl,{},
					  function(data,status){
							var refValue = $("#"+ref).val();
							$("#"+ref).empty();
							$("#"+ref).append("<option value=\"\">请选择</option>");
						    $.each( data, function(i, n){
					    	  $("#"+ref).append("<option value="+n.id+">"+n.text+"</option>");
					    	});
						    
						    $("#"+ref).val(refValue);
						    
						    if($("#"+ref).get(0).options && $("#"+ref).get(0).options.length>0){
						    	$("#"+ref+"_input").val($("#"+ref).get(0).options[$("#"+ref).get(0).selectedIndex].text);
							}else{
								$("#"+ref+"_input").val("");
							}
					  },"json");
//			modify by yjj 20130913 多级级联菜单第三级以后无法显示信息
			if($("#"+ref).attr("ref")!=""){
				loadNextData($("#"+ref));
			}
		}
	}
	$.fn.iselect = function(){
		$(this).each(function(){
			if($(this).attr("iselect")!="1"){
				$(this).attr("iselect","1");
				var value = $(this).val();
				var randomNum = Math.floor(Math.random()*10000);
				if(this.id){
					if($.pdialog.getCurrent() && $("select[ref="+this.id+"]",$.pdialog.getCurrent()).size()>0){
						$("select[ref="+this.id+"]",$.pdialog.getCurrent()).attr("ref",(this.id+randomNum));
					}else{
						$("select[ref="+this.id+"]",navTab.getCurrentPanel()).attr("ref",(this.id+randomNum));
					}
				}
				this.id = this.id+randomNum;
				var selectId = this.id;
				var divId = selectId + '_div';
				var inputId = selectId + '_input';
				var inputTipId = selectId + '_inputTip';
				var width = $(this).attr("width")?$(this).attr("width"):$.iselect.csses.defaultWidth;
				var inputCls = $(this).attr("class");
				$(this).removeClass("required");
				var loadUrl = $("#"+selectId).attr("loadUrl");
				$(this).wrap('<span id="'+divId+'"></span>');
				if(loadUrl){
					$.post(loadUrl,{},
						  function(data,status){
							$("#"+selectId).empty();
							$("#"+selectId).append("<option value=\"\">请选择</option>");
						    $.each( data, function(i, n){
						    	$("#"+selectId).append("<option value="+n.id+">"+n.text+"</option>");
					    	});
						    $("#"+selectId).val(value);
						    
						    $('#'+divId).append('<input type="text" id="'+inputId+'" name="'+inputId+'_name" class="'+inputCls+'" value="'+$("#"+selectId).get(0).options[$("#"+selectId).get(0).selectedIndex].text+'" autocomplete="off">')
										.css($.iselect.csses.divContainer)
										.css("width",width+"px");
						  //为文本框添加按键、焦点和点击事件
							$("#"+inputId).keyup(function(){
									$.iselect.showTips(selectId,inputId,inputTipId);
							}).focus(function(){
									$.iselect.showTips(selectId,inputId,inputTipId);
									if(this.value=="请选择"){
										this.value = "";
									}
							}).click(function(event){
									event.stopPropagation();	
							}).css($.iselect.csses.inputCss)
							  .css("width",(width-23)+"px");
							
						    $("#"+selectId).trigger("change");
						  },"json");
				}else{
					if(this.options && this.options.length>0){
						$('#'+divId).append('<input type="text" id="'+inputId+'" name="'+inputId+'_name" class="'+inputCls+'" value="'+this.options[this.selectedIndex].text+'" autocomplete="off">')
						.css($.iselect.csses.divContainer)
						.css("width",width+"px");
					}else{
						$('#'+divId).append('<input type="text" id="'+inputId+'" name="'+inputId+'_name" class="'+inputCls+'" value="" autocomplete="off">')
						.css($.iselect.csses.divContainer)
						.css("width",width+"px");
					}
				}
				$('body').append('<div id="'+inputTipId+'"></div>');
	
				$('#'+inputTipId).css($.iselect.csses.inputTipCss)
								 .css("width",(width-2)+"px")
								 .attr("rel","iselect");
				
				var changeFun = document.getElementById(selectId).onchange + "";
				//为下拉列表中添加change和click事件
				$("#"+selectId).removeAttr("onchange").change(function(){
					$("#"+inputId).val(this.options[this.selectedIndex].text);
					changeFun = changeFun.replace(/[\n\{\}]/g,"").replace(/function onchange\((event)?\)/,"");
					eval(changeFun);
					
					loadNextData($(this));
				}).css($.iselect.csses.selectCss).css("width",width+"px")
					.attr("rel","iselect");
				
				//为文本框添加按键、焦点和点击事件
				$("#"+inputId).keyup(function(){
						$.iselect.showTips(selectId,inputId,inputTipId);
				}).focus(function(){
						$.iselect.showTips(selectId,inputId,inputTipId);
						if(this.value=="请选择"){
							this.value = "";
						}
				}).click(function(event){
						event.stopPropagation();	
				}).css($.iselect.csses.inputCss)
				  .css("width",(width-22)+"px");
			}
		});
	};
	
	//点击其他地方隐藏下拉提示
	$(document).click(function(){
			$('div[rel=iselect]').hide();
			$('select[rel=iselect]').each(function(){
				if(this.options[this.selectedIndex]){
					$("#"+this.id+"_input").val(this.options[this.selectedIndex].text);
				}else{
					$("#"+this.id+"_input").val("");
				}
			});
	});
})(jQuery);

