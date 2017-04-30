/**
 * @class hpin.sheet  工单相关函数
 * @version 0.1
 */
hpin.Sheet = function(){
	return {
		/**
		 * 设置一个下拉框为ajax页面载入器
		 * @param {String} selectId 下拉框id或下拉框元素
		 * @param {String} divId 载入页面的容器id
		 */
		setPageLoader : function(select,divId){
			var sel = Ext.get(select);
			if(!sel) return;
			sel.on("change",function(e){
				sel.blur();
				document.body.focus(); //for IE6
				
				function onUpdate(){
					window.scrollTo(sel.getX(),sel.getY());				
				}
			
				hpin.util.appendPage(divId,sel.dom.value,true,onUpdate);		
			});
			
		}
	}
}();