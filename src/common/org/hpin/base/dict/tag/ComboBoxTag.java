package org.hpin.base.dict.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.FormTag;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.base.dict.service.SysDictTypeService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.util.JSONUtil;
import org.hpin.common.util.StaticMethod;
import org.jdom.Element;

/**
 * 
 */
public class ComboBoxTag extends TagSupport {

	// 元素的id
	protected String id = null;

	// 元素的name
	protected String name = null;

	// 元素的下级联动元素的id
	protected String sub = null;

	// 元素初始化的字典id
	protected String initDicId = null;

	// 指定从自定义的action地址取数据，而不通过字典表
	// ds值为此action的链接地址
	protected String ds = null;

	// 元素的class
	protected String styleClass = null;

	// 默认选中的选项值
	protected String defaultValue = null;

	// formBean名称
	protected String form = null;

	// 是否可以多选
	protected String multiple = null;

	// 显示行数
	protected String size = null;

	// onchange事件监听，其中的JS将在doCombo之后执行
	protected String onchange = null;

	// TODO 加入自定义的html属性
	protected String attributes = null;
	
	//利用alt属性作为Ext框架接口
	protected String alt = null;
	
	

	public int doEndTag() {

		try {
			String selectedValue = null;

			
			String selectText = "请选择";
			String waitText = "加载中";

			Element defaultOp = new Element("option");
			defaultOp.addContent(selectText);
			defaultOp.setAttribute("value", "");

			Element disableOp = new Element("option");
			disableOp.addContent(waitText);
			disableOp.setAttribute("value", "");

			// 创建select元素
			Element rootElement = new Element("select");
			rootElement.setAttribute("id", id);
			rootElement.setAttribute("name", name);

			if (ds != null) {
				rootElement.setAttribute("ds", ds);
			}
			if (styleClass != null) {
				rootElement.setAttribute("class", styleClass);
			}
			if (multiple != null) {
				rootElement.setAttribute("multiple", multiple);
			}
			if (size != null) {
				rootElement.setAttribute("size", size);
			}
			if (alt != null) {
				rootElement.setAttribute("alt", alt);
			}

			// 设置subid属性和onchage事件
			if (sub !=null && !"".equals(sub)) {
				rootElement.setAttribute("subid", sub);
				rootElement.setAttribute("onchange",
						"javascript:hpin.ComboBox.doCombo(this,'" + sub + "');"+StaticMethod.null2String(onchange));
			}
			else if(onchange != null){
				rootElement.setAttribute("onchange", "javascript:"+StaticMethod.null2String(onchange));
			}

			if (form != null) {
				// 是否能直接获取form名称，而不用设置？
				FormTag formTag= (FormTag)findAncestorWithClass(this, FormTag.class);
				Component component = formTag.getComponent();
				Map map = component.getParameters();
				selectedValue = map.get(name).toString();
			}
			// 如果有initDicId，则初始化option选项
			if (initDicId != null) {
				rootElement.setAttribute("initDicId", initDicId);



				// 取字典数据
				SysDictTypeService _objDictManager = (SysDictTypeService) SpringTool.getBean(SysDictTypeService.class);
				List list = _objDictManager.getDictSonsByDictid(initDicId);

				if(list.size()>0){
					if (multiple == null)
						rootElement.addContent(defaultOp);
					String itemName = null;
					String itemId = null;

				// 将list转为option元素插入select元素
				for (Iterator it = list.iterator(); it.hasNext();) {
					SysDictType item = (SysDictType) it.next();
					itemName = StaticMethod.null2String(item.getDictName());
					itemId = StaticMethod.null2String(item.getDictId());

					//修改dictCode为空的时候comboBox联动无数据问题 edit
					Element option = new Element("option").addContent(itemName);
					option.setAttribute("value", itemId);
					if (form != null && itemId.equals(selectedValue)) {
						option.setAttribute("selected", "true");
					} else if (itemId.equals(defaultValue)) {
						option.setAttribute("selected", "true");
					}
					rootElement.addContent(option);
				}
				}
				else{
					rootElement.addContent(disableOp);
					rootElement.setAttribute("disabled", "true");
				}
			} else {
				rootElement.addContent(disableOp);
				rootElement.setAttribute("disabled", "true");

			}

			pageContext.getOut().println(JSONUtil.getStrElement(rootElement));

		} catch (IOException e) {
			
		} 
		return EVAL_PAGE;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInitDicId() {
		return initDicId;
	}

	public void setInitDicId(String initDicId) {
		this.initDicId = initDicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange.replaceAll("javascript:","");
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}