package qinyuan.lib.web.html;

public class Hidden extends EmptyForm {

	@Override
	public String getTagName() {
		setAttr("type", "hidden");
		return "input";
	}

	@Override
	public Hidden setClass(String style) {
		return (Hidden) super.setClass(style);
	}

	@Override
	public Hidden setAttr(String name, String value) {
		return (Hidden) super.setAttr(name, value);
	}

	@Override
	public Hidden setId(String id) {
		return (Hidden) super.setId(id);
	}

	@Override
	public Hidden setValue(String value) {
		return (Hidden) super.setValue(value);
	}

	@Override
	public Hidden setDisabled(boolean disabled) {
		return (Hidden) super.setDisabled(disabled);
	}

	public static void main(String[] args) {
		System.out.println(new Hidden().setId("testid").setValue("testvalue")
				.setDisabled(true));
	}
}
