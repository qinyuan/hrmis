$(document).ready(function() {
	$('td:contains("添加成功")').css("color", "green");
	$('td:contains("添加失败")').css("color", "red");
	$('#startHour').change(startTimeChange);
	$('#startMin').change(startTimeChange);
	$('#endHour').change(endTimeChange);
	$('#endMin').change(endTimeChange);
	$('#leave').change(function() {
		$.cookie('leaveAddSelectedLeaveTypeId', this.value);
	});
	var leaveTypeId = $.cookie('leaveAddSelectedLeaveTypeId');
	if (leaveTypeId != null) {
		$('#leave').val(leaveTypeId);
	}
});

function startTimeChange() {
	var startTime = $("#startHour").val() + ":" + $('#startMin').val() + ":00";
	location.href = "leave-add.action?startTime=" + startTime;
}
function endTimeChange() {
	var endTime = $('#endHour').val() + ":" + $('#endMin').val() + ":00";
	location.href = "leave-add.action?endTime=" + endTime;
}