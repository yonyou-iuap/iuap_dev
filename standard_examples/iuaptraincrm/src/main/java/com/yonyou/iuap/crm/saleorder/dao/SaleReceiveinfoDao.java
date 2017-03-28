package com.yonyou.iuap.crm.saleorder.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import com.yonyou.iuap.crm.saleorder.entity.Receiveinfo;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

@Repository
public class SaleReceiveinfoDao {
	
	@Resource(name="baseDAO")
    private BaseDAO dao;

    public Receiveinfo findById(String id) throws DAOException {

        String sql = "select * from train_receiveinfo  where dr='0' and pk_receiveinfo=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<Receiveinfo> list = dao.queryByClause(Receiveinfo.class, sql, sqlparam);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public void delete(Receiveinfo entity) {
        if (null != entity) {
            dao.remove(entity);
        }
    }

    /**
     * 点击主表数据加载子表，服务端传入主表主键，根据主表主键查询子表数据并返回
     * @param pageRequest
     * @param searchParams
     * @return
     * @throws DAOException
     */
    public Page<Receiveinfo> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams)
            throws DAOException {
        String sql = " select * from train_receiveinfo";
        SQLParameter sqlparam = new SQLParameter();

        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            
            String mainid = (String) searchParams.get("fk_id_orderdetail");
            if(!StringUtil.isEmpty(mainid)){
            	sql = sql + " fk_id_receiveinfo = ? ";
                sqlparam.addParam(mainid);
            }
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Receiveinfo.class);
    }

}
