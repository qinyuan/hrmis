$(document).ready(function() {
	var objs = [ $('#postId'), $('#genderId'), $('#degreeId') ];
	for ( var i in objs) {
		var cookieValue = $.cookie('resume_add_' + objs[i].attr('id'));
		if (cookieValue) {
			objs[i].val(cookieValue);
		}
		objs[i].change(function() {
			$.cookie('resume_add_' + this.id, this.value);
		});
	}
});