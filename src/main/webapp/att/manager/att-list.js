$(document).ready(function() {
	var buttonCells = new ButtonCells();
	buttonCells.setLinkForm("mainForm");
	buttonCells.setButtons($('button'));

	$('input[type=submit]').each(function() {
		this.name = this.id.toString();
	});
});