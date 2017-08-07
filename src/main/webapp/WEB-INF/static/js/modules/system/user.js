var userManager = {
	showAddUserView : function() {
		base.showWindow(projectName + '/user/add', '用户新增', 400, 500,userManager.refreshList);
	},
	showUpdateUserView : function() {
		var rows = $("#userdg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警示', '请选择需要修改的用户', 'warning');
			return;
		}
		var uid = rows[0].id;
		base.showWindow(projectName + '/user/update?uid=' + uid, '用户修改',400, 500,userManager.refreshList);
	},
	
	refreshList:function(){
		$("#userdg").datagrid("reload");
	},
	queryUsers:function(){
		var loginName=$("#loginName").val();
		var sex=$("#sex").val();
		if(!loginName && !sex){
			$.messager.alert('警示', '请至少输入一个查询条件', 'warning');
			return;
		}
		//searchForm.serialize()
		$('#userdg').datagrid('load',{
			loginName: loginName,
			sex: sex
		});

		
	},
	
	//清空
	clearQueryForm:function(){
		$('#searchUserForm').form('clear');
	},
	saveUser : function() {

		$('#user_add_form').form('submit', {
			onSubmit : function() {
				var result = $(this).form('enableValidation').form('validate');
				if (result) {
					$.messager.progress(); // 显示进度条
				}
				return result;
			},
			success : function(data) {
				$.messager.alert('提示', "新增用户成功");
				$.messager.progress('close'); // 如果提交成功则隐藏进度条
				$("#win").window('close');
			 
			}

		});
	},

	changePwd : function() {
		$('#user_changePwd_form').form('submit', {
			onSubmit : function() {
				var result = $(this).form('enableValidation').form('validate');
				if (result) {
					$.messager.progress(); // 显示进度条
				}
				return result;
			},
			success : function(data) {
			    $.messager.alert('提示', "修改密码成功");
			    $.messager.progress('close'); // 如果提交成功则隐藏进度条
			    $("#win").window('close');
			}

		});
	},
	updateUser : function() {
		$('#user_update_form').form('submit', {
			onSubmit : function() {
				var result = $(this).form('enableValidation').form('validate');
				if (result) {
					$.messager.progress(); // 显示进度条
				}
				return result;
			},
			success : function(data) {
			    $.messager.alert('提示', "修改用户成功");
			    $.messager.progress('close'); // 如果提交成功则隐藏进度条
			    $("#win").window('close');
			}

		});
	},

	delUser : function() {
		var rows = $("#userdg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警示', '请选择要删除的用户', 'warning');
			return;
		}

		var uid = rows[0].id;
		var loginName = rows[0].loginName;
		$.messager.confirm('确认对话框', '您确定要删除用户[' + loginName + ']吗？',
				function(r) {
					if (r) {
						$.messager.progress(); // 显示进度条
						$.ajax({
							url : projectName + '/user/delete',
							data : {
								uid : uid
							},
							success : function(result) {
							  $.messager.alert('提示', "删除用户成功");
							  $.messager.progress('close'); // 如果提交成功则隐藏进度条
							  userManager.refreshList();
							},
							error : function() { // 当后台方法出现异常，也要关闭提示层
								$.messager.progress('close'); 
							}
						});
					}
				});

	},
	
	resetPwd : function() {
		var rows = $("#userdg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警示', '请选择要需要重置密码的用户', 'warning');
			return;
		}

		var uid = rows[0].id;
		var loginName = rows[0].loginName;
		$.messager.confirm('确认对话框', '您确定要对用户[' + loginName + ']进行密码重置吗？',
				function(r) {
					if (r) {
						$.messager.progress(); // 显示进度条
						$.ajax({
							url : projectName + '/user/reSetPwd',
							data : {
								uid : uid
							},
							success : function(result) {
							  $.messager.alert('提示', "重置密码成功");
							  $.messager.progress('close'); // 如果提交成功则隐藏进度条
							  userManager.refreshList();
							},
							error : function() { // 当后台方法出现异常，也要关闭提示层
								$.messager.progress('close'); 
							}
						});
					}
				});

	}
	

};
