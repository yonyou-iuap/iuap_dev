package com.yonyou.iuap.crm.common.base.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.ResultSetProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Repository
public class AppBaseDao extends AbstractAppDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AppBaseDao.class);
	
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Clock clock = Clock.DEFAULT;
	
	public void setClock(Clock clock) {
        this.clock = clock;
    }
	
	
	@SuppressWarnings("unchecked")
	public AppBaseDao() {}
	
	@Override
	public <T extends BaseEntity> void checkTs(Class<T> entityClass, String id, String ts) throws DAOException{
		String pkField = FastBeanHelper.getPKFieldName(entityClass);
		T vo = (T) baseDao.queryByPK(entityClass, id);
		Object oldts = vo.getAttribute("ts");
		String newts = ts;
		String oldstr = "";
		if(oldts instanceof String){
			oldstr = oldts.toString();
		}else if(oldts instanceof Date){
			oldstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(oldts);
		}
		if(!oldstr.equalsIgnoreCase(newts)){
			throw new DAOException("该条数据已经被其他人修改，当前操作不允许，请重新操作！");
		}
	}
	
	@Override
	public <T extends BaseEntity> void checkTs(T entity) throws DAOException{
		String pkField = FastBeanHelper.getPKFieldName(entity.getClass());
		String pkvalue = entity.getAttribute(pkField);
		Object newts = entity.getAttribute("ts");
		String newstr = "";
		if(newts instanceof String){
			newstr = newts.toString();
		}else if(newts instanceof Date){
			newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newts);
		}
		T vo = (T) baseDao.queryByPK(entity.getClass(), pkvalue);
		Object oldts = vo.getAttribute("ts");
		String oldstr = "";
		if(oldts instanceof String){
			oldstr = oldts.toString();
		}else if(oldts instanceof Date){
			oldstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(oldts);
		}
		if(!oldstr.equalsIgnoreCase(newstr)){
			throw new DAOException("该条数据已经被其他人修改，当前操作不允许，请重新操作！");
		}
	}
	
	@Override
	public <T extends BaseEntity> String save(T entity) throws DAOException {
//		entity.setAttribute("creationtime", value);
		return baseDao.insert(entity);
	}
	
	@Override
	public <T extends BaseEntity> String saveWithPK(T entity) throws DAOException {
		return baseDao.insertWithPK(entity);
	}
	
	@Override
	public <T extends BaseEntity> T findByIdWithDR(Class<T> entityClass,String id) throws DAOException {
		String PkColumn = FastBeanHelper.getPkColumn(entityClass);
		String tableName = FastBeanHelper.getTableName(entityClass);
		String sql = "select * from "+ tableName +" where "+ PkColumn +"=? and dr=0";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(id);
		T vo = baseDao.queryForObject(sql, sqlParameter, new BeanProcessor(entityClass));
		return vo;
	}
	
	@Override
	public <T extends BaseEntity> T findById(Class<T> entityClass,String id) throws DAOException {
		return baseDao.queryByPK(entityClass, id);
	}

	@Override
	public <T extends BaseEntity> List<T> findAllWithDR(Class<T> entityClass) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		String sql = "select * from "+ tableName +" where dr=0 order by ts desc";
		return baseDao.queryByClause(entityClass, sql);
	}
	
	@Override
	public <T extends BaseEntity> List<T> findAll(Class<T> entityClass) throws DAOException {
		return baseDao.queryAll(entityClass);
	}

	@Override
	public <T extends BaseEntity> void update(T entity) throws DAOException {
		this.checkTs(entity);
		baseDao.update(entity);			
	}
	
	/**
	 * 按照指定字段进行相关修改
	 */
	@Override
	public <T extends BaseEntity> void update(T vo, String... fieldNames) throws DAOException {
		this.checkTs(vo);
		baseDao.update(vo,fieldNames);			
	}

	@Override
	public <T extends BaseEntity> void delete(T entity) throws DAOException {
		this.checkTs(entity);
		baseDao.remove(entity);
	}

	@Override
	public <T extends BaseEntity> void delete(Class<T> entityClass,String id, String ts) throws DAOException {
		this.checkTs(entityClass,id,ts);
		this.delete(entityClass,id);
	}
	
	@Deprecated
	@Override
	public <T extends BaseEntity> void delete(Class<T> entityClass,String id) throws DAOException {
		try {
			T entity = entityClass.newInstance();
			String pkField = FastBeanHelper.getPKFieldName(entityClass);
			entity.setAttribute(pkField, id);
			baseDao.remove(entity);
		} catch (Exception e) {
			throw new DAOException();
		}
	}
	
	//------------
	/**
	 * 需要考虑ts校验逻辑
	 * @param entityClass
	 * @param condition
	 * @throws DAOException
	 */
	@Deprecated
	@Override
	public <T extends BaseEntity> void deleteByClause(Class<T> entityClass,String condition) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		String sql = "delete from "+tableName+" where "+condition;
		baseDao.update(sql);
	}
	
	@Override
	public <T extends BaseEntity> void deleteWithDR(T entity) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entity.getClass());
		String pkColumn = FastBeanHelper.getPkColumn(entity.getClass());
		String pkField = FastBeanHelper.getPKFieldName(entity.getClass());
		String pkvalue = entity.getAttribute(pkField);
		this.checkTs(entity);
		String sql = "update "+tableName+" set dr=1 where "+pkColumn+"='" +pkvalue+ "'";
		baseDao.update(sql);
	}

	@Override
	public <T extends BaseEntity> void deleteWithDR(Class<T> entityClass,String id, String ts) throws DAOException {
		this.checkTs(entityClass, id, ts);
		this.deleteWithDR(entityClass, id);
	}
	
	@Deprecated
	@Override
	public <T extends BaseEntity> void deleteWithDR(Class<T> entityClass,String id) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		String pkColumn = FastBeanHelper.getPkColumn(entityClass);
		String sql = "update "+tableName+" set dr=1 where "+pkColumn+"='" +id+ "'";
		baseDao.update(sql);
	}
	
	//-------------------
	/**
	 * 需要考虑ts校验逻辑
	 * @param entityClass
	 * @param condition
	 * @throws DAOException
	 */
	@Deprecated
	@Override
	public <T extends BaseEntity> void deleteByClauseWithDR(Class<T> entityClass,String condition) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		String sql = "update "+tableName+" set dr=1 where "+condition;
		baseDao.update(sql);
	}
	
	/**
	 * 
	 * @param childClass
	 * @param parentClass
	 * @param pid
	 * @param ts 校验主表的ts
	 * @throws DAOException
	 */
	@Override
	public <P extends BaseEntity,C extends BaseEntity> void deleteByFKWithDR(Class<C> childClass,Class<P> parentClass,String pid, String ts) throws DAOException {
		this.checkTs(parentClass, pid, ts);
		this.deleteByFKWithDR(childClass, parentClass, pid);
	}
	
	@Deprecated
	@Override
	public <P extends BaseEntity,C extends BaseEntity> void deleteByFKWithDR(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		String tableName = FastBeanHelper.getTableName(childClass);
//		String pkColumn = FastBeanHelper.getPkColumn(childClass);
		String childFKField = FastBeanHelper.getFKFieldName(childClass, parentClass);
		String sql = "update "+tableName+" set dr=1 where "+childFKField+"='" +pid+ "'";
		baseDao.update(sql);
	}
	
	/**
	 * 
	 * @param childClass
	 * @param parentClass
	 * @param pid
	 * @param ts 校验主表的ts
	 * @throws DAOException
	 */
	@Override
	public <P extends BaseEntity,C extends BaseEntity> void deleteByFK(Class<C> childClass,Class<P> parentClass,String pid, String ts) throws DAOException {
		this.checkTs(parentClass, pid, ts);
		this.deleteByFKWithDR(childClass, parentClass, pid);
	}
	
	@Deprecated
	@Override
	public <P extends BaseEntity,C extends BaseEntity> void deleteByFK(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		String tableName = FastBeanHelper.getTableName(childClass);
//		String pkColumn = FastBeanHelper.getPkColumn(childClass);
		String childFKField = FastBeanHelper.getFKFieldName(childClass, parentClass);
		String sql = "delete from "+tableName+" where "+childFKField+"='" +pid+ "'";
		baseDao.update(sql);
	}
	
	@Override
	public <P extends BaseEntity,C extends BaseEntity> List<C> queryByFK(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		String tableName = FastBeanHelper.getTableName(childClass);
//		String pkColumn = FastBeanHelper.getPkColumn(childClass);
		String childFKField = FastBeanHelper.getFKFieldName(childClass, parentClass);
		String sql = "select * from "+tableName+" where "+childFKField+"='" +pid+ "'";
		return baseDao.queryByClause(childClass, sql);
	}
	
	@Override
	public <P extends BaseEntity,C extends BaseEntity> List<C> queryByFKWithDR(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		String tableName = FastBeanHelper.getTableName(childClass);
//		String pkColumn = FastBeanHelper.getPkColumn(childClass);
		String childFKField = FastBeanHelper.getFKFieldName(childClass, parentClass);
		String sql = "select * from "+tableName+" where dr=0 and "+childFKField+"='" +pid+ "'";
		return baseDao.queryByClause(childClass, sql);
	}
	
	@Override
	public <P extends BaseEntity,C extends BaseEntity> List<C> findByFK(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		return this.queryByFK(childClass, parentClass, pid);
	}
	
	@Override
	public <P extends BaseEntity,C extends BaseEntity> List<C> findByFKWithDR(Class<C> childClass,Class<P> parentClass,String pid) throws DAOException {
		return this.queryByFKWithDR(childClass, parentClass, pid);
	}
	
	@Override
	public <T extends BaseEntity> List<T> findListByClause(Class<T> entityClass,String condition) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName+" where ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" order by ts desc");
		return baseDao.queryByClause(entityClass, sqlBuffer.toString());
	}
	
	@Override
	public <T extends BaseEntity> List<T> findListByClauseWithDR(Class<T> entityClass,String condition) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName+" where ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" and dr=0 order by ts desc");
		return baseDao.queryByClause(entityClass, sqlBuffer.toString());
	}
	
	@Override
	public <T> List<T> findForList(String sql, ResultSetProcessor processor) throws DAOException{
		return baseDao.queryForList(sql, processor);
	}
	
	
	@Override
	public <T extends BaseEntity> Page<T> findBypage(Class<T> entityClass,String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		if(sqlParameter!=null && sqlParameter.getCountParams()==0)
			sqlParameter = null;
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName);
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(" where " + condition);
		}
		sqlBuffer.append(" order by ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithDR(Class<T> entityClass,String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		if(sqlParameter!=null && sqlParameter.getCountParams()==0)
			sqlParameter = null;
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName+" where ");
		sqlBuffer.append(" dr=0 ");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypage(Class<T> entityClass, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName +" where 1=1 ");
		SQLParameter sqlParameter = new SQLParameter();
		buildSql(searchParams, sqlBuffer, sqlParameter);
		sqlBuffer.append(" order by ts desc");
		String sql = sqlBuffer.toString();
		return baseDao.queryPage(sql, sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithDR(Class<T> entityClass, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName+" where dr=0 ");
		SQLParameter sqlParameter = new SQLParameter();
		buildSql(searchParams, sqlBuffer, sqlParameter);
		String sql = sqlBuffer.toString();
		return baseDao.queryPage(sql, sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithSqlJoin(Class<T> entityClass,String sqlJoin, String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		if(sqlParameter!=null && sqlParameter.getCountParams()==0)
			sqlParameter = null;
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select distinct "+tableName+".* from "+ tableName +" "+sqlJoin);
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(" where " + condition);
		}
		sqlBuffer.append(" order by "+tableName+".ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithSqlJoinAndDR(Class<T> entityClass,String sqlJoin,String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		if(sqlParameter!=null && sqlParameter.getCountParams()==0)
			sqlParameter = null;
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select distinct "+tableName+".* from "+ tableName +" "+sqlJoin+" where ");
		sqlBuffer.append(" "+tableName+".dr=0 ");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by "+tableName+".ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithSqlJoin(Class<T> entityClass,String sqlJoin, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select distinct "+tableName+".* from "+ tableName +" "+sqlJoin +" where 1=1 ");
		SQLParameter sqlParameter = new SQLParameter();
		buildSql(searchParams, sqlBuffer, sqlParameter);
		sqlBuffer.append(" order by "+tableName+".ts desc");
		String sql = sqlBuffer.toString();
		return baseDao.queryPage(sql, sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithSqlJoinAndDR(Class<T> entityClass,String sqlJoin, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException {
		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select distinct "+tableName+".* from "+ tableName +" "+sqlJoin+" where "+tableName+".dr=0 ");
		SQLParameter sqlParameter = new SQLParameter();
		buildSql(searchParams, sqlBuffer, sqlParameter);
		String sql = sqlBuffer.toString();
		return baseDao.queryPage(sql, sqlParameter, pageRequest, entityClass);
	}
	
	@Override
	public <T extends BaseEntity> Page<T> findBypageWithJoin(Class<T> entityClass,String condition,
			SQLParameter sqlParameter, PageRequest pageRequest,String sql) throws DAOException {
		if(sqlParameter!=null && sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer(""+sql+"");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, entityClass);
	}
	
	//业务开发根据自己的需求，修改查询条件的拼接方式
	private void buildSql(Map<String, Object> searchParams, StringBuffer sqlBuffer, SQLParameter sqlParameter) {
		int index = 0;
		StringBuffer sb = new StringBuffer();
		if(null!=searchParams){
			for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
				String[] keySplit = entry.getKey().split("_");
				if (keySplit.length == 2) {
					String columnName = keySplit[1];
					String compartor = keySplit[0];
					Object value = entry.getValue();
					if (value != null && StringUtils.isNotBlank(value.toString())) {
						
						sb.append(columnName).append(" ").append(compartor).append(" ? ");
						// 处理模糊查询
						value = "like".equalsIgnoreCase(compartor) ? "%" + value + "%" : value;
						sqlParameter.addParam(value);
						index ++;
						
						if(index != searchParams.keySet().size()){
							sb.append(" or ");
						}
					}
				}
			}
		}
		
		String conditionSql = sb.toString();
		if(StringUtils.isNoneBlank(conditionSql)){
			sqlBuffer.append(" and (" + conditionSql.toString() + ");");
		}
		
	}

	@Override
	public <T extends BaseEntity> void batchSave(List<T> list) throws DAOException {
		baseDao.insert(list);		
	}
	
	@Override
	public <T extends BaseEntity> void batchSaveWithPK(List<T> list) throws DAOException {
		baseDao.insertWithPK(list);		
	}

	@Override
	public <T extends BaseEntity> void batchUpdate(List<T> list) throws DAOException {
		for(T vo : list){
			this.checkTs(vo);
		}
		baseDao.update(list);
	}
	
	/**
	 * 按照指定字段进行相关修改
	 */
	@Override
	public <T extends BaseEntity> void batchUpdate(List<T> list,String... fieldNames) throws DAOException {
		for(T vo : list){
			this.checkTs(vo);
		}
		baseDao.update(list,fieldNames);
	}
	
	@Override
	public <T extends BaseEntity> void batchDeleteWithDR(List<T> list) throws DAOException {
		for(T vo:list){
			this.checkTs(vo);
			vo.setAttribute("dr", 1);
		}
		baseDao.update(list);
	}

	@Override
	public <T extends BaseEntity> void batchDelete(List<T> list) throws DAOException {
		for(T vo:list){
			this.checkTs(vo);
		}
		baseDao.remove(list);
	}
	
	/**
	 * 主子操作的新增和修改方法；对于
	 */
	@Override
	@Transactional
	public <T extends BaseEntity> void mergeWithChild(T parent, List<? extends BaseEntity>... children) throws DAOException {
		String pkField = FastBeanHelper.getPKFieldName(parent.getClass());
		String pkvalue = parent.getAttribute(pkField);
		
		if(parent.getStatus() == VOStatus.NEW){
			pkvalue = AppTools.generatePK();
			parent.setAttribute(pkField, pkvalue);
			this.saveWithPK(parent);
		}else if(parent.getStatus() == VOStatus.UPDATED){
			this.checkTs(parent);
			this.update(parent);
		}else if(parent.getStatus() == VOStatus.DELETED){
			//主表及字表删除调用此方法;这种方式下，表体也要求传VO
			this.checkTs(parent);
			this.delete(parent);
		}
		
		for (int i=0; children != null && children.length > 0 && i<children.length && children[i] != null; i++) {
            List<BaseEntity> insertList = new ArrayList<BaseEntity>();
            List<BaseEntity> updateList = new ArrayList<BaseEntity>();
            List<BaseEntity> deleleList = new ArrayList<BaseEntity>();
            for (BaseEntity child : children[i]) {
                if ((parent.getStatus() != VOStatus.DELETED) && (child.getStatus() == VOStatus.NEW)) {
                	String childFKField = FastBeanHelper.getFKFieldName(child.getClass(), parent.getClass());
                	child.setAttribute(childFKField,pkvalue);
                	String pkchild = AppTools.generatePK();
                	String pkchildField = FastBeanHelper.getPKFieldName(child.getClass());
                	child.setAttribute(pkchildField,pkchild);
                    insertList.add(child);
                } else if ((parent.getStatus() != VOStatus.DELETED) && (child.getStatus() == VOStatus.UPDATED)) {
                    updateList.add(child);
                } else if ((parent.getStatus() == VOStatus.DELETED) || (child.getStatus() == VOStatus.DELETED)) {
                    deleleList.add(child);
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Child entity {} status is unchanged,will not modify data", child);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(insertList)) {
                baseDao.insertWithPK(insertList);		
            }
            if (CollectionUtils.isNotEmpty(updateList)) {
                baseDao.update(updateList);
            }
            if (CollectionUtils.isNotEmpty(deleleList)) {
        		baseDao.remove(deleleList);
            }
        }
	}
	
	@Override
	@Transactional
	public <T extends BaseEntity> void mergeWithChildByDR(T parent, List<? extends BaseEntity>... children) throws DAOException {
		String pkField = FastBeanHelper.getPKFieldName(parent.getClass());
		String pkvalue = parent.getAttribute(pkField);
		
		if(parent.getStatus() == VOStatus.NEW){
			pkvalue = AppTools.generatePK();
			parent.setAttribute(pkField, pkvalue);
			this.saveWithPK(parent);
		}else if(parent.getStatus() == VOStatus.UPDATED){
			this.checkTs(parent);
			this.update(parent);
		}else if(parent.getStatus() == VOStatus.DELETED){
			//主表及字表删除调用此方法;这种方式下，表体也要求传VO
			this.checkTs(parent);
			this.deleteWithDR(parent);
		}
		
		for (int i=0; children != null && children.length > 0 && i<children.length && children[i] != null; i++) {
            List<BaseEntity> insertList = new ArrayList<BaseEntity>();
            List<BaseEntity> updateList = new ArrayList<BaseEntity>();
            List<BaseEntity> deleleList = new ArrayList<BaseEntity>();
            for (BaseEntity child : children[i]) {
                if ((parent.getStatus() != VOStatus.DELETED) && (child.getStatus() == VOStatus.NEW)) {
                	String childFKField = FastBeanHelper.getFKFieldName(child.getClass(), parent.getClass());
                	child.setAttribute(childFKField,pkvalue);
                	String pkchild = AppTools.generatePK();
                	String pkchildField = FastBeanHelper.getPKFieldName(child.getClass());
                	child.setAttribute(pkchildField,pkchild);
                    insertList.add(child);
                } else if ((parent.getStatus() != VOStatus.DELETED) && (child.getStatus() == VOStatus.UPDATED)) {
                    updateList.add(child);
                } else if ((parent.getStatus() == VOStatus.DELETED) || (child.getStatus() == VOStatus.DELETED)) {
                	child.setAttribute("dr",1);
                    deleleList.add(child);
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Child entity {} status is unchanged,will not modify data", child);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(insertList)) {
                baseDao.insertWithPK(insertList);		
            }
            if (CollectionUtils.isNotEmpty(updateList)) {
                baseDao.update(updateList);
            }
            if (CollectionUtils.isNotEmpty(deleleList)) {
        		baseDao.update(deleleList);
            }
        }
	}
	
	@Override
	public long queryCount(String sql) throws DAOException{
		StringBuffer countBuffer = new StringBuffer("select count(*) ");
		int fromIndex = sql.indexOf("from");
		fromIndex = fromIndex > 0 ? fromIndex : sql.indexOf("FROM");
		int orderByIndex = sql.lastIndexOf("order by");
		orderByIndex = orderByIndex > 0 ? orderByIndex : sql.lastIndexOf("ORDER BY");
		String fromPart = orderByIndex > 0 ? sql.substring(fromIndex, orderByIndex) : sql.substring(fromIndex);
		countBuffer.append(fromPart);
		String countSQL = countBuffer.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("query count sql is {}", countBuffer);
		}
		Number number = (Number)this.jdbcTemplate.queryForObject(countSQL, Long.class);
		return (number != null ? number.longValue() : 0);
	}
	
	@Override
	public List<Map<String, Object>> queryList(String sql) throws DAOException{
		return this.jdbcTemplate.queryForList(sql);
	}
	@Override
	public <T> List<T> queryForList(String sql, SQLParameter sqlParameter, ResultSetProcessor processor)throws DAOException{
		return baseDao.queryForList(sql, sqlParameter, processor);
	}
	
}
