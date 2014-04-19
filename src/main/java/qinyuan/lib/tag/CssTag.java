package qinyuan.lib.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class CssTag extends MyTagSupport {

	private String href;

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<link rel='stylesheet' type='text/css' href='");
		if (href.charAt(0) == '/') {
			print(WEB_BASE + href);
		} else {
			print(href);
		}
		if(!href.endsWith(".css")){
			print(".css");
		}
		print("' />");
	}
}
