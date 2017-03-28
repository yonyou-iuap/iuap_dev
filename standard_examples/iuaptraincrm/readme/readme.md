# 项目示例概述 #
## 项目功能说明 ##
本系统作为示例项目，提供了后台管理的基本功能，预留了三个模块；分别为系统管理、基础档案、补贴管理；

其中系统管理包含基础数据（人员档案）、组织管理（组织维护、部门维护）、权限管理（用户管理、功能节点注册、角色管理、功能按钮注册、岗位维护、用户申请、用户审核）</br>
基础档案包含产品信息（品牌维护、车辆类别维护、车系维护、车系毛利维护、车型维护、提成系数）、竞品管理（竞品品牌、竞品车型维护）、客户信息（客户信息维护）</br>
补贴管理包含补贴维护（国补申报）
以上为该系统的所有功能；

### 典型的场景应用: ###
**系统管理-基础数据-人员档案** ：该界面比较复杂又比较典型；提供了查询区，为卡片列表界面；列表界面显示人员信息，通过新增或者修改进入编辑界面即卡片界面，提供了主子结构，主为人员信息的编辑，子为任职信息的显示；
并且查询区域中使用了参照控件，其中所属组织及所属部门控件存在关联操作；可供开发参考；</br>
**系统管理-组织管理-组织维护**：典型的左树右卡界面；并且对于界面的状态进行了控制，分为编辑态和浏览态；其中像组织类型与组织状态都为下拉控件，演示了下拉控件的使用及不可编辑的特性</br>
**系统管理-权限管理-用户管理**：典型的单实体卡片列表界面；包含分页及查询区域的特性，及表格中参照翻译的处理</br>
**基础档案-产品信息-车型维护/车型毛利维护**：提供了导出、下载、导入功能</br>
**基础档案-产品信息-车型维护**：列表卡片界面，并且在列表中提供超链接的方式跳转到浏览界面；浏览界面通过tab页签的方式实现了多子表展现；并在卡片界面的电池电量的点击事件弹出模态框的特性</br>
**基础档案-竞品管理-竞品品牌**：单实体卡片列表界面，该节点中品牌编码使用编码规则组件的api，完成品牌编码的生成</br>
**补贴管理-补贴维护-国补申报**：项目中保留的业务单据，具有快速查询区域、列表卡片方式、表格超链接进入浏览界面、导入、导出、文件上传、单据号生成（编码规则组件）、参照组件使用、一主多子、分页及相关字段的编辑校验及保存校验的场景

## 项目代码说明 ##
本代码为示例系统，项目代码分为前端项目及后端项目，并在开发上采用了前后端分离的开发方式； 前端项目iuaptraincrm_front、后端项目iuaptraincrm;前后端基于iuap的开发框架进行开发；项目中使用了参照及附件上传组件作为服务；故在环境搭建时，还需要同时搭建参照服务及上传组件的服务；
### 前端代码示例结构说明：###
- basedata 基础数据
- bter_corp 公司、组织
- bter_dept 部门
- bter_func 功能节点注册
- bter_FunctionActivity 按钮注册
- bter_position 岗位维护
- bter_psndoc 人员档案
- bter_role 角色管理
- dashboard 首页消息
- subsidy 国补申报
- tools 工具；error异常的处理；iReferComp参照相关动作（如参照过滤、参照翻译等）；util保存动作校验逻辑
- user 用户管理

### 后端代码示例结构说明： ###
- basedata 基础数据
- billcode 编码规则公共服务，用来生成单据号等
- common 后台公共模块，含validator校验器、ref参照控制模型、listener启动监听设置、exception后台异常处理、base.repository封装的持久化层、auth.realm扩展的认证域
- corp 公司、组织
- dept 部门
- enums 枚举工具类
- ieop security基于rbac权限模型的扩展
- psn 人员档案
- ref 参照本身服务
- subsidy 国补申报
- system 系统登陆及首页信息服务
- user 用户管理

## 环境搭建说明 ##

环境包里包括：
- 前端项目iuaptraincrm_front

>由于项目采用全后端分离的开发方式，并且前端开发是基于nodejs；对于需要运行环境的可以通过安装nodejs及gulp模块等；通过gulp方式即可启动前端服务；
也可以通过nginx进行配置资源路径到dist目录下进行访问；

- 后端项目iuaptraincrm

>application.properties数据源的正确设置
redis及zookeeper的服务支持

- iuaptraincrm.sql；
- 运行环境需要uitemplate_web（uui）提供参照服务；

>注意修改:uitemplate_web\WEB-INF\conf\uitemplate目录下的uitemplate.properties配置文件及datasource.properties配置文件
对于uitemplate.properties中refctx=http://127.0.0.1:8090这个地址要改为示例系统后端服务的地址；
datasource.properties中修改数据源的连接配置信息（与后端示例系统配置的一致即可）

- iuap-saas-filesystem-service提供文件服务；

>文件上传服务使用的是Alioss，也可以直接切换为FDFS；这里使用Alioss需要注意几项设置
application.properties中数据源的设置
iuapfile.properties对应的阿里oss账户中的defaultBucket设置与defaultBucketRead设置

## 其他说明 ##
- applicationContext-cache.xml缓存配置文件
- applicationContext-persistence.xml持久化配置文件；持久化层使用的是基于iuapjdbc的方式，并且采用uapoid方式（可参考对象OID组件开发）生成唯一性主键；
- applicationContext-restful.xml提供restful方式调用服务
- applicationContext-shiro.xml登陆认证及权限认证配置
- billcode-applicationContext.xml编码规则配置
- applicationContext.xml总装配置文件
- spring-mvc.xml
- web.xml

### 编码规则生成单据号 ###
例如：国补申报单的单据编号需要符合***GBSB+[日期]20161109+[流水号]0001***规范
- 1.配置billcode-applicationContext.xml文件，编码规则组件需要zookeeper做分布式锁，所以保证zk服务正常，还需要依赖数据库服务，也需要正常设置
- 2.后端代码中com.yonyou.iuap.crm.billcode下的代码进行了编码规则的封装，提供了统一的api；
*** 补充说明：由于业务上没有要求进行单据号的断号补齐，编码连续；故提供了获取单据号的方法，在业务层面只调用该服务去获取单据号，对于在业务层面获取单据号出现异常，也就没有考虑退号操作，而对于编码规则组件本身提供了事务补偿机制，如果需要，可以在异常处理中通过退号操作以保证断号补齐 ***

```
public interface ICommonComponentsService {

	/**
	 * 根据编码号获取单据号
	* TODO description
	* @author
	* @date 2016年12月9日
	* @param ruleCode
	* @return
	* @throws AppBusinessException
	 */
	public String generateOrderNo(String ruleCode) throws AppBusinessException;
}
```

- 3.在数据库中需要注册相应的编码规则pub_bcr_rulebase及编码元素
pub_bcr_elem

#### 编码规则主表 pub_bcr_rulebase ####

主键|编码|名称|编码方式|是否可编辑|断号补齐|是否默认|是否启用|序列号是否补零|是否随机数|创建时间
-|-|-
pk_billcodebase|rulecode|rulename|codemode|iseditable|isautofill|	isdefault|isused|islenvar|isgetpk|createdate
bydsycbillcode000001|CS|国补申报|after|N|N|Y|Y|Y|N|sysdate()

`insert into pub_bcr_rulebase(pk_billcodebase,rulecode,rulename,codemode,iseditable,isautofill,isdefault,isused,islenvar,isgetpk,createdate) values ('bydsycbillcode000001','CS','国补申报','after','N','N','Y','Y','Y','N',sysdate());`

#### 编码元素 pub_bcr_elem ####

序号|元素类型|元素值|元素长度|流水号依据|补位方式|日期格式|补位符|创建时间
-|-|-
备注|0：流水号</br>1：常量</br>2：系统时间</br>3：随机数|0：数字</br>1：数字+字母||0：不作为流水依据</br>1：按年流水</br>2：按月流水</br>3：按日流水|0：不补位</br>1：左补位</br>2：右补位|yy</br>yyyy</br>yyMM</br>yyyyMM</br>yyMMdd</br>yyyyMMdd||
eorder|elemtype|elemvalue|elemlenth|isrefer|fillstyle|	datedisplayformat|fillsign|createdate
0	|1	|GBSB	|4	|0	|0	|       |	|	sysdate()
1	|2	|     |6	|2	|0	|yyMMdd |	|	sysdate()
2	|0	|0	  |4	|0	|1	|	      |0| sysdate()

```
insert into pub_bcr_elem(pk_billcodeelem,pk_billcodebase,eorder,elemtype,elemvalue,elemlenth,isrefer,fillstyle,datedisplayformat,fillsign,createdate) values ('bydsycbillitem000001','bydsycbillcode000001','0','1','GBSB','4','0','0','','',sysdate());
insert into pub_bcr_elem(pk_billcodeelem,pk_billcodebase,eorder,elemtype,elemvalue,elemlenth,isrefer,fillstyle,datedisplayformat,fillsign,createdate) values ('bydsycbillitem000002','bydsycbillcode000001','1','2','','6','2','0','yyMMdd','',sysdate());
insert into pub_bcr_elem(pk_billcodeelem,pk_billcodebase,eorder,elemtype,elemvalue,elemlenth,isrefer,fillstyle,datedisplayformat,fillsign,createdate) values ('bydsycbillitem000003','bydsycbillcode000001','2','0','0','4','0','1','','0',sysdate());
```
- 4.在保存单据的逻辑处，调用api生成单据号，并保持单据

```
@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object saveOrderInfo(
			@RequestBody CountrysubsidyVO mainVo, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			…………
			mainVo.setStatus(VOStatus.NEW);
			mainVo.setVstatus(DictCode.COUNTRY_SUBSIDY_STATUS_SAVE);//已保存
			mainVo.setVbillno(commonService.generateOrderNo("CS"));//单号生成规则
			mainVo.setPk_org(AppInvocationInfoProxy.getPk_Corp());//组织
			mainVo.setVdeclaredept(AppInvocationInfoProxy.getPk_Dept());//部门
			mainVo.setCreator(AppInvocationInfoProxy.getPk_User());//取登录用户的组织和部门信息
			service.saveCountrysbusidy(mainVo);
			…………
			result.put("msg", "保存成功!");
			result.put("flag", "success");
		} catch (Exception e) {
			result.put("msg", "保存失败!");
			result.put("flag", "fail");
			throw new AppBusinessException(e.getMessage());
		}
    return result;  
	}
```

### 上下文信息 ###
对于实际应用中，会需要全局的上下文信息，平台有提供InvocationInfoProxy，对于更多业务层面的东西，我们需要去做扩展，那么应该如何去做呢？
有两种方式实现，一种通过cookie机制实现，另外一种需要使用sessionManager来实现；

- 方案一：

如下代码示例，平台提供了InvocationInfoProxy，业务上对此进行了扩展，在上下文中提供了提供了公司、部门等更多的信息；

```
public class AppInvocationInfoProxy extends InvocationInfoProxy{

	/**
	 *
	* TODO 获取当前公司或组织pk
	* @author
	* @date 2016年12月9日
	* @return
	 */
	public static String getPk_Corp() {
		return (String) InvocationInfoProxy.getParameter("pk_corp");
	}

	/**
	 *
	* TODO 设置当前公司或组织pk
	* @author
	* @date 2016年12月9日
	* @param pk_corp
	 */
	public static void setPk_Corp(String pk_corp) {
		InvocationInfoProxy.setParameter("pk_corp", pk_corp);
	}
	……………
}
```

在登陆逻辑logincontroller中设置相关用户信息，通过将相关信息写入cookie的方式；每次浏览器端请求都会携带cookie进行访问，结合iuap-auth认证组件；会自动从cookie中恢复信息至上下文中；

```
@RequestMapping(method = RequestMethod.POST,value="formLogin")
	@ResponseBody
	public Map<String,String> formLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws AppBusinessException {
……
try {
    tp.getExt().put("userCNName" , URLEncoder.encode(user.getName(), "UTF-8"));
  } catch (UnsupportedEncodingException e) {
    logger.error("encode error!", e);
  }
    tp.getExt().put("pk_user" , user.getId());
    tp.getExt().put("pk_corp" , user.getPkCorp());
    tp.getExt().put("pk_dept" , user.getPkDept());

    Cookie[] cookies = webTokenProcessor.getCookieFromTokenParameter(tp);
    ……
}
```
- 方案二：

对于敏感数据不希望通过cookie暴露至浏览器端的，可以在logincontroller，通过sessionManager保存信息；

```
@RequestMapping(method = RequestMethod.POST,value="formLogin")
	@ResponseBody
	public Map<String,String> formLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws AppBusinessException {
……
String token = CookieUtil.findCookieValue(cookies, "token");
sessionManager.putSessionCacheAttribute(token,
user.getLoginName(), user.getName());
sessionManager.putSessionCacheAttribute(token, "pk_user",
user.getId());
//设置当前组织或者公司
sessionManager.putSessionCacheAttribute(token, "pk_corp",
user.getPkCorp());
//设置当前部门
sessionManager.putSessionCacheAttribute(token, "pk_dept",
user.getPkDept());
}
```

基于shiro的认证机制，平台提供了statelessRealm；用来实现对每次请求的访问认证；在此，我们可以扩展这个认证过程，对于认证成功的情况下，将需要的信息从sessionManager中恢复至上下文中；代码如下：

```
public class ExtStatelessRealm extends StatelessRealm {

	 @Autowired
	 private SessionManager sessionManager;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		SimpleAuthenticationInfo info =  (SimpleAuthenticationInfo)super.doGetAuthenticationInfo(arg0);
		String token = (String)info.getCredentials();
		StatelessToken token1 = (StatelessToken)arg0;
		String uname = (String)token1.getPrincipal();
		String userCNName = (String)sessionManager.getSessionCacheAttribute(token, uname);
		String pk_user = (String)sessionManager.getSessionCacheAttribute(token, "pk_user");
		String pk_corp = (String)sessionManager.getSessionCacheAttribute(token, "pk_corp");
		String pk_dept = (String)sessionManager.getSessionCacheAttribute(token, "pk_dept");

		AppInvocationInfoProxy.setUserCNName(userCNName);
		AppInvocationInfoProxy.setPk_User(pk_user);
		AppInvocationInfoProxy.setPk_Corp(pk_corp);
		AppInvocationInfoProxy.setPk_Dept(pk_dept);
		return info;
	}
```

并且需要在applicationContext-shiro.xml文件中进行配置：
```
<bean id="statelessRealm" class="com.yonyou.iuap.auth.shiro.StatelessRealm">
		<property name="cachingEnabled" value="false" />
	</bean>
```

### 数据字典枚举的处理 ###

数据字典枚举使用，在前台控件上的表现为下拉控件；想要实现数据字典与界面UI控件的解耦，需要动态的获取数据字典的数据；由于该类数据不会经常更新，所以可以使用redis的缓存机制；对于缓存的数据如何更新，我们只有通过数据字典的维护界面进行更新，并主动的刷新缓存；这里主要讨论下拉控件如何动态获取数据；
如下代码，后台提供可以获取枚举数据的服务；并在applicationContext-cache.xml中配置了缓存管理器，结合使用缓存的API

`//通过cacheManager进行数据的缓存操作
List<ArrayList<AppEnumVO>> list = saasCacheManager.hmget(tenantId, key, vtype);`

```
@Controller
@RequestMapping(value = "/bd/enums")
public class AppEnumController {

	@Autowired
	private IAppEnumService enumService;

	@RequestMapping(value = "/queryByVtype/{vtype}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String,String>> queryByVtype(@PathVariable("vtype") String vtype) throws DAOException{
		List<Map<String,String>> enums= new ArrayList();
		List<AppEnumVO> enumlist = enumService.queryByVtype(vtype);
		for (AppEnumVO appEnumVO : enumlist) {
			Map<String,String> enummap = new HashMap();
			enummap.put("name", appEnumVO.getVname());
			enummap.put("value", appEnumVO.getVcode());
			enums.add(enummap);
		}
		return enums;
	}
}
```

前台项目中index.js中提供了公共的方法，向后台发请求，并将枚举数据缓存至window.syc_fixcode_map中；

```
function enumerate(type){
	var retData = [];
	$.ajax({
		type: 'get',
		url: window.cturl+"/bd/enums/queryByVtype/"+type,
		contentType: "application/json ; charset=utf-8",
		async:false,
		success: function(data) {
			retData = data;
			if(null != data){
				for(var i=0;i<data.length;i++){
					window.syc_fixcode_map[data[i].value]=data[i].name;
				}
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			errors.error(XMLHttpRequest);
		}
	});
	return retData;
}
```

以国补申报界面为例countrysubsidy.js；其中国补申报状态是一个枚举类型

```
1.定义DataTable
searchData: new u.DataTable({
			meta:{
				…………
				search_vstatus: {type: 'string'}//国补申报状态
			}
		})

2.定义枚举数据源vsubsidyStatus
enums = {
		//国补申报状态
		vsubsidyStatus: enumerate(3001)
};

3.进行初始化
viewModel = u.extend(basicDatas,events,enums,comValueNameMap)

```

countrysubsidy.html界面控件设置如下：
```
<div class="u-combo u-col-2 u-col-sm-8" u-meta='{"id":"search_vstatus","name":"状态","type":"u-combobox","data":"searchData","datasource":"vsubsidyStatus","field":"search_vstatus"}'>
								<input class="u-input"/>
								<span class="u-combo-icon"></span>
							</div>
```

### 统一的异常处理 ###

- 1.spring-mvc.xml中进行配置

  `<bean id="iwebExceptionHandler" class="com.yonyou.iuap.crm.common.exception.handler.AppWebExceptionHandler"/>`

- 2.编写AppWebExceptionHandler继承HandlerExceptionResolver，并通过AppExceptionView返回异常信息，AppExceptionView详细请查阅代码

```
public class AppWebExceptionHandler implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) {
		ModelAndView mv = new ModelAndView();
		View view = new AppExceptionView(ex);
		mv.setView(view);
		return mv;
	}
}
```

### 权限控制 ###

#### 功能权限控制 ####
功能权限控制是通过动态渲染菜单实现的，通过后台ExtFunctionController.getRootMenu()提供的服务，获取当前用户具有权限的功能；

```
@RequestMapping(value = "rootmenu", method = RequestMethod.GET)
	public @ResponseBody ExtFunction getRootMenu(Model model, HttpServletRequest request) throws AppBusinessException {
		String username = null;
		if (SecurityUtils.getSubject().getPrincipal() != null)
			username = (String) SecurityUtils.getSubject().getPrincipal();
		if (username == null || SecurityUtils.getSubject().hasRole("admin")) {
			try {
				return service.getFuncRoot();
			} catch (AppBusinessException e) {
				throw new AppBusinessException("获取菜单树出错",e);
			}
		}
		SecurityUser cuser = null;
		try {
			cuser = accountService.findUserByLoginName(username);
		} catch (Exception e) {
			throw new AppBusinessException("查询登陆用户错误",e);
		}
		if (cuser!=null) {
			try {
				return service.getFuncRootByUser(cuser.getId());
			} catch (AppBusinessException e) {
				throw new AppBusinessException("获取菜单树出错",e);
			}
		}else{
			return null;
		}
	}
```

前端界面初始化进行动态渲染菜单；index.js中相关代码如下：

```
$(function() {
	$.ajax({
				type : 'GET',
				url : window.cturl + "/security/extfunction/rootmenu",
				data : '',
				dataType : 'json',
				success : function(data) {
					initFuncTree(data);
					window.router.init();
          …………
        }
  });
});
```

#### 按钮权限控制 ####
按钮权限控制应该从两个方面实现控制：
- 一，对于具体单据节点实现界面上，不同角色、不同用户授权了不同的按钮，应该加载不同的按钮操作；这里控制界面的展现；
- 二，按钮权限的第二层控制则是，对于不同角色、不同用户进行了权限控制的动作操作，不能够通过url非法的请求后台服务；
这里对于按钮权限的控制提供一种思路，建议使用将相关数据进行cache缓存；

- 1.对于每个节点需要将该节点的按钮都应该在按钮表中进行注册做预置数据；对于节点注册和按钮注册提供了维护界面，这里我们可以从界面上进行维护；并在角色管理节点进行授权；

id|activity_name|activity_code|func_code|func_id|isactive|createdate
-|-|-
19c65ec3e1bb4cceb8543ac9062190a1|完结|/subsidy/country/finish|F070101|60000000000000000003|Y|
2a862751dd8546179241b8b5f0f155a0|修改|/subsidy/country/update|F070101|60000000000000000003|Y|
6989e50703254e1c8470b1b2d23f6cfa|删除|/subsidy/country/delete|F070101|60000000000000000003|Y|
b405a214cd844d578bb68d01a1d54c0b|撤销完结|/subsidy/country/unfinish|F070101|60000000000000000003|Y|
d7c2a98c10cc41f484c5443bccd3dc76|新增|/subsidy/country/save|F070101|60000000000000000003|Y|

- 2.后台服务提供，通过访问http://ip:port/iuaptraincrm/security/authBtn/auth?funcCode=F070101根据节点编码获取所有有权限的按钮名称；
其中对于没有启用按钮权限的则会返回所有的按钮；如下类中有方法，可查阅：

```
com.yonyou.iuap.crm.ieop.security.web.controller.MyBtnAuthController.handleBtnGroups(String)
```

- 3.按钮界面控制实现：

界面UI层，添加按钮实现时，需要对visible属性的ko绑定的动态控制；countrysubsidy.html中相关代码如下：

```
<button class="u-button raised primary" id="table-search" data-bind="click: searchClick">查询</button>
<button class="u-button raised primary" id="table-add" data-bind="click: addClick,***visible: addFunShow***">新增</button>
<button class="u-button raised primary" id="table-edit" data-bind="click: editClick,***visible: editFunShow***">修改</button>
<button class="u-button raised primary" id="table-del" data-bind="click: delClick,***visible: delFunShow***">删除</button>
<button class="u-button raised primary" id="table-finish" data-bind="click: finishClick,***visible: finishFunShow***">完结</button>
<button class="u-button raised primary" id="table-unfinish" data-bind="click: unFinishClick,***visible: unFinishFunShow***">撤销完结</button>
```
数据集中对于visible中ko的绑定，并且也页面初始化数据时，向服务器端发起请求获取当前功能节点对应的有权限的按钮，并设置按钮的可见性，countrysubsidy.js中相关代码如下：

```
basicDatas = {
    //新增
		addFunShow : ko.observable(false),
		//修改
		editFunShow : ko.observable(false),
		//删除
		delFunShow : ko.observable(false),
		//完结
		finishFunShow : ko.observable(false),
		//撤销完结
		unFinishFunShow : ko.observable(false)
}

events = {
		//点击查询按钮
		searchClick: function(){
      var queryFunData = {};
	        $.ajax({
      				type: 'get',
      				url: ctxRoot+"/security/authBtn/auth?funcCode=F070101",
      				success: function(data) {
      					var funList = data;
      					if(null != funList){
      						for(var i=0;i<funList.length;i++){
      							var funTemp = funList[i];
      							if("新增"==funTemp){
      								viewModel.addFunShow(true);
      							}else if("修改"==funTemp){
      								viewModel.editFunShow(true);
      							}else if("删除"==funTemp){
      								viewModel.delFunShow(true);
      							}else if("完结"==funTemp){
      								viewModel.finishFunShow(true);
      							}else if("撤销完结"==funTemp){
      								viewModel.unFinishFunShow(true);
      							}
      						}
      					}
      				},
      				error: function(XMLHttpRequest, textStatus, errorThrown) {
      					errors.error(XMLHttpRequest);
      				}
			  })
      },
      ……………
}
viewModel = u.extend(basicDatas,events,enums,comValueNameMap)

var getInitData = function() {
		viewModel.searchData.createEmptyRow();
		viewModel.searchClick();
    …………
}
```

- 4.权限拦截器用于实现上述的第二个方面的请求控制，该拦截器需要配置在shiroFilter的后面即iuap-auth认证组件的之后，只有请求经过登陆认证组件验证过的请求，才需要进行权限拦截；

*** 在web.xml中进行拦截器的配置 ***

```
<filter>
    <filter-name>shiroFilter</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    <init-param>
      <param-name>targetFilterLifecycle</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>shiroFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <filter>
    <filter-name>authorizationCheckFilter</filter-name>
    <filter-class>com.yonyou.iuap.crm.ieop.security.filter.AuthorizationCheckFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>authorizationCheckFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

在拦截器中需要验证当前用户是否具有当前请求即操作的功能节点的权限，并且对于该节点的按钮操作是否有权限的验证；详细实现，可查阅AuthorizationCheckFilter代码实现：

```
com.yonyou.iuap.crm.ieop.security.filter.AuthorizationCheckFilter
```
