package qinyuan.hrmis.lib.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class define some important constant strings, at the same time define a
 * useful method getSession. This class is extends by AdminFilter and AttFilter
 * and so on
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFilter implements Filter {

	protected final static String ADMIN = "admin";
	protected final static String ATT_ADMIN = "attAdmin";
	protected final static String ATT_MANAGER = "attManager";
	protected final static String ASS_ADMIN = "assAdmin";
	protected final static String ASS_DEALER = "assDealer";
	protected final static String RCR_MANAGER = "rcrManager";
	protected final static String RECRUITOR = "recruitor";
	protected final static String INDEX_PAGE = "/hrmis/user/returnIndex.jsp";

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	protected HttpSession getSession(ServletRequest request) {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		return hrequest.getSession();
	}
}