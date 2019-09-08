





http://47.100.192.151:5555/

### 1. 首页请求展示

~~~json
 GET /  

result:
{
code:200, 
data:{xw:[  //四条

    {id:xx,
    title:xx,
    imgs:[{
        name:xx,
        url:xx
        id:xx
    },...]
    }
    ...],
    
zc:[   //两条
    {
      id:xx,
      title:xx,
      content:xx
    },
    ...]
    }
    
hydt:[//行业动态，8条
    {
    id:xx,
    title:xx,
    date:xx
    }
]
     
}

~~~



### 2. 获取新闻列表

~~~json
get /list?type=1&page=1   
//不分类0  公司简介 1  政策法规2 新闻动态 3    公告发布 5   人才招聘 6  联系我们 7  行业动态8
//增加
get /list?type=0&page=1  //查询所有类别的新闻 
type: (gs/zc/xw)[1 2 3]
page:x    （页数(10条/页)）//放宽到15条/页

result:
{
    code:200,
    data:{
		type:1"，  //3 新闻 2政策 1公示  
		count:x,
		list:[
		   {
      	     id:xx,
      		title:xx,
      		//修改
      		date:xx (精确到秒)
      		
      		//增加
      		looked_times: (num)；
      		},
			...]	
			
		}
    
}

~~~



### 3. 获取单篇新闻内容

~~~json
get   /content?id=x


result:
{
    code:200,
    
    data:{
         id  :xx,  
		type :xx, //公司简介 1  政策法规2 新闻动态 3    公告发布 5   人才招聘 6  联系我们 7 
		title :xx,
		author :xx,
		date ：xx,
		looked_times:xx,
         content: xx,
         imgs :[
             {
             name:xx,
             url:xx
             id:xx
             },
             ...
          ],
         fujian:[
             {
               id:xx,
               ext:xx,  //pdf,doc
               name:xx,  
               url:xx,
               size:xx,
               download_times:xx,
             },
             ...
            ]
        }
}


~~~

### 4 根据类型获取最新的一篇文章

~~~
get  /recentnews?type=1     1公司简介   7联系我们

result:
{
    code:200,
    data:{
         id  :xx,  
		type :xx, //公司简介 1  政策法规2 新闻动态 3    公告发布 5   人才招聘 6  联系我们 7 
		title :xx,
		author :xx,
		date ：xx,
		looked_times:xx,
         content: xx,
        imgs :[
             {
             name:xx,
             url:xx
             id:xx
             },
             ...
          ],
         fujian:[
             {
               id:xx,
               ext:xx,  //pdf,doc
               name:xx,  
               url:xx,
               size:xx,
               download_times:xx,
             },
             ...
            ]
    }
    
}




~~~













## 后台管理模块

### 1. 添加新闻请求

~~~json
post  /news/add

发送json数据

{
	typeId:(num), //公司简介 1  
	title:xx,     // 政策法规2
	author:xx,	 // 新闻动态 3    公告发布 5   人才招聘 6  联系我们 7 行业动态8
	content:xx,//文章内容
	imgs:[1,2,3],  //返回的id传回来 
	fujian:[1,2,3...]//fujianid列表
}

返回
{
    code:200
    data:{isOk:1,//成功,失败为0}
    
}
~~~

###2. 删除新闻请求

~~~
post  /news/delete
发送
{
news_id: xx  //待删除新闻的id
}

response:
{
    code:200  //200表示成功 其他失败
    data:'success'  // 失败则返回'failed'
}




~~~



###3. 修改新闻请求 

~~~json
post  /news/change

request:
{	news_id:xx, //新闻数据库中的id
    type:(num), //公司简介 1   政策法规2  新闻动态 3    公告发布 5   人才招聘 6  联系我们 7 行业动态8
	title:xx,     
	author:xx,	
	date:xx
}




response:

{
    code:200  //200表示成功 其他失败
    data:'success'  // 失败则返回'failed'
}



~~~











###上传图片(单个上传)

~~~
post  mutil/form
返回
{
code:200;   //200是ok 400是faile
data:{
   fileName:xxx,
   id:xx,  //数据库的id 上传新闻一并提交
   url:xx,
}

}



{
    "code": 200,
    "data": {
        "fileName": "e5bfab486201ca77fd1ba22fe8f4db9282a38f55.jpg",
        "id": 107,
        "url": "/qs/imgs/e5bfab486201ca77fd1ba22fe8f4db9282a38f55.jpg"
    }
}
~~~



### 上传文件（单个上传）

```JSON
post  mutil/form

返回
{
    code:200   //200是ok 400是faile
    data{
    	fileName:xxx,
    	id:xx,  //数据库的id 上传新闻一并提交
    	url:xx,
        
	}	
}

例如：
{
    "code": 200,
    "data": {
        "fileName": "02-2019-03-017.pdf",
        "id": 102,
        "url": "/qs/files/02-2019-03-017.pdf"
    }
}

```



### 登陆  

```JSON

POST发送json数据  /manage/login

{
    user:(str)
    pwd:(str)
}

返回
{
    code:200  //200表示成功 
    data：{
    		token:xx   //临时token
		  }
}
```





#### 下载文件计数

~~~
get 发送计数请求
url :  /file/count/id=xx     //id是文件id



返回    //后面统一
{
code:200
data:{
    isOk:1; //成功1,失败为0  
}
}
~~~



#### 验证token

~~~
post  /token/check
{
    token:xx
}

response:
{
    code:200   // 验证成功200  错误400
    data: "token is right"  //验证错误就没data这个字段
}



~~~

#### 修改管理员密码

~~~
post  /admin/pwd/update
{
    user:xx,
    old_pwd:xx,
    new_pwd:xx,
    token:xx
}

response:
{
    code:200,  // 验证成功200  错误400
    data:"success"  //修改失败就为空  
}
~~~



