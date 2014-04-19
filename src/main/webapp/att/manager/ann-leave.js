$(document).ready(function() {
	$('#year').change(function() {
		var year = this.value;
		window.location.href = "index.jsp?year=" + year;
	});
});