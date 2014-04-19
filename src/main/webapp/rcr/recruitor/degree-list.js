$(document)
		.ready(
				function() {
					$('tr[id]')
							.attr('title', '双击即可修改')
							.dblclick(
									function() {
										var degreeId = this.id.substring(2);
										if (isNumeric(degreeId)) {
											var oldDegreeName = $(this).find(
													'td:eq(0)').text();
											var newDegreeName = prompt(
													"请输入新的学历名称", oldDegreeName);
											if (newDegreeName != null) {
												location.href = 'degree-list.action?mdfDegreeId='
														+ degreeId
														+ '&mdfDegreeName='
														+ newDegreeName;
											}
										}
									});
				});