$(document).ready(function() {
	var ic = new InputCurtain();
	$('#showAddForm').click(function() {
		ic.show();
		$('#addForm').show(250);
	});
	$('input:submit[id$=lockSubmit]').click(function(event) {
		if (getSelectedCheckBoxCount() == 0) {
			alert("月份未选择");
			event.preventDefault();
		}
	});
	$('#delSubmit').click(function(event) {
		if (getSelectedCheckBoxCount() == 0) {
			alert("月份未选择");
			event.preventDefault();
		} else if (confirm("确定删除？") == false) {
			event.preventDefault();
		}
	});
	$('button[id$="Cancel"]').click(function() {
		ic.hide();
		$('#' + this.id.replace('Cancel', 'Form')).hide(250);
	});
	$('tr[id]').attr('title', '双击即可修改').dblclick(function() {
		$('#mdfId').val(this.id.substring(2));
		$('#mdfYear').val($(this).find('td:eq(1)').text());
		$('#mdfMon').val($(this).find('td:eq(2)').text());
		ic.show();
		$('#mdfForm').show(250);
	});
});