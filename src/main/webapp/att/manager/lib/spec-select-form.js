$(document).ready(function() {
	var menus = [ $('#norLeaveMenu'), $('#othLeaveMenu'), $('#accdMenu') ];
	for (index in menus) {
		menus[index].hover(function() {
			$('#' + this.id + ' .subMenu').show();
		}, function() {
			$('#' + this.id + ' .subMenu').hide();
		});
	}
});