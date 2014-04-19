package qinyuan.lib.web.html;

public class Button extends BodyElement {

	private String text;

	public Button setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getBody() {
		return text;
	}

	@Override
	public String getTagName() {
		setAttr("type", "button");
		return "button";
	}

	@Override
	public Button setAttr(String name, String value) {
		return (Button) super.setAttr(name, value);
	}

	@Override
	public Button setId(String id) {
		super.setId(id);
		return setAttr("name", id);
	}

	@Override
	public Button setClass(String style) {
		return setAttr("class", style);
	}

	public Button setDisabled(boolean disabled) {
		return disabled ? setAttr("disabled", "disabled") : this;
	}
}