$(document).ready(function(){
	// add events for employee table row
	$('#empT tr').hover(empRowOverOut, empRowOverOut);

	var searchTextObj = document.getElementById("searchText");
	searchTextObj.onkeyup = searchTextChange;
	var searchText = $.cookie('searchText');
	if (searchText != null) {
		searchTextObj.value = searchText;
		selectEmployee();
	}
});

function buttonIdToColNum(buttonId) {
	var dayOfWeek = parseInt(buttonId.charAt(buttonId.length - 1));
	var reachOrLeave = buttonId.charAt(1);
	var colNum = dayOfWeek * 2;
	if (reachOrLeave == 'L') {
		colNum++;
	}
	return colNum;
}

function colNumToButtonId(colNum, userId) {
	var reachOrLeave = colNum % 2;
	var dayOfWeek = parseInt(colNum / 2);
	var buttonId = "B";
	if (reachOrLeave == 0) {
		buttonId += "R";
	} else {
		buttonId += "L";
	}
	buttonId += userId + "_" + dayOfWeek;
	return buttonId;
}

function empRowOverOut(event) {
	var subMenuId = (this.id).replace("E", "esm");
	if (event.type == "mouseenter") {
		var userId = this.id.substring(1);
		var td = '<td id="' + subMenuId + '">';
		td += '<a href="spec-list.action?userId=' + userId + '">更正</a>';
		td += ' <a href="leave-add.action?userId=' + userId + '">添加请假</a>';
		td += '</td>';
		$(td).appendTo('#' + this.id);
	} else {
		$('#' + subMenuId).remove();
	}
}

function getUserIdFromButtonId(buttonId) {
	return buttonId.substring(2, buttonId.indexOf("_"));
}

function searchTextChange() {
	$.cookie('searchText', this.value);
	selectEmployee();
}

function selectEmployee() {
	$('#empT tr').css("color", "black");
	text = $.cookie('searchText');
	if (text == null || text == "") {
		return;
	}

	var obj = $('#empT').find('td:contains(' + text + ')');
	if (obj.size() == 0) {
		return;
	}

	var trId = obj.parent().get(0).id;

	var trObj = document.getElementById(trId);
	trObj.style.color = "red";
	var pos = $('#' + trId).offset().top - 100;
	$("html,body").animate({
		scrollTop : pos
	}, 500);
}