// var test_url = "http://127.0.0.1:1234";
// var product_url = "http://www.ncqsjg.cn:5555";
// var  true_url = test_url
// var imWebsite = "http://www.ncqsjg.cn:8888";
$.ajax({
	url: true_url+"/recentnews",
	data: {
		type: 1
	},
	type: "get",
	dataType: "json",
	success: function(data) {
		if(data.code == 200) {
			data = data.data;
			console.log(data.content);
			var cont1 = $('#intro_id1');
			var cont2 = $('#intro_id2');
			cont1.append(data.content);
			// cont2.append(data.content);

			cont1.click(function() {
				$(this).hide().next().show();
			});
			cont2.click(function() {
				$(this).hide().prev().show();
			})
			cont1.each(function() {
				var that = $(this),
					txt = that.text();
				// console.log(txt);
				that.text(cutString(txt, 420));
			})
		}
	}
})

$.ajax({
	url: "http://www.ncqsjg.cn:5555/",
	type: "get",
	dataType: "json",
	success: function(e) {
		//		var time=NOW();
		if(e.code == 200) {
			e = e.data;
			var a = e.xw;
			var gg = e.hydt;
			var c = "article.html?id=";
			if(a.length >= 4) {
				for(var f = 0; f < 4; f++) {
					if(a[f].imgs == undefined) {
						a[f].img = "img/nopic.jpg"
					} else {
						a[f].img = server_res_root + a[f].imgs[0].url
					}
					var h = "<td><div class='" + "new-shower" + "'><a href='" + c + a[f].id + "'><img src='" + a[f].img + "' /><p>" + a[f].title.substr(0, 16) + "<br/>" + a[f].title.substr(16, 16) + "</p></a></div></td>";
					$("#sm").append(h)
				}
			} else {
				for(var f = 0; f < a.length; f++) {
					console.log(a[f])
					if(a[f].imgs == undefined || a[f].imgs.length == 0) {
						a[f].img = "img/nopic.jpg"
					} else {
						a[f].img = server_res_root + a[f].imgs[0].url
					}
					var h = "<td><div class='" + "new-shower" + "'><a href='" + c + a[f].id + "'><img src='" + a[f].img + "' /><p>" + a[f].title.substr(0, 16) + "<br/>" + a[f].title.substr(16, 16) + "</p></a></div></td>";
					$("#sm").append(h)
				}
				for(f; f < 4; f++) {
					var h = "<td><div class='new-shower'><a href='#'><img src='img/nopic.jpg'/><p>暂无新闻，暂无新闻</p></a></div></td>";
					$("#sm").append(h)
				}
			}
			var b = e.zc;
			if(b.length >= 2) {
				var g = (b[0].content.replace(/\<.*?\>/g, ""));
				var k = "<a href=" + c + b[0].id + " title=" + b[0].title + "><h1>" + b[0].title.substr(0, 15) + "</h1><p>" + g.substr(0, 53) + "</p></a>";
				$("#polic1").append(k);
				var g = (b[1].content.replace(/\<.*?\>/g, ""));
				var j = "<a href=" + c + b[1].id + " title=" + b[1].title + "><h1>" + b[1].title.substr(0, 15) + "</h1><p>" + g.substr(0, 53) + "</p></a>";
				$("#polic2").append(j)
			} else {
				if(b.length == 1) {
					var g = (b[0].content.replace(/\<.*?\>/g, ""));
					var k = "<a href=" + c + b[0].id + " title=" + b[0].title + "><h1>" + b[0].title.substr(0, 15) + "</h1><p>" + g.substr(0, 53) + "</p></a>";
					$("#polic1").append(k);
					var j = "<a href='javascript:;' title='暂无内容'><h1>暂无内容</h1><p>目前暂无内容</p></a>";
					$("#polic2").append(j)
				} else {
					var k = "<a href='javascript:;' title='暂无内容'><h1>暂无内容</h1><p>目前暂无内容</p></a>";
					$("#polic1").append(k);
					var k = "<a href='javascript:;' title='暂无内容'><h1>暂无内容</h1><p>目前暂无内容</p></a>";
					$("#polic2").append(k)
				}
			}
			if(gg == undefined)
				var flag = 1;
			else {
				if(gg.length == 0)
					var flag = 1;
				else
					var flag = 0;
			}
			if(flag == 1) {
				var h = "<li><span class='span'>[2018-12-31]</span>	<a href='javascript:void(0);' title='暂无动态'>暂无动态</a></li>";
				$("#list").append(h);
			} else if(gg.length >= 8) {
				for(var f = 0; f < 8; f++) {
					var datea = gg[f].date;
					var ddt = datea.indexOf("日");
					datea = datea.substring(0, ddt + 1);
					var h = "<li><span class='span'>" + datea + "</span><a href='" + c + gg[f].id + "'title='" + gg[f].title + "'>" + gg[f].title.substr(0, 21) + " </a></li>";
					$("#list").append(h);
				}
			} else {
				for(var f = 0; f < gg.length; f++) {
					var datea = gg[f].date;
					var ddt = datea.indexOf("日");
					datea = datea.substring(0, ddt + 1);
					var h = "<li><span class='span'>" + datea + "</span><a href='" + c + gg[f].id + "'title='" + gg[f].title + "'>" + gg[f].title.substr(0, 21) + " </a></li>";
					$("#list").append(h);
				}
				//				for(f; f < 8; f++) {
				//					var h = "<li><span class='span'>'" + datea + "'</span><a href='" + c + gg[f].id + "'title='" + gg[f].title + "'>" + gg[f].title.substr(0, 20) + " </a></li>";
				//					$("#list").append(h);
				//				}
			}
		} else {
			if(e.code == 400) {
				for(var f = 0; f < 3; f++) {
					var h = "<div class='new-shower'><a href='#'><img src='img/nopic.jpg'/><p>暂无新闻，暂无新闻</p></a></div>";
					$("#news_title").append(h)
				}
				var k = "<a href='javascript:;' title='暂无内容'><h1>暂无内容</h1><p>目前暂无内容</p></a>";
				$("#polic1").append(k);
				$("#polic2").append(k);
			}
		}
	}
});

window.onload = function() {
	Pic2.innerHTML = Pic1.innerHTML; //复制一组图片，但被隐藏
	function scrolltoleft() { //定义向左移动的方法
		news_title.scrollLeft++; //改变层的水平坐标，实现向左移动
		if(news_title.scrollLeft >= Pic1.scrollWidth) //需要复位
			news_title.scrollLeft = 0; //层的位置复位，浏览器窗口的宽度有限的
	}

	var MyMar = setInterval(scrolltoleft, 40); //定时器，方法名后不可加()

	function mouseover(x) { //放大图片
		x.style.width = "210";
		x.style.height = "256"
		x.style.cursor = "pointer"
	}

	function mouseout(x) { //图片复原
		x.style.width = "105";
		x.style.height = "128"
	}

	//两面两行是用方法响应对象的事件
	news_title.onmouseover = function() { //匿名方法（函数）
		clearInterval(MyMar); //停止滚动
	}

	news_title.onmouseout = function() {
		MyMar = setInterval(scrolltoleft, 40); //继续滚动
	}
}

$(document).ready(function() {

})

//function intro_method_1() {
//	var c = document.getElementById("intro_id1");
//	var b = document.getElementById("intro_a_1");
//	b.style.display = "none";
//	c.style = ""
//};
//
//function intro_method_2() {
//	var c = document.getElementById("intro_id2");
//	var b = document.getElementById("intro_a_2");
//	b.style.display = "none";
//	c.style = ""
//};