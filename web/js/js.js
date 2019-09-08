function GetUrlParam(f) {
	var b = document.location.toString();
	var e = b.split("?");
	if(e.length > 1) {
		var d = e[1].split("&");
		var a;
		for(var c = 0; c < d.length; c++) {
			a = d[c].split("=");
			if(a != null && a[0] == f) {
				return a[1]
			}
		}
		return undefined;
	} else {
		return undefined;
	}
};
//获得浏览器系统当前时间
function NOW() {
	var now = new Date();
	var nowYear = now.getFullYear(); //年
	var nowMonth = now.getMonth() + 1 < 10 ? "0" + (now.getMonth() + 1) : now.getMonth(); //月
	var nowDay = now.getDate() < 10 ? "0" + now.getDate() : now.getDate(); //日期
	var nowHour = now.getHours() < 10 ? "0" + now.getHours() : now.getHours(); //时
	var nowMinute = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes(); //分
	var nowSeconds = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds(); //分
	var nowDate = nowYear + "-" + nowMonth + "-" + nowDay;
	var nowTime = nowHour + ":" + nowMinute + ":" + nowSeconds;
	var dates = new Array(nowDate, nowTime);
	return dates;
}

//  截取字符串
function cutString(str, len) {
	//length属性读出来的汉字长度为1
	if(str.length * 2 <= len) {
		return str;
	}
	var strlen = 0;
	var s = "";
	for(var i = 0; i < str.length; i++) {
		s = s + str.charAt(i);
		if(str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if(strlen >= len) {
				return s.substring(0, s.length - 1) + "...";
			}
		} else {
			strlen = strlen + 1;
			if(strlen >= len) {
				return s.substring(0, s.length - 2) + "...";
			}
		}
	}
	return s;
}