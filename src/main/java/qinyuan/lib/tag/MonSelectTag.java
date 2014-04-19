package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import qinyuan.lib.web.html.NumSelect;

public class MonSelectTag extends BodyEleTag {

	@Override
	public void doTag() throws JspException, IOException {
		print(new NumSelect(1, 12).setId(getId()).setEmptyOpt(false)
				.select(getValue()));
	}
}
