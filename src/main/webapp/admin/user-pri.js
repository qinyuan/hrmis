$(document).ready(function() {
	$('input[type=checkbox]').each(function() {
		if (this.checked === false) {
			$(this).parent().css("color", "darkgray");
		}
		$(this).parent().css("margin", "8px");
		this.onclick = function() {
			if (this.checked === true) {
				$(this).parent().css("color", "black");
			} else {
				$(this).parent().css("color", "darkgray");
			}
		};
	});
});