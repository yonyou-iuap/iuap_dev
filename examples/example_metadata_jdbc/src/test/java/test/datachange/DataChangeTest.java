package test.datachange;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.Employee;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by zengxs on 2016/5/24.
 */
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("/applicationContext.xml")
public class DataChangeTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MetadataDAO metadataDAO;

    @Test
    public void testAdd() throws DAOException, InterruptedException {
        Employee employee = metadataDAO.queryByPK(Employee.class, "a0f3704f-1466-45ac-a238-d8c08cadcefe");
        metadataDAO.insert(employee);
        Thread.sleep(5000);
    }

    @Test
    public void testDelete() throws DAOException, InterruptedException {
        Employee employee = metadataDAO.queryByPK(Employee.class, "a0f3704f-1466-45ac-a238-d8c08cadcefe");
        metadataDAO.remove(employee);
        Thread.sleep(5000);
    }

    @Test
    public void testUpdate() throws DAOException, InterruptedException {
        Employee employee = metadataDAO.queryByPK(Employee.class, "a0f3704f-1466-45ac-a238-d8c08cadcefe");
        employee.setE_code("testaaa");
        metadataDAO.update(employee);
        Thread.sleep(5000);
    }

}
