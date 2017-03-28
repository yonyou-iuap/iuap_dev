
测试服务器访问地址：http://10.10.5.217:8080/boss_admin 

前端框架说明地址：http://172.20.14.49/iweb/

——————————————————————————————————————————————————————————————————
# BOSS_CLIENT前端项目开发指导

## 0. 环境准备

- 全局安装nodejs环境，下载请前往[nodejs.org](https://nodejs.org)，如果对nodejs基础部分感兴趣，请前往[Node.js知识详解](http://guoyongfeng.github.io/idoc/html/%E6%8A%80%E6%9C%AF%E5%88%86%E4%BA%AB/Node.js%E7%9F%A5%E8%AF%86%E8%AF%A6%E8%A7%A3.html)。
- 下载Git客户端，方便命令行操作，下载请前往[git-scm.com](https://git-scm.com/download/)，如果你还不熟悉基本的git命令行操作，请前往[学习Git](http://guoyongfeng.github.io/idoc/html/%E6%8A%80%E6%9C%AF%E5%88%86%E4%BA%AB/%E5%AD%A6%E4%B9%A0Git.html)。
- 选一款自己喜欢的编辑器：Atom/Sublime Text 3/Webstorm，贴一下集中的导航[下载地址](http://guoyongfeng.github.io/idoc/html/%E5%9B%A2%E9%98%9F/%E6%96%B0%E5%91%98%E5%B7%A5%E5%85%A5%E8%81%8C%E6%8C%87%E5%8D%97.html)。
- 推荐下载Chrome或Firefox进行前端开发调试


以上下载之后，请对nodejs环境和git的做初步的配置，请参考[Nodejs环境配置和Git基本配置](http://guoyongfeng.github.io/idoc/html/%E6%8A%80%E6%9C%AF%E5%88%86%E4%BA%AB/Nodejs%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE%E5%92%8CGit%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE.html)


## 1. 下载项目并且启动

```
$ git clone git@git.yonyou.com:lujwa/Boss_Client.git
$ cd Boss_Client
# 切换到develop分支进行开发
$ git checkout develop
# 安装依赖
$ npm install
# 启动项目
$ node app.js
# 访问http://localhost:8080
```

## 2. 开发和调试

### 2.1 目录说明

```
│  .gitignore
│  .project
│  app.js 本地调试的入口文件
│  mock.config.js 配置模拟路由
│  package.json  npm管理文件
│  proxy.js 代理文件
│  README.md
├─docs 技术文档，或是前端规范，注意事项等文档内容
│      stylesheet.md
├─mockData 本地模拟调试的模拟数据
│      addAttendance.json
├─examples 查看示例请前往[best-practice](http://git.yonyou.com/guoyff/best-practice/tree/master)
├─node_modules npm下载的第三方package
├─src
│      │  index.html
│      │  
│      ├─config requirejs的配置文件
│      │      require.config.js
│      ├─iwebDemos iweb提供的示例目录
│      │
│      ├─pages 存放业务单页面，每个目录一个页面
│      │  ├─attendance
│      │  │      attendance.css
│      │  │      attendance.html
│      │  │      attendance.js
│      │  │      map.js
│      │  └─staff
│      │          staff.css
│      │          staff.html
│      │          staff.js
│      ├─static 图片、图标字体、音频视频等
│      ├─styles 样式文件
│      ├─util 公共方法或是工具函数
│      └─vendor 存放第三方类库或是框架
│
└─temp 备份文件，不用管

```

### 2.2 开发起步

- 在 `src/pages` 目录下新增功能页面，可参考其他目录的代码。
- 为了方便调试，在 `src/index.html` 文件内新增你的页面 URL，具体可以看里面的注释部分。

```
$ cd src/pages && mkdir demo
$ cd demo && touch demo.html demo.js demo.css
```

代码清单：`src/pages/demo/demo.html`

```
.demo {
  border: 1px solid #ccc;
}
```

代码清单：`src/hrcloud/pages/demo/demo.css`

```
<div class="demo">
  <h3>
    这是一个示例页面的html代码片段
  </h3>
</div>
```
代码清单：`src/pages/demo/demo.js`

```
define(function(require, module, exports){
  // 引入相关的功能插件或模块
  var html = require('text!./demo.html');
  var demoStyles = require('css!./demo.css');

  function DemoLogic(){
    this.init = function(){
      alert('hey, man!');
    }
  }

  // 以下部分代码基本是格式化的，暴露的init方法用于和portal进行集成
  return {
		init: function( content ) {
			// 插入html片段
			content.innerHTML = html;
			// 执行我们的业务主逻辑
			new DemoLogic().init();
		}
	}

})

```

> 对以上代码的基本说明：引入了相应的html片段和css样式，另外，由于是和portal集成，在portal部分加载了以下公共的类库或是框架：
> 1. `bootstrap`
> 2. `director` **处理前端路由**
> 3. `jquery`
> 4. `knockout` **前端MVVM框架**
> 5. `require`
> 6. `hrfonts`  **hrfonts是UE通过iconfont制作的图标字体库**
> 7. `font-awesome`
> 8. `uui`   **UUI是UAP WEB平台基于knockout和jquery封装的集数据模型和功能插件于一体的内部前端框架**

这样基本编辑之后，在`src/hrcloud/index.html`文件中按照注释说明，添加你新增页面的导航，代码如下：

```
<a href="#/hrcloud/pages/demo/demo"><i class="demo-menu-icon u-color-grey-400 fa fa-home fa-2x" role="presentation"></i>示例</a>
```

### 2.3 数据模拟和联调

调试是开发过程中关键而重要的一步，经常在这个阶段出现一些问题，建议的开发步骤是：
1. 前端本地模拟数据调试，跑通业务逻辑
2. 前后端本地调试，跑通接口数据
3. 部署到服务器上联调

#### 2.3.1 前端本地模拟数据调试

- 数据模拟配置文件： `mock.config.js`，先在这个文件中新增一条模拟路由：

```
{
	  type:"get",         // 请求类型
	  url:"/demo/data",   //请求url
	  json:"demo.json"    //模拟数据文件，路径地址相对于mockData目录
}
```

- 在mockData目录下新增相应的json模拟数据

```
$ cd mockData && touch demo.json
```

编辑demo.json里面的模拟数据，代码清单：`mockData\demo.json`

```
{
    "statusCode": 200,
    "message": "操作成功",
    "method": null,
    "data": {
        "name": "guoyongfeng"
    },
    "callbackType": null
}
```

具体应该写什么样的模拟数据，需要前后端提前约定模拟接口和接口数据。

- 现在我们是本地模拟数据，请修改`app.js`里面的`useProxy`为`false`。
- 在demo.js文件里面编辑代码，获取我们模拟的数据

```
define(function(require, module, exports){

  // 引入相关的功能插件或模块
  var html = require('text!./demo.html');
  var demoStyles = require('css!./demo.css');

  function DemoLogic(){
    this.init = function(){
      alert('hey, man!');
      this.getData();
    }
  }

  DemoLogic.prototype = {
    getData: function(){
      $.ajax({
        url: '/demo/data',
        type: 'get',
        dataType: 'json',
        success: function( res ){
          alert( res.data.name );
        }
      })
    }
  };

  // 以下部分代码基本是格式化的，暴露的init方法用于和portal进行集成
  return {
		init: function( content ) {
			// 插入html片段
			content.innerHTML = html;
			// 执行我们的业务主逻辑
			new DemoLogic().init();
		}
	}

})

```

#### 2.3.2 前后端本地联调

- 将`app.js`文件中的`useProxy`值设置为`true`即可
- `app.js`里有个host字段用于配置请求哪个服务器地址的数据，默认是`http://10.10.5.217:8080`，在前后端本地联调过程中，可以修改这个host指到对应的服务端工程师本机的服务地址，如：`var host = 'http://10.1.255.252:8081';`

### 2.4 iweb基本使用

> 完成表单的展示<br>
> 完成表单的的增删改查

[iweb开发指导](http://172.20.14.49/iweb/guide/index.html)

### 2.5 联调测试

### 开发环境
| URL | 用户名  |  密码  |
| --- | --- | --- |
| 10.10.5.217/ | hrtest | 666666a|

### 测试环境
| URL | 用户名  |  密码  |
| --- | --- | --- |
| 172.20.14.197/portal | hrtest | 666666a|

### 构建到开发服务器

> 请在本地测试正常后构建，不要频繁操作。

- 登录`ci.yonyou.com`
- 用户名/密码：guoyff/Aa296512521
- 点击hr一栏
- 点击最下面的fe job
- 点击左侧的开始构建

## 3. 开发相关资源
### 3.1 UE 产出的 PSD 设计稿

请访问这里下载：http://wiki.yonyou.com/display/NCCLOUDHR/ueui

### 3.2 UUI相关的学习资料

- [iweb文档](http://172.20.14.49/iweb/)
- [示例](http://ieop.yyuap.com/views/demo.html)

### 3.3 QQ群交流咨询问题

QQ群号： 

有不懂的问题一定要及时说出来讨论交流，在群里找丁锐锋同学或是李传忠同学进行解答。

## 4. 前端开发任务列表

### 4.1 2016年4月第一版

| # | 功能 | 前端开发 | 优先级 | 状态 | 是否有设计图 | 工作量预估 | 开发时间 | 前后端联调时间 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | 菜单管理 | 岳振华 | 10 | 进行中| 是 | ||
| 2 | 用户管理 | 岳振华 | 10 |进行中|是 | |||
| 3 | 单位管理 | 陈官胜 | 10 |进行中| 是 | |||
| 4 | 角色管理 | 陈官用 | 9 | 进行中  | 是 | |||
 

### 4.2 2016年4月第二版

故事更新中...

### git操作
http://blog.csdn.net/wfdtxz/article/details/7973608
本地
$ git checkout -b develop master //以master为源创建分支develop
$ git branch test //创建本地test分支

远程分支推送
$ git push origin test:master         // 提交本地test分支作为远程的master分支
$ git push origin test:test              // 提交本地test分支作为远程的test分支

查看远程分支
git branch -a  //查看远程分支
git checkout -b local-branchname origin/remote_branchname   //将远程分支映射到本地命名为local-branchname  的一分支
git fetch origin develop:tmp 

git merge develop 将develop 合并到  这是在master分支上执行的命令

提交到远程
