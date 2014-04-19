package qinyuan.hrmis.lib.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class RequestMonitor {

	private RequestMonitor() {
	}

	private final static int MAX_REQUEST_TO_RECORD = 200;
	private final static List<Requester> requesters = new ArrayList<Requester>();
	private final static Set<String> ignoredPages = new HashSet<String>();
	static {
		ignoredPages.add("/hrmis/index.jsp");
		ignoredPages.add("/hrmis/ident-code.jsp");
	}

	public static void addRequest(HttpServletRequest request) {
		String page = request.getRequestURI();

		// ignore pages in '/hrmis/user/'
		if (page.startsWith("/hrmis/user/"))
			return;

		// ignore pages not ends with .jsp or .action
		if (!(page.endsWith(".jsp") || page.endsWith(".action")))
			return;

		// ignore certain files
		if (ignoredPages.contains(page))
			return;

		String ip = request.getRemoteAddr();
		String name = getName(request);

		Requester requester = new Requester();
		requester.setIp(ip);
		requester.setPage(page);
		requester.setName(name);

		requesters.add(requester);

		while (requesters.size() > MAX_REQUEST_TO_RECORD)
			requesters.remove(0);
	}

	public static Table getRecordTable() {
		Table table = new Table();

		TableRow tr = new TableRow(true);
		tr.add("序号，IP，用户名，访问页面，访问时间");
		table.add(tr);

		int index = 1;
		for (Requester requester : requesters) {
			table.add(new TableRow().add(index++).add(requester.toTableRow()));
		}

		return table;
	}

	private static String getName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("visitor");
		if (user == null) {
			return null;
		} else {
			return user.getNickname();
		}
	}
}
