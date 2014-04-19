package qinyuan.hrmis.lib.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qinyuan.hrmis.domain.att.admin.SimpleAttManager;
import qinyuan.hrmis.domain.user.AttManager;

public class AttManagerFilter extends BaseFilter {

	private final static String NO_DEPT_SET_PAGE = "/hrmis/att/no-dept-set-error.jsp";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = getSession(request);
		HttpServletResponse hresponse = (HttpServletResponse) response;

		// session parameter attManager must be set
		AttManager attManager = (AttManager) session.getAttribute(ATT_MANAGER);
		if (attManager == null) {
			hresponse.sendRedirect(INDEX_PAGE);
			return;
		}

		// the department of attManager must be set
		Boolean attDeptSet = (Boolean) session.getAttribute("attDeptSet");
		if (attDeptSet == null) {
			SimpleAttManager sam = new SimpleAttManager(attManager.getUserid());
			if (sam.getDept() == null) {
				attDeptSet = false;
			} else {
				attDeptSet = true;
			}
			session.setAttribute("attDeptSet", attDeptSet);
		}
		if (!attDeptSet) {
			hresponse.sendRedirect(NO_DEPT_SET_PAGE);
			return;
		}

		chain.doFilter(request, response);
	}
}
