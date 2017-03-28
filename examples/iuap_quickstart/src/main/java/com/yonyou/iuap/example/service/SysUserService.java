package com.yonyou.iuap.example.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.entity.meta.Org;
import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.example.repository.meta.SysUserDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

@Service
public class SysUserService {
    private Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    private SysUserDao userDao;

    @Transactional
    public SysUser save(SysUser entity) {
    	logger.debug("execute  SysUser save .");
        return	userDao.save(entity) ;
    }

    
    /**
     * 批量删除数据
     * @param list
     */
    @Transactional
    public void batchDeleteEntity(List<SysUser> list) {
        this.batchDelChild(list);
        userDao.batchDelete(list);
    }

    /**
     * 删除主表下面的子表数据
     * 
     * @param list
     * @throws DAOException
     */
    private void batchDelChild(List<SysUser> list) throws DAOException {
        userDao.batchDelChild(list);
    }

    /**
     * 根据传递的参数，进行分页查询
     */
    public Page<SysUser> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
    	Page<SysUser> pageResult = userDao.selectAllByPage(pageRequest, searchParams) ;
    	this.setRefName(pageResult) ;
        return pageResult;
    }
    
    /** 用户任职表里面 需要显示参照 名字 字段， 这里进行转换 */
    private Page<SysUser> setRefName(Page<SysUser> pageResult) {
        if (pageResult != null && pageResult.getContent() != null && pageResult.getContent().size() > 0) {
			/**
			 * 上面的 orgid.orgname orgid表示参照对应的外键属性名， orgname是参照实体对应的属性名，
			 * */
			Map<String, Map<String, Object>> refMap =
                    DASFacade.getAttributeValueAsPKMap(new String[] {"orgid.orgname"}, pageResult
                            .getContent().toArray(new SysUser[] {}));
            for (SysUser user : pageResult.getContent()) {
                String userId = user.getId();
                Map<String, Object> userRefMap = refMap.get(userId);
                if (userRefMap != null) {
                    user.setOrgname((String) userRefMap.get("orgid.orgname"));
                }
            }
        }
        return pageResult;
    }

    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public List<SysUser> findByUserCode(String code) {
    	 return userDao.findByUserCode(code ) ;
    }

    /**
     * 查询除某个id外，是否存在相同编码的 实体 ，用于更新实体时验证重复
     * @param code
     * @param id
     * @return
     */
    public List<SysUser> findByUserCode(String code, String id) {
    	 return userDao.findByUserCode(code, id) ;
    }

    
    /**
     * 查询用户参照的   所有组织，以树形展示
     * @return
     */
    public List<Org> findAllOrg() {
    	logger.debug("execute findAll when data is less");
        return userDao.findAllOrg() ;
    }

}
