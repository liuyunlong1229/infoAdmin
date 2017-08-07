// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18

/**
 * 日期工具类
 * 
 * @returns 2015-10-13
 */
var DateUtil = {
	/** 格式化日期 */
	formatterDate : function(value, row, index) {
		if (value) {
			var unixTimestamp = new Date(value);
			return DateUtil.format(unixTimestamp, "yyyy-MM-dd");
		} else {
			return '';
		}

	},
	/** 格式化日期+时间 */
	formatterDateTime : function(value, row, index) {
		if (value) {
			var unixTimestamp = new Date(value);
			return DateUtil.format(unixTimestamp, "yyyy-MM-dd hh:mm:ss");
		} else {
			return '';
		}
	},

	format : function(date, fmt) { // author: meizz
		var o = {
			"M+" : date.getMonth() + 1, // 月份
			"d+" : date.getDate(), // 日
			"h+" : date.getHours(), // 小时
			"m+" : date.getMinutes(), // 分
			"s+" : date.getSeconds(), // 秒
			"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	},

	getTime : function() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
				.getSeconds(), ms = now.getMilliseconds();
		if (h < 10)
			h = "0" + h;
		if (m < 10)
			h = "0" + m;
		if (s < 10)
			h = "0" + s;
		return (h + ":" + m + ":" + s + " " + ms);
	},
	getDate : function() {
		var now = new Date(), y = now.getFullYear(), m = now.getMonth(), w = now
				.getDay(), d = now.getDate();

		if (m < 10)
			h = "0" + m;
		if (d < 10)
			d = "0" + d;
		return (y + "年" + m + "月" + d + "日" + week[w]);
	},

	getDateTime : function() {
		var now = new Date(), yyyy = now.getFullYear(), mm = now.getMonth(), dd = now
				.getDate(), hh = now.getHours(), nn = now.getMinutes(), ss = now
				.getSeconds();

		if (mm < 10)
			mm = "0" + mm;
		if (dd < 10)
			dd = "0" + dd;
		if (hh < 10)
			hh = "0" + hh;
		if (nn < 10)
			nn = "0" + nn;
		if (ss < 10)
			ss = "0" + ss;
		return yyyy + "-" + mm + "-" + dd + " " + hh + ":" + nn + ":" + ss;
	}

};

var base = {

	showWindow : function(url, title, width, height,callBack) {
		width = width || 500;
		height = height || 400;
		if(!callBack){
			callBack=function(){};
		}
		$('#win').window({
			height : height,
			width : width,
			title : title,
			href : url,
			closed : true,
			minimizable : false,
			modal : true,
			onClose:callBack
		});
		$('#win').window('center');
		$('#win').window('open');
	},

	showWindowById : function(id, url, title, width, height) {
		width = width || 500;
		height = height || 400;
		$('#' + id).window({
			height : height,
			width : width,
			title : title,
			href : url,
			closed : true,
			minimizable : false,
			modal : true
		});
		$('#' + id).window('center');
		$('#' + id).window('open');
	},

	showloading : function(msg) {
		$("#tabMain").tabs("loading", msg);
	},

	hiddenloading : function() {
		$("#tabMain").tabs("loaded");
	}

};

$.extend($.fn.tabs.methods, {
	// 显示遮罩
	loading : function(jq, msg) {
		return jq.each(function() {
			var panel = $(this).tabs("getSelected");
			if (msg == undefined) {
				msg = "正在加载数据，请稍候...";
			}
			var width = document.body.scrollWidth;
			var height = document.body.scrollHeight;
			$("<div class=\"datagrid-mask\" style='z-index:9999;'></div>").css(
					{
						display : "block",
						position : "fixed"
					}).appendTo(panel);
			$("<div class=\"datagrid-mask-msg\" style='z-index:9999;'></div>")
					.html(msg).appendTo(panel).css(
							{
								display : "block",
								position : "fixed",
								left : (width - $("div.datagrid-mask-msg",
										panel).outerWidth()) / 2,
								top : (height - $("div.datagrid-mask-msg",
										panel).outerHeight()) / 2
							});
		});
	},
	// 隐藏遮罩
	loaded : function(jq) {
		return jq.each(function() {
			var panel = $(this).tabs("getSelected");
			panel.find("div.datagrid-mask-msg").remove();
			panel.find("div.datagrid-mask").remove();
		});
	}
});
