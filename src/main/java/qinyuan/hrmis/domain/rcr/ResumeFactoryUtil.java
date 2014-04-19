package qinyuan.hrmis.domain.rcr;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import qinyuan.lib.web.SpringUtil;
import qinyuan.lib.web.html.Button;
import qinyuan.lib.web.html.NumSelect;

/**
 * This methods of this class are used to convert ResumeFactory to table
 * 
 * @author qinyuan
 * 
 */
public class ResumeFactoryUtil {

	private ResumeFactoryUtil() {
	}

	private static ResumeUtil reUtil = SpringUtil.getBean("resumeUtil",
			ResumeUtil.class);

	public static String getResumesTableAsRecruitor(ResumeFactory rf) {
		StringBuilder o = new StringBuilder("<table>");
		o.append(reUtil.getTableHead());

		List<Resume> list = rf.getResumes();
		for (Resume resume : list) {
			o.append(reUtil.getTrAsRecruitor(resume));
		}

		o.append("</table>");
		return getFilterHead(rf) + o;
	}

	public static String getResumesTableAsRcrManager(ResumeFactory rf) {
		StringBuilder o = new StringBuilder("<table>");
		o.append(reUtil.getTableHead());

		List<Resume> list = rf.getResumes();
		for (Resume resume : list) {
			o.append(reUtil.getTrAsRcrManager(resume));
		}

		o.append("</table>");
		return getFilterHead(rf) + o;
	}

	private static String getFilterHead(ResumeFactory rf) {
		StringBuilder o = new StringBuilder();
		final String space = "&nbsp;&nbsp;&nbsp;";
		int resumeCount = rf.getResumeCount();
		int pageNum = rf.getPageNum();
		int pageCount = (int) Math.ceil((double) resumeCount
				/ ResumeFactory.PAGE_SIZE);
		o.append("记录数: " + resumeCount);
		o.append(StringUtils.repeat("&nbsp;", 7 - String.valueOf(resumeCount)
				.length()));
		o.append("页码：" + pageNum + "/" + pageCount + space);

		o.append(new Button().setId("previousPage").setDisabled(pageNum == 1)
				.setText("上一页"));
		o.append(space);
		o.append(new Button().setId("nextPage")
				.setDisabled(pageNum == pageCount).setText("下一页"));
		o.append(space);

		if (pageCount > 0) {
			o.append("转到第");
			o.append(new NumSelect(1, pageCount).setId("pageNum")
					.select(rf.getPageNum()).setEmptyOpt(false));
			o.append("页");
		}

		o.append(space + "岗位：" + rf.getPostSelect());
		return o.toString();
	}
}
