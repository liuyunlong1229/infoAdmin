var memberManager = {
		//初始化表格
		initGrid : function() {
			var name = $('#searchMemberForm #name').val();
			var gender =$('#searchMemberForm #gender').val();
			
			$('#memberListGrid')
					.datagrid(
							{
								url : projectName + "/member/list",
								queryParams : {name:name,gender:gender},
								rownumbers : true,
								singleSelect : true,
								autoRowHeight : true,
								pagination : true,
								pageSize:10,
								pageNumber : 1,
								fitColumns : true,
								columns : [ [
										{
											title : "账号",
											field : "userId",
											width : 100,
											align : "center"
										},   
										{
											title : "姓名",
											field : "name",
											width : 100,
											align : "center"
										},
										{
											title : "性别",
											field : "gender",
											width : 50,
											align : "center",
											formatter:function(value,rec){
												if(value=='1'){
													return '男';
												}else if(value=='0'){
													return '女';
												}else{
													return '';
												}
								            }  
									
										},
										{
											title : '会员类型',
											field : 'type',
											width : 100,
											align : "center",
											formatter:function(value,rec){
												if(value=='1'){
													return '高级会员';
												}else if(value=='0'){
													return '普通会员';
												}else{
													return '';
												}
								            }  
										},
										{
											title : '年龄',
											field : 'age',
											width : 50,
											align : "center",
											sortable : true	
										},
										{
											title : "手机号",
											field : "mobile",
											width : 100,
											align : "center"
											
										},
										{
											title : "微信号",
											field : "weChatNum",
											width : 100,
											align : "center"
										
										},
										{
											title : "支付宝号",
											field : "alipayNum",
											width : 100,
											align : "center",
											sortable : true
									
										},
										{
											title : "发放的金额",
											field : "sendMoney",
											width : 100,
											align : "center",
											sortable : true
									
										},
										{
											title : "邮箱",
											field : "email",
											width : 100,
											align : "center"
										},
										{
											title : "操作",
											field : "opt",
											width : 100,
											align : "center",
											 formatter:function(value,rec){ 
												 	var btn="";
												 	if(rec.sendMoney==null){
												 		btn= '<a class="editcls" onclick="memberManager.sendMoney(\''+rec.id+'\')" href="javascript:void(0)">发放金额</a>';  
												 		btn+="&nbsp;&nbsp;&nbsp;";
												 	}
												 	btn+='<a class="editcls" onclick="memberManager.showDetail(\''+rec.id+'\')" href="javascript:void(0)">详情</a>';  
												 	return btn;
											 }  
										}

										 ] ],

								onLoadSuccess : function(data) {
									// $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
								}

							});
		},

		
		//查询
		queryMembers:function(){
			memberManager.initGrid();
		},
		//清空
		clearQueryForm:function(){
			$('#searchMemberForm').form('clear');
		},
		showDetail : function(mid) {
			base.showWindow(projectName + '/member/detail?mid=' + mid, '会员详情',400, 500,memberManager.refreshList);
		},
		
		refreshList:function(){
			$("#memberListGrid").datagrid("reload");
		},
		
		sendMoney :function(id){
			$.messager.prompt("发放金额", "请输入要发放的金额？", function (data) {
				
              if (data) {
            	  
            	  var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/; 
            	  if (!patrn.exec(data)) 
            	  {
            		  $.messager.alert('提示','金额格式有误!','error');
            		  return;
            	  }
            	  
            	  $.ajax({
            		   type: "POST",
            		   url: projectName + '/member/sendMoney',
            		   data: {id:id,sendMoney:data},
            		   success: function(msg){
            			   memberManager.refreshList();
            		   }
            		});
              }
			});
		}
		
	};