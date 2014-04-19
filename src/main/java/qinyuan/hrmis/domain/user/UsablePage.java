package qinyuan.hrmis.domain.user;

public class UsablePage {

	private String href;
	private String text;

	public UsablePage(String href, String text) {
		this.href = href;
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public String getText() {
		return text;
	}
}
