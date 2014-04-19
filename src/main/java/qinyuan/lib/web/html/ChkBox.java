package qinyuan.lib.web.html;

public class ChkBox extends EmptyForm {

	@Override
	public String getTagName() {
		setAttr("type", "checkbox");
		return "input";
	}

	@Override
	public ChkBox setClass(String style) {
		return (ChkBox) super.setClass(style);
	}

	@Override
	public ChkBox setAttr(String name, String value) {
		return (ChkBox) super.setAttr(name, value);
	}

	@Override
	public ChkBox setId(String id) {
		return (ChkBox) super.setId(id);
	}

	@Override
	public ChkBox setValue(String value) {
		return (ChkBox) super.setValue(value);
	}

	@Override
	public ChkBox setValue(int value) {
		return (ChkBox) super.setValue(value);
	}

	@Override
	public ChkBox setDisabled(boolean disabled) {
		return (ChkBox) super.setDisabled(disabled);
	}

	public ChkBox setChecked(boolean checked) {
		return checked ? setAttr("checked", "checked") : this;
	}

	public static void main(String[] args) {
		System.out.println(new ChkBox().setId("testid").setValue("testvalue")
				.setDisabled(true));
		System.out.println(new ChkBox().setId("id").setChecked(true));
	}
}
