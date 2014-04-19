package qinyuan.lib.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class PasswordTag extends BodyEleTag {
	
	@Override
	public void doTag() throws JspException, IOException {
		print("<input type='password'" + getBaseAttr() + " />");
	}

}
