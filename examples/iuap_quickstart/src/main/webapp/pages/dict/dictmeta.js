var dictmeta = {
    params: {
        "cls": "com.yonyou.iuap.example.entity.mybatis.SysDictType" //使用 mybaties方式
        //"cls": "com.yonyou.iuap.example.entity.meta.SysDictTypeMeta" //使用 元数据
    },
    meta: {
        //主键
        dictid: {type: 'string'},
        //编码
        dicttypecode: {
            type: 'string',
            required: true,
            nullMsg: '字典类型编码不能为空!'
        },
        //名称
        dicttypename: {
            type: 'string',
            required: true,
            nullMsg: '字典类型名称不能为空!'
        },
        //是否固定(系统预置)
        isfixed: {
            type: 'string',
            'default': 'N'
        },
        //备注信息
        remark: {
            type: 'string'
        },
        //创建者
        creator: {
            type: 'string'
        },
        creatorid: {
            type: 'string'
        },
        //创建时间
        createtime: {
            type: 'string'
        }
    },
    pageSize: 5,

    //启用前端缓存
    pageCache: true
}

