var visitRuleManager = {
		//查询
		queryRules:function(){
			var code=$("#searchVisitRuleForm #code").val();
			if(!code){
				$.messager.alert('警示', '请至少输入一个查询条件', 'warning');
				return;
			}
			$('#visitRuleListGrid').datagrid('load',{
				code: code
			});
		},
		//清空
		clearQueryForm:function(){
			$('#searchVisitRuleForm').form('clear');
		},
		
		showAddRuleView : function() {
			base.showWindow(projectName + '/visitRule/add', '规则新增', 500, 300,visitRuleManager.refreshList);
		},
		
		refreshList:function(){
			$("#visitRuleListGrid").datagrid("reload");
		},
		saveVisitRule : function() {
			$('#visirRule_add_form').form('submit', {
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
		
	};