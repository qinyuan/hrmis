var colorBeforeOver;
$(document).ready(function() {
	$('div.body tr').hover(trOver, trOut);
});

function trOver() {
	this.style.backgroundColor = "yellow";
}

function trOut() {
	this.style.backgroundColor = "#eeeeee";
}