$(document).ready(function() {
	var ic = new InputCurtain();
	$('tr[id]').attr('title', '双击即可修改数据').dblclick(function() {
		if ($('#monLocked').val() == "true") {
			alert("本月数据已锁定，不能再修改");
		} else {
			ic.show();
			showInput(this.id);
		}
	});

	$('#assScoreSubmit').click(validateInput);
	$('#assValueSubmit').click(validateInput);

	$('button[id$="Close"]').click(function() {
		ic.hide();
		$('#' + this.id.replace('Close', 'Input')).hide(250);
	});
	function validateInput(event) {
		var inputResult = $('#result').val();
		if (inputResult == null) {
			alert("出现未知错误");
			event.preventDefault();
			return;
		} else if (!isNumeric(inputResult)) {
			alert("考核结果只能输入数字");
			event.preventDefault();
			return;
		}
	}
});
function showInput(trId) {
	var trObj = $('#' + trId);
	var strArr = trId.split('_');
	var colCount = null;
	if (strArr[0] == 's') {
		colCount = 7;
		$('#hidScoreId').val(strArr[1]);
		$('#scoreInput').show(250);
	} else {
		colCount = 8;
		$('#hidValueId').val(strArr[1]);
		$('#valueInput').show(250);
	}

	for ( var i = 1; i < colCount - 1; i++) {
		$('#' + trId[0] + 'i' + i).text(getTdText(i - 1));
	}

	// set data form
	$('#data').remove();
	var dataTdId = strArr[0] + 'i' + (colCount - 1);
	var dataElement = '<textarea id="data" name="data" cols="70" rows="10">'
			+ getTdText(colCount - 2) + '</textarea>';
	$(dataElement).appendTo('#' + dataTdId);

	// set result form
	$('#result').remove();
	var resultTdId = strArr[0] + 'i' + colCount;
	var resultElement = '<input type="text" id="result" name="result" value="'
			+ getTdText(colCount - 1) + '" />';
	$(resultElement).appendTo('#' + resultTdId);

	$('#data').select();

	function getTdText(index) {
		return trObj.find('td:eq(' + index + ')').text();
	}
}