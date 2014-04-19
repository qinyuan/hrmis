package qinyuan.lib.web;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to show navigation links
 * 
 * @author qinyuan
 * 
 */
public class NaviLinks {

	private List<String> hrefs = new ArrayList<String>();
	private List<String> texts = new ArrayList<String>();
	private String hrefBase = "";
	private String splitStr = "";
	private String closeTag = "span";

	public void setTag(String tagName) {
		this.closeTag = tagName;
	}

	public void setHrefs(List<String> hrefs) {
		this.hrefs = hrefs;
	}

	public void setTexts(List<String> texts) {
		this.texts = texts;
	}

	public void setHrefBase(String hrefBase) {
		this.hrefBase = hrefBase.endsWith("/") ? hrefBase : (hrefBase + "/");
	}

	public void setSplitStr(String splitStr) {
		this.splitStr = splitStr;
	}

	public String toString() {
		StringBuilder o = new StringBuilder();
		boolean first = true;
		int hrefCount = Math.min(hrefs.size(), texts.size());
		for (int i = 0; i < hrefCount; i++) {
			if (first) {
				first = false;
			} else {
				o.append(splitStr);
			}

			o.append("<" + closeTag + " class='nav'><a href='" + hrefBase
					+ hrefs.get(i) + "'>" + texts.get(i) + "</a></" + closeTag
					+ ">");
		}
		return o.toString();
	}

	public static void main(String[] args) {
		NaviLinks links = new NaviLinks();
		links.setHrefBase("base");

		List<String> hrefs = new ArrayList<String>();
		hrefs.add("link1");
		hrefs.add("link2");
		hrefs.add("link3");
		links.setHrefs(hrefs);

		List<String> texts = new ArrayList<String>();
		texts.add("text1");
		texts.add("text2");
		texts.add("text3");
		links.setTexts(texts);

		links.setSplitStr("|");

		System.out.println(links);

		NaviLinks links2 = SpringUtil.getBean("headNavi", NaviLinks.class);
		System.out.println(links2);
	}
}
