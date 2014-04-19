package qinyuan.hrmis.lib.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AssAdminFilter extends BaseFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = getSession(request);
		if (session.getAttribute(ASS_ADMIN) == null) {
			HttpServletResponse hresponse = (HttpServletResponse) response;
			hresponse.sendRedirect(INDEX_PAGE);
		} else {
			chain.doFilter(request, response);
		}
	}
}
