package qinyuan.lib.web;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import qinyuan.lib.date.MyDate;
import qinyuan.lib.lang.MyMath;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MyBasicAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String REQUEST_CHARSET = "UTF-8";
	public static final String DATA_PATH_CONF_FILE = "/WEB-INF/conf/dataPath.txt";

	private HttpServletRequest request;
	private ServletContext application;
	private Map<String, Object> session;

	public MyBasicAction() {
		ActionContext actionContext = ActionContext.getContext();
		session = actionContext.getSession();
		request = ServletActionContext.getRequest();
		application = ServletActionContext.getServletContext();

		// set common character encoding
		try {
			request.setCharacterEncoding(REQUEST_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getAppAttribute(String key) {
		return application.getAttribute(key);
	}

	public Object getAttribute(String key) {
		return request.getAttribute(key);
	}

	public String getGetParameter(String paramName) {
		String str = getParameter(paramName);
		if (str == null) {
			return null;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ServletContext getServletContext() {
		return application;
	}

	public String getParameter(String paramName) {
		return request.getParameter(paramName);
	}

	public Set<String> getParameterNames() {
		return request.getParameterMap().keySet();
	}

	public Map<String, String[]> getParameterMap() {
		return request.getParameterMap();
	}

	public String[] getParameters(String key) {
		return request.getParameterValues(key);
	}

	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}

	public Object getSession(String key) {
		return session.get(key);
	}

	public WebFileIO getWebFileIO() {
		return new WebFileIO(getServletContext());
	}

	public boolean hasParameter(String key) {
		return getParameter(key) != null;
	}

	public boolean hasSession(String key) {
		return getSession(key) != null;
	}

	public void printParameters() {
		for (String key : getParameterNames()) {
			System.out.println(key + ": " + getParameter(key));
		}
	}

	public void setAppAttribute(String key, Object obj) {
		application.setAttribute(key, obj);
	}

	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	public void setSession(String key, Object obj) {
		session.put(key, obj);
	}

	public final static boolean empty(String str) {
		return str == null || str.isEmpty();
	}

	public final static boolean isNull(Object obj) {
		return obj == null;
	}

	public final static boolean isNumeric(String str) {
		return MyMath.isNumeric(str);
	}

	public final static boolean isDate(String str) {
		return MyDate.isDate(str);
	}

	public final static double parseDouble(String str) {
		return empty(str) ? 0 : Double.parseDouble(str);
	}

	public final static double[] parseDouble(String[] strs) {
		if (strs == null) {
			return new double[0];
		}
		double[] nums = new double[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nums[i] = parseDouble(strs[i]);
		}
		return nums;
	}

	public final static float parseFloat(String str) {
		return empty(str) ? 0 : Float.parseFloat(str);
	}

	public final static float[] parseFloat(String[] strs) {
		if (strs == null) {
			return new float[0];
		}
		float[] nums = new float[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nums[i] = parseFloat(strs[i]);
		}
		return nums;
	}

	public final static int parseInt(String str) {
		return empty(str) ? 0 : Integer.parseInt(str);
	}

	public final static int[] parseInt(String[] strs) {
		if (strs == null) {
			return new int[0];
		}
		int[] ints = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			ints[i] = parseInt(strs[i]);
		}
		return ints;
	}
}