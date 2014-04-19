$(document).ready(function() {
	$('<div id="annLeave"></div>').appendTo('body');
	$('#annLeave').css({
		'position' : 'absolute',
		'width' : '180px',
		'backgroundColor' : 'black',
		'color' : 'white',
		'zIndex' : '6'
	});
	var lastUserId = null;
	$('#attT button').hover(function() {
		var userId = this.id;
		userId = userId.substring(2, userId.indexOf('_'));
		if (userId != lastUserId) {
			$('#annLeave').text($('#al' + userId).val().replace(',', ',剩余年休'));
			lastUserId = userId;
		}
		$('#annLeave').show();
	}, function() {
		$('#annLeave').hide();
	});
	$(document).mousemove(function(e) {
		$('#annLeave').css({
			'left' : e.pageX + 50,
			'top' : e.pageY - 20
		});
	});
});