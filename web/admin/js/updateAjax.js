var url = document.location.toString(); // /list?type=1&page=1
//console.log(url);
var title = "暂无内容";
//				//console.log(getParm);
var newsId = GetUrlParam("id");
var website = "http://www.ncqsjg.cn";
//console.log(newsId);
if(newsId == undefined) {
	newsId = 0;
}
//	//console.log("执行ajax");
var q = getCookie('token');
//console.log(q);
$.ajax({
	url: "http://www.ncqsjg.cn:5555/content",
	data: {
		id: newsId
	},
	type: 'get',
	dataType: 'json',
	success: function(data) {
		//console.log(data);
		if(data.code == 200) {
			data = data.data;
			//console.log(data);
			//		var author=document.getElementsByTagName("author");
			//		//console.log(author);
			//		author.value = data.author;
			$("#catid").val(data.type);
			var dateStr = data.date;
			dateStr = dateStr.split("日");
			dateStr1 = dateStr[0].split("年");
			var year = dateStr1[0];
			dateStr1 = dateStr1[1].split("月");
			var mouth = dateStr1[0];
			mouth = buquanshijian(mouth);
			var day = dateStr1[1];
			day = buquanshijian(day);
			dateStr2 = dateStr[1].split("时");
			var hour = dateStr2[0];
			hour = buquanshijian(hour);
			dateStr2 = dateStr2[1].split("分");
			var min = dateStr2[0];
			min = buquanshijian(min);
			var sec = dateStr2[1].split("秒")[0];
			sec = buquanshijian(sec);
			dateStr = year + "-" + mouth + "-" + day + "T" + hour + ":" + min + ":" + sec;
			//console.log(dateStr);
			//			$("#date").value=dataStr;
			$("#date").val(dateStr);
			$("#title").val(data.title);
			$("#author").val(data.author);
			//		$(".summernote").summernote('code', );
			var content = data.content
			//		content = content.replace(/#/g, "&");
			$("#editor").val(content); //这里不做渲染
			content = content.replace(/\+/g, " ");
			content = content.replace(/#space#/g, " ");
			//console.log(content);
			$("#editorShow").append(content);
			//$(".summernote").code(data.content)

			if(data.fujian != undefined) {
				fujian = data.fujian;
				//console.log(fujian);
				if(fujian.length > 0) {
					for(e = 0; e < fujian.length; e++) {
						if(e == fujian.length - 1) {
							var m = "."
						} else {
							var m = "；"
						}
						var b = fujian[e].size / 1024 / 1024;
						b = b.toFixed(2);
						var h = "<li><a href='" + website + fujian[e].url + "' target='_blank'  onclick='fileCount(" + fujian[e].id + ")';>" + fujian[e].name + m + "</a><span>[文件大小：" + b + "Mb 下载次数：" + fujian[e].download_times + "次]</span></li>";
						$("#fujian").append(h)
					}
				}
				for(var i = 0; i < fujian.length; i++) {
					var tempstr = fujian[i].id + ",";
					fileListp = $("#fileList")[0];
					//console.log(fileListp);
					//console.log((fileListp.innerText));
					fileListp.innerText += tempstr;
				}
			}
			if(data.imgs != undefined) {
				imgs = data.imgs;
				for(var i = 0; i < imgs.length; i++) {
					var tempstr = imgs[i].id + ",";
					imgListp = $("#imgList")[0];
					//console.log(imgListp);
					//console.log((imgListp.innerText));
					imgListp.innerText += tempstr;
				}
			}
		}
	}
});

imgWebsite = "http://47.100.192.151";
$(function() {
	$('.summernote').summernote({
		height: 200,
		tabsize: 2,
		lang: 'zh-CN',
		//上传图片的接口
		callbacks: { //本文的主题来了，调用官方提供的callbacks接口，用来自定义
			onImageUpload: function(files) { //onImageUpload代表图片上传事件，默认将图片变为base64的那个事件
				var data = new FormData(); //html5提供的formdata对象，将图片加载为数据的容器
				if(checkImageSize(files[0])) {
					data.append('image_up', files[0]); //加载选中的第一张图片，并给这图片数据标记一个'image_up'的名称
					//调用上传图片
					$.ajax({
						url: 'http://47.100.192.151:5555/img/add', //上传图片请求的路径
						//url:"http://127.0.0.1:5555/file/add",
						type: 'POST', //方法
						data: data, //数据
						token: q,
						processData: false, //告诉jQuery不要加工数据
						contentType: false, //<code class="javascript comments"> 告诉jQuery,在request head里不要设置Content-Type
						success: function(data) { //图片上传成功之后，对返回来的数据要做的事情
							//console.log(data);
							if(data.code == 200) {
								data = data.data;
								//console.log(imgWebsite + data.url)
								$(".summernote").summernote('insertImage', imgWebsite + data.url); //调用内部api——insertImage以路径的形式插入图片到文本编辑区
								$("#imgList")[0].innerText += data.id + ",";
							} else {
								alert(data['message']);
							}
						}
					});
				}
			}
		}
		//上传图片回调结束
	});
});
$(function() {
	$("#upFile1").click(function() {
		$("#imgWait").show();
		var file1 = $("#file_1")[0]
		//					//console.log(file1);
		//					//console.log(file1.files);
		upFileFunc(file1);
	});
});
$(function() {
	$("#upFile2").click(function() {
		$("#imgWait").show();
		var file2 = $("#file_2")[0]
		//					//console.log(file1);
		//					//console.log(file1.files);
		upFileFunc(file2);
	});
});
$(function() {
	$("#upFile3").click(function() {
		$("#imgWait").show();
		var file3 = $("#file_3")[0]
		//					//console.log(file1);
		//					//console.log(file1.files);
		upFileFunc(file3);
	});
});
$(function() {
	$("#upFile4").click(function() {
		$("#imgWait").show();
		var file4 = $("#file_4")[0]
		//					//console.log(file1);
		//					//console.log(file1.files);
		upFileFunc(file4);
	});
});

function buquanshijian(date) {
	if(date.length == 1) {
		date = "0" + date;
	}
	return date;
}

function upFileFunc(file1) {
	var fileData = new FormData();
	var fileList = file1.files;
	var i_t = 0;
	for(var i = 0; i < fileList.length; i++) {
		var fileObj = fileList[i];
		//console.log(fileObj);
		var flag1 = checkFileExt(fileObj);
		if(flag1) {
			var flag2 = checkFileSize(fileObj);
		}
		//console.log(flag1, ',', flag2);
		if(flag1 && flag2) {
			fileData.append("file_up", fileObj);
			$("#imgWait").hide();
			i_t = i_t + 1;
		} else {
			$("#imgWait").hide();
		}
	}
	if(i_t == fileList.length) {
		$.ajax({
			url: "http://www.ncqsjg.cn:5555/file/add", //上传文件请求的路径
			type: "POST",
			data: fileData,
			/**
			 *必须false才会自动加上正确的Content-Type
			 */
			contentType: false,
			token: q,
			/**
			 * 必须false才会避开jQuery对 formdata 的默认处理
			 * XMLHttpRequest会对 formdata 进行正确的处理
			 */
			processData: false,
			success: function(data) {
				//console.log(data);
				if(data.code == "200") {
					alert("上传成功！");
					var fileInfo = data.data;
					//console.log(fileInfo);
					//console.log(fileInfo.id);
					var tempstr = fileInfo.id + ",";

					fileList1p = $("#fileList")[0];
					//console.log(fileList1p);
					//console.log((fileList1p.innerText));
					fileList1p.innerText += tempstr;

					$("#imgWait").hide();
				}
				if(data.code == "400") {
					alert(data.code);
					alert("上传失败！");
					$("#imgWait").hide();
				}
			},
			error: function() {
				alert("上传失败！");
				$("#imgWait").hide();
			}
		});
	} else {
		alert("有文件不符合规则，请重新选择。");
	}
}

//$(function() {
//	NOW();
//});

function SetNow(nowDate, nowTime) {
	var nows = nowDate + "T" + nowTime;
	$("#date").val(nows);
}
$(function() {
	$("#btnNow").click(function() {
		var date = NOW();
		//console.log(date)
		SetNow(date[0], date[1]);
	});
});

function mySubmit() {
	var myform = $("#myform");
	//返回字符串xxx=xxx&yyy=yyy;
	var formData = myform.serialize();
	$("#show").html(formData);
	// 返回的是json数组[{xxx:xxx},{yyy:yyy}]
	formData = myform.serializeArray();
	$("#show2").html(JSON.stringify(formData));

	//将json数组转为json 对象
	var jsons = transformToJson(formData);
	//console.log(jsons);
	var date = jsons["date"];
	var date = date.split("T");
	var date1 = date[0].split("-");
	var date2 = date[1].split(":");
	//console.log(date2);
	var date = date1[0] + "年" + date1[1] + "月" + date1[2] + "日" + date2[0] + "时" + date2[1] + "分" + date2[2] + "秒";
	jsons["date"] = date;
	jsons["news_id"] = newsId;
	jsons["token"]=q,
	//	jsons=JSON.stringify(jsons);
	delete jsons.content;
	var flag = true;
//	console.log(jsons);
	if(flag == false) {
		alert("有必填内容未填写！");
	} else {
		//		$("#show3").html(JSON.stringify(jsons));
		//get是通过地址栏传参数
		$.ajax({
			url: "http://www.ncqsjg.cn:5555/news/change",
			cache: false,			
			type: "post",
			datatype: "json",
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: jsons,
			success: function(data) {
				console.log(data);
				if(data.code == 200) {
					alert("修改成功！");
					window.location.href = 'design.html?type=0&page=1';
				}
				else{
					alert("提交失败！");
				}
			}
		});
		//var sHTML = $('.summernote').summernote('code');
	}
	////console.log(sHTML);
}
