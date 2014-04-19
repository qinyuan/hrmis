$(document).ready(
		function() {
			$('#next').change(function() {
				toWeek(parseInt(this.value));
			});
			$('#previous').change(function() {
				toWeek(parseInt(0 - this.value));
			});
			WdatePicker({
				eCont : 'calendar',
				onpicked : function(dp) {
					window.location.href = "att-detail.action?attDate="
							+ dp.cal.getNewDateStr();
				},
				doubleCalendar : true,
				startDate : $('#startDate').val()
			});
			$('#date').hover(function() {
				$('#calendar').show();
			}, function() {
				$('#calendar').hide();
			});
			function toWeek(weekToAdd) {
				var httpLink = "att-detail.action?weekToAdd=" + weekToAdd;
				window.location.href = httpLink;
			}
		});