package qinyuan.lib.web.html;

/**
 * EmptyElement includes the HTML element without body, such as input and so on
 * 
 * @author qinyuan
 * 
 */
public abstract class EmptyElement extends HTMLElement {

	public String toString() {
		String tagName = getTagName();

		StringBuilder o = new StringBuilder();
		o.append("<" + tagName + " " + getAttrStr());
		o.append(" />");
		return o.toString();
	}
}
