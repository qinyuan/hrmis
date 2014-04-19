$(document).ready(function() {
	$('input:checkbox').each(function() {
		if (!this.checked) {
			$(this).parent().css('color', 'darkgrey');
		}
	});
	$('input:checkbox').click(function() {
		if (!this.checked) {
			$(this).parent().css('color', 'darkgrey');
		} else {
			$(this).parent().css('color', 'black');
		}
	});
});