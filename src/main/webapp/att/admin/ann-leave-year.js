$(document).ready(function() {
	postValueAfterFormChange("deptId");
	var ic = new InputCurtain();
	$('table tr[id]').attr('title', '双击即可修改').dblclick(function() {
		// after table row clicked, show modify form
		var badgenumber = $(this).find('td:eq(1)').text();
		$('input:hidden[name="mdf_badgenumber"]').val(badgenumber);
		$('#mdf_badgenumber').text(badgenumber);
		$('#mdf_username').text($(this).find('td:eq(2)').text());
		$('#mdf_workDate').val($(this).find('td:eq(3)').text());
		$('#mdf_joinDate').val($(this).find('td:eq(4)').text());
		$('#mdf_insurePlace').val($(this).find('td:eq(5)').text());
		$('#mdf_usabledDays').val($(this).find('td:eq(6)').text());

		ic.show();
		$('#mdfForm').show(250);
	});

	$('button.add').click(function() {
		var parent = $(this).parent().parent();
		var badgenumber = parent.find('td:eq(0)').text();
		$('input:hidden[name="badgenumber"]').val(badgenumber);
		$('#badgenumber').text(badgenumber);
		$('#username').text(parent.find('td:eq(1)').text());
		ic.show();
		$('#addForm').show(250);
	});

	$('button[id$="Cancel"]').click(function() {
		ic.hide();
		$('#' + this.id.replace('Cancel', '') + 'Form').hide(250);
	});
	
	$('#delAnnLeave').click(function(event) {
		if (getSelectedCheckBoxCount() == 0) {
			alert("未选择");
			event.preventDefault();
		} else if (confirm("确定删除？") == false) {
			event.preventDefault();
		}
	});
});