

var pathName=window.document.location.pathname; 
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	

$(function() {

	//页面加载时，从当前cookie中找到找对应的主题和方言
	var cookie_Lan = $.cookie("MyLanguage"); 
	if (cookie_Lan) {
		index.switchLanguage(cookie_Lan); //切换语言
	}
	var cookie_theme = $.cookie("MyTheme"); 
	if (cookie_Lan) {
		index.switchTheme(cookie_theme); //切换主题
	}
	$(document).ajaxError(function(event,xhr,settings){
		if(xhr.status == 401){
			alert("登录超时，请重新登录");
			window.location.replace(projectName+"/login"); 
		}else if(xhr.status == 406){
			alert("对不起，您没有权限访问此功能");
		}else{
			alert("操作错误，请稍后再试");
		}
	});
});

var index={
			
	switchLanguage:function(lan) {
		$("#lanauagefile").attr("src", projectName+"/static/js/jquery-easyui-1.4/locale/easyui-lang-" + lan + ".js");
		$.cookie("MyLanguage", lan, {path: '/',expires: 10});
	},
	
	switchTheme:function (theme) {
		$('#themesfile').attr("href", projectName+"/static/css/jquery-easyui-1.4/themes/" + theme + "/easyui.css");
		$.cookie("MyTheme", theme, {path: '/',expires: 10});
	},
	
	changeLanguage: function (lan) {
		index.switchLanguage(lan);
	},
	changeThemes:function (theme) {
		index.switchTheme(theme);
	}
};
	