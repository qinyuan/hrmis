package qinyuan.lib.web.html;

public class Submit extends EmptyForm {

	@Override
	public String getTagName() {
		setAttr("type", "submit");
		return "input";
	}

	@Override
	public Submit setClass(String style) {
		return (Submit) super.setClass(style);
	}

	@Override
	public Submit setAttr(String name, String value) {
		return (Submit) super.setAttr(name, value);
	}

	@Override
	public Submit setId(String id) {
		return (Submit) super.setId(id);
	}

	@Override
	public Submit setValue(String value) {
		return (Submit) super.setValue(value);
	}

	@Override
	public Submit setDisabled(boolean disabled) {
		return (Submit) super.setDisabled(disabled);
	}

	public static void main(String[] args) {
		System.out.println(new Submit().setId("testid").setValue("testvalue")
				.setDisabled(true));
	}
}
