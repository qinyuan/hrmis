function createModifyForm(trId) {
	var strArr = trId.split('_');
	$('#iId').val(strArr[1]);
	$('#iCheckee').val(strArr[2]);
	$('#iChecker').val(strArr[3]);
	var trObj = $('#' + trId);
	$('#iItem').val(trObj.find('td:eq(3)').text());
	$('#iTarget').text(trObj.find('td:eq(4)').text());
	$('#iUnit').val(trObj.find('td:eq(5)').text());
	$('#iFormula').text(trObj.find('td:eq(6)').text());
	$('#iOther').text(trObj.find('td:eq(7)').text());
}