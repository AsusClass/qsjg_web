var title = "暂无内容";
var listType = GetUrlParam("type");
var listPage = GetUrlParam("page");

var xianzhi = 15;
var listList = ["全部分类", "公司简介", "公告发布", "政策法规", "新闻动态"];
var q = getCookie('token');
$.ajax({
	url: "http:///www.ncqsjg.cn:5555/list",
	data: {
		type: listType,
		page: listPage,
		token: q,
	},	
	type: 'get',
	dataType: 'json',
	success: function(data) {
		data = data.data;
		pages = Math.ceil(data.count / xianzhi); //总页数
		console.log(data);
		var searchSort = document.getElementById("searchSort");
		for(var i = 0; i < searchSort.length; i++) {
			var valueStr = searchSort[i].value;
			valueStr = parseInt(valueStr.substring(17, 18));
			if(valueStr == data.type) {
				searchSort[i].selected = true;
			}
		}
		lists = data.list;
		console.log(lists);
		var newsUrl = '/article.html?id=';
		var updateUrl = 'update.html?id=';
		var delUrl = '/del?id=';
		for(i = 0; i < lists.length; i++) {
			var list = lists[i];
			if(list.title.length > 24) {
				var title = list.title.substr(0, 25) + "…";
			} else {
				var title = list.title;
			}
			if(list.author == undefined)
				author = "admin";
			else {
				author = list.author;
			}
			titles=checkTitle(list.title);
			titles =checkTitle(titles);
			console.log(titles);
			infoStr = "<tr>" +
				"<td class= 'tc'><input name= 'id[]' value='" + list.id + "' type='checkbox'></td>" +
				"<td>" +
				"<input name= 'ids' value= '" + list.id + "' type= 'hidden'>" +
				"<input class= 'common-input sort-input' name= 'ord[]' value= '" + list.id + "' type= 'text'>" +
				"</td>" +
				"<td>" + list.id + "</td>" +
				"<td 'title='" + list.title + "'>" +
				"<a target='_blank' href='" + newsUrl + list.id + "' title='" + list.title + "'>" + title + "</a>" +
				"</td>" +
				"<td>" + list.looked_times + "</td>" +
				"<td>" + author + "</td>" +
				"<td>" + list.date + "</td>" +
				"<td>" +
				"<a class='link-update' href='" + updateUrl + list.id + "'>修改</a> " +
				"<a class='link-del' href='javascript:void(0)' onclick='del(" + list.id + ",\"" + titles + "\")'>删除</a>" +
				"</td></tr>";
			$("#result_info").append(infoStr);
		}
		var pagesStr = "" + data.count + " 条 " + listPage + "/" + pages + " 页"

		//list_page
	}
})

function checkTitle(title){
	var title = title.replace(/"/g, " ");
	return title;
}


function del(id, title) {
	console.log(id,title);
	var msg = "您真的确定要删除《" + title + "》吗？";
	if(!confirm(msg)) {
		window.event.returnValue = false;
	} else {
		console.log("已经执行删除");
		var jsons = {
			"news_id": id
		};
		jsons = JSON.stringify(jsons);
		jsons = JSON.parse(jsons);
		jsons["token"]=q,
		console.log(jsons);
		$.ajax({
			url: "http:///www.ncqsjg.cn:5555/news/delete",
			type: "post",
			cache: false,
			datatype: "json",
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: jsons,
			success: function(data) {
				console.log(data);
				if(data.code == 200) {
					alert("成功删除");
					location.replace(location.href); //成功后刷新页面
				} else {
					alert("删除失败！");
				}
			},
			error: function(data) {
				alert("删除失败！");
			}
		})
		//					$.post("http://47.100.192.151:5555/news/delete", {
		//							name: "news_id",
		//							city: id
		//						},
		//						function(data, status) {
		//							alert("Data: " + data + "\nStatus: " + status);
		//						});
	}
}
