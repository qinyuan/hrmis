package qinyuan.lib.web.html;

public class Text extends EmptyForm {

	@Override
	public Text setId(String id) {
		return (Text) super.setId(id);
	}

	@Override
	public Text setClass(String style) {
		return (Text) super.setClass(style);
	}

	@Override
	public Text setValue(String value) {
		return (Text) super.setValue(value);
	}

	@Override
	public Text setDisabled(boolean disabled) {
		return (Text) super.setDisabled(disabled);
	}

	@Override
	public Text setAttr(String name, String value) {
		return (Text) super.setAttr(name, value);
	}

	@Override
	public String getTagName() {
		setAttr("type", "text");
		return "input";
	}

	public static void main(String[] args) {
		System.out.println(new Text().setId("testid").setValue("testvalue"));
	}
}
