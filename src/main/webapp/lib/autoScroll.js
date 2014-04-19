$(document).ready(
		function() {
			// set cookie parameter name
			var currentPath=String(location.pathname);
			var cookieParamName = currentPath.substring(0, currentPath
					.lastIndexOf("."));
			cookieParamName = cookieParamName.replace(/\W/g, "_");

			// set cookie when window scrolls
			$(window).scroll(function() {
				$.cookie(cookieParamName, $(window).scrollTop());
			});

			// scroll to last recorded position on page loaded
			var scrollPosition = $.cookie(cookieParamName);
			if (scrollPosition != null) {
				$(window).scrollTop(scrollPosition);
			}
		});