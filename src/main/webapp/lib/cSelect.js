$(document).ready(function() {
	$('#selectAll').click(function() {
		var checked = this.checked;
		$('input:checkbox[name]').each(function() {
			this.checked = checked;
		});
	});
});
function getSelectedCheckBoxCount() {
	return $('input:checkbox[name]').filter(function() {
		return this.checked == true;
	}).size();
}