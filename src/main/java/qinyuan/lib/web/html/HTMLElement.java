package qinyuan.lib.web.html;

import java.util.HashMap;
import java.util.Map;

public abstract class HTMLElement {

	private Map<String, String> attrs = new HashMap<String, String>();

	public HTMLElement setAttr(String name, String value) {
		attrs.put(name, value);
		return this;
	}

	public HTMLElement setClass(String style) {
		return setAttr("class", style);
	}

	public HTMLElement setId(String id) {
		return setAttr("id", id);
	}

	public String getAttr(String name) {
		return attrs.get(name);
	}

	protected String getAttrStr() {
		StringBuilder o = new StringBuilder();
		String value;
		for (String key : attrs.keySet()) {
			if ((value = attrs.get(key)) != null) {
				o.append(key + "='" + value + "' ");
			}
		}
		if (o.length() > 0) {
			o.deleteCharAt(o.length() - 1);
		}
		return o.toString();
	}

	public abstract String getTagName();
}