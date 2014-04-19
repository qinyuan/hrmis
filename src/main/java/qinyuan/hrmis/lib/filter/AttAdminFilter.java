package qinyuan.hrmis.lib.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import qinyuan.hrmis.domain.user.AttAdmin;

public class AttAdminFilter extends BaseFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = getSession(request);
		// session parameter attAdmin must be set
		AttAdmin attAdmin = (AttAdmin) session.getAttribute(ATT_ADMIN);
		if (attAdmin == null) {
			HttpServletResponse hresponse = (HttpServletResponse) response;
			hresponse.sendRedirect(INDEX_PAGE);
		} else {
			chain.doFilter(request, response);
		}
	}
}
