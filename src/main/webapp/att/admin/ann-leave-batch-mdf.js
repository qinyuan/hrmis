$(document).ready(function() {
	var ic = new InputCurtain();
	$('#filterShow').click(function() {
		ic.show();
		$('div#filter').show(250);
	});
	$('button[id$="Cancel"]').click(function() {
		ic.hide();
		$('div#' + this.id.replace('Cancel', '')).hide(250);
	});

	var submits = [ 'reduce', 'add', 'set' ];
	for ( var i in submits) {
		$('#' + submits[i] + 'Show').click(function(e) {
			if (getSelectedCheckBoxCount() == 0) {
				alert("未选择");
			} else {
				ic.show();
				$('#' + this.id.replace('Show', '')).show(250);
				$('#' + this.id.replace('Show', 'Value')).select();
			}
		});
		$('#' + submits[i] + 'Submit').click(function(e) {
			var value = $('#' + this.id.replace('Submit', 'Value')).val();
			if (!isNumeric(value)) {
				alert("请输入正确的数字格式");
				e.preventDefault();
			}
		});
	}
});