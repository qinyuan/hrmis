$(document).ready(function() {
	$('#mdfSubmit').click(function(e) {
		if (!checkInput()) {
			e.preventDefault();
		}
	});
	$('#addSubmit').click(function(e) {
		if (!checkInput()) {
			e.preventDefault();
		}
	});
	function checkInput() {
		if (!isNumeric($('#iWeight').val())) {
			alert("分值仅能输入数字");
			$('#iWeight').select();
			return false;
		} else {
			return true;
		}
	}
});
function createModifyForm(trId) {
	var strArr = trId.split('_');
	$('#iId').val(strArr[1]);
	$('#iCheckee').val(strArr[2]);
	$('#iChecker').val(strArr[3]);
	var trObj = $('#' + trId);
	$('#iItem').val(trObj.find('td:eq(3)').text());
	$('#iTarget').text(trObj.find('td:eq(4)').text());
	$('#iWeight').val(trObj.find('td:eq(5)').text());
	$('#iFormula').text(trObj.find('td:eq(6)').text());
}