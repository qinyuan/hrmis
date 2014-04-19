$(document).ready(
		function() {
			var ic = new InputCurtain().add("addForm");
			$('#addUserOK').click(
					function() {
						location.href = "user-list.action?username="
								+ $('#username').val() + "&password="
								+ $('#password').val() + "&nickname="
								+ $('#nickname').val();
					});
			$('#createRandom').click(function() {
				$('#randomText').val(Math.round(Math.random() * 1000000));
			});
			$('#addUserButton').click(function() {
				ic.show();
			});
			$('#cancel').click(function() {
				ic.hide();
			});
			$('#delUser').click(function(event) {
				if (getSelectedCheckBoxCount() == 0) {
					alert("未选择用户");
					event.preventDefault();
				} else if (confirm("确定删除？") == false) {
					event.preventDefault();
				}
			});
			var result = $.trim($('#result').val());
			if (result != "") {
				alert(result);
			}
		});