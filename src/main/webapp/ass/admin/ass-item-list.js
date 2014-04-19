$(document).ready(function() {
	postValueAfterFormChange("checkee");
	postValueAfterFormChange("checker");
	postValueAfterFormChange("monId");

	var ic = new InputCurtain();
	$('tr[id]').attr('title', '双击即可修改').dblclick(function(e) {
		if ($('#monLocked').val() == "true") {
			alert("本月数据已锁定，不允许修改！");
			e.preventDefault();
			return;
		}

		ic.show();
		if (this.id[0] == 'v') {
			showValueMdfForm(this.id);
		} else if (this.id[0] == 's') {
			showScoreMdfForm(this.id);
		}
	});
	$('#addScoreButton').click(function() {
		if ($('#monLocked').val() == "true") {
			alert("本月数据已锁定，不允许添加！");
		} else {
			ic.show();
			$('#score').show(250);
			$('#sCheckee').val($('#checkee').val());
			$('#sChecker').val($('#checker').val());
			$('#scoreSubmit').hide();
			$('#scoreAddSubmit').show();
		}
	});
	$('#addValueButton').click(function() {
		if ($('#monLocked').val() == "true") {
			alert("本月数据已锁定，不允许添加！");
		} else {
			ic.show();
			$('#value').show(250);
			$('#vCheckee').val($('#checkee').val());
			$('#vChecker').val($('#checker').val());
			$('#valueSubmit').hide();
			$('#valueAddSubmit').show();
		}
	});
	$('button[id$="Cancel"]').click(function() {
		ic.hide();
		$('#' + this.id.replace('Cancel', '')).hide();
	});
	$('#scoreSubmit').click(function(e) {
		if (!checkScoreInput()) {
			e.preventDefault();
		}
	});
	$('#scoreAddSubmit').click(function(e) {
		if (!checkScoreInput()) {
			e.preventDefault();
		}
	});
	$('#valueSubmit').click(function(e) {
		if (!checkValueInput()) {
			e.preventDefault();
		}
	});
	$('#valueAddSubmit').click(function(e) {
		if (!checkValueInput()) {
			e.preventDefault();
		}
	});
	$('#delSubmit').click(function(e) {
		if ($('#monLocked').val() == "true") {
			alert("本月数据已锁定，不允许删除！");
			e.preventDefault();
		} else if (getSelectedCheckBoxCount() == 0) {
			alert("未选择");
			e.preventDefault();
		} else if (confirm("确定删除？") == false) {
			e.preventDefault();
		}
	});
});
function checkScoreInput() {
	if (!isNumeric($('#sWeight').val())) {
		alert("分值仅能输入数字");
		$('#sWeight').select();
		return false;
	} else if (!isNumeric($('#sResult').val())) {
		alert("结果仅能输入数字");
		$('#sResult').select();
		return false;
	} else {
		return true;
	}
}
function checkValueInput() {
	if (!isNumeric($('#vResult').val())) {
		alert("结果仅能输入数字");
		$('#vResult').select();
		return false;
	} else {
		return true;
	}
}
function showScoreMdfForm(trId) {
	$('#score').show(250);

	var strArr = trId.split("_");
	$('#sId').val(strArr[1]);
	$('#sCheckee').val(strArr[2]);
	$('#sChecker').val(strArr[3]);

	trObj = $('#' + trId);
	$('#sItem').val(getTd(3));
	$('#sTarget').text(getTd(4));
	$('#sWeight').val(getTd(5));
	$('#sFormula').text(getTd(6));
	$('#sData').text(getTd(7));
	$('#sResult').val(getTd(8));

	$('#scoreAddSubmit').hide();
	$('#scoreSubmit').show();

	function getTd(index) {
		return trObj.find('td:eq(' + index + ')').text();
	}
}
function showValueMdfForm(trId) {
	$('#value').show(250);

	var strArr = trId.split("_");
	$('#vId').val(strArr[1]);
	$('#vCheckee').val(strArr[2]);
	$('#vChecker').val(strArr[3]);

	trObj = $('#' + trId);
	$('#vItem').val(getTd(3));
	$('#vTarget').text(getTd(4));
	$('#vUnit').val(getTd(5));
	$('#vFormula').text(getTd(6));
	$('#vOther').text(getTd(7));
	$('#vData').text(getTd(8));
	$('#vResult').val(getTd(9));

	$('#valueAddSubmit').hide();
	$('#valueSubmit').show();

	function getTd(index) {
		return trObj.find('td:eq(' + index + ')').text();
	}
}
