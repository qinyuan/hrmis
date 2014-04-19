$(document).ready(function() {
	var ic = new InputCurtain().add("recoverInputForm");
	$('#backup').click(function() {
		window.open("backup.action");
	});
	$('#recover').click(function() {
		ic.show();
	});
	$('#cancel').click(function() {
		ic.hide();
	});
	$('#recoverSubmit').click(function(event) {
		var file = $('#upload').val();
		if (file == null || file == "") {
			alert("文件未选择");
			event.preventDefault();
		} else {
			if (confirm("确定恢复？如选择确定，现有数据将被覆盖！") == false) {
				event.preventDefault();
			}
		}
	});
});