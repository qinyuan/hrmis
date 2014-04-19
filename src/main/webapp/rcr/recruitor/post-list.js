$(document).ready(
		function() {
			$('tr[id]').attr("title", "双击即可修改岗位名称").dblclick(
					function() {
						var postId = this.id.substring(2);
						if (isNumeric(postId)) {
							var oldPostName = $(this).find('td:eq(0)').text();
							var newPostName = prompt("请输入新的岗位名称", oldPostName);
							if (newPostName != null) {
								var href = "post-list.action?mdfPostId="
										+ postId + "&mdfPostName="
										+ newPostName;
								location.href = href;
							}
						}
					});
		});