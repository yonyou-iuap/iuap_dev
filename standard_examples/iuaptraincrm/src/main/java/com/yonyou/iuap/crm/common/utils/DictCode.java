package com.yonyou.iuap.crm.common.utils;

/*
 * CommonConstants
 * @date 2016年11月21日
 */
public class DictCode {
	//是否
	public static final String BILL_YES_NO_TYPE = "1001";
	public static final String BILL_YES_STATUS = "10011001";
	public static final String BILL_NO_STATUS = "10011002";
	
	public static final String ALREADY_SAVED_STATUS_TYPE = "1002";
	/** 公用单据状态已保存 **/
	public static final String ALREADY_SAVED_STATUS = "10021001";
	/** 公用单据状态已确认 **/
	public static final String ALREADY_CONFIRMED_STATUS = "10021002";
	/** 公用单据状态已关闭 **/
	public static final String ALREADY_CLOSED_STATUS = "10021003";
	
	public static final String ALREADY_START_TYPE = "1005";
	/** 公用单据已启用*/
	public static final String ALREADY_START_STATUS = "10051001";
	/** 公用单据已停用*/
	public static final String ALREADY_STOP_STATUS = "10051002";
			
	public static final String SALETARGET_STATUS_TYPE = "2017";
	// 销售目标单状态---已保存
	public static final String SALETARGET_SAVE_STATUS = "20171001";
	// 销售目标单状态---已确认
	public static final String SALETARGET_CONFIRM_STATUS = "20171002";
	// 销售目标单状态---已调整
	public static final String SALETARGET_AJUST_STATUS = "20171003";
	
	public static final String POLICY_STATUS_TYPE = "2030";
	/** 政策信息已保存 **/
	public static final String POLICY_NORELEASED_STATUS = "20301001";
	/** 政策信息已发布 **/
	public static final String POLICY_RELEASED_STATUS = "20301002";
	/** 政策信息已撤销 **/
	public static final String POLICY_CANCELED_STATUS = "20301003";
	
	public static final String CITY_STATUS_TYPE = "2022";
	/** 城市信息已保存 **/
	public static final String CITY_SAVED_STATUS = "20221001";
	/** 城市信息已作废 **/
	public static final String CITY_CANCELED_STATUS = "20221002";
	
	public static final String APPLY_STATUS_TYPE = "2027";
	/** 发运申请已保存 **/
	public static final String APPLY_SAVED_STATUS = "20271001";
	/** 发运申请已提交**/
	public static final String APPLY_COMMITED_STATUS = "20271002";
	/** 发运申请已确认 **/
	public static final String APPLY_EXECUTED_STATUS = "20271003";
	/** 发运申请已接车**/
	public static final String APPLY_CONFIRM_STATUS = "20271004";
	/** 发运申请已交付 **/
	public static final String APPLY_DELIVERED_STATUS = "20271005";

	
	/*
	 * ========================= = 竞品信息状态 = ==========================
	 */
	public static final String COMPETBRAND_STATUS_TYPE = "2021";
	// 已保存
	public static final String COMPETBRAND_SAVED_STATUS = "20211001";
	// 已发布
	public static final String COMPETBRAND_PUBLISHED_STATUS = "20211002";
	// 已撤销
	public static final String COMPETBRAND_REVOKED_STATUS = "20211003";
	// 已关闭
	public static final String COMPETBRAND_CLOSED_STATUS = "20211004";
	/*
	 * ========================= = 立项申请状态 = ==========================
	 */	
	public static final String PROJECT_STATUS_TYPE = "2020";
	// 已保存
	public static final String PROJECT_SAVED_STATUS = "20201001";
	// 审核中
	public static final String PROJECT_AUDIT_STATUS = "20201002";
	// 已通过
	public static final String PROJECT_PASS_STATUS = "20201003";
	// 已驳回
	public static final String PROJECT_REJECT_STATUS = "20201004";
	// 已关闭
	public static final String PROJECT_CLOSE_STATUS = "20201005";
	// 已确认
	public static final String PROJECT_COMFIRM_STATUS = "20201006";
	/*
	 * ========================= = 立项评估信息 = ==========================
	 */	
	public static final String PROJECT_BID_TYPE = "2019";
	//有标书/合同
	public static final String PROJECT_WITH_BID = "20191001";
	// 无标书/合同
	public static final String PROJECT_WITHOUT_BID  = "20191003";
	// 公司批准
	public static final String PROJECT_COMPANY_CONFIRMED= "20191004";
	/*** ========================= =项目配置状态= ==========================*/	
	public static final String PROJCONFIG_STATUS_TYPE = "2026";
	// 已保存
	public static final String PROJCONFIG_SAVED_STATUS = "20261001";
	// 已确认
	public static final String PROJCONFIG_CONFIRM_STATUS = "20261002";
	// 已变更
	public static final String PROJCONFIG_CHANGE_STATUS = "20261003";
	/*
	 * ========================= = 线索单据状态 = ==========================
	 */
	public static final String TRACK_STATUS_TYPE = "2028";
	// 未客户
	public static final String TRACK_SAVED_STATUS = "20281001";
	// 已转客户
	public static final String TRACK_CHANGE_CSTOMER = "20281002";
	
	/*
	 * ========================= = 国补申报单据状态 = ==========================
	 */
	public static final String COUNTRY_SUBSIDY_STATUS_TYPE = "3001";
	// 已保存
	public static final String COUNTRY_SUBSIDY_STATUS_SAVE = "30011001";
	// 已结案
	public static final String COUNTRY_SUBSIDY_STATUS_FINISH = "30011002";
	/*
	 * ========================= = 国补申报申报状态 = ==========================
	 */
	public static final String COUNTRY_SUBSIDY_APP_STATUS = "2013";
	// 已申报
	public static final String COUNTRY_SUBSIDY_APP_STATUS_SAVE = "20131001";
	// 未申报
	public static final String COUNTRY_SUBSIDY_APP_STATUS_FINISH = "20131002";
	/*
	 * ========================= = 国补申报	未申报状态 = ==========================
	 */
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS = "2014";
	// 未销售给终端
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS_UNSALES = "20141001";
	// 已销售未上牌
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS_UNLICENSE = "20141002";
	// 已上牌无资料
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS_NOINFO = "20141003";
	// 已上牌有资料
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS_LINCESEINFO = "20141004";
	// 未交车
	public static final String COUNTRY_SUBSIDY_UNAPP_STATUS_UNFINISH = "20141005";
	/**
	 * ==========================招投标状态= =====================================
	 */
	public static final String BIDDING_STATUS_TYPE = "2023";
	//已保存
	public static final String BIDDING_SAVED_STATUS = "20231001";
	//已确认
	public static final String BIDDING_CONFIRM_STATUS = "20231002";
	//已回款
	public static final String BIDDING_RECEIVED_STATUS = "20231003";
	//中标结案
	public static final String BIDDING_TONED_STATUS = "20231004";
	//未中标结案
	public static final String BIDDING_UNTONED_STATUS = "20231005";
	//未竞标结案
	public static final String BIDDING_UNBIDDING_STATUS = "20231006";
	
	/*
	 * ========================= = 地补申报单据状态 = ==========================
	 */
	public static final String LOCAL_SUBSIDY_STATUS_TYPE = "3002";
	// 已保存
	public static final String LOCAL_SUBSIDY_STATUS_SAVE = "30021001";
	// 已结案
	public static final String LOCAL_SUBSIDY_STATUS_FINISH = "30021002";
	/*
	 * ========================= = 地补申报申报状态 = ==========================
	 */
	public static final String LOCAL_SUBSIDY_APP_STATUS_TYPE = "2013";
	// 已申报
	public static final String LOCAL_SUBSIDY_APP_STATUS_SAVE = "20131001";
	// 未申报
	public static final String LOCAL_SUBSIDY_APP_STATUS_FINISH = "20131002";
	
	
	/*
	 * ========================= = 地补年终清算单据状态 = ==========================
	 */
	public static final String LOCAL_YEAR_STATUS_TYPE = "3004";
	// 已保存
	public static final String LOCAL_YEAR_STATUS_SAVE = "30041001";
	// 已结案
	public static final String LOCAL_YEAR_STATUS_FINISH = "30041002";
	
	
	/*
	 * ========================= = 地补申报 未申报状态 = ==========================
	 */
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_TYPE = "2014";
	// 未销售给终端
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_UNSALES = "20141001";
	// 已销售未上牌
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_UNLICENSE = "20141002";
	// 已上牌无资料
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_NOINFO = "20141003";
	// 已上牌有资料
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_LINCESEINFO = "20141004";
	// 未交车
	public static final String LOCAL_SUBSIDY_UNAPP_STATUS_UNFINISH = "20141005";
	/**
	 * =========================== ====销售合同状态=================================
	 */
	public static final String CONTRACT_STATUS_TYPE = "2024";
	//已保存
	public static final String CONTRACT_SAVED_STATUS = "20241001";
	//已确认
	public static final String CONTRACT_CONFIRM_STATUS = "20241002";
	//已变更
	public static final String CONTRACT_CHANGE_STATUS = "20241003";
	//已完成
	public static final String CONTRACT_FINISHED_STATUS = "20241004";
	//已关闭
	public static final String CONTRACT_CLOSE_STATUS = "20241005";
	/*
	 * ========================= = PO订单信息状态 = ==========================
	 */
	public static final String POORDER_STATUS_TYPE = "2025";
	// 已保存
	public static final String POORDER_SAVED_STATUS = "20251001";
	// 已确认
	public static final String POORDER_CONFIRMED_STATUS = "20251002";
	// 已变更
	public static final String POORDER_ADJUSTED_STATUS = "20251003";
	// 已关闭
	public static final String POORDER_CLOSED_STATUS = "20251004";
	/*
	 * ========================= = PO订单信息需求内容 = ==========================
	 */
	public static final String POORDER_VDEMAND_TYPE = "2016";
	public static final String POORDER_VDEMAND_ZC = "20161001";
	public static final String POORDER_VDEMAND_CDSS = "20161002";
	public static final String POORDER_VDEMAND_KD = "20161003";
	/*
	 * ========================= === PO订单导出配置 ================================
	 */
	//模板地址
	public static final String PO_TEMPLATE_PATH = "/WEB-INF/template/PO_export_template.xls";
	//PO订单导出文件名
	public static final String PO_EXPORT_NAME = "PO订单导出";
	/*
	 * ========================= === 项目车型配置导出配置 ================================
	 */
	//交付配置模板地址
	public static final String AFFORD_TEMPLATE_PATH = "/WEB-INF/template/afford_export_template.xls";
	//特殊/定制配置模板地址
	public static final String ALT_TEMPLATE_PATH = "/WEB-INF/template/alt_export_template.xls";
	//交付配置导出文件名
	public static final String AFFORD_EXPORT_NAME = "交付配置导出";
	//特殊导出文件名
	public static final String SPECIAL_EXPORT_NAME = "车型特殊配置导出";
	//定制导出文件名
	public static final String CUSTOMIZE_EXPORT_NAME = "车型定制配置导出";
	/*
	 * ========================= = 生产基地枚举 = ==========================
	 */
	public static final String PRODUCTBASE_TYPE = "3006";
	public static final String PRODUCTBASE_SZ = "30061001";
	public static final String PRODUCTBASE_XA = "30061002";
	public static final String PRODUCTBASE_CS = "30061003";
	public static final String PRODUCTBASE_HZ = "30061004";
	public static final String PRODUCTBASE_NJ = "30061005";
	public static final String PRODUCTBASE_TJ = "30061006";
	public static final String PRODUCTBASE_WH = "30061007";
	public static final String PRODUCTBASE_QD = "30061008";
	public static final String PRODUCTBASE_DL = "30061009";
	/*
	 * ========================= = 国补年终清算状态 = ==========================
	 */
	public static final String COUNTRY_YEAREND_STATUS_TYPE = "3003";
	// 已保存
	public static final String COUNTRY_YEAREND_STATUS_SAVE = "30031001";
	// 已结案
	public static final String COUNTRY_YEAREND_STATUS_FINISH = "30031002";
	
	/**
	 * =========================== 车型状态   ===================================
	 */
	public static final String MODEL_TYPE = "1003";
	//设计
	public static final String MODEL_DESIGN_STATUS = "10031001";
	//试制
	public static final String MODEL_TRY_STATUS = "10031002";
	//调试
	public static final String MODEL_TEST_STATUS = "10031003";
	//量产
	public static final String MODEL_MP_STATUS = "10031004";
	//停产
	public static final String MODEL_SP_STATUS = "10031005";
	
	/**
	 *  =========================== 工信部车辆公告   ===================================
	 */
	public static final String NOUNCEMENT_TYPE = "1004";
	
	//申报中
	public static final String NOUNCEMENT_SAVED_STATUS = "10041001";
	//已完成
	public static final String NOUNCEMENT_FINISHED_STATUS = "10041002";
	/*
	 * =========================消息提醒单据类型==========================
	 */
	public static final String ALERT_TYPE = "4001";
	// 立项申请单
	public static final String ALERT_TYPE_A = "40011001";
	
	/*
	 * =========================数据权限规则==========================
	 */
	public static final String DATA_ROLE_TYPE = "1006"; //仅本人
	public static final String DATA_ROLE_01 = "10061001"; //仅本人
	public static final String DATA_ROLE_02 = "10061002"; //本岗位
	public static final String DATA_ROLE_03 = "10061003"; //本部门
	public static final String DATA_ROLE_04 = "10061004"; //本组织
	public static final String DATA_ROLE_05 = "10061005"; //本组织及以下组织
	public static final String DATA_ROLE_06 = "10061006"; //全部
	
	/*
	 * =========================用户申请状态==========================
	 */
	public static final String USER_POS_01 = "20291001"; //已保存
	public static final String USER_POS_02 = "20291002"; //已提交
	public static final String USER_POS_03 = "20291003"; //已审核
	public static final String USER_POS_04 = "20291004"; //已审批
	public static final String USER_POS_05 = "20291005"; //已停用
}
