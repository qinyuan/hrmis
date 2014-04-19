$(document).ready(function() {
	postValueAfterFormChange("checkee");
	postValueAfterFormChange("checker");
	var ic = new InputCurtain();
	$('tr[id]').attr('title', '双击即可修改').dblclick(function() {
		$('#addSubmit').hide();
		$('#mdfSubmit').show();
		createModifyForm(this.id);
		ic.show();
		$('#input').show(250);
	});
	$('#cancel').click(function() {
		ic.hide();
		$('#input').hide(250);
	});
	$('#addButton').click(function() {
		$('#mdfSubmit').hide();
		$('#addSubmit').show();
		var checkeeId = $('#checkee').val();
		var checkerId = $('#checker').val();
		if (checkeeId != -1) {
			$('#iCheckee').val(checkeeId);
		}
		if (checkerId != -1) {
			$('#iChecker').val(checkerId);
		}
		ic.show();
		$('#input').show(250);
	});
	$('#delSubmit').click(function(e) {
		if (getSelectedCheckBoxCount() == 0) {
			alert('未选择');
			e.preventDefault();
		} else if (confirm("确定删除？") == false) {
			e.preventDefault();
		}
	});
});