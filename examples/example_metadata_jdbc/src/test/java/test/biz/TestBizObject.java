package test.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yonyou.Org;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.bs.jdbc.framework.ref.IBDObject;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.BizObject;
import com.yonyou.metadata.spi.service.MetaDataException;

/**
 * Created by zengxs on 2016/3/17.
 */

@ContextConfiguration(locations = "/applicationContext.xml")
public class TestBizObject extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MetadataDAO metadataDAO;

    @Test
    public void testBiz() throws InstantiationException, IllegalAccessException, MetaDataException, DAOException {
        Org org = metadataDAO.queryByPK(Org.class, "8c8aae76-0296-44d4-b321-6f2ce0193f92");
        BizObject bizObject = new BizObject(org);
        IBDObject ibdObject = bizObject.getBizInterface(IBDObject.class);
        System.out.println(ibdObject.getName());
    }

}
