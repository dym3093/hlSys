/*
Ajax 三级省市联动
http://code.ciaoca.cn/
日期：2012-7-18

settings 参数说明
-----
url:省市数据josn文件路径
cycleName:默认省份
activityName:默认城市
dist:默认地区（县）
nodata:无数据状态
required:必选项
------------------------------ */
(function($){
	$.fn.activityNameSelect=function(settings){
		
		if(this.length<1){return;};

		// 默认值
		settings=$.extend({
			url:"../scripts/ws/wsDoctorVisit/activityName.min.js",
			cycleName:null,
			activityName:null,
			dist:null,
			nodata:null,
			required:true
		},settings);

		var box_obj=this;
		var cycleName_obj=box_obj.find(".cycleName");
		var activityName_obj=box_obj.find(".activityName");
		var dist_obj=box_obj.find(".dist");
		var cycleName_val=settings.cycleName;
		var activityName_val=settings.activityName;
		var dist_val=settings.dist;
		var select_prehtml=(settings.required) ? "" : "<option value='0'>所有环节</option>";
		var activityName_json;

		// 赋值市级函数
		var activityNameStart=function(){
			var cycleName_id=cycleName_obj.get(0).selectedIndex;
			if(!settings.required){
				cycleName_id--;
			};
			activityName_obj.empty().attr("disabled",true);
			dist_obj.empty().attr("disabled",true);

			if(cycleName_id<0||typeof(activityName_json.activityNamelist[cycleName_id].c)=="undefined"){
				if(settings.nodata=="none"){
					activityName_obj.css("display","none");
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					activityName_obj.css("visibility","hidden");
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(activityName_json.activityNamelist[cycleName_id].c,function(i,activityName){
				temp_html+="<option value='"+activityName.n+"'>"+activityName.n+"</option>";
			});
			activityName_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			distStart();
		};

		// 赋值地区（县）函数
		var distStart=function(){
			var cycleName_id=cycleName_obj.get(0).selectedIndex;
			var activityName_id=activityName_obj.get(0).selectedIndex;
			if(!settings.required){
				cycleName_id--;
				activityName_id--;
			};
			dist_obj.empty().attr("disabled",true);

			if(cycleName_id<0||activityName_id<0||typeof(activityName_json.activityNamelist[cycleName_id].c[activityName_id].a)=="undefined"){
				if(settings.nodata=="none"){
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(activityName_json.activityNamelist[cycleName_id].c[activityName_id].a,function(i,dist){
				temp_html+="<option value='"+dist.s+"'>"+dist.s+"</option>";
			});
			dist_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
		};

		var init=function(){
			// 遍历赋值省份下拉列表
			temp_html=select_prehtml;
			$.each(activityName_json.activityNamelist,function(i,cycleName){
				if(cycleName.p=="0"){
					temp_html+="<option value='"+cycleName.p+"'>所有环节</option>";
				}else if(cycleName.p=="1"){
					temp_html+="<option value='"+cycleName.p+"'>服务前</option>";	
				}else if(cycleName.p=="2"){
					temp_html+="<option value='"+cycleName.p+"'>服务中</option>";	
				}else if(cycleName.p=="3"){
					temp_html+="<option value='"+cycleName.p+"'>结算中</option>";	
				}else if(cycleName.p=="4"){
					temp_html+="<option value='"+cycleName.p+"'>服务结束</option>";
				}else{
					temp_html+="<option value='"+cycleName.p+"'>"+cycleName.p+"</option>";
				}
			});
			cycleName_obj.html(temp_html);

			// 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function(){
				if(settings.cycleName!=null){
					cycleName_obj.val(settings.cycleName);
					activityNameStart();
					setTimeout(function(){
						if(settings.activityName!=null){
							activityName_obj.val(settings.activityName);
							distStart();
							setTimeout(function(){
								if(settings.dist!=null){
									dist_obj.val(settings.dist);
								};
							},1);
						};
					},1);
				};
			},1);

			// 选择省份时发生事件
			cycleName_obj.bind("change",function(){
				activityNameStart();
			});

			// 选择市级时发生事件
			activityName_obj.bind("change",function(){
				distStart();
			});
		};

		// 设置省市json数据
		if(typeof(settings.url)=="string"){
			$.getJSON(settings.url,function(json){
				activityName_json=json;
				init();
			});
		}else{
			activityName_json=settings.url;
			init();
		};
	};
})(jQuery);