package qinyuan.hrmis.domain.ass;

class AssItem {

	private AssMon mon;
	private String data;
	private float result;

	public AssItem(AssMon mon, String data, float result) {
		this.mon = mon;
		this.data = data;
		this.result = result;
	}

	public String getMon() {
		return mon.toString();
	}

	public String getData() {
		return data == null ? "" : data;
	}

	public float getResult() {
		return result;
	}
}
