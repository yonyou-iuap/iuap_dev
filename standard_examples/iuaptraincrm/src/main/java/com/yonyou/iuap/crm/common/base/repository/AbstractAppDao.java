package com.yonyou.iuap.crm.common.base.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.ResultSetProcessor;
import com.yonyou.iuap.persistence.vo.BaseEntity;


public abstract class AbstractAppDao {

	public abstract <T extends BaseEntity> T findByIdWithDR(Class<T> entityClass,String id) throws DAOException;
	
	public abstract <T extends BaseEntity> T findById(Class<T> entityClass,String id) throws DAOException;
	
	public abstract <T extends BaseEntity> List<T> findAllWithDR(Class<T> entityClass) throws DAOException;
	
	public abstract <T extends BaseEntity> List<T> findAll(Class<T> entityClass) throws DAOException;
	
	public abstract <T extends BaseEntity> String save(T entity) throws DAOException;
	
	public abstract <T extends BaseEntity> String saveWithPK(T entity) throws DAOException;

	public abstract <T extends BaseEntity> void update(T entity) throws DAOException;
	
	public abstract <T extends BaseEntity> void update(T vo, String... fieldNames) throws DAOException;

	public abstract<T extends BaseEntity> void delete(T entity) throws DAOException;

	public abstract <T extends BaseEntity> void delete(Class<T> entityClass,String id) throws DAOException;
	
	public abstract <T extends BaseEntity> void delete(Class<T> entityClass, String id, String ts) throws DAOException;
	
	public abstract <T extends BaseEntity> void deleteByClause(Class<T> entityClass, String condition) throws DAOException;
	
	public abstract <T extends BaseEntity> void deleteWithDR(T entity) throws DAOException;

	public abstract <T extends BaseEntity> void deleteWithDR(Class<T> entityClass,String id) throws DAOException;
	
	public abstract <T extends BaseEntity> void deleteWithDR(Class<T> entityClass, String id, String ts) throws DAOException;
	
	public abstract <T extends BaseEntity> void deleteByClauseWithDR(Class<T> entityClass, String condition) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypage(Class<T> entityClass,String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithDR(Class<T> entityClass,String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypage(Class<T> entityClass, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithDR(Class<T> entityClass, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithSqlJoin(Class<T> entityClass, String sqlJoin,String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithSqlJoinAndDR(Class<T> entityClass, String sqlJoin,String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithSqlJoin(Class<T> entityClass, String sqlJoin, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithSqlJoinAndDR(Class<T> entityClass, String sqlJoin, Map<String, Object> searchParams, PageRequest pageRequest) throws DAOException;
	
	public abstract <T extends BaseEntity> Page<T> findBypageWithJoin(Class<T> entityClass,String condition,
			SQLParameter sqlParameter, PageRequest pageRequest,String sql) throws DAOException;

	public abstract <T extends BaseEntity> void batchSave(List<T> list) throws DAOException;

	public abstract <T extends BaseEntity> void batchSaveWithPK(List<T> list) throws DAOException;

	public abstract <T extends BaseEntity> void batchUpdate(List<T> list) throws DAOException;
	
	public abstract <T extends BaseEntity> void batchUpdate(List<T> list, String... fieldNames) throws DAOException;

	public abstract <T extends BaseEntity> void batchDelete(List<T> list) throws DAOException;

	public abstract <T extends BaseEntity> void batchDeleteWithDR(List<T> list) throws DAOException;

	public abstract <T extends BaseEntity> List<T> findListByClause(Class<T> entityClass, String condition) throws DAOException;

	public abstract <T extends BaseEntity> List<T> findListByClauseWithDR(Class<T> entityClass, String condition) throws DAOException;

	public abstract <T extends BaseEntity> void mergeWithChildByDR(T parent, List<? extends BaseEntity>... children) throws DAOException;

	public abstract <T extends BaseEntity> void mergeWithChild(T parent, List<? extends BaseEntity>... children) throws DAOException;

	public abstract <T> List<T> findForList(String sql, ResultSetProcessor processor) throws DAOException;

	public abstract <P extends BaseEntity,C extends BaseEntity> void deleteByFKWithDR(Class<C> childClass, Class<P> parentClass, String pid) throws DAOException ;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> void deleteByFK(Class<C> childClass, Class<P> parentClass, String pid) throws DAOException ;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> void deleteByFK(Class<C> childClass, Class<P> parentClass, String pid, String ts) throws DAOException;

	public abstract <P extends BaseEntity,C extends BaseEntity> List<C> queryByFKWithDR(Class<C> childClass, Class<P> parentClass,String pid) throws DAOException;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> void deleteByFKWithDR(Class<C> childClass, Class<P> parentClass, String pid, String ts) throws DAOException;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> List<C> queryByFK(Class<C> childClass, Class<P> parentClass,String pid) throws DAOException;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> List<C> findByFKWithDR(Class<C> childClass, Class<P> parentClass,String pid) throws DAOException;
	
	public abstract <P extends BaseEntity,C extends BaseEntity> List<C> findByFK(Class<C> childClass, Class<P> parentClass,String pid) throws DAOException;

	public abstract <T extends BaseEntity> void checkTs(T entity) throws DAOException;

	public abstract <T extends BaseEntity> void checkTs(Class<T> entityClass, String id, String ts) throws DAOException;
	
	public abstract long queryCount(String sql) throws DAOException;
	
	public abstract List<Map<String, Object>> queryList(String sql) throws DAOException;
	
	public abstract <T> List<T> queryForList(String sql, SQLParameter sqlParameter, ResultSetProcessor processor)throws DAOException;
}
