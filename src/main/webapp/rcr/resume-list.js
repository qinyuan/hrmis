$(document).ready(function() {
	var lastColor = null;
	$('tr').hover(function() {
		lastColor = this.style.backgroundColor;
		this.style.backgroundColor = "yellow";
	}, function() {
		this.style.backgroundColor = lastColor;
	});

	$('#previousPage').click(function() {
		location.href = getCurrentActionPath() + "?previousPage=true";
	});
	$('#nextPage').click(function() {
		location.href = getCurrentActionPath() + "?nextPage=true";
	});
	postValueAfterFormChange("pageNum");
	postValueAfterFormChange("postId");
});