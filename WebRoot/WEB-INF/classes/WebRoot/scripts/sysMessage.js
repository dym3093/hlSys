setInterval("getSysMessageForStauts()",300000);
//setInterval("getSysMessageForStautsRemind()"360000);
//setInterval("getSysMessageForStautsImpotr()",390000);
var sysRemindStatus_ok=false;
var str_mess="";
var sys_me="资产管理系统";
//var functionRemindStatus_ok=false;
//var impotrRemindStatus_ok=false;
function getSysMessageForStauts() {
	if(!sysRemindStatus_ok ){		
		Ext.Ajax.request({
	 		url : "../sysmessage/SysMessage!getSysMessageForStauts.action" , 
	 		success : function(response , options){
	 			var responseArray = Ext.util.JSON.decode(response.responseText) ;
	 				//salert(sysRemindStatus_ok+"==="+responseArray.sysRemindStatus+"==="+responseArray.other1);
					if ( responseArray.sysRemindStatus =="yes" && responseArray.other1 ==0  ) {
						//message=message+responseArray.other5;
						makeHtml("系统重启提醒",responseArray.other5,false,"",sys_me);
						sysRemindStatus_ok=true;				
					}
					
					if (   responseArray.functionRemindStatus =="yes" && responseArray.other2 ==0  ) {
						//message=message+responseArray.other6;
						makeHtml("新功能发布提醒",responseArray.other6,false,"",sys_me);
						//startShow(responseArray.other6);
						//functionRemindStatus_ok=true;
						sysRemindStatus_ok=true;
					}	
					
					if (responseArray.impotrRemindStatus =="yes" && responseArray.other3 ==0  ) {
						//message=message+responseArray.other7;
						makeHtml("重要事宜及时发布提醒",responseArray.other7,false,"",sys_me);
						//startShow(responseArray.other7);
						//impotrRemindStatus_ok=true;
						sysRemindStatus_ok=true;
					}		
					if(str_mess!=""){
						startShow();					
					}
	 		  }
	 	});
	}
}

//function getSysMessageForStautsRemind() {
//	if(!functionRemindStatus_ok){		
//		Ext.Ajax.request({
//	 		url : "../sysmessage/SysMessage!getSysMessageForStauts.action" , 
//	 		success : function(response , options){
//	 			var responseArray = Ext.util.JSON.decode(response.responseText) ;
//	 				//alert(functionRemindStatus_ok+"==="+responseArray.functionRemindStatus+"==="+responseArray.other2);
//					if (   responseArray.functionRemindStatus =="yes" && responseArray.other2 ==0  ) {
//						startShow(responseArray.other6);
//						functionRemindStatus_ok=true;
//						//alert("ok_ajax?2"+responseArray.other2);
//					}			
//	 		}
//	 	});
//	}
//
//}
//
//function getSysMessageForStautsImpotr() {
//	if(!impotrRemindStatus_ok){
//		Ext.Ajax.request({
//	 		url : "../sysmessage/SysMessage!getSysMessageForStauts.action" , 
//	 		success : function(response , options){
//	 			var responseArray = Ext.util.JSON.decode(response.responseText) ;
//	 			 	//alert(impotrRemindStatus_ok+"==="+responseArray.impotrRemindStatus+"==="+responseArray.other3);
//					if (responseArray.impotrRemindStatus =="yes" && responseArray.other3 ==0  ) {
//						startShow(responseArray.other7);
//						impotrRemindStatus_ok=true;
//						//alert("ok_ajax?3"+responseArray.other3);
//					}				
//	 			}
//	 		});
//		}
//
//}

function CLASS_MSN_MESSAGE(id,width,height,caption,title,message,target,action){ 
    this.id     = id; 
    this.title  = title; 
    this.caption= caption; 
    this.message= message; 
    this.target = target; 
    this.action = action; 
    this.width    = width?width:200; 
    this.height = height?height:120; 
    this.timeout= 500000000; 
    this.speed    = 20;
    this.step    = 1;
    this.right    = screen.width -1; 
    this.bottom = screen.height;
    this.left    = this.right - this.width;
    this.top    = this.bottom - this.height;
    this.timer    = 0;
    this.pause    = false;
    this.close    = false;
    this.autoHide    = true;
} 
 
/*
*    隐藏消息方法 
*/ 
CLASS_MSN_MESSAGE.prototype.hide = function(){ 
    if(this.onunload()){ 
        var offset  = this.height>this.bottom-this.top?this.height:this.bottom-this.top;
        var me  = this; 
        if(this.timer>0){  
            window.clearInterval(me.timer); 
        } 
        var fun = function(){ 
            if(me.pause==false||me.close){
                var x  = me.left;
                var y  = 0;
                var width = me.width;
                var height = 0;
                if(me.offset>0){
                    height = me.offset;
                }
    
                y  = me.bottom - height;
    
                if(y>=me.bottom){
                    window.clearInterval(me.timer); 
                    me.Pop.hide(); 
                } else {
                    me.offset = me.offset - me.step; 
                }
                me.Pop.show(x,y,width,height);   
            }            
        } 
        this.timer = window.setInterval(fun,this.speed)     
    } 
} 
 
/* 
*    消息卸载事件，可以重写 
*/ 
CLASS_MSN_MESSAGE.prototype.onunload = function() { 
    return true; 
} 
/*
*    消息命令事件，要实现自己的连接，请重写它 
* 
*/ 
CLASS_MSN_MESSAGE.prototype.oncommand = function(){ 
    //this.close = true;
    this.hide(); 
 //window.open("http://www.newxing.com");
  
}

function aoncommand(action){ 
	alert(action);
    //this.close = true;
    //this.hide(); 
   window.open("http://"+action); 
}
/**//* 
*    消息显示方法 
*/ 
CLASS_MSN_MESSAGE.prototype.show = function(){ 
    var oPopup = window.createPopup(); //IE5.5+ 
    this.Pop = oPopup; 
 
    var w = this.width; 
    var h = this.height; 
 
    var str = "<DIV style='BORDER-RIGHT: #455690 1px solid; BORDER-TOP: #a6b4cf 1px solid; Z-INDEX: 99999; LEFT: 0px; BORDER-LEFT: #a6b4cf 1px solid; WIDTH: " + w + "px; BORDER-BOTTOM: #455690 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: " + h + "px; BACKGROUND-COLOR: #c9d3f3'>" 
        str += "<TABLE style='BORDER-TOP: #ffffff 1px solid; BORDER-LEFT: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor=#cfdef4 border=0>" 
        str += "<TR>" 
        str += "<TD style='FONT-SIZE: 12px;COLOR: #0f2c8c' width=30 height=24></TD>" 
        str += "<TD style='PADDING-LEFT: 4px; FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #1f336b; PADDING-TOP: 4px' vAlign=center width='100%'>" + this.caption + "</TD>" 
        str += "<TD style='PADDING-RIGHT: 2px; PADDING-TOP: 2px' vAlign=center align=right width=19>" 
        str += "<SPAN title=关闭 style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: hand; COLOR: red; MARGIN-RIGHT: 4px' id='btSysClose' >×</SPAN></TD>" 
        str += "</TR>" 
        str += "<TR>" 
        str += "<TD style='PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px' colSpan=3 height=" + (h-28) + ">" 
        str += "<DIV style='BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%'>" + this.title + "<BR><BR>" 
        str +=str_mess
        str += "</DIV>" 
        str += "</TD>" 
        str += "</TR>" 
        str += "</TABLE>" 
        str += "</DIV>" 
 
    oPopup.document.body.innerHTML = str;  
    this.offset  = 0;
    var me  = this; 
    oPopup.document.body.onmouseover = function(){me.pause=true;}
    oPopup.document.body.onmouseout = function(){me.pause=false;}
    var fun = function(){ 
        var x  = me.left;
        var y  = 0;
        var width    = me.width;
        var height    = me.height;
            if(me.offset>me.height){
                height = me.height;
            } else {
                height = me.offset;
            }
        y  = me.bottom - me.offset;
        if(y<=me.top){
            me.timeout--;
            if(me.timeout==0){
                window.clearInterval(me.timer); 
                if(me.autoHide){
                    me.hide();
                }
            }
        } else {
            me.offset = me.offset + me.step;
        }
        me.Pop.show(x,y,width,height);   
    } 
 
    this.timer = window.setInterval(fun,this.speed)     
    var btClose = oPopup.document.getElementById("btSysClose"); 
    btClose.onclick = function(){ 
        me.close = true;
        me.hide(); 
    }  
} 
/**//*
** 设置速度方法
**/
CLASS_MSN_MESSAGE.prototype.speed = function(s){
    var t = 20;
    try {
        t = praseInt(s);
    } catch(e){}
    this.speed = t;
}
/**//*
** 设置步长方法
**/
CLASS_MSN_MESSAGE.prototype.step = function(s){
    var t = 1;
    try {
        t = praseInt(s);
    } catch(e){}
    this.step = t;
}
 
CLASS_MSN_MESSAGE.prototype.rect = function(left,right,top,bottom){
    try {
        this.left        = left    !=null?left:this.right-this.width;
        this.right        = right    !=null?right:this.left +this.width;
        this.bottom        = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height;
        this.top        = top    !=null?top:this.bottom - this.height;
    } catch(e){}
}

/**
* title:  什么类型的提醒信息
* mess:   系统消息内容
* aflag:  是消息是否连接的标识 true false
* action: 连接地址
* whatSys: 那个系统--资产系统或者...其他系统
*/
function makeHtml(title,mess,aflag,action,whatSys){
	//消息带连接情况
	if(aflag){
		str_mess+=title+":<DIV style='WORD-BREAK: break-all' align=left><a  href='javascript:aoncommand("+action+");' ><FONT color=#ff0000>" + mess + "</FONT></a><FONT color=#ff0000>("+whatSys+")</FONT></DIV>";
		//alert(str_mess);
	}else{
		str_mess+=title+":<DIV style='WORD-BREAK: break-all' align=left><FONT color=#ff0000>" + mess + "</FONT><FONT color=#ff0000>("+whatSys+")</FONT></DIV>";
	}	
}

//外部调用的方法
function startShow(){
var MSG1 = new CLASS_MSN_MESSAGE("aa",300,300,"消息提示：","",""); 
    MSG1.rect(null,null,null,screen.height-30);
    MSG1.speed    = 30;   
    MSG1.step    = 5;
    MSG1.show(); 
}