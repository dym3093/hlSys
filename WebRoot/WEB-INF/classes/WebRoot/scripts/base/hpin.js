/**
 * hpin javascript 基础类库
 *
 */

hpin = {
	isIE : (/*@cc_on!@*/false),
	isFF : navigator.userAgent.toLowerCase().indexOf("firefox") > -1,
	isIE6 : navigator.userAgent.toLowerCase().indexOf("msie 6") > -1,
	isIE7 : navigator.userAgent.toLowerCase().indexOf("msie 7") > -1,

	/**
 	 * 用于拷贝一个对象的属性到另一个对象
	 */
	apply : function(o, c, defaults){
	    if(defaults){
	        hpin.apply(o, defaults);
	    }
	    if(o && c && typeof c == 'object'){
	        for(var p in c){
	            o[p] = c[p];
	        }
	    }
	    return o;
	},
	
	/**
	 * 拷贝一个对象的属性到另一个属性，如果目标对象已有这个属性，则不拷贝
	 */
	applyIf : function(o, c){
        if(o && c){
            for(var p in c){
                if(typeof o[p] == "undefined"){ o[p] = c[p]; }
            }
        }
        return o;
    },
    
	/**
	 * 在firefox的控制台打出调试信息
	 */	
	log:function(s){
		if(typeof console != "undefined") {
			for (var i=0; i<arguments.length; i++){
				console.log(arguments[i]);
			}				
		}
	},
	
    /**
     * 把JSON字符串转换成对象
     */
    JSONDecode : function(json){
		return eval("(" + json + ')');
	},
	
	/**
	 * 事件停止
	 */
	stopEvent : function(e){
		if (e.preventDefault) {
	      e.preventDefault();
	      e.stopPropagation();
	    } else {
	      e.returnValue = false;
	      e.cancelBubble = true;
	    }
	},
	
    /**
     * 用于创建名字空间
     */
	namespace : function(){
        var a=arguments, o=null, i, j, d, rt;
        for (i=0; i<a.length; ++i) {
            d=a[i].split(".");
            rt = d[0];
            eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
            for (j=1; j<d.length; ++j) {
                o[d[j]]=o[d[j]] || {};
                o=o[d[j]];
            }
        }
    },    
    /**
     * 把集合转换成数组
     */		
	$A : function(list){
		var arr = [];
		for (var i=0,len=list.length; i<len; i++){
			arr[i] = list[i];
		}
		return arr;
	},
    $D : function(str){return str.URLDecode();},
    $E : function(str){return str.URLEncode();},
    $V : function(id){return $(id).value;}
};

/**
 * 分配名字空间
 */
hpin.namespace("hpin.util","hpin.form","hpin.dom","hpin.layout");

/**
 * 对数组对象的扩展
 */

/**
 * 查询一个对象在数组中的位置
 */
Array.prototype.indexOf = function(){
	for (var i=0; i<this.length; i++){
		if (this[i]==arguments[0])
			return i;
    }
 	return -1;
};

/**
 * 为每个数组元素绑定一个函数并执行
 */
Array.prototype.each = function(fn){
		for (var i=0,len=this.length; i<len; i++){
			fn(this[i],i);
		}
		return this;
};

/**
 * 删除数组中的一个元素
 */
Array.prototype.remove = function (o) {
    	var index = this.indexOf(o);
   		if(index != -1){
			this.splice(index, 1);
		}
};

/**
 * 对字符串对象的扩展
 */

/**
 * 去掉首尾空格
 */
String.prototype.trim = function(){
	var _re,_argument = arguments[0] || " ";
	typeof(_argument)=="string"?(_argument == " "?_re = /(^\s*)|(\s*$)/g : _re = new RegExp("(^"+_argument+"*)|("+_argument+"*$)","g")) : _re = _argument;
	return this.replace(_re,"");
};

/**
 * 骆驼写法
 */
String.prototype.camelize = function(){
	return this.replace(/(-\S)/g,function($1){return $1.toUpperCase().substring(1,2)});
};	

/**
 * 用于查找className
 */
String.prototype.hasSubString = function(s,f){
	if(!f) f="";
	return (f+this+f).indexOf(f+s+f)==-1?false:true;
};
String.prototype.hasSubStrInArr = function(arr,f){
	for(var i=0; i<arr.length; i++){
		if(this.hasSubString(arr[i],f)){return true;}
	}
	return false;
}	    
/**
 * 链接地址编码
 */
String.prototype.URLEncode = function(){ return encodeURIComponent(this);};

/**
 * 链接地址解码
 */
String.prototype.URLDecode = function(){ return decodeURIComponent(this);};


/**
 * 获取页面中某个元素，并绑定一些常用方法
 */
hpin.$ = function(el){
	if(!el){ return null; }
	var elem = typeof el == "string" ? document.getElementById(el) : el;
	if (!elem){return null; }
	if (elem.tagName && !elem['isComposite']){
		hpin.apply(elem,{
			isComposite : true,
			hide : function(){this.style.display="none"; return this;},
			show : function(){this.style.display=""; return this;},
			toggle : function(){this.getStyle("display")=="none"?this.show():this.hide(); return this;},		
			getStyle : function(s){
				var value = this.style[s=="float"?(hpin.isIE?"styleFloat":"cssFloat"):s.camelize()];
				if (!value){
					if (this.currentStyle){
						value = this.currentStyle[s.camelize()];
					}else if (document.defaultView && document.defaultView.getComputedStyle){
						var css = document.defaultView.getComputedStyle(this, null);
						value = css ? css.getPropertyValue(s) : null;
					}
				}
				return value;
			},
			setStyle : function(s){
				var sList = s.split(";");
				for (var i=0,j; j=sList[i]; i++){
					var k = j.split(/:(?!\/\/)/g);
					var key = k[0].trim();
					key=key=="float"?(hpin.isIE?"styleFloat":"cssFloat"):key.camelize();
					this.style[key] = k[1].trim();
				}
				return this;
			},
			hasClass : function(c){return this.className.hasSubString(c," ");},
			addClass : function(c){if(!this.hasClass(c)){this.className+=" "+c};return this;},
			removeClass : function(c){if(this.hasClass(c)){this.className = (" "+this.className+" ").replace(" "+c+" "," ").trim(); return this;}},
			toggleClass : function(c){if(this.hasClass(c)){this.removeClass(c);}else{this.addClass(c);};return this;},
			getElementsByClassName : function(c){return this.getElementsByAttribute("className",c);},
			getElementsByAttribute : function(n,v){ //name,value;
				var elems = this.getElementsByTagName("*");
				var elemList = [];
				for (var i=0,j; j=elems[i]; i++){
					var att = j[n] || j.getAttribute(n);
					if (att==v){
						elemList.push(j);
					}
				}
				return elemList;
			},
			on : function(eventName, fn, scope){
				var h = function(e){
					fn.call(scope, e.target || e.srcElement, e);
				};
				if (window.addEventListener) {
					this.addEventListener(eventName, h, (false));
				} else if (window.attachEvent) {
					this.attachEvent("on" + eventName, h);
				}
			},
			select : function(selector){return hpin.Query.select(selector,this);},
			update : function(str){
				if(this.tagName.hasSubStrInArr(["INPUT","TEXTAREA","SELECT","BUTTON"])){
					this.value = str || '';
				}
				else{
					this.innerHTML = str || '';
				}
				return this;
			}
		});
	}
	return elem;
};

/**
 * CSS选择器，from Extjs
 */

hpin.Query = function(){
    var cache = {}, simpleCache = {}, valueCache = {};
    var nonSpace = /\S/;
    var trimRe = /^\s+|\s+$/g;
    var tplRe = /\{(\d+)\}/g;
    var modeRe = /^(\s?[\/>+~]\s?|\s|$)/;
    var tagTokenRe = /^(#)?([\w-\*]+)/;
    var nthRe = /(\d*)n\+?(\d*)/, nthRe2 = /\D/;

    function child(p, index){
        var i = 0;
        var n = p.firstChild;
        while(n){
            if(n.nodeType == 1){
               if(++i == index){
                   return n;
               }
            }
            n = n.nextSibling;
        }
        return null;
    };

    function next(n){
        while((n = n.nextSibling) && n.nodeType != 1);
        return n;
    };

    function prev(n){
        while((n = n.previousSibling) && n.nodeType != 1);
        return n;
    };

    function children(d){
        var n = d.firstChild, ni = -1;
 	    while(n){
 	        var nx = n.nextSibling;
 	        if(n.nodeType == 3 && !nonSpace.test(n.nodeValue)){
 	            d.removeChild(n);
 	        }else{
 	            n.nodeIndex = ++ni;
 	        }
 	        n = nx;
 	    }
 	    return this;
 	};

    function byClassName(c, a, v){
        if(!v){
            return c;
        }
        var r = [], ri = -1, cn;
        for(var i = 0, ci; ci = c[i]; i++){
            if((' '+ci.className+' ').indexOf(v) != -1){
                r[++ri] = ci;
            }
        }
        return r;
    };

    function attrValue(n, attr){
        if(!n.tagName && typeof n.length != "undefined"){
            n = n[0];
        }
        if(!n){
            return null;
        }
        if(attr == "for"){
            return n.htmlFor;
        }
        if(attr == "class" || attr == "className"){
            return n.className;
        }
        return n.getAttribute(attr) || n[attr];

    };

    function getNodes(ns, mode, tagName){
        var result = [], ri = -1, cs;
        if(!ns){
            return result;
        }
        tagName = tagName || "*";
        if(typeof ns.getElementsByTagName != "undefined"){
            ns = [ns];
        }
        if(!mode){
            for(var i = 0, ni; ni = ns[i]; i++){
                cs = ni.getElementsByTagName(tagName);
                for(var j = 0, ci; ci = cs[j]; j++){
                    result[++ri] = ci;
                }
            }
        }else if(mode == "/" || mode == ">"){
            var utag = tagName.toUpperCase();
            for(var i = 0, ni, cn; ni = ns[i]; i++){
                cn = ni.children || ni.childNodes;
                for(var j = 0, cj; cj = cn[j]; j++){
                    if(cj.nodeName == utag || cj.nodeName == tagName  || tagName == '*'){
                        result[++ri] = cj;
                    }
                }
            }
        }else if(mode == "+"){
            var utag = tagName.toUpperCase();
            for(var i = 0, n; n = ns[i]; i++){
                while((n = n.nextSibling) && n.nodeType != 1);
                if(n && (n.nodeName == utag || n.nodeName == tagName || tagName == '*')){
                    result[++ri] = n;
                }
            }
        }else if(mode == "~"){
            for(var i = 0, n; n = ns[i]; i++){
                while((n = n.nextSibling) && (n.nodeType != 1 || (tagName == '*' || n.tagName.toLowerCase()!=tagName)));
                if(n){
                    result[++ri] = n;
                }
            }
        }
        return result;
    };

    function concat(a, b){
        if(b.slice){
            return a.concat(b);
        }
        for(var i = 0, l = b.length; i < l; i++){
            a[a.length] = b[i];
        }
        return a;
    }

    function byTag(cs, tagName){
        if(cs.tagName || cs == document){
            cs = [cs];
        }
        if(!tagName){
            return cs;
        }
        var r = [], ri = -1;
        tagName = tagName.toLowerCase();
        for(var i = 0, ci; ci = cs[i]; i++){
            if(ci.nodeType == 1 && ci.tagName.toLowerCase()==tagName){
                r[++ri] = ci;
            }
        }
        return r;
    };

    function byId(cs, attr, id){
        if(cs.tagName || cs == document){
            cs = [cs];
        }
        if(!id){
            return cs;
        }
        var r = [], ri = -1;
        for(var i = 0,ci; ci = cs[i]; i++){
            if(ci && ci.id == id){
                r[++ri] = ci;
                return r;
            }
        }
        return r;
    };

    function byAttribute(cs, attr, value, op, custom){
        var r = [], ri = -1, st = custom=="{";
        var f = hpin.Query.operators[op];
        for(var i = 0, ci; ci = cs[i]; i++){
            var a;
            if(st){
                a = hpin.Query.getStyle(ci, attr);
            }
            else if(attr == "class" || attr == "className"){
                a = ci.className;
            }else if(attr == "for"){
                a = ci.htmlFor;
            }else if(attr == "href"){
                a = ci.getAttribute("href", 2);
            }else{
                a = ci.getAttribute(attr);
            }
            if((f && f(a, value)) || (!f && a)){
                r[++ri] = ci;
            }
        }
        return r;
    };

    var isIE = window.ActiveXObject ? true : false;

    // this eval is stop the compressor from
    // renaming the variable to something shorter
    eval("var batch = 30803;");

    var key = 30803;

    function nodupIEXml(cs){
        var d = ++key;
        cs[0].setAttribute("_nodup", d);
        var r = [cs[0]];
        for(var i = 1, len = cs.length; i < len; i++){
            var c = cs[i];
            if(!c.getAttribute("_nodup") != d){
                c.setAttribute("_nodup", d);
                r[r.length] = c;
            }
        }
        for(var i = 0, len = cs.length; i < len; i++){
            cs[i].removeAttribute("_nodup");
        }
        return r;
    }

    function nodup(cs){
        if(!cs){
            return [];
        }
        var len = cs.length, c, i, r = cs, cj, ri = -1;
        if(!len || typeof cs.nodeType != "undefined" || len == 1){
            return cs;
        }
        if(isIE && typeof cs[0].selectSingleNode != "undefined"){
            return nodupIEXml(cs);
        }
        var d = ++key;
        cs[0]._nodup = d;
        for(i = 1; c = cs[i]; i++){
            if(c._nodup != d){
                c._nodup = d;
            }else{
                r = [];
                for(var j = 0; j < i; j++){
                    r[++ri] = cs[j];
                }
                for(j = i+1; cj = cs[j]; j++){
                    if(cj._nodup != d){
                        cj._nodup = d;
                        r[++ri] = cj;
                    }
                }
                return r;
            }
        }
        return r;
    }

    function quickDiffIEXml(c1, c2){
        var d = ++key;
        for(var i = 0, len = c1.length; i < len; i++){
            c1[i].setAttribute("_qdiff", d);
        }
        var r = [];
        for(var i = 0, len = c2.length; i < len; i++){
            if(c2[i].getAttribute("_qdiff") != d){
                r[r.length] = c2[i];
            }
        }
        for(var i = 0, len = c1.length; i < len; i++){
           c1[i].removeAttribute("_qdiff");
        }
        return r;
    }

    function quickDiff(c1, c2){
        var len1 = c1.length;
        if(!len1){
            return c2;
        }
        if(isIE && c1[0].selectSingleNode){
            return quickDiffIEXml(c1, c2);
        }
        var d = ++key;
        for(var i = 0; i < len1; i++){
            c1[i]._qdiff = d;
        }
        var r = [];
        for(var i = 0, len = c2.length; i < len; i++){
            if(c2[i]._qdiff != d){
                r[r.length] = c2[i];
            }
        }
        return r;
    }

    function quickId(ns, mode, root, id){
        if(ns == root){
           var d = root.ownerDocument || root;
           return d.getElementById(id);
        }
        ns = getNodes(ns, mode, "*");
        return byId(ns, null, id);
    }

    return {
        getStyle : function(el, name){
            return $(el).getStyle(name);
        },
        /**
         * 运行查询路径，生成一个function，此function只有一个参数root，用来判断从哪个节点开始查询
         * @param {String} path CSS选择符/xpath 路径
         * @param {String} type (可选) 缺省为"select", 或者 "simple" 进行简单查询
         * @return {Function}
         */
        compile : function(path, type){
            type = type || "select";

            var fn = ["var f = function(root){\n var mode; ++batch; var n = root || document;\n"];
            var q = path, mode, lq;
            var tk = hpin.Query.matchers;
            var tklen = tk.length;
            var mm;

            // accept leading mode switch
            var lmode = q.match(modeRe);
            if(lmode && lmode[1]){
                fn[fn.length] = 'mode="'+lmode[1].replace(trimRe, "")+'";';
                q = q.replace(lmode[1], "");
            }
            // strip leading slashes
            while(path.substr(0, 1)=="/"){
                path = path.substr(1);
            }

            while(q && lq != q){
                lq = q;
                var tm = q.match(tagTokenRe);
                if(type == "select"){
                    if(tm){
                        if(tm[1] == "#"){
                            fn[fn.length] = 'n = quickId(n, mode, root, "'+tm[2]+'");';
                        }else{
                            fn[fn.length] = 'n = getNodes(n, mode, "'+tm[2]+'");';
                        }
                        q = q.replace(tm[0], "");
                    }else if(q.substr(0, 1) != '@'){
                        fn[fn.length] = 'n = getNodes(n, mode, "*");';
                    }
                }else{
                    if(tm){
                        if(tm[1] == "#"){
                            fn[fn.length] = 'n = byId(n, null, "'+tm[2]+'");';
                        }else{
                            fn[fn.length] = 'n = byTag(n, "'+tm[2]+'");';
                        }
                        q = q.replace(tm[0], "");
                    }
                }
                while(!(mm = q.match(modeRe))){
                    var matched = false;
                    for(var j = 0; j < tklen; j++){
                        var t = tk[j];
                        var m = q.match(t.re);
                        if(m){
                            fn[fn.length] = t.select.replace(tplRe, function(x, i){
                                                    return m[i];
                                                });
                            q = q.replace(m[0], "");
                            matched = true;
                            break;
                        }
                    }
                    // prevent infinite loop on bad selector
                    if(!matched){
                        throw 'Error parsing selector, parsing failed at "' + q + '"';
                    }
                }
                if(mm[1]){
                    fn[fn.length] = 'mode="'+mm[1].replace(trimRe, "")+'";';
                    q = q.replace(mm[1], "");
                }
            }
            fn[fn.length] = "return nodup(n);\n}";
            eval(fn.join(""));
            return f;
        },

        /**
         * 选择指定的元素集合
         * @param {String} selector 选择符路径或xpath，可用逗号隔开进行分组
         * @param {Node} root (可选) 指定开始进行查询的节点(defaults to document).
         * @return {Array}
         */
        select : function(path, root){
            if(!root || root == document){
                root = document;
            }
            if(typeof root == "string"){
                root = document.getElementById(root);
            }
            var paths = path.split(",");
            var results = [];
            for(var i = 0, len = paths.length; i < len; i++){
                var p = paths[i].replace(trimRe, "");
                if(!cache[p]){
                    cache[p] = hpin.Query.compile(p);
                    if(!cache[p]){
                        throw p + " is not a valid selector";
                    }
                }
                var result = cache[p](root);
                if(result && result != document){
                    results = results.concat(result);
                }
            }
            if(paths.length > 1){
                return nodup(results);
            }
            return results;
        },

        selectNode : function(path, root){
            return hpin.Query.select(path, root)[0];
        },

        filter : function(els, ss, nonMatches){
            ss = ss.replace(trimRe, "");
            if(!simpleCache[ss]){
                simpleCache[ss] = hpin.Query.compile(ss, "simple");
            }
            var result = simpleCache[ss](els);
            return nonMatches ? quickDiff(result, els) : result;
        },

        /**
         * Collection of matching regular expressions and code snippets.
         */
        matchers : [{
                re: /^\.([\w-]+)/,
                select: 'n = byClassName(n, null, " {1} ");'
            },{
                re: /^(?:([\[\{])(?:@)?([\w-]+)\s?(?:(=|.=)\s?['"]?(.*?)["']?)?[\]\}])/,
                select: 'n = byAttribute(n, "{2}", "{4}", "{3}", "{1}");'
            }, {
                re: /^#([\w-]+)/,
                select: 'n = byId(n, null, "{1}");'
            },{
                re: /^@([\w-]+)/,
                select: 'return {firstChild:{nodeValue:attrValue(n, "{1}")}};'
            }
        ],

        /**
         * Collection of operator comparison functions. The default operators are =, !=, ^=, $=, *=, %=, |= and ~=.
         * New operators can be added as long as the match the format <i>c</i>= where <i>c</i> is any character other than space, &gt; &lt;.
         */
        operators : {
            "=" : function(a, v){
                return a == v;
            },
            "!=" : function(a, v){
                return a != v;
            },
            "^=" : function(a, v){
                return a && a.substr(0, v.length) == v;
            },
            "$=" : function(a, v){
                return a && a.substr(a.length-v.length) == v;
            },
            "*=" : function(a, v){
                return a && a.indexOf(v) !== -1;
            },
            "%=" : function(a, v){
                return (a % v) == 0;
            },
            "|=" : function(a, v){
                return a && (a == v || a.substr(0, v.length+1) == v+'-');
            },
            "~=" : function(a, v){
                return a && (' '+a+' ').indexOf(' '+v+' ') != -1;
            }
        }

    };
}();

hpin.select = hpin.Query.select;

/**
 * 以checkbox的checked为标准，选中所有名称为name的checkbox
 * @example 
 * 		<input type="checkbox" onclick="hpin.util.checkAll(this,'user')">
 */
hpin.util.checkAll = function(checkbox, name){
	$A(document.getElementsByName(name)).each(function(el){
		if(el.tagName=='INPUT' && el.type=='checkbox'){
			el.checked = checkbox.checked;
		}
	});
};

/**
 * 将id为tableid的table元素隔行换色
 */
hpin.util.colorRows = function(tableid, startIndex){
	if(startIndex==null) startIndex = 1;
	hpin.Query.select('tr', tableid).each(function(tr, index) {
		(index >= startIndex && index % 2 == 0) ? $(tr).addClass("colorrow") : "";
	});	
};

/**
 * 载入一个页面的内容，并执行其中的JS
 * @param id 元素id
 * @param url 页面url
 * @param isUpdate 是否要重复载入
 */
hpin.util.appendPage = function(id, url, isUpdate, fn, scope) {
	try{
		if(Ext){
			var el = Ext.get(id);
			if (!isUpdate && el.dom.innerHTML != "") return;
			var mgr = el.getUpdateManager();
			mgr.loadScripts = true;
			mgr.update(url);
			if(typeof fn == "function"){			
				mgr.on('update',fn,scope);
			}
			mgr.on('failure',function(){
		      hpin.log("页面载入失败");
		      $(id).innerHTML = "";			
			});
		}
	}catch(e){}
};

hpin.form = function(){
	return {
		disable : function(){
			var a=arguments;
        	for (var i=0; i<a.length; ++i) {
				$(a[i]).disabled = "disabled";
				$(a[i]).removeClass('invalid');
        	}
		},
		hide : function(){
			var a=arguments;
        	for (var i=0; i<a.length; ++i) {
				$(a[i]).hide();
        	}
		},
		enable : function(){
			var a=arguments;
        	for (var i=0; i<a.length; ++i) {
				$(a[i]).disabled = false;
				$(a[i]).show();
        	}
		},
		disableArea : function(id,isHide){
			if ($(id).innerHTML == '') return;
			//Ext.get(id).mask();
			$(id).select('input,select,textarea').each(function(f) {
				$(f).disabled = "disabled";			
				$(f).removeClass('invalid');
			});
			if (isHide)	$(id).hide();
		},
		enableArea : function(id){
			//Ext.get(id).unmask();
			$(id).show();
			$(id).select('input,select,textarea').each(function(f) {
				$(f).disabled = false;
			});
		}
	}
}();

/**
 * 下面是prototype.js的习惯写法
 */

$ = hpin.$;
$A = hpin.$A;
$D = hpin.$D;
$E = hpin.$E;
$V = hpin.$V;

$(document);

var Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
};

var Ajax={
	xmlhttp:function (){
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){
				return new XMLHttpRequest();
			}
		}
	}
};

Ajax.Request=function (){
	if(arguments.length<2)return ;
	var para = {asynchronous:true,method:"GET",parameters:""};
	for (var key in arguments[1]){
		para[key] = arguments[1][key];
	}
	var _x=Ajax.xmlhttp();
	var _url=arguments[0];
	if(para["parameters"].length>0) para["parameters"]+='&_=';
	if(para["method"].toUpperCase()=="GET") _url+=(_url.match(/\?/)?'&':'?')+para["parameters"];
	_x.open(para["method"].toUpperCase(),_url,para["asynchronous"]);
	_x.onreadystatechange=function (){
		if(_x.readyState==4){
			if(_x.status==200 || _x.status == 0)
				para["onComplete"]?para["onComplete"](_x):"";
			else{
				para["onError"]?para["onError"](_x):"";
			}
		}
	};
	if(para["method"].toUpperCase()=="POST")_x.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

	for (var ReqHeader in para["setRequestHeader"]){
		_x.setRequestHeader(ReqHeader,para["setRequestHeader"][ReqHeader]);
	}
	_x.send(para["method"].toUpperCase()=="POST"?(para["postBody"]?para["postBody"]:para["parameters"]):null);

};



