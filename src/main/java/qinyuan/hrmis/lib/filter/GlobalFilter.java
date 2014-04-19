package qinyuan.hrmis.lib.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;


public class GlobalFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		RequestMonitor.addRequest((HttpServletRequest) request);
		super.doFilter(request, response, filterChain);
	}
}
