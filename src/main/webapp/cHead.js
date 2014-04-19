$(document).ready(
		function() {
			var anchorToCurrent = $('span.nav a[href="' + getCurrentJspPath()
					+ '"]');
			if (anchorToCurrent.size() > 0) {
				var anchorText = anchorToCurrent.text();
				$("<span><b>" + anchorText + "</b></span>").insertAfter(
						anchorToCurrent);
				anchorToCurrent.remove();
			}			
		});
function isNumeric(num) {
	if (num == undefined || num == null) {
		return false;
	}
	var r = "^(-?\\d+)(\\.\\d+)?$";
	if (num.match(r)) {
		return true;
	} else {
		return false;
	}
}
function getCurrentJspPath() {
	var currentPath = String(location.pathname);
	return currentPath.replace(".action", ".jsp");
}
function getCurrentActionPath() {
	var currentPath = String(location.pathname);
	return currentPath.replace(".jsp", ".action");
}
function postValueAfterFormChange(elementId) {
	var jqueryObj = $('#' + elementId);
	jqueryObj.change(function() {
		location.href = getCurrentActionPath() + "?" + elementId + "="
				+ jqueryObj.val();
	});
}