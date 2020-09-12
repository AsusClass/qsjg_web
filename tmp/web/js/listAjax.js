var title = "暂无内容";
var listType = GetUrlParam("type");
var listPage = GetUrlParam("page");
var jiange = 15;
// var test_url = "http://127.0.0.1:1234";
// var product_url = "http://www.ncqsjg.cn:5555";
// var  true_url = test_url


// 左下角联系我们
$.ajax({
	url: true_url+"/recentnews",
	data: {
		type: 7
	},
	type: "get",
	dataType: "json",
	success: function(data) {
		console.log(data);
		if(data.code == 200) {
			data = data.data;
			console.log(data.content);
			var cont1 = $('#info1');
			// var cont2 = $('#info2');
			var f = data.content;
			console.log(f);
			f = f.replace(/#space#/g, " ");
			f = f.replace(/&/g, "#");
			//		f = f.replace(/\+/g, " ");			
			console.log(f);
			cont1.append(f);
			// cont2.append(f);
			// cont1.click(function() {
			// 	$(this).hide().next().show();
			// });
			// cont2.click(function() {
			// 	$(this).hide().prev().show();
			// })
			// cont1.each(function() {
			// 	var that = $(this);
			// 	var txt = that.text();
			// 	console.log(txt.length);
			// 	that.text(cutString(txt, 400));
			// })
		}
	}
})
$.ajax({
	url: true_url+"/list",
	data: {
		type: listType,
		page: listPage
	},
	type: "get",
	dataType: "json",
	success: function(i) {
		i = i.data;
		switch(parseInt(i.type)) {
			case 5:
				title = "公告发布";
				break;
			case 2:
				title = "政策法规";
				break;
			case 3:
				title = "新闻动态";
				break;
			case 1:
				title = "公司简介";
				break;
			case 6:
				title = "人才招聘";
				break;
			case 7:
				title = "联系我们 ";
				break
			case 8:
				title = "行业动态 ";
				break
		}
		console.info(i.type)
		document.title = title + "-南昌亲水建筑工程咨询有限公司";
		var j = document.getElementsByTagName("meta");
		keswordsStr = "南昌亲水建筑工程咨询有限公司," + title + ",水土保持,环境影响评价,水资源论证,防洪评价,水利设计,资金申请 ,可行性研究报告,节能评估,地灾评估,土地复垦,测绘仪器";
		j[1].setAttribute("content", keswordsStr);
		descriptionStr = "南昌亲水建筑工程咨询有限公司," + title;
		j[2].setAttribute("content", descriptionStr);
		var w = "<h4>" + title + "</h4>";
		$("#list_left_a").append(w);
		var z = "<span>您当前位置：<a href='index.html'>南昌亲水建筑工程咨询有限公司</a> &gt;&gt; <a href='list.html?type=" + i.type + "&page=" + listPage + "'> " + title + "</a> &gt;&gt; 频道首页</span>";
		console.info(z)
		$("#list-right-top").append(z);
		var y = i.list;
		var x = "article.html?id=";
		for(var B = 0; B < y.length; B++) {
			var F = y[B].date;
			var I = F.indexOf("日");
			F = F.substring(0, I + 1);
			var u = "<li><span class='span'>" + F + "</span><a href='" + x + y[B].id + "' title=" + y[B].title + ">" + y[B].title + "</a>";
			$("#list").append(u)
		}
		var G = Math.ceil(i.count / jiange);
		var v = listPage - 1;
		var D = listPage + 1;
		var J = "共 " + i.count + " 篇  页次: " + listPage + "/ " + G + " 页" + 10 + "篇 / 页 <a href = 'list.html?type=" + listType + "&page=1'> 首页 </a> ";
		if(v > 0) {
			var K = "<a href ='list.html?type=" + listType + "&page=" + v + "'> 上一页 </a>"
		} else {
			var K = " "
		}
		if(D < G) {
			var A = "<a href = 'list.html?type=" + listType + "&page=" + D + "'> 下一页 </a>"
		} else {
			var A = " "
		}
		var H = "<a href = 'list.html?type=" + listType + "&page=" + G + "' > 尾页 </a> ";
		$("#fenye").append(J).append(K).append(A).append(H);
		for(var C = 1; C <= G; C++) {
			if(C == listPage) {
				var E = "<option value = 'list.html?type=" + listType + "&page=" + C + "'  selected = '' > 第" + C + "页 </option> "
			} else {
				var E = "<option value = 'list.html?type=" + listType + "&page=" + C + "'  > 第" + C + "页 </option>"
			}
			$("#fpage").append(E)
		}
	}
});