package qinyuan.hrmis.action.rcr.recruitor;

import qinyuan.hrmis.domain.rcr.Post;

public class PostListAction extends RecruitorAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		String postId = getParameter("delPostId");
		if (isNumeric(postId)) {
			deletePost(parseInt(postId));
		}

		if (hasParameter("addPostSubmit")) {
			String postName = getParameter("postName");
			if (postName != null) {
				if (Post.hasPost(postName)) {
					setAttribute("result", "添加的岗位名称与原有岗位名称重复");
				} else {
					recruitor.addPost(postName);
				}
			}
		}

		String mdfPostId = getParameter("mdfPostId");
		String mdfPostName = getGetParameter("mdfPostName");
		if (isNumeric(mdfPostId) && !empty(mdfPostName)) {
			if (Post.hasPost(mdfPostName)) {
				setAttribute("result", "新的岗位名称与其他岗位重复");
			} else {
				recruitor.mdfPost(parseInt(mdfPostId), mdfPostName);
			}
		}

		return SUCCESS;
	}

	private void deletePost(int postId) {
		Post post = Post.getPost(postId);
		if (post == null)
			return;

		if (post.isUsed()) {
			setAttribute("result", "岗位'" + post.getName()
					+ "'已被部分简历使用，在删除这些简历前不允许删除此岗位");
		} else {
			recruitor.deletePost(postId);
		}
	}
}
