$(document)
		.ready(
				function() {
					function SubmitData(ajaxSubmitData) {
						strArr = ajaxSubmitData.split('&');
						this.specType = strArr[0].substring(strArr[0]
								.indexOf('=') + 1);

						this.leaveTypeId = null;
						this.accdTypeId = null;
						if (this.specType[0] == 'L') {
							this.leaveTypeId = parseInt(this.specType
									.substring(1));
						} else if (this.specType[0] == 'A') {
							this.accdTypeId = parseInt(this.specType
									.substring(1));
						}

						this.buttonIds = new Array();
						this.userIds = new Array();
						var buttonId;
						for ( var i = 1; i < strArr.length; i++) {
							this.buttonIds[i - 1] = buttonId = strArr[i]
									.substring(strArr[i].indexOf('=') + 5);
							this.userIds[i - 1] = buttonId.substring(2,
									buttonId.indexOf('_'));
						}
					}
					SubmitData.prototype.getAccdTypeId = function() {
						return this.accdTypeId;
					};
					SubmitData.prototype.getButtonIds = function() {
						return this.buttonIds;
					};
					SubmitData.prototype.getLeaveTypeId = function() {
						return this.leaveTypeId;
					};
					SubmitData.prototype.getSpecTypeName = function() {
						return $('#' + this.specType).val();
					};
					SubmitData.prototype.getUserIds = function() {
						return this.userIds;
					};

					var buttons = $('#attT button');
					buttons.hover(function() {
						buttonOverOut(this.id, '#ffff00');
					}, function() {
						buttonOverOut(this.id, '#dddddd');
					});

					var buttonCells = new ButtonCells();
					buttonCells.setButtons(buttons).setSubmitHref(
							'lib/att-matrix-submit.jsp');

					var infoPanel = $('#submitResult');
					$('div.subMenu input').click(
							function() {
								$('div.subMenu').hide(250);
								buttonCells.setSubmitInfo("specType", this.id);
								infoPanel.text("正在添加数据...").css(
										'backgroundColor', '#5599ff').show();
								buttonCells.submitAsAjax(afterSubmit);
							});

					function afterSubmit(result) {
						if ($.trim(result) == "success") {
							var submitData = new SubmitData(buttonCells
									.getSubmitData());
							var buttonIds = submitData.getButtonIds();
							for ( var i in buttonIds) {
								$('#hid_' + buttonIds[i]).remove();
								var buttonObj = $('#' + buttonIds[i]);
								var newText = getNewButtonText(
										buttonObj.text(), submitData
												.getSpecTypeName());
								buttonObj.text(newText).attr("disabled", true)
										.attr('title', newText).css({
											"borderColor" : "white",
											"backgroundColor" : "white"
										});
							}
							var userIds = submitData.getUserIds();
							if (submitData.getSpecTypeName() == '年休假') {
								for ( var i in userIds) {
									var annLeaveObj = $('#al' + userIds[i]);
									annLeaveObj
											.val(getNewAnnLeaveValue(annLeaveObj));
								}
							}

							infoPanel.text("数据添加成功！").css('backgroundColor',
									'lime').fadeOut(2000);
						} else {
							infoPanel.hide();
							alert("数据添加失败！具体原因请询问网页开发人员");
						}
					}

					function buttonOverOut(id, color) {
						var strArr = id.split("_");
						var empId = 'E' + strArr[0].substring(2);
						var dayId = (strArr[0].charAt(1) == 'R' ? 'r' : 'l')
								+ strArr[1];
						$('#' + dayId).css('backgroundColor', color);
						$('#' + empId).css('backgroundColor', color);
					}

					function getNewAnnLeaveValue(annLeaveObj) {
						var strArr = annLeaveObj.val().split(',');
						str = strArr[0] + ',' + (strArr[1] - 0.5);
						return str.indexOf('.') < 0 ? str + '.0' : str;
					}

					function getNewButtonText(oldText, specTypeName) {
						if (oldText.length < 5 || oldText.charAt(2) != ':') {
							return specTypeName;
						} else {
							return oldText.substring(0, 5) + specTypeName;
						}
					}
				});