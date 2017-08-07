	var qrCodeManager = {

		//初始化表格
		initGrid : function() {
			var code = $('#searchQrCodeForm #code').val();
			var name = $('#searchQrCodeForm #name').val();
			$('#qrCodeListGrid')
					.datagrid(
							{
								url : projectName +"/qrCode/list",
								queryParams : {name:name,code:code},
								rownumbers : true,
								singleSelect : true,
								autoRowHeight : true,
								pagination : true,
								pageSize:10,
								pageNumber : 1,
								fitColumns : true,
								toolbar:"#qrCodeToolBar",
								columns : [ [
										{
											title : "编码",
											field : "code",
											width : 100,
											align : "center"
										},
										{
											title : "名称",
											field : "name",
											width : 100,
											align : "center"
									
										},
										{
											title : '描述',
											field : 'remark',
											width : 100,
											align : "center"
										},
										{
											title : '生成的图片地址',
											field : 'picUrl',
											width : 100,
											align : "center"
										},
										
										{
											
											title : "操作",
											field : "opt",
											width : 100,
											align : "center",
											 formatter:function(value,rec){  
												    btn = '<a class="editcls" onclick="qrCodeManager.showQrcode(\''+rec.code+'\',\''+rec.name+'\')" href="javascript:void(0)">查看二维码</a>';
									                btn =btn+"&nbsp;&nbsp;&nbsp";
												    btn = btn+ '<a class="editcls" onclick="qrCodeManager.showDetail(\''+rec.id+'\')" href="javascript:void(0)">详情</a>';
									                return btn;  
									            }  
										},
										
										
										
										
										
										 ] ],

								onLoadSuccess : function(data) {
									// $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
								}

							});
		},

		
		//查询
		queryQrCodes:function(){
			qrCodeManager.initGrid();
		},
		//清空
		clearQrCodeQueryForm:function(){
			$('#searchQrCodeForm').form('clear');
		},
		
		showQrcode : function(code,name){
				window.open('http://localhost:8080/images/qrcode/'+code+".jpg"  , name);
		},
		
		showDetail : function(qrId) {
			base.showWindow(projectName + '/qrCode/detail?qrId=' + qrId, '二维码详情',400, 500,qrCodeManager.refreshList);
		},
		
		refreshList:function(){
			$("#qrCodeListGrid").datagrid("reload");
		},
		
		showAddQrCodeView : function() {
			base.showWindow(projectName + '/qrCode/add', '二维码新增', 500, 300,qrCodeManager.refreshList);
		},
		saveQrCode :function(){
			$('#qrCode_add_form').form('submit', {
				onSubmit : function() {
					var result = $(this).form('enableValidation').form('validate');
					if (result) {
						$.messager.progress(); // 显示进度条
					}
					return result;
				},
				success : function(data) {
					$.messager.alert('提示', "新增二维码成功");
					$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$("#win").window('close');
				 
				}

			});
		}
		
	};
  