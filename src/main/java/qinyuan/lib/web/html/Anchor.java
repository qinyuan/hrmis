package qinyuan.lib.web.html;

public class Anchor extends BodyElement {

	private String text;

	public Anchor setText(String text) {
		this.text = text;
		return this;
	}

	public Anchor setNew(boolean bool) {
		return bool ? setAttr("target", "_blank") : this;
	}

	public Anchor setHref(String href) {
		return setAttr("href", href);
	}

	@Override
	public Anchor setAttr(String name, String value) {
		return (Anchor) super.setAttr(name, value);
	}

	@Override
	public Anchor setClass(String styel) {
		return (Anchor) super.setAttr("class", styel);
	}

	@Override
	public Anchor setId(String id) {
		return (Anchor) super.setId(id);
	}

	@Override
	public String getBody() {
		return text;
	}

	@Override
	public String getTagName() {
		return "a";
	}

}
