package test.verify;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.Employee;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;

/**
 * Created by zengxs on 2016/4/27.
 */
@ContextConfiguration("/applicationContext.xml")
public class TestDelete extends AbstractJUnit4SpringContextTests {

    @Autowired
    private MetadataDAO metadataDAO;

    @Test
    public void testDeleteVerify() throws DAOException {
        Employee employee = new Employee();
        employee.setE_id("e9036b8c-1569-464d-b09b-015ed999c922");
        metadataDAO.remove(employee);
    }
}
