package qinyuan.lib.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class AnchorTag extends BodyEleTag {

	private String href;
	private String text;

	public void setHref(String href) {
		this.href = href;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<a href='" + href + "'>" + text + "</a>");
	}
}
