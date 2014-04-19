package qinyuan.lib.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {

	private static String[] beanFiles = { "NaviLinksBean.xml",
			"ResumeUtilBean.xml" };

	private SpringUtil() {
	}

	private static ApplicationContext ctx;

	public static <T> T getBean(String id, Class<T> requireType) {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(beanFiles);
		}
		return ctx.getBean(id, requireType);
	}
}
