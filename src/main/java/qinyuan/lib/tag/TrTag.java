package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class TrTag extends BodyEleTag {

	private String text;
	private boolean head;

	public void setHead(String str) {
		if (str.equals("true")) {
			head = true;
		} else {
			head = false;
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void doTag() throws JspException, IOException {
		String[] strArr = text.split(",|，");
		print("<tr>");
		String tag;
		if (head) {
			tag = "th";
		} else {
			tag = "td";
		}
		for (String str : strArr) {
			print("<" + tag + ">" + str + "</" + tag + ">");
		}
		print("</tr>");
	}
}
