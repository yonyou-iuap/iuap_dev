module.exports = [

  //单位数据
  {
  	type:"post",
  	url:'/sysOrg/list',
  	json:'company.json'
  },
  {
    type:"get",
    url:"/productmgr/product/queryAll",
    json:"product.json"
  }
]