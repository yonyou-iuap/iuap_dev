var today = new Date();
var todayStr = today.getFullYear() + '/' + (today.getMonth() + 1) + '/' + today.getDate();

var  userMetaDt = {
		meta: {
			//用户主键
			id: {
				type: 'string'
			},
			//用户登录帐号
			loginname: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			//用户绑定员工编码
			usercode: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			
			//用户姓名
			username: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			//职责等级
			levelid: {
				type: 'string'
			},
			//职责等级
			levelname: {
				type: 'string'
			},
			//所属单位主键
			orgid: {
				type: 'string'
			},
			//所属单位名称
			orgname: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true,
				enable: false
			},
			//所属平台类型
			platformid: {
				type: 'string'
			},
			//所属平台名称
			platformname: {
				type: 'string'
			},
			//性别
			sexid: {
				type: 'string'
			},
			//邮箱
			useremail: {
				type: 'string',
				required: true,
				regExp: /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/,
				notipFlag: true,
				hasSuccess: true,
				errorMsg: "邮箱格式不对。"
			},
			//手机
			usermobile: {
				type: 'string',
				regExp: /^1[34578]\d{9}$/,
				notipFlag: true,
				hasSuccess: true,
				errorMsg: "手机号码格式不对。"
			},
			//证件类型ID
			certificateid: {
				type: 'string'
			},
			//证件类型名称
			certificatename: {
				type: 'string'
			},
			//证件号码
			certificatecode: {
				type: 'string'
			},
			//用户出生日期
			birthdate: {
				type: 'date',
				'default': todayStr
			},
			//用户入职日期
			registerdate: {
				type: 'date',
				'default': todayStr
			},
			//备注信息
			remark: {
				type: 'string'
			},
			//状态
			statusid: {
				type: 'string'
			},
			//  创建人
			creator: {
				type: 'string'
			},
			//  创建人
			creatorid: {
				type: 'string'
			},
			//  创建时间
			createtime: {
				type: 'string'
			},
			statusname: {
				type: 'string'
			}
		}
};
//end user metaDt

//userjob meta
 var userJobMetaDt = {
		meta: {
			//主键
			id: {type: 'string'},
			//用户ID
			userid: {
				type: 'string'
			},
			//用户姓名
			username: {
				type: 'string'
			},
			//所属组织ID
			orgid: {
				type: 'string',
				required: true,
				nullMsg: '请选择所属组织!'
			},
			orgname:{},
			//所属部门主键
			deptid: {
				required: true,
				type: 'string'
			},
			deptname:{},
			//启用状态（1：启用，2：未启用。默认启用）
			isactive: {
				type: 'string',
				'default': 'Y'
			},
			//是否默认（N：不默认，Y：默认）
			isdefault: {
				type: 'string',
				'default': 'N'
			},
			//任职说明
			userjobnote: {
				type: 'string'
			},
			//  创建人
			creator: {
				type: 'string'
			},
			creatorid: {
				type: 'string'
			},
			//  创建时间
			createtime: {
				type: 'string'
			}
		}
};
//end userjob meta
