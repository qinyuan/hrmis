$(document).ready(function() {
	var result = $.trim($('input:hidden[id="result"]').val());
	if (result != "") {
		alert(result);
	}
});