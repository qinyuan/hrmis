$(document).ready(function() {
	if ($('div#leave').height() > $('div#accd').height()) {
		$('div#accd').height($('div#leave').height());
	} else {
		$('div#leave').height($('div#accd').height());
	}
	var light = "#dddddd";
	var dark = '#777777';
	$('div#leaveHead').mouseover(showLeave);
	$('div#accdHead').mouseover(showAccd);
	function showLeave() {
		$('div#leaveHead').css("backgroundColor", light);
		$('div#leave').css({
			"backgroundColor" : light,
			"zIndex" : 5
		});
		$('div#accdHead').css("backgroundColor", dark);
		$('div#accd').css({
			"backgroundColor" : dark,
			"zIndex" : 4
		});
		$.cookie("showType", "leave");
	}
	function showAccd() {
		$('div#accdHead').css("backgroundColor", light);
		$('div#accd').css({
			"backgroundColor" : light,
			"zIndex" : 5
		});
		$('div#leave').css("backgroundColor", dark);
		$('div#leaveHead').css({
			"backgroundColor" : dark,
			"zIndex" : 4
		});
		$.cookie("showType", "accd");
	}
	var cookieParam = $.cookie('showType');
	if (cookieParam == null || cookieParam == "leave") {
		showLeave();
	} else if (cookieParam == "accd") {
		showAccd();
	}
});