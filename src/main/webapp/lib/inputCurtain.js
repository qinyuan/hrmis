function InputCurtain() {
	$("<div class='inputCurtain'></div>").appendTo('body');
	this.curtain = $('.inputCurtain').css({
		'position' : 'fixed',
		'left' : '0px',
		'top' : '0px',
		'height' : '2000px',
		'width' : '2000px',
		'opacity' : '0.5',
		'zIndex' : '5',
		'backgroundColor' : '#222222',
		'display' : 'none'
	});
	this.elements = new Array();
}
InputCurtain.prototype.show = function(func) {
	this.curtain.show(250);
	for ( var index in this.elements) {
		this.elements[index].show(250);
	}
	if (func != null) {
		func();
	}
};
InputCurtain.prototype.hide = function(func) {
	this.curtain.hide(250);
	for ( var index in this.elements) {
		this.elements[index].hide(250);
	}
	if (func != null) {
		func();
	}
};
InputCurtain.prototype.add = function(id) {
	this.elements.push($('#' + id).css({
		'position' : 'fixed',
		'zIndex' : 6,
		'display' : 'none',
		'backgroundColor' : 'white'
	}));
	return this;
};