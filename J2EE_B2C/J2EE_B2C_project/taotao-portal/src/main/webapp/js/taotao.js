var TT = TAOTAO = {
	checkLogin : function() {
		var _ticket = $.cookie("TT_TOKEN");
		if (!_ticket) {
			return;
		}
		$
				.ajax({
					url : "http://localhost:8084/user/token/" + _ticket,
					dataType : "jsonp",
					type : "GET",
					success : function(data) {
						if (data.status == 200) {
							var username = data.data.username;
							var html = username
									+ "，欢迎来到淘淘！<a href=\"javascript:logout();\" class=\"link-logout\" id=\"logout\">[退出]</a>";
							$("#loginbar").html(html);
						}
					}
				});
	}
}

function logout() {
	var _ticket = $.cookie("TT_TOKEN");
	if (!_ticket) {
		return;
	}
	$.ajax({
		url : "http://localhost:8084/user/logout/" + _ticket,
		dataType : "jsonp",
		type : "GET",
		success : function(data) {
			if (data.status == 200) {
				// alert("Log out successful");
				location.href = "http://localhost:8082";
			} else {
				alert("Fail to log out.");
			}
		}
	});
}

$(function() {
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();

});