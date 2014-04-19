$(document).ready(function() {
	var thickBorder = "4px solid black";
	var thinBorder = "1px solid darkgray";
	$('table').css({
		"borderTop" : thickBorder,
		"borderBottom" : thickBorder,
		"borderCollapse" : "collapse",
		"margin" : "5px"
	});
	$('th').css({
		"borderBottom" : "2px solid black",
		"borderLeft" : thinBorder,
		"borderRight" : thinBorder,
		"padding" : "4px"
	});
	$('td').css({
		"border" : thinBorder,
		"padding" : "4px"
	});

	var trs = null;
	$('tr').hover(function() {
		var ip = $(this).children('td:eq(1)').text();
		if (ip == null || ip == "") {
			trs = $(this);
		} else {
			trs = $('td:contains(' + ip + ')').parent();
		}
		trs.css('backgroundColor', 'yellow');
	}, function() {
		if (trs) {
			trs.css('backgroundColor', 'white');
		}
	});
});