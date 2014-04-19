package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class JsTag extends MyTagSupport{
	
	private String src;
	
	public void setSrc(String src){
		this.src=src;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<script Charset='UTF-8' type='text/javascript' src='");
		if(src.charAt(0)=='/'){
			print(WEB_BASE+src);
		}else{
			print(src);
		}
		if(!src.endsWith(".js")){
			print(".js");
		}
		print("'></script>");
	}
}
