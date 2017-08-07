var roleManager = {
	showAddRoleView : function() {
		base.showWindow(projectName + '/role/add', '角色新增', 500, 500,roleManager.refreshList);
	},

	showUpdateRoleView : function() {
		var rows = $("#roledg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警示', '请选择需要修改的角色', 'warning');
			return;
		}
		var rid = rows[0].id;
		base.showWindow(projectName + '/role/update?rid=' + rid, '角色修改',500, 500,roleManager.refreshList);
	},

	getCheckedNodes : function() {
		var nodes = $('#tt').tree('getChecked');
		var ids = '';
		for ( var i = 0; i < nodes.length; i++) {
			if (ids != '')
				ids += ',';
			ids += nodes[i].id;
		}
		return ids;
	},
	addRole : function() {
		var selectedIds = roleManager.getCheckedNodes();
		if (selectedIds == '') {
			$.messager.alert('提示', '请选择至少一个资源！', 'warning');
			return;
		}
		;

		$("#resourceIds").val(selectedIds);
		var result = $("#role_add_form").form('enableValidation').form('validate');
		if (result) {
			var param = $("#role_add_form").serialize();
			$.messager.progress(); // 显示进度条
			$.ajax({
				type : 'POST',
				url : projectName + '/role/add',
				data : param,
				success : function(msg) {
					$.messager.alert('提示', '新增角色成功！');
					$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$("#win").window('close');
				},
				error : function() { // 当后台方法出现异常，也要关闭提示层
					$.messager.progress('close'); 
				}
			});
		}
	},
	updateRole : function() {
		var selectedIds = roleManager.getCheckedNodes();
		if (selectedIds == '') {
			$.messager.alert('提示', '请选择至少一个资源！', 'warning');
			return;
		}
		$("#resourceIds").val(selectedIds);
		var result = $("#role_update_form").form('enableValidation').form('validate');
		if (result) {
			var param = $("#role_update_form").serialize();
			$.messager.progress(); // 显示进度条
			$.ajax({
				type : "POST",
				url : projectName + "/role/update",
				data : param,
				success : function(msg) {
					$.messager.alert('提示', '修改角色成功！');
					$.messager.progress('close'); 
					$("#win").window('close');
				},
				error : function() { // 当后台方法出现异常，也要关闭提示层
					$.messager.progress('close'); 
				}
			});
		}
	},
	
	refreshList:function(){
		$("#roledg").datagrid("reload");
	},


	delRole : function() {
		var rows = $("#roledg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警示', '请选择要删除的角色', 'warning');
			return;
		}

		var rid = rows[0].id;
		var roleName = rows[0].name;
		$.messager.confirm('确认对话框', '您确定要删除角色[' + roleName + ']吗？',
				function(r) {
					if (r) {
						$.messager.progress(); // 显示进度条
						$.ajax({
							type : 'post',
							url : projectName + '/role/delete',
							data : {
								rid : rid
							},
							success : function(result) {
									$.messager.alert('提示', "删除角色成功");
									$("#roledg").datagrid('reload');
									$.messager.progress('close'); 
							},
							error : function() { // 当后台方法出现异常，也要关闭提示层
								$.messager.progress('close'); 
							}
						});
					}
				});

	}

};