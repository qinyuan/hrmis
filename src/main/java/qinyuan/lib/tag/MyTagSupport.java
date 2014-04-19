package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyTagSupport extends SimpleTagSupport {

	protected final String WEB_BASE = "/hrmis";
	private JspWriter out;

	protected void print(Object str) throws IOException {
		if (out == null) {
			out = getJspContext().getOut();
		}
		out.print(str.toString());
	}
}
