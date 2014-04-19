package qinyuan.hrmis.domain.ass;

import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public abstract class AssTpl {

	private int id;
	private AssDept checkee, checker;
	private String item, target, formula;

	public AssTpl(int id, AssDept checkee, AssDept checker, String item,
			String target, String formula) {
		this.id = id;
		this.checkee = checkee;
		this.checker = checker;
		this.item = item;
		this.target = target;
		this.formula = formula;
	}

	public abstract TableData getCheckBoxTd();

	public AssDept getCheckee() {
		return checkee;
	}

	public int getCheckeeId() {
		return checkee.getId();
	}

	public String getCheckeeName() {
		return checkee.getName();
	}

	public Select getCheckeeSelect() {
		return checkee.toSelect("checkee");
	}

	public TableData getCheckeeTd() {
		return getTd("mid", checkee);
	}

	public AssDept getChecker() {
		return checker;
	}

	public int getCheckerId() {
		return checker.getId();
	}

	public String getCheckerName() {
		return checker.getName();
	}

	public Select getCheckerSelect() {
		return checker.toSelect("checker");
	}

	public TableData getCheckerTd() {
		return getTd("mid", checker);
	}

	public String getFormula() {
		return formula;
	}

	public int getId() {
		return id;
	}

	public String getItem() {
		return item;
	}

	public TableData getItemTd() {
		return getTd("mid", getItem());
	}

	public String getTarget() {
		return target;
	}

	public abstract TableRow toTableRow();

	public abstract TableRow toTableRowAsCheckee();

	public abstract TableRow toTableRowAsChecker();

	public final static TableData selectAllTh = getTh("mini",
			"<input type='checkbox' id='selectAll' />");

	protected static TableData getTd(String style, Object text) {
		return new TableData().setClass(style).setText(text.toString());
	}

	protected static TableData getTh(String style, String text) {
		return new TableData(true).setClass(style).setText(text);
	}
}
