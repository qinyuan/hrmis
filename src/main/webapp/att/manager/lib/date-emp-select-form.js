$(document).ready(
		function() {
			$.each([ $('#startDate'), $('#endDate') ], function(i, val) {
				val.attr('readonly', 'readonly').css('width', '90px');
				val.click(function() {
					var dateObj = this;
					WdatePicker({
						onpicked : function() {
							location.href = getCurrentActionPath() + "?"
									+ dateObj.id + "=" + dateObj.value;
						}
					});
				});
			});
			postValueAfterFormChange('userId');
		});