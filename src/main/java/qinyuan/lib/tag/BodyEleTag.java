package qinyuan.lib.tag;

public class BodyEleTag extends MyTagSupport {

	private String id;
	private String style;
	private String value;

	public String getBaseAttr() {
		String o = "";
		if (id != null) {
			o += " id='" + id + "' name='" + id + "' ";
		}
		if (style != null) {
			o += " class='" + style + "' ";
		}
		if (value != null) {
			o += " value='" + value + "' ";
		}
		return o;
	}

	public String getId() {
		return id;
	}

	public String getStyle() {
		return style;
	}

	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
