package qinyuan.lib.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class ButtonTag extends BodyEleTag {

	@Override
	public void doTag() throws JspException, IOException {
		print("<button type='button'" + getBaseAttr() + ">" + getValue() + "</button>");
	}
}
