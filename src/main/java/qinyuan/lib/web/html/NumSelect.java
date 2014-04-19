package qinyuan.lib.web.html;

public class NumSelect extends Select {

	private int startIndex;
	private int endIndex;
	private boolean emptyOption = true;

	public NumSelect(int startIndex, int endIndex) {
		if (startIndex <= endIndex) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		} else {
			this.startIndex = endIndex;
			this.endIndex = startIndex;
		}
	}

	@Override
	public NumSelect addOption(int value, Object text) {
		return (NumSelect) super.addOption(value, text);
	}

	@Override
	public NumSelect addOption(Object value, Object text) {
		return (NumSelect) super.addOption(value, text);
	}

	@Override
	public NumSelect select(int value) {
		return (NumSelect) super.select(value);
	}

	@Override
	public NumSelect select(String value) {
		return (NumSelect) super.select(value);
	}

	@Override
	public NumSelect setId(String id) {
		return (NumSelect) super.setId(id);
	}

	@Override
	public NumSelect setAttr(String name, String value) {
		return (NumSelect) super.setAttr(name, value);
	}

	public NumSelect setClass(String style) {
		return (NumSelect) super.setClass(style);
	}

	public NumSelect setEmptyOpt(boolean bool) {
		emptyOption = bool;
		return this;
	}

	@Override
	public String getBody() {
		if (emptyOption) {
			addOption("", "");
		}
		for (int i = startIndex; i <= endIndex; i++) {
			addOption(i, String.valueOf(i));
		}
		return super.getBody();
	}

	public static void main(String[] args) {
		NumSelect numForm = new NumSelect(15, 10).select(11).setId("id")
				.setEmptyOpt(true);
		System.out.println(numForm.toString());
	}
}
