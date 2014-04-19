function ButtonCells() {
	this.linkForm = "buttonCellForm";
	this.lastClickedButtonId = null;
	this.buttons = null;
	this.submitHref = null;
	this.submitKey = null;
	this.submitValue = null;
}
ButtonCells.prototype.getSubmitData = function() {
	var hiddenParam = $('#' + this.linkForm).serialize();
	if (this.submitKey == null) {
		return hiddenParam;
	} else {
		return this.submitKey + "=" + this.submitValue + "&" + hiddenParam;
	}
};
ButtonCells.prototype.setButtons = function(buttons) {
	this.buttons = buttons;
	buttons.css('borderWidth', '5px');
	buttons.click(buttonCellClick);
	var self = this;
	function buttonCellClick(event) {
		if ($('#' + self.linkForm).size() == 0) {
			$('<form action="" id="' + self.linkForm + '"></form>').appendTo(
					'body');
		}
		if (event.shiftKey && self.lastClickedButtonId != null) {
			switchButtonCells(self.lastClickedButtonId, this.id);
		}
		self.lastClickedButtonId = this.id;
		switchButtonCell(this.id);
	}
	function switchButtonCell(id) {
		var buttonObj = $('#' + id);
		if (buttonObj.attr('disabled')) {
			return;
		}
		var hiddenObj = $('#hid_' + id);
		if (hiddenObj.size() == 0) {
			buttonObj.css("borderColor", "#00ff00");
			$(
					'<input type="hidden" id="hid_' + id + '" name="hid_' + id
							+ '" value="hid_' + id + '" />').appendTo(
					'#' + self.linkForm);
		} else {
			buttonObj.css("borderColor", buttonObj.css("backgroundColor"));
			hiddenObj.remove();
		}
	}
	function switchButtonCells(firstId, lastId) {
		if (firstId == lastId)
			return;
		var buttons = document.getElementsByTagName("button");
		var buttonCount = buttons.length;
		var start = false;
		var currentId;
		for ( var i = 0; i < buttonCount; i++) {
			currentId = buttons.item(i).id;
			if (currentId == firstId || currentId == lastId) {
				if (start == false) {
					start = true;
					continue;
				} else {
					return;
				}
			}
			if (start) {
				switchButtonCell(currentId);
			}
		}
	}
	return self;
};
ButtonCells.prototype.setLinkForm = function(linkForm) {
	this.linkForm = linkForm;
	return this;
};
ButtonCells.prototype.setSubmitHref = function(submitHref) {
	this.submitHref = submitHref;
	return this;
};
ButtonCells.prototype.setSubmitInfo = function(key, value) {
	this.submitKey = key;
	this.submitValue = value;
	return this;
};
ButtonCells.prototype.submitAsAjax = function(callBackFun) {
	if (this.submitHref != null) {
		$.post(this.submitHref, {
			submitData : this.getSubmitData()
		}, callBackFun);
	}
};
ButtonCells.prototype.submitAsGet = function() {
	if (this.submitHref != null) {
		location.href = this.submitHref + "?" + this.getSubmitData();
	}
};