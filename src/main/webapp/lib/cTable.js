$(document).ready(function(){
	var thickBorder="4px solid black";
	var thinBorder="1px solid darkgray"; 
	$('table').css({
		"borderTop":thickBorder,
		"borderBottom":thickBorder,
		"borderCollapse":"collapse",
		"margin":"5px"
	});
	$('th').css({
		"borderBottom":"2px solid black",
		"borderLeft":thinBorder,
		"borderRight":thinBorder,
		"padding":"4px"	
	});
	$('td').css({
		"border":thinBorder,
		"padding":"4px"
	});
	$('tr:nth-child(even)').css("backgroundColor","lightgray");
	$('tr:nth-child(odd)').css("backgroundColor","white");
	
	var bgColor=null;
	$('tr').hover(function(){
		bgColor=String(this.style.backgroundColor);
		this.style.backgroundColor="yellow";
	},function(){
		if(bgColor!=null){
			this.style.backgroundColor=bgColor;
		}
	});
});