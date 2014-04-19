package qinyuan.lib.web.html;

public class Radio extends EmptyForm {

	@Override
	public String getTagName() {
		setAttr("type", "radio");
		return "input";
	}

	@Override
	public Radio setClass(String style) {
		return (Radio) super.setClass(style);
	}

	@Override
	public Radio setAttr(String name, String value) {
		return (Radio) super.setAttr(name, value);
	}

	@Override
	public Radio setId(String id) {
		return (Radio) super.setId(id);
	}

	@Override
	public Radio setValue(String value) {
		return (Radio) super.setValue(value);
	}

	@Override
	public Radio setDisabled(boolean disabled) {
		return (Radio) super.setDisabled(disabled);
	}

	public Radio setChecked(boolean checked) {
		return checked ? setAttr("checked", "checked") : this;
	}

	public static void main(String[] args) {
		System.out.println(new Radio().setId("testid").setValue("testvalue")
				.setDisabled(true));
		System.out.println(new Radio().setId("id").setChecked(true));
	}
}
