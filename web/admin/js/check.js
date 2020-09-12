var q = getCookie('token');
var website = 'http://www.ncqsjg.cn:5555/';
console.log(document.cookie);
console.log(q);
var u = getCookie('user');
console.log(u);
if(q == undefined) {
	window.location.href = 'login.html'
} else {
	var c_start = document.cookie.indexOf("token");
	//	console.log(c_start);
	if(c_start == -1) {
		window.location.href = 'login.html'
	} else {
		var data = {};
		var data = transformToJson(data);
		data["token"] = q;
		//		console.log(data);
		$.ajax({
			url: website + "token/check",
			cache: false,
			type: "post",
			datatype: "json",
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: data,
			success: function(data) {
				if(data.code == "200") {
					//根据后台返回值确定是否操作成功
					console.log("验证成功");
					if(u != undefined) {
						$("#user").append(u);
						$("#user").val(u);
					} else {
						$("#user").append("管理员");
					}
					//					document.cookie = data.token;
				} else {
					console.log("非法访问");
					window.location.href = 'login.html'
				}
			}
		});
	}

}

//获取cookie
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while(c.charAt(0) == ' ') c = c.substring(1);
		if(c.indexOf(name) != -1) return c.substring(name.length, c.length);
	}
	return "";
}
