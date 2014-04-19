$(document).ready(
		function() {
			postValueAfterFormChange("deptId");
			$('tr[id]').attr('title', '双击即可修改').dblclick(
					function() {
						var reason = prompt("原因：", $(this).children('td:eq(3)')
								.text());
						if (reason != null) {
							$.post("spec-emp.action?mdfReason=true&userId="
									+ this.id.substring(2) + "&reason="
									+ reason, function() {
								location.reload(true);
							});
						}
					});
			$('#deleteSubmit').click(function(event) {
				if (getSelectedCheckBoxCount() == 0) {
					alert("未选择");
					event.preventDefault();
				} else if (confirm("确定删除？") == false) {
					event.preventDefault();
				}
			});
			var ic = new InputCurtain().add("add");
			$('#addSpecEmp').click(function() {
				ic.show();
			});
			$('#addUserCancel').click(function() {
				ic.hide();
			});
		});