/**
 * desc: 跨浏览器日历控件
 * author by mios
 * version 1.0
 */

with (document){
  write ("<style type=\"text/css\">");
  write (".cal_table {font-family:arial,helvetica;font-size:12px;border:1px solid #C3DAF9;background:#f7f7f7}");
  write (".cal_border {border:1px solid #3366FF;cursor:pointer;padding:1px}");
  write ("a.cal_todayshow {background-color:#8fcde4;cursor:pointer}");
  write (".cal_date {color:#6d84b4;font-size:12px;text-decoration:none;}");
  write (".cal_selected {color:orange;text-decoration:underline;}");
  write (".cal_disabled {cursor:default;color:#bbbbbb;text-decoration:none;}");
  write (".cal_today {color:red;font-weight:bold;text-decoration:none;}");
  write ("#cal_time {display:none;padding:3px;}");
  write ("#calendar,#selectYear,#selectMonth {z-index:+999;position:absolute;visibility:hidden;}");
  write ("#selectMonth {margin-left:47px;margin-top:1px}");
  write ("#captioncell {padding:2px;font-family:arial; font-size:11px;color:#fff}");
  write (".cal_btn {padding:0px;font-size:11px;margin-bottom:2px}");
  write (".cal_menu {font-family:arial; font-size:11px; border:1px solid #a0a0a0;background:#FFFFDD}");
  write ("</style>");
}
var isShowTime = false;
if(typeof vLangue == 'undefined')
	vLangue = 1
if(typeof vWeekManagement == 'undefined')
	vWeekManagement = 1

var	fixedX 			= -1;							// x position (-1 if to appear below control)
var	fixedY 			= -1;							// y position (-1 if to appear below control)
var startAt 		= parseFloat(vWeekManagement);  // 0 - sunday ; 1 - monday
var showWeekNumber 	= 0;							// 0 - don't show; 1 - show
var showToday 		= 1;							// 0 - don't show; 1 - show
var imgDir 			= hpin.appPath+"/scripts/form/img/";		// directory for images ... e.g. var imgDir="/img/"

var todayString 		= "今天";
var weekString 			= "周";
var scrollLeftMessage 	= "点击选择上个月";
var scrollRightMessage 	= "点击选择下个月";
var selectMonthMessage 	= "点击选择月份";
var selectYearMessage 	= "点击选择年份";
var altCloseCalendar 	= "关闭日历";
var	monthName 			= new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");

dayName = new Array	("日","一","二","三","四","五","六")
arrTemp = dayName.slice(startAt,7)
dayName = arrTemp.concat(dayName.slice(0,startAt))
	
var	crossobj, crossMonthObj, crossYearObj;
var monthSelected, yearSelected, dateSelected;
var omonthSelected, oyearSelected, odateSelected;
var hourSelected, minuteSelected, secondSelected;
var monthConstructed, yearConstructed;
var intervalID1, intervalID2, timeoutID1, timeoutID2;
var ctlToPlaceValue, ctlNow, dateFormat, timeFormat, nStartingYear;

var	bPageLoaded=false;
var ie=/*@cc_on!@*/false;
var	calendardom=document.getElementById;

var	ns4=document.layers;
var	today =	new	Date();
var	dateNow	 = today.getDate();
var	monthNow = today.getMonth();
var	yearNow	 = today.getYear();
var hourNow = today.getHours();
var minNow = today.getMinutes();
var secNow = today.getSeconds();
var	imgsrc = new Array("drop1.gif","drop2.gif","left1.gif","left2.gif","right1.gif","right2.gif");
var	img	= new Array();

var min=today;
var max=new Date(3000,0,1);

var bShow = false;

if (calendardom)
{
	for	(i=0;i<imgsrc.length;i++)
	{
		img[i] = new Image
		img[i].src = imgDir + imgsrc[i]
	}
	var strTemp = "<div onclick='bShow=true' id='calendar'>";
	
	if(ie) strTemp += makeItTop();
	
	strTemp += "<table width="+(176)+" class=\"cal_table\">" +
				"<tr bgcolor='#0000aa'><td>" +
					"<table width='"+(174)+"'>" +
					"<tr><td id='captioncell' align='left'><span id='caption'></span></td>" +
					"<td align='right' id='caption-right'></td>" +
					"</tr></table></td></tr>" +
				"<tr><td style='padding:5px' bgcolor=#ffffff id='content'></td></tr>";
		
	strTemp += "<tr><td align='left'><span id=\"cal_time\">"+makeHour()+""+makeMinute()+""+makeSecond()+
			"</span></td></tr><tr><td align='center'><input type=\"button\" value=\"确定\" onclick=\"closeCalendar();\" class=\"cal_btn\">" +
			"</td></tr>";
		
	if (showToday==1)
	{
		strTemp += "<tr><td id='lblToday'></td></tr>";
	}
		
	strTemp += "</table></div><div id='selectYear'></div><div id='selectMonth'></div>";
	document.write(strTemp);
}

function swapImage(srcImg, destImg){
	if (ie)	{ $(srcImg).setAttribute("src",imgDir + destImg) }
}

function initCalendar()	{
	if (!ns4)
	{
		if (!ie) { yearNow += 1900	}

		crossobj=(calendardom)?$("calendar").style : ie? document.all.calendar : document.calendar
		hideCalendar()

		crossMonthObj=(calendardom)?$("selectMonth").style : ie? document.all.selectMonth	: document.selectMonth

		crossYearObj=(calendardom)?$("selectYear").style : ie? document.all.selectYear : document.selectYear

		monthConstructed=false;
		yearConstructed=false;
		
		sHTML1="<span id='spanLeft'	class='cal_border' " +
				"onmouseover='swapImage(\"changeLeft\",\"left2.gif\");" +
				"this.style.borderColor=\"#88AAFF\";window.status=\""+scrollLeftMessage+"\"' " +
				"onclick='javascript:decMonth()' " +
				"onmouseout='clearInterval(intervalID1);swapImage(\"changeLeft\",\"left1.gif\");" +
				"this.style.borderColor=\"#3366FF\";window.status=\"\"' " +
				"onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartDecMonth()\",500)'	" +
				"onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>" +
				"<img id='changeLeft' src='"+imgDir+"left1.gif'>" +
				"</span>";
				
		sHTML1+="<span id='spanRight' class='cal_border'" +
				"onmouseover='swapImage(\"changeRight\",\"right2.gif\");" +
				"this.style.borderColor=\"#88AAFF\";window.status=\""+scrollRightMessage+"\"' " +
				"onmouseout='clearInterval(intervalID1);swapImage(\"changeRight\",\"right1.gif\");" +
				"this.style.borderColor=\"#3366FF\";window.status=\"\"' " +
				"onclick='incMonth()' " +
				"onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartIncMonth()\",500)'	" +
				"onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>" +
				"<img id='changeRight' src='"+imgDir+"right1.gif'>" +
				"</span>";
				
		sHTML1+="<span id='spanYear' class='cal_border' " +
				"onmouseover='swapImage(\"changeYear\",\"drop2.gif\");" +
				"this.style.borderColor=\"#88AAFF\";window.status=\""+selectYearMessage+"\"' " +
				"onmouseout='swapImage(\"changeYear\",\"drop1.gif\");" +
				"this.style.borderColor=\"#3366FF\";window.status=\"\"'	onclick='popUpYear()'></span>";
						
		sHTML1+="<span id='spanMonth' class='cal_border' " +
				"onmouseover='swapImage(\"changeMonth\",\"drop2.gif\");" +
				"this.style.borderColor=\"#88AAFF\";window.status=\""+selectMonthMessage+"\"' " +
				"onmouseout='swapImage(\"changeMonth\",\"drop1.gif\");this.style.borderColor=\"#3366FF\";" +
				"window.status=\"\"' onclick='popUpMonth()'></span>";
				
		sHTML1+="<span class='cal_border'" +
				" onmouseover='this.style.borderColor=\"#88AAFF\";'" +
				" onmouseout='this.style.borderColor=\"#3366FF\";'" +
				" onclick='javascript:monthSelected=monthNow;" +
				"yearSelected=yearNow;dateSelected=dateNow;" +
				"constructCalendar();returndate();'>"+todayString+"</span>";
		
		$("caption").innerHTML = sHTML1;
		
		bPageLoaded=true;
	}
}
function makeHour(){
    var selstr = "";
	var s ="<select name=\"selecthour\" id=\"selecthour\" >";
	for( j = 0; j < 24; j++ ) {
	  if(hourNow==j)selstr="selected";
	   s += "<option "+selstr+" value=\"" + padZero(j) + "\">" + j + "时</option>";
	  selstr = "";
	}
	s += "</select>";
	return s;
}
function makeMinute(){
    var selstr = "";
	var s ="<select name=\"selectminute\" id=\"selectminute\">";
	for( j = 0; j < 60; j++ ) {
	  if(minNow==j)selstr="selected";
	  s += "<option "+selstr+" value=\"" + padZero(j) + "\">" + j + "分</option>";
	  selstr = "";	
	}
	s += "</select>";
	return s;
}
function makeSecond(){
    var selstr = "";
	var s ="<select name=\"selectsecond\" id=\"selectsecond\">";
	for( j = 0; j < 60; j++ ) {
	  if(minNow==j)selstr="selected";
	  s += "<option "+selstr+" value=\"" + padZero(j) + "\">" + j + "秒</option>";
	  selstr = "";	
	}
	s += "</select>";
	return s;
}

function firstdayofweek(day)
{
	day -= startAt
	if (day < 0){day = 7 + day}
	return day
}
function hideCalendar(){
   try{
	crossobj.visibility="hidden";
	if (crossMonthObj != null){crossMonthObj.visibility="hidden"};
	if (crossYearObj !=	null){crossYearObj.visibility="hidden"};
	}catch(e){
	}
	min=today;
	max=new Date(3000,0,1);
}

function padZero(num) {
	return (num	< 10)? '0' + num : num ;
}

function constructDate(d,m,y)
{
	sTmp = dateFormat
	sTmp = sTmp.replace	("dd","<e>")
	sTmp = sTmp.replace	("d","<d>")
	sTmp = sTmp.replace	("<e>",padZero(d))
	sTmp = sTmp.replace	("<d>",d)
	sTmp = sTmp.replace	("mmm","<o>")
	sTmp = sTmp.replace	("mm","<n>")
	sTmp = sTmp.replace	("m","<m>")
	sTmp = sTmp.replace	("<m>",m+1)
	sTmp = sTmp.replace	("<n>",padZero(m+1))
	sTmp = sTmp.replace	("<o>",monthName[m])
	if (isShowTime)
	{
		return sTmp.replace ("yyyy",y)+" "+$("selecthour").value+":"+$("selectminute").value + ":"+$("selectsecond").value;
	}else{
		return sTmp.replace ("yyyy",y)
	}
}

function closeCalendar() {
	var	sTmp;
	var mindate=min.getDate();
	var minmonth=min.getMonth();
	var minyear=ie? min.getYear():min.getYear()+1900;
	
	var maxdate=max.getDate();
	var maxmonth=max.getMonth();
	var maxyear=ie? max.getYear():max.getYear()+1900;
	
	if ((dateSelected<mindate)&&(monthSelected==minmonth)&&(yearSelected==minyear)||(monthSelected<minmonth)&&(yearSelected==minyear)||(yearSelected<minyear)||((dateSelected>maxdate)&&(monthSelected==maxmonth)&&(yearSelected==maxyear))||((monthSelected>maxmonth)&&(yearSelected==maxyear))||(yearSelected>maxyear))
	{
		hideCalendar();
	}
	else{
		hideCalendar();
		ctlToPlaceValue.value =	constructDate(dateSelected,monthSelected,yearSelected);
		ctlToPlaceValue.focus();
		ctlToPlaceValue.blur();
	}
}

function returndate(){
	ctlToPlaceValue.value =	constructDate(dateSelected,monthSelected,yearSelected);
}
/*** Month Pulldown	***/

function StartDecMonth(){
	intervalID1=setInterval("decMonth()",80);
}

function StartIncMonth(){
	intervalID1=setInterval("incMonth()",80);
}

function incMonth () {
	monthSelected++
	if (monthSelected>11) {
		monthSelected=0
		yearSelected++
	}
	constructCalendar()
}

function decMonth () {
	monthSelected--
	if (monthSelected<0) {
		monthSelected=11
		yearSelected--
	}
	constructCalendar()
}

function constructMonth() {
	popDownYear();
	if (!monthConstructed) {
		sHTML =	"";
		for	(i=0; i<12;	i++) {
			sName =	monthName[i];
			if (i==monthSelected){
				sName =	"<strong>" +	sName +	"</strong>";
			}
			sHTML += "<tr><td id='m" + i + "' onmouseover='this.style.backgroundColor=\"#FFCC99\"' " +
					"onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' " +
					"onclick='monthConstructed=false;monthSelected=" + i + ";constructCalendar();" +
					"popDownMonth();event.cancelBubble=true;'>&nbsp;" + sName + "&nbsp;</td></tr>";
		}

		$("selectMonth").innerHTML = "<table width='50' class='cal_menu' cellspacing=0 " +
				"onmouseover='clearTimeout(timeoutID1)'	" +
				"onmouseout='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"popDownMonth()\",100);" +
				"event.cancelBubble=true'>" +	sHTML +	"</table>";

		monthConstructed=true;
	}
}

function popUpMonth() {
	constructMonth();
	crossMonthObj.visibility = (calendardom||ie)? "visible"	: "show";
	crossMonthObj.left = parseInt(crossobj.left) + 50 + "px";
	crossMonthObj.top =	parseInt(crossobj.top) + 26 + "px";
	
	if(ie){	
		$("selecthour").style.display = "none";
		$("selectminute").style.display = "none";
		$("selectsecond").style.display = "none";
	}
				
}

function popDownMonth()	{
	crossMonthObj.visibility= "hidden";
	
	if(ie){	
		$("selecthour").style.display = "";
		$("selectminute").style.display = "";
		$("selectsecond").style.display = "";
	}	
}
/*** Year Pulldown ***/

function incYear() {
	for	(i=0; i<7; i++){
		newYear	= (i+nStartingYear)+1
		if (newYear==yearSelected)
		{ txtYear =	"&nbsp;<B>"	+ newYear +	"</B>&nbsp;" }
		else
		{ txtYear =	"&nbsp;" + newYear + "&nbsp;" }
		$("y"+i).innerHTML = txtYear
	}
	nStartingYear ++;
	bShow=true
}

function decYear() {
	for	(i=0; i<7; i++){
		newYear	= (i+nStartingYear)-1
		if (newYear==yearSelected)
		{ txtYear =	"&nbsp;<B>"	+ newYear +	"</B>&nbsp;" }
		else
		{ txtYear =	"&nbsp;" + newYear + "&nbsp;" }
		$("y"+i).innerHTML = txtYear
	}
	nStartingYear --;
	bShow=true
}

function selectYear(nYear) {
	yearSelected=parseInt(nYear+nStartingYear);
	yearConstructed=false;
	constructCalendar();
	popDownYear();
}

function constructYear() {
	popDownMonth()
	sHTML =	""
	if (!yearConstructed) {

		sHTML =	"<tr><td align='center'	onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='clearInterval(intervalID1);this.style.backgroundColor=\"\"' style='cursor:pointer'	onmousedown='clearInterval(intervalID1);intervalID1=setInterval(\"decYear()\",30)' onmouseup='clearInterval(intervalID1)'>-</td></tr>"

		j =	0
		nStartingYear =	yearSelected-3
		for	(i=(yearSelected-3); i<=(yearSelected+3); i++) {
			sName =	i;
			if (i==yearSelected){
				sName =	"<B>" +	sName +	"</B>";
			}

			sHTML += "<tr><td id='y" + j + "' onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' onclick='selectYear("+j+");event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>"
			j ++;
		}

		sHTML += "<tr><td align='center' onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='clearInterval(intervalID2);this.style.backgroundColor=\"\"' style='cursor:pointer' onmousedown='clearInterval(intervalID2);intervalID2=setInterval(\"incYear()\",30)'	onmouseup='clearInterval(intervalID2)'>+</td></tr>"

		$("selectYear").innerHTML	= "<table width='44' class='cal_menu' " +
				"onmouseover='clearTimeout(timeoutID2)' " +
				"onmouseout='clearTimeout(timeoutID2);timeoutID2=setTimeout(\"popDownYear()\",100);' " +
				"cellspacing=0>" + sHTML + "</table>";

		yearConstructed	= true
	}
}

function popDownYear() {
	clearInterval(intervalID1);
	clearTimeout(timeoutID1);
	clearInterval(intervalID2);
	clearTimeout(timeoutID2);
	crossYearObj.visibility= "hidden";
	if(ie){	
		$("selecthour").style.display = "";
		$("selectminute").style.display = "";
		$("selectsecond").style.display = "";
	}	
}

function popUpYear() {
	var	leftOffset;

	constructYear();
	crossYearObj.visibility	= (calendardom||ie)? "visible" : "show";
	leftOffset = parseInt(crossobj.left) + $("spanYear").offsetLeft;
	if (ie)
	{
		leftOffset += 6
	}
	crossYearObj.left =	leftOffset + "px";
	crossYearObj.top = parseInt(crossobj.top) +	26 + "px";
	
	if(ie) {
		$("selecthour").style.display = "none";
		$("selectminute").style.display = "none";
		$("selectsecond").style.display = "none";
	}
}

/*** calendar ***/
   function WeekNbr(n) {
 
      year = n.getFullYear();
      month = n.getMonth() + 1;
	  /*
  if (startAt == 0) {
     day = n.getDate() + 1;
  }
  else {
     day = n.getDate();
  }*/
	  day = n.getDate() + 1-startAt;
 
      a = Math.floor((14-month) / 12);
      y = year + 4800 - a;
      m = month + 12 * a - 3;
      b = Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400);
      J = day + Math.floor((153 * m + 2) / 5) + 365 * y + b - 32045;
      d4 = (((J + 31741 - (J % 7)) % 146097) % 36524) % 1461;
      L = Math.floor(d4 / 1460);
      d1 = ((d4 - L) % 365) + L;
      week = Math.floor(d1/7) + 1;
 
      return week;
   }

	function constructCalendar () {
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);

		var dateMessage;
		var	startDate =	new	Date (yearSelected,monthSelected,1);
		var endDate;
		
		var mindate=min.getDate();
		var minmonth=min.getMonth();
		var minyear=ie?min.getYear():min.getYear()+1900;
		
		var maxdate=max.getDate();
		var maxmonth=max.getMonth();
		var maxyear=ie?max.getYear():max.getYear()+1900;
		
		if (monthSelected==1)
		{
			endDate	= new Date (yearSelected,monthSelected+1,1);
			endDate	= new Date (endDate	- (24*60*60*1000));
			numDaysInMonth = endDate.getDate()
		}
		else
		{
			numDaysInMonth = aNumDays[monthSelected];
		}

		datePointer	= 0
		dayPointer = firstdayofweek(startDate.getDay())
	
	if (dayPointer<0)
	{
		//dayPointer = 6
	}

	sHTML =	"<table	border=0 cellspacing='0' cellpadding='3'><tr style='background-color:#F9F7ED'>"

	if (showWeekNumber==1)
	{
		sHTML += "<td width=22><b>" + weekString + "</b></td><td width=1 rowspan=7 bgcolor='#d0d0d0' style='padding:0px'>" +
				 "<img src='"+imgDir+"divider.gif' width=1></td>";
	}

	for	(i=0; i<7; i++)	{
		sHTML += "<td width='22' align='center'><B>"+ dayName[i]+"</B></td>"
	}
	sHTML +="</tr><tr>"
	
	if (showWeekNumber==1)
	{
		sHTML += "<td align=right>" + WeekNbr(startDate) + "&nbsp;</td>"
	}

	for	( var i=1; i<=dayPointer;i++ )
	{
		sHTML += "<td>&nbsp;</td>"
	}

	for	( datePointer=1; datePointer<=numDaysInMonth; datePointer++ )
	{
		dayPointer++;
		sHTML += "<td align=right>";
		
		var sClassName = "cal_date";
		if ((datePointer==odateSelected) &&	(monthSelected==omonthSelected)	&& (yearSelected==oyearSelected))
		{ 
		  sClassName = " cal_selected";
		}

		sHint = ""
		var regexp= /\"/g
		sHint=sHint.replace(regexp,"&quot;");
		
		//alert(dayPointer % 7);
		
		if ((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow)){
		  sClassName = " cal_today";	 
		}

		if ((datePointer<mindate)&&(monthSelected==minmonth)&&(yearSelected==minyear)||(monthSelected<minmonth)&&(yearSelected==minyear)||(yearSelected<minyear)||(datePointer>maxdate)&&(monthSelected==maxmonth)&&(yearSelected==maxyear)||(monthSelected>maxmonth)&&(yearSelected==maxyear)||(yearSelected>maxyear)){
			sClassName = " cal_disabled";	
			sHTML += "<a title=\"" + sHint + "\" class=\""+sClassName+"\" " +
				"href='#' onclick='setDate("+datePointer+",this);event.cancelBubble=true;return false;'>" +
				"&nbsp;" + datePointer + "&nbsp;</a>"
		}
		else{
			sHTML += "<a title=\"" + sHint + "\" class=\""+sClassName+"\" " +
				"href='#' onclick='setDate("+datePointer+",this);event.cancelBubble=true;return false;'>" +
				"&nbsp;" + datePointer + "&nbsp;</a>"
		}
		
		if ((dayPointer+startAt) % 7 == startAt) { 
			sHTML += "</tr><tr>" 
			if ((showWeekNumber==1)&&(datePointer<numDaysInMonth))
			{
				sHTML += "<td align=right>" + (WeekNbr(new Date(yearSelected,monthSelected,datePointer+1))) + "&nbsp;</td>"
			}
		}
	}

	$("content").innerHTML   = sHTML;
	$("spanMonth").innerHTML = "&nbsp;" + monthName[monthSelected] + "&nbsp;" +
			"<img id='changeMonth' SRC='"+imgDir+"drop1.gif'>";
	$("spanYear").innerHTML = "&nbsp;" + yearSelected	+ "&nbsp;" +
			"<img id='changeYear' SRC='"+imgDir+"drop1.gif'>";
	
	if(ie){	
		$("selecthour").style.display = "";
		$("selectminute").style.display = "";
		$("selectsecond").style.display = "";
	}
}

function makeItTop(id){
	var strTemp = "<iframe src=\"javascript:false\" style=\"border:0;position:absolute; visibility:inherit;";
	strTemp += "top:0px;left:0px;width:210px; height:220px;";
	strTemp += "z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';\"></iframe>";
	return strTemp;
}
function setDate(d,obj){
	dateSelected = d;
}

/**
 * @param {String} minDate 最小可选日期,缺省为当前日期(当前日期之前的不可选)
 * 	minDate=-1 无最小可选日期 
 *  minDate="2008-08-01" 指定日期之前的日期不可选
 */
function popUpCalendar(ctl, ctl2, format,top, left,showTime,minDate,maxDate) {
	var	leftpos = left || -1;
	var	toppos = top || -1;
	var format = format || 'yyyy-mm-dd';
	var minDateRe = /\d{4}\D\d{2}\D\d{2}/;
	var maxDateRe = /\d{4}\D\d{2}\D\d{2}/;

 	if(minDate==-1)
 	{
 		min=new Date(1900,0,1);
 	}
 	else if(minDateRe.test(minDate))
 	{
 		var yMin= minDate.substring(0,4);
 		var mMin= minDate.substring(5,7);
 		var dMin= minDate.substring(8,10);
 		min = new Date(yMin,mMin-1,dMin);
 	}
 	if(maxDate==-1)
 	{
 		max=new Date();
 		max.setDate(today.getDate()-1);
 	}
 	else if(minDateRe.test(minDate))
 	{
 		var yMax= maxDate.substring(0,4);
 		var mMax= maxDate.substring(5,7);
 		var dMax= maxDate.substring(8,10);
 		max = new Date(yMax,mMax-1,dMax);
 	}
 	
	if(showTime==false){
	  isShowTime = false;
	  $("cal_time").style.display = "none";
	}
	else{
	  isShowTime = true;
	  $("cal_time").style.display = "block";
	  format += " hh:ii:ss";
	}
	if (bPageLoaded)
	{
		if ( crossobj.visibility ==	"hidden" ) {
			ctlToPlaceValue	= ctl2;
			var splitFormat = format.split(" ");
			dateFormat=splitFormat[0];
			
			if(isShowTime && ctl2.value!=""){
				var splitValue = ctl2.value.split(" ");
				var tValue = splitValue[1];
				tValue = tValue.split(":");
				
				timeFormat=splitFormat[1];
				tFormat=timeFormat.split(":");
				for(i=0;i<3;i++){
					if(tFormat[i]=="hh" || tFormat[i]=="h"){
						hourSelected = tValue[i]
					}
					else if(tFormat[i]=="ii" || tFormat[i]=="i"){
						minuteSelected = tValue[i]
					}
					else if(tFormat[i]=="ss" || tFormat[i]=="s"){
						secondSelected = tValue[i]
					}				
				}
				if($("selecthour").value != hourSelected){
					$("selecthour").value = hourSelected;		
				}
				if($("selectminute").value != minuteSelected){
					$("selectminute").value = minuteSelected;				
				}
				if($("selectsecond").value != minuteSelected){
					$("selectsecond").value = minuteSelected;				
				}			
			}

			formatChar = " ";
			aFormat	= dateFormat.split(formatChar);
			if (aFormat.length<3)
			{
				formatChar = "/"
				aFormat	= dateFormat.split(formatChar)
				if (aFormat.length<3)
				{
					formatChar = "."
					aFormat	= dateFormat.split(formatChar)
					if (aFormat.length<3)
					{
						formatChar = "-"
						aFormat	= dateFormat.split(formatChar)
						if (aFormat.length<3)
						{
							// invalid date	format
							formatChar=""
						}
					}
				}
			}
    
			tokensChanged =	0
			if ( formatChar	!= "" && ctl2.value!="")
			{
				// use user's date
				aData =	ctl2.value.split(formatChar)

				for	(i=0;i<3;i++)
				{
					if ((aFormat[i]=="d") || (aFormat[i]=="dd"))
					{
						dateSelected = parseInt(aData[i], 10)
						tokensChanged ++
					}
					else if	((aFormat[i]=="m") || (aFormat[i]=="mm"))
					{
						monthSelected =	parseInt(aData[i], 10) - 1
						tokensChanged ++
					}
					else if	(aFormat[i]=="yyyy")
					{
						yearSelected = parseInt(aData[i], 10)
						tokensChanged ++
					}
					else if	(aFormat[i]=="mmm")
					{
						for	(j=0; j<12;	j++)
						{
							if (aData[i]==monthName[j])
							{
								monthSelected=j
								tokensChanged ++
							}
						}
					}
				}
			}

			if ((tokensChanged!=3)||isNaN(dateSelected)||isNaN(monthSelected)||isNaN(yearSelected))
			{
				dateSelected = dateNow
				monthSelected =	monthNow
				yearSelected = yearNow
			}

			odateSelected=dateSelected
			omonthSelected=monthSelected
			oyearSelected=yearSelected

			aTag = ctl
			do {

				aTag = aTag.offsetParent;
				//TODO
				if($(aTag).hasClass("x-tabs-item-body"))continue;
				leftpos	+= aTag.offsetLeft;
				toppos += aTag.offsetTop;
			} while(aTag.tagName!="BODY");
			
			crossobj.left =	fixedX==-1 ? ctl.offsetLeft	+ leftpos +"px" : fixedX + "px";
			crossobj.top = fixedY==-1 ?	ctl.offsetTop +	toppos + ctl.offsetHeight +	2 + "px" :	fixedY + "px";
			
			constructCalendar (1, monthSelected, yearSelected);
			
			crossobj.visibility=(calendardom||ie)? "visible" : "show";		

			bShow = true;
		}
		else
		{
			hideCalendar();
		}
		ctlNow = ctl
	}
}

function hidecal2 () {
	//alert(bShow);
	if (!bShow)
	{
		hideCalendar();
	}
	bShow = false;
}

if(window.attachEvent){
  document.body.attachEvent("onclick",hidecal2);
  window.attachEvent("onload",initCalendar); 
}
else if(window.addEventListener){
  window.addEventListener('load', initCalendar,false);
  document.body.addEventListener("click",hidecal2,false);
}
