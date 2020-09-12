  var domain = window.location.host;
console.log(domain);
var host = domain.split(".");
console.log(host);
var flag = 0;
if(host[0] == "luov" || host[1] == "luov") {
	var mubiao = "http://ncqsjg.cn";
	flag = 1;
}

console.log(mubiao);
var other = GetUrlRelativePath();
console.log(other);

if(flag == 1) {
	window.location.href = mubiao + other;
}


//获取相对路径
function  GetUrlRelativePath()　　 {　　　　
	var  url = document.location.toString();　　　　
	var  arrUrl = url.split("//");

	　　　　
	var  start = arrUrl[1].indexOf("/");　　　　
	var  relUrl = arrUrl[1].substring(start); //stop省略，截取从start开始到结尾的所有字符

	　　　　
	if(relUrl.indexOf("?") != -1) {　　　　　　
		relUrl = relUrl.split("?")[0];　　　　
	}　　　　
	return  relUrl;　　
}