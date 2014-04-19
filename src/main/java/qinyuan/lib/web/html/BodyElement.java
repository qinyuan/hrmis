package qinyuan.lib.web.html;

/**
 * BodyElement includes the HTML element those contains body, such as
 * td,table,p,div and so on
 * 
 * @author qinyuan
 * 
 */
public abstract class BodyElement extends HTMLElement {

	public String toString() {
		String body = getBody();
		String tagName = getTagName();

		StringBuilder o = new StringBuilder();
		String attrStr = getAttrStr();
		o.append("<" + tagName);
		if (!attrStr.isEmpty()) {
			o.append(" " + getAttrStr());
		}
		o.append(">");
		if (body != null) {
			o.append(body);
		}
		o.append("</" + tagName + ">");
		return o.toString();
	}

	public abstract String getBody();
}