var prefix = "/rpc/article"
$().ready(function() {
	validateRule();
});

function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : 	prefix+"/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            pid : {
				required : true
			},
            pName : {
				required : true,
				minlength : 8
			},
            port : {
				required : true,
				minlength : 2
			}
		},
		messages : {

            pid : {
				required : icon + "请输入规约编号"
			},
            pName : {
				required : icon + "请输入规约名称",
				minlength : icon + "用户名必须8个字符以上"
			},
            port : {
				required : icon + "请输入端口号",
				minlength : icon + "密码必须6个字符以上"
			}
		}
	})
}
