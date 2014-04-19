package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import qinyuan.lib.web.html.NumSelect;

public class YearSelectTag extends BodyEleTag {

	@Override
	public void doTag() throws JspException, IOException {
		print(new NumSelect(2010, 2100).setEmptyOpt(false).setId(getId())
				.select(getValue()));
	}
}
