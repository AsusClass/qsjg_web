var url = document.location.toString(); // /list?type=1&page=1
console.log(url);
var title = "暂无内容";
//				console.log(getParm);
var newsId = GetUrlParam("id");
//var website = "http://47.100.192.151:8888";
console.log(newsId);
if(newsId == undefined) {
	newsId = 0;
}
//	console.log("执行ajax");
//$.ajax({
//	url: "http://47.100.192.151:5555/content",
//	data: {
//		id: newsId
//	},
//	type: 'get',
//	dataType: 'json',
//	success: function(data) {
//		if(data.code==200){
//		data = data.data;
//		console.log(data);
//		//		var author=document.getElementsByTagName("author");
//		//		console.log(author);
//		//		author.value = data.author;
//		$("#catid").val(data.type);
//		$("#title").val(data.title);
//		$("#author").val(data.author);
//		$(".summernote").summernote('code', data.content);
//		//$(".summernote").code(data.content)
//		imgs = data.imgs;
//		for(var i = 0; i < imgs.length; i++) {
//			var tempstr = imgs[i].id + ",";
//			fileListp = $("#fileList")[0];
//			console.log(fileListp);
//			console.log((fileListp.innerText));
//			fileListp.innerText += tempstr;
//		}
//		fujian = data.fujian;
//		for(var i = 0; i < fujian.length; i++) {
//			var tempstr = fujian[i].id + ",";
//			imgListp = $("#imgList")[0];
//			console.log(imgListp);
//			console.log((imgListp.innerText));
//			imgListp.innerText += tempstr;
//		}
//	}
//		}
//});


imgWebsite = "http://www.ncqsjg.cn:8888";
$(function() {
	$('.summernote').summernote({
		height: 200,
		tabsize: 2,
		lang: 'zh-CN',
		//上传图片的接口
		callbacks: { //本文的主题来了，调用官方提供的callbacks接口，用来自定义
			onImageUpload: function(files) { //onImageUpload代表图片上传事件，默认将图片变为base64的那个事件
				var data = new FormData(); //html5提供的formdata对象，将图片加载为数据的容器
//				data["token"]=q;
				if(checkImageSize(files[0])) {
					data.append('token',q);
					data.append('image_up', files[0]); //加载选中的第一张图片，并给这图片数据标记一个'image_up'的名称
					console.log(data);
					console.log(files[0]);
					//调用上传图片
					$.ajax({
						url: 'http://www.ncqsjg.cn:5555/img/add', //上传图片请求的路径
						//url:"http://127.0.0.1:5555/file/add",
						type: 'POST', //方法
						data: data, //数据
						processData: false, //告诉jQuery不要加工数据
						contentType: false, //<code class="javascript comments"> 告诉jQuery,在request head里不要设置Content-Type
						success: function(data) { //图片上传成功之后，对返回来的数据要做的事情
							console.log(data);
							if(data.code == 200) {
								data = data.data;
								console.log(imgWebsite + data.url)
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
		//					console.log(file1);
		//					console.log(file1.files);
		upFileFunc(file1);
	});
});
$(function() {
	$("#upFile2").click(function() {
		$("#imgWait").show();
		var file2 = $("#file_2")[0]
		//					console.log(file1);
		//					console.log(file1.files);
		upFileFunc(file2);
	});
});
$(function() {
	$("#upFile3").click(function() {
		$("#imgWait").show();
		var file3 = $("#file_3")[0]
		//					console.log(file1);
		//					console.log(file1.files);
		upFileFunc(file3);
	});
});
$(function() {
	$("#upFile4").click(function() {
		$("#imgWait").show();
		var file4 = $("#file_4")[0]
		//					console.log(file1);
		//					console.log(file1.files);
		upFileFunc(file4);
	});
});

function upFileFunc(file1) {
	var fileData = new FormData();
	fileData.append("token",q);
	var fileList = file1.files;
	var i_t = 0;
	for(var i = 0; i < fileList.length; i++) {
		var fileObj = fileList[i];
		console.log(fileObj);
		var flag1 = checkFileExt(fileObj);
		if(flag1) {
			var flag2 = checkFileSize(fileObj);
		}
		console.log(flag1, ',', flag2);
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
			url: "http:///www.ncqsjg.cn:5555/file/add", //上传文件请求的路径
			//url:"http://127.0.0.1:5555/file/add",
			type: "POST",
			data: fileData,
			/**
			 *必须false才会自动加上正确的Content-Type
			 */
			contentType: false,
			/**
			 * 必须false才会避开jQuery对 formdata 的默认处理
			 * XMLHttpRequest会对 formdata 进行正确的处理
			 */
			processData: false,
			success: function(data) {
				console.log(data);
				if(data.code == "200") {
					alert("上传成功！");
					var fileInfo = data.data;
					console.log(fileInfo);
					console.log(fileInfo.id);
					var tempstr = fileInfo.id + ",";

					fileList1p = $("#fileList")[0];
					console.log(fileList1p);
					console.log((fileList1p.innerText));
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
	console.log(jsons);
	//获取图像与文件的id
	imgStr = $(imgList).text();
	imgStr = imgStr.slice(0, imgStr.length - 1);
	imgStr = "[" + imgStr + "]";
	console.log(imgStr);
	//				var imgListArray = imgStr.split(",");
	//				console.log(imgListArray);
	fileStr = $(fileList).text();
	fileStr = fileStr.slice(0, fileStr.length - 1);
	fileStr = "[" + fileStr + "]";
	console.log(fileStr);
	//				var fileListArray = fileStr.split(",");
	//				console.log(fileListArray);

	jsons["imgs"] = imgStr;
	jsons["fujian"] = fileStr;
	//检查不为空				
	var flag1 = checkNULL(jsons.title, "titleAlert");
	var flag2 = checkNULL(jsons.content, "editordataAlert");
	jsons.content=jsons.content.replace(/&/g,"#");
	jsons.content=jsons.content.replace(/#nbsp;/g,"#space#");//替换空格为特殊符号
	jsons.content=jsons.content.replace(/\s/g,"#space#");//替换空格为特殊符号
//	jsons.content=jsons.content.replace(/ #/g,"#");	
	var flag = flag1 && flag2;
	jsons["token"]=q,
	console.log(jsons);
	if(flag == false) {
		alert("有必填内容未填写！");
	} else {
		$("#show3").html(JSON.stringify(jsons));
		//get是通过地址栏传参数
		$.ajax({
			url: "http://www.ncqsjg.cn:5555/news/add",
			cache: false,
			type: "post",
			datatype: "json",
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: jsons,
			success: function(data) {
				console.log(data);
				if(data.code == 200) {
					alert("提交成功！");
					window.location.href = 'design.html?type=0&page=1';
				}
				else{
					alert("提交失败！");
				}
			}
		});
		//var sHTML = $('.summernote').summernote('code');
	}
	//console.log(sHTML);
}
