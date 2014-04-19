$(document).ready(function() {
	$('#searchFile').click(function() {
		var applicant = $.trim($('#applicant').val());
		if (applicant == '') {
			alert("姓名未填写！");
			$('#applicant').select();
		} else {
			$('#fileList option').remove();
			var href = "getResumeFiles.jsp?applicant=" + applicant;
			$.get(href, function(data) {
				var trimData = $.trim(data);
				if (trimData == '') {
					alert('未找到与"' + applicant + '"对应的文件！');
					return;
				}
				
				var fileNames = trimData.split(',');
				for ( var i in fileNames) {
					$(createOption(fileNames[i])).appendTo($('#fileList'));
				}

				$('#fileList option').each(function() {
					this.title = this.value;
					this.ondblclick = function() {
						$('#resumeLink').val(this.value);
						$('#resumeLink').select();
					};
				});
			});
		}
	});

	function createOption(value) {
		return '<option value="' + value + '">' + value + '</option>';
	}
});