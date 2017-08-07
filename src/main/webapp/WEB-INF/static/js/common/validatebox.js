//扩展easyui表单的验证

$.extend($.fn.validatebox.defaults.rules,{
	
	number : { // 数字可以
		validator : function(value) {
			var reg = /^[0-9]*$/;
			return reg.test(value);
		},
		message : '请输入整数！'
	},
	
	integer : {// 验证整数
		validator : function(value) {
			return /^[1-9]+\d*$/i.test(value);
		},
		message : '请输入正整数！'
	},
	intOrFloat : {// 验证整数或小数
		validator : function(value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message : '请输入数字，并确保格式正确！'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符！'
	},
	
	
	
	mobile : {// 移动手机号码验证
		validator : function(value) {
			var reg = /^1[3|4|5|8|9]\d{9}$/;
			return reg.test(value);
		},
		message : '请输入正确的手机号码！'
	},
	phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '请使用下面格式:020-88888888！'
	},
	phoneAndMobile : {// 电话号码或手机号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)
					|| /^1[3|4|5|8|9]\d{9}$/.test(value);
		},
		message : '电话号码或手机号码格式不正确！'
	},
	password : {
		validator : function(value) {
			if (value.length >= 3) {
				var v = value.substr(0, 3).split("");
				if (v[0] == v[1] == v[2]) {
					return false;
				}
			}
			if (value.length < 8 || value.length > 18) {
				return false;
			}

			var re = new RegExp("[a-zA-Z]");
			var len = re.test(value);
			re = new RegExp("[0-9]");
			len = re.test(value);
			re = new RegExp("((?=[\x21-\x7e]+)[^A-Za-z0-9])");
			len = re.test(value);
			return len;
			
		},
		message : '8-18位字符，由字母、符号和数字三种及以上组合，前三位不重复！'
	},
	confirmPwd : {
		validator : function(value, param) {
			if ($("#" + param[0]).val() != "" && value != "") {
				return $("#" + param[0]).val() == value;
			} else {
				return true;
			}
		},
		message : '两次输入的密码不一致！'
	},
	
	
	idcard : {// 验证身份证
		validator : function(value) {
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value)
					|| /^\d{18}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message : '身份证号码格式不正确！'
	},
	
	currency : {// 验证货币
		validator : function(value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确！'
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确！'
	},
	
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value);
		},
		message : '请输入中文！'
	},
	chineseAndLength : {// 验证中文及长度
		validator : function(value) {
			var len = $.trim(value).length;
			if (len >= param[0] && len <= param[1]) {
				return /^[\u0391-\uFFE5]+$/i.test(value);
			}
		},
		message : '请输入{0}到{1}位中文！'
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	englishAndLength : {// 验证英语及长度
		validator : function(value, param) {
			var len = $.trim(value).length;
			if (len >= param[0] && len <= param[1]) {
				return /^[A-Za-z]+$/i.test(value);
			}
		},
		message : '请输入{0}到{1}位英文！'
	},
	englishUpperCase : {// 验证英语大写
		validator : function(value) {
			return /^[A-Z]+$/.test(value);
		},
		message : '请输入大写英文！'
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）！'
	},
	faxno : {// 验证传真
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
					.test(value);
		},
		message : '传真号码不正确！'
	},
	zip : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确！'
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确！'
	},
	name : {// 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value)| /^\w+[\w\s]+\w+$/i.test(value);
		},
		message : '请输入姓名！'
	},
	engOrChinese : {// 可以是中文或英文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value)
					| /^\w+[\w\s]+\w+$/i.test(value);
		},
		message : '请输入中文！'
	},
	engOrChineseAndLength : {// 可以是中文或英文
		validator : function(value) {
			var len = $.trim(value).length;
			if (len >= param[0] && len <= param[1]) {
				return /^[\u0391-\uFFE5]+$/i.test(value)| /^\w+[\w\s]+\w+$/i.test(value);
			}
		},
		message : '请输入{0}到{1}中文或英文！'
	},
	resource_url:{
		validator : function(value) {
				return /^[\w]+[\/][\w]+[\.]do$/.test(value);
		},
		message : '请输入*/*格式的资源路径！'
		
	}
	
});