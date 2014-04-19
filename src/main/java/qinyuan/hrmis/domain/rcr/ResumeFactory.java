package qinyuan.hrmis.domain.rcr;

import java.util.ArrayList;
import java.util.List;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.Select;

public class ResumeFactory {

	public final static int PAGE_SIZE = 20;
	private final static Post[] emptyPostArr = new Post[0];

	private int pageNum = 1;
	private int postId = -1;
	private Post[] posts = emptyPostArr;

	public int getResumeCount() {
		if (posts.length == 0) {
			return 0;
		}
		String query = "SELECT COUNT(*) FROM rcr_resume WHERE post_id";
		if (postId > 0) {
			query += "=" + postId;
		} else {
			query += getInClause(posts);
		}
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			cnn.next();
			return cnn.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getPageCount() {
		double resumeCount = getResumeCount();
		return (int) Math.ceil(resumeCount / PAGE_SIZE);
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPostId() {
		return postId;
	}

	public Select getPostSelect() {
		Select select = new Select().setId("postId").select(postId);
		if (posts.length > 1) {
			select.addOption("-1", "(全部)");
		}
		for (Post post : posts) {
			select.addOption(post.getId(), post.getName());
		}
		return select;
	}

	public List<Resume> getResumes() {
		if (posts.length == 0) {
			return new ArrayList<Resume>();
		}

		int startRow = (pageNum - 1) * PAGE_SIZE;
		String query = "FROM Resume AS r WHERE r.postId";
		if (postId > 0) {
			query += "=" + postId;
		} else {
			query += getInClause(posts);
		}
		query += " ORDER BY r.statusId,r.postId,r.resumeId DESC";
		try (HbnConn cnn = new HbnConn()) {
			List<Resume> list = cnn.createList(Resume.class, query, startRow,
					PAGE_SIZE);
			return list;
		} catch (Exception e) {
			return new ArrayList<Resume>();
		}
	}

	public void setPageNum(int pageNum) {
		if (pageNum > 0) {
			this.pageNum = pageNum;
		}
	}

	public void setPostId(int postId) {
		if (postId < 0) {
			this.postId = postId;
			pageNum = 1;
			return;
		}
		for (Post post : posts) {
			if (post.getId() == postId) {
				this.postId = postId;
				pageNum = 1;
				return;
			}
		}
	}

	public void setPost(Post[] posts) {
		this.posts = posts;
	}

	public void nextPage() {
		pageNum++;
	}

	public void previousPage() {
		if (pageNum > 1) {
			pageNum--;
		}
	}

	private static String getInClause(Post[] posts) {
		StringBuilder o = new StringBuilder();
		o.append(" IN (");
		for (int i = 0; i < posts.length; i++) {
			if (i > 0) {
				o.append(",");
			}
			o.append(posts[i].getId());
		}
		o.append(")");
		return o.toString();
	}

	public static void main(String[] args) {
		ResumeFactory rf = new ResumeFactory();
		// rf.setPost(Post.getPosts());
		System.out.println(rf.getResumeCount());
	}
}
