
var resourceManager = {

	// 根据条件显示不同的右键菜单
	showContextMenu : function(e, row) {
		e.preventDefault();
		var menuid = "";
		console.log('row.resourceType=='+row.resourceType);
		if (row.resourceType == '根节点') {
			menuid = "root_menu";
		} else { 
			if (row.resourceType == '按钮') {
				menuid = "menu_button";
			} else {
				if (row.isLeaf == true) {
					menuid = "menu_nochild";
				} else {
					menuid = "menu_child";
				}
			}
		}
		$(this).treegrid('select', row.id);

		$('#' + menuid).menu('show', {
			left : e.pageX,
			top : e.pageY
		});

	},

	refreshTreeGrid : function() {
		$("#tg").treegrid("reload");

	},

	appendMenu : function() {
		var node = $('#tg').treegrid('getSelected'); // 获得选中的节点
		var selectDataId = node.id;
		base.showWindow(projectName + '/resource/add?rsid=' + selectDataId,'资源新增', 500, 300,resourceManager.refreshTreeGrid);
	},

	updateMenu : function() {
		var node = $('#tg').treegrid('getSelected'); // 获得选中的节点
		var selectDataId = node.id;
		base.showWindow(projectName + '/resource/update?rsid='+ selectDataId, '资源查看/修改', 500, 300,resourceManager.refreshTreeGrid);
	},

	removeMenu : function() {
		var node = $('#tg').treegrid('getSelected'); // 获得选中的节点
		var selectDataId = node.id;
		$.messager.confirm('提示', '确定要删除资源 [' + node.name + '] ？', function(r) {
			if (r) {
				$.messager.progress(); // 显示进度条
				$.ajax({
					type : 'post',
					url : projectName + '/resource/delete',
					data : {rsid : selectDataId},
					success : function(result) {
							$.messager.alert('提示', "删除资源成功");
							$.messager.progress('close'); 
							resourceManager.refreshTreeGrid();
					},
					error : function() { // 当后台方法出现异常，也要关闭提示层
						$.messager.progress('close'); 
					}
				});
			}
		});

	},
	collapse : function() {
		var node = $('#tg').treegrid('getSelected');
		if (node) {
			$('#tg').treegrid('collapse', node.id);
		}
	},
	expand : function() {
		var node = $('#tg').treegrid('getSelected');
		if (node) {
			$('#tg').treegrid('expand', node.id);
		}
	},

	saveResource : function() {
		$('#resource_add_form').form('submit', {
			onSubmit : function() {
				var result = $(this).form('enableValidation').form('validate');
				if (result) {
					$.messager.progress(); // 显示进度条
				}
				return result;
			},
			success : function(data) {
					$.messager.alert('提示', "新增资源成功");
					$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$("#win").window('close');
			}

		});
	},
	updateResource : function() {
		$('#resource_update_form').form('submit', {
			onSubmit : function() {
				var result = $(this).form('enableValidation').form('validate');
				if (result) {
					$.messager.progress(); // 显示进度条
				}
				return result;
			},
			success : function(data) {
					$.messager.alert('提示', "修改资源成功");
					$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$("#win").window('close');
			}

		});
	}

};