var t = "暂无内容";
var newsId = GetUrlParam("id");
var type = parseInt(GetUrlParam("type"))
console.info(newsId)
console.info(type)


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
			// f = f.replace(/<\/p>/g, "</p>\n")
			f = f.replace(/#space#/g, " ");
			// f = f.replace(/&/g, "#");
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
			// 	var that = $(this),
			// 		txt = that.text();
			// 	// console.log(txt);
			// 	that.text(cutString(txt, 1000));
			// })
		}
	}
})

if(newsId==undefined){
	$.ajax({
		url: true_url+"/recentnews",
		data: {
			type: type
		},
		type: "get",
		dataType: "json",
		success: function(data) {


			if(data.code == 200) {
				data = data.data;
				console.info(data)
				console.log(data.content);
				var cont1 = $('#content');
				var f = data.content;
				console.log(f);
				// f = f.replace(/<\/p>/g, "</p>\n")
				f = f.replace(/#space#/g, " ");
				// f = f.replace(/&/g, "#");
				//		f = f.replace(/\+/g, " ");
				console.log(f);
				cont1.append(f);
				if(type==1){
					t = "公司简介";
				}else if (type==7){
					t = "联系我们"
				}
				document.title = "浏览文章-" + t + "-南昌亲水建筑工程咨询有限公司";


				var C = "<h4>" + t + "</h4>";
				$("#list_left_a").append(C);

				var d = "<span>您当前位置：<a href='index.html'>南昌亲水建筑工程咨询有限公司</a> &gt;&gt; <a > " + t + "</a> &gt;&gt; 浏览文章</span>";
				$("#list-right-top").append(d);

				var o = document.getElementsByTagName("meta");
				var g = data.title;
				keswordsStr = "南昌亲水建筑工程咨询有限公司," + t + "," + g + ",水土保持,环境影响评价,水资源论证,防洪评价,水利设计,资金申请 ,可行性研究报告,节能评估,地灾评估,土地复垦,测绘仪器";
				o[1].setAttribute("content", keswordsStr);
				descriptionStr = "南昌亲水建筑工程咨询有限公司," + t + "," + g;
				o[2].setAttribute("content", descriptionStr);

				var a = "<h1 class='bt'>" + g + "</h1>";
				var k = "<p class='info'>作者：" + data.author + " &nbsp; 发布时间：" + data.date + " &nbsp; 浏览次数：" + data.looked_times + "</p>";
				$("#article_title").append(a).append(k);

				var f = c.content;
				f = f.replace(/&/g, "#");
				//		f = f.replace(/\+/g, " ");
				f = f.replace(/#space#/g, " ");
				f = "<p>" + f + "</p>";
				console.log(f);
				$("#content").append(f);


			}
		}
	})
}else {
	$.ajax({
		url: true_url + "/content",
		data: {
			id: newsId
		},
		type: "get",
		dataType: "json",
		success: function(c) {
			c = c.data;
			switch(parseInt(c.type)) {
				case 5:
					t = "公告发布";
					break;
				case 2:
					t = "政策法规";
					break;
				case 3:
					t = "公司新闻";
					break;
				case 1:
					t = "公司简介";
					break;
				case 6:
					t = "人才招聘";
					break;
				case 7:
					t = "联系我们 ";
					break
				case 8:
					t = "行业动态 ";
					break
			}
			document.title = "浏览文章-" + t + "-南昌亲水建筑工程咨询有限公司";
			var o = document.getElementsByTagName("meta");
			var C = "<h4>" + t + "</h4>";
			$("#list_left_a").append(C);
			var d = "<span>您当前位置：<a href='index.html '>南昌亲水建筑工程咨询有限公司</a> &gt;&gt; <a href='list.html?type=" + c.type + "&page=1'> " + t + "</a> &gt;&gt; 浏览文章</span>";
			console.info(d)
			$("#list-right-top").append(d);
			var g = c.title;
			keswordsStr = "南昌亲水建筑工程咨询有限公司," + t + "," + g + ",水土保持,环境影响评价,水资源论证,防洪评价,水利设计,资金申请 ,可行性研究报告,节能评估,地灾评估,土地复垦,测绘仪器";
			o[1].setAttribute("content", keswordsStr);
			descriptionStr = "南昌亲水建筑工程咨询有限公司," + t + "," + g;
			o[2].setAttribute("content", descriptionStr);
			var a = "<h1 class='bt'>" + g + "</h1>";
			var k = "<p class='info'>作者：" + c.author + " &nbsp; 发布时间：" + c.date + " &nbsp; 浏览次数：" + c.looked_times + "</p>";
			$("#article_title").append(a).append(k);
			var f = c.content;
			f = f.replace(/&/g, "#");
			//		f = f.replace(/\+/g, " ");
			f = f.replace(/#space#/g, " ");
			f = "<p>" + f + "</p>";
			console.log(f);
			$("#content").append(f);


			/* 有附件的文章*/
			if(c.fujian != undefined) {
				fujian = c.fujian;
				if(fujian.length > 0) {
					var n = "<p class='f_title'>附件列表(点击下载):</p>";
					$("#fujian").append(n);
					for(e = 0; e < fujian.length; e++) {
						if(e == fujian.length - 1) {
							var m = "."
						} else {
							var m = "；"
						}
						var b = fujian[e].size / 1024 / 1024;
						b = b.toFixed(2);
						var h = "<li><a href='" + server_res_root + fujian[e].url + "' target='_blank'  onclick='fileCount(" + fujian[e].id + ")';>" + fujian[e].name + m + "</a><span>[文件大小：" + b + "Mb 下载次数：" + fujian[e].download_times + "次]</span></li>";
						$("#fujian").append(h)
					}
				}
			}
		}
	});
}




function fileCount(a) {
	$.ajax({
		url: true_url+"/file/count",
		data: {
			id: a
		},
		type: "get",
		dataType: "json",
		success: function(b) {
			if(b.code == 200) {}
		}
	})
};