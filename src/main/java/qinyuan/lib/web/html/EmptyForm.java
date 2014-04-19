package qinyuan.lib.web.html;

public abstract class EmptyForm extends EmptyElement {

	@Override
	public EmptyForm setId(String id) {
		super.setId(id);
		return setAttr("name", id);
	}

	@Override
	public EmptyForm setAttr(String name, String value) {
		return (EmptyForm) super.setAttr(name, value);
	}

	public EmptyForm setValue(String value) {
		return setAttr("value", value);
	}

	public EmptyForm setValue(int value) {
		return setValue(String.valueOf(value));
	}

	public EmptyForm setDisabled(boolean disabled) {
		return disabled ? setAttr("disabled", "disabled") : this;
	}
}
