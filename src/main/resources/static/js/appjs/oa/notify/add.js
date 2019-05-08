$().ready(function() {
	loadType();
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	//TODO ; 图片上传功能待修复
    // var file = checkFile();
    // if (!file) {
    //     alert('请先选择文件');
    //     return false;
    // };
    // // 构建form数据
    // var formFile = new FormData();
    // //把文件放入form对象中
    // formFile.append("file", file);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/oa/notify/save",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}
function loadType(){
	var html = "";
	$.ajax({
		url : '/common/dict/list/oa_notify_type',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].value + '">' + data[i].name + '</option>'
			}
			$(".chosen-select").append(html);
			$(".chosen-select").chosen({
				maxHeight : 200
			});
			//点击事件
			$('.chosen-select').on('change', function(e, params) {
				console.log(params.selected);
				var opt = {
					query : {
						type : params.selected,
					}
				}
				$('#exampleTable').bootstrapTable('refresh', opt);
			});
		}
	});
}

var openUser = function(){
	layer.open({
		type:2,
		title:"选择人员",
		area : [ '300px', '450px' ],
		content:"/sys/user/treeView"
	})
}

function loadUser(userIds,userNames){
	$("#userIds").val(userIds);
	$("#userNames").val(userNames);
}

// 检测是否选择文件，如果选择，返回文件对象;如果没选择，返回false
function checkFile() {
    // 获取文件对象(该对象的类型是[object FileList]，其下有个length属性)
    var fileList = $('#files')[0].files;
    // 如果文件对象的length属性为0，就是没文件
    if (fileList.length === 0) {
        console.log('没选择文件');
        return false;
    };
    return fileList[0];
};
