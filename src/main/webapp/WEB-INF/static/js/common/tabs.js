var tabs={
	closeTab:function(title){
	if(title == null || title == 'undefined'){
		var tabpanel = $('#tabMain').tabs('getSelected');
		//var tab = tabpanel.panel('options').tab;
		title = tabpanel.panel('options').title;
	}
	$('#tabMain').tabs('close', title);
 },


showTab:function (title, url, id) {
	var tab = $('#tabMain').tabs('getTab', title);
	
	if (tab){
		$('#tabMain').tabs('select', title);
	}else{
		$('#tabMain').tabs('add', {
			id : id,
			title : title,
			href : url,
			closable : true
		});
	}
}

};
