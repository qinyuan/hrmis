$(document).ready(function() {
	var checkboxes = $('input:checkbox');
	checkboxes.each(function() {
		if (!this.checked) {
			$(this).parent().css("color", "darkgray");
		}
	});
	checkboxes.click(function() {
		if (this.checked) {
			$(this).parent().css("color", "black");
		} else {
			$(this).parent().css("color", "darkgray");
		}
	});
});