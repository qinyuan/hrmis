$(document).ready(function() {
	$('#delSubmit').click(function(e) {
		if (getSelectedCheckBoxCount() == 0) {
			alert("未选择");
			e.preventDefault();
		} else if (confirm("确定删除？") == false) {
			e.preventDefault();
		}
	});
	$('tr[id]').attr("title", "双击修改部门名称").dblclick(function() {
		var id = this.id.substring(2);
		var oldDeptName = $(this).find('td:eq(1)').text();
		var newDeptName = prompt("请输入新的部门名称：", oldDeptName);
		if (newDeptName != null) {
			href = "dept-list?mdfId=" + id + "&mdfName=" + newDeptName;
			location.href = href;
		}
	});
});