$(document).ready(function() {
	$('#mdfAccessPath').click(function() {
		var path = prompt("新路径名：", $('#accessPath').attr("value"));
		if (path != null) {
			location.href = "att-data.action?accessPath=" + path;
		}
	});
});