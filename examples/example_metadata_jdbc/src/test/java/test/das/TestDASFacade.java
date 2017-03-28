package test.das;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.Employee;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

/**
 * Created by zengxs on 2016/4/14.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestDASFacade extends AbstractJUnit4SpringContextTests {


    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAttr() throws DAOException, JsonProcessingException {

        String[] paths = new String[] {"fk_e_oid.o_name", "fk_e_oid.o_code", "e_name", "e_code", "e_code", "e_name"};
        Employee[] employees = new Employee[3];

        Employee employee = new Employee();
        employee.setE_id("05ffe037-3f02-4f3d-ae26-909d70954cf1");
        employee.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");
        employees[0] = employee;
        Employee employee1 = new Employee();
        employee1.setE_id("0bb2efab-cb03-4ff8-adb9-c4c7601f03fd");
        employee1.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");

        employees[1] = employee1;
        Employee employee2 = new Employee();
        employee2.setE_id("2224a6ba-d523-443a-9f46-bd1b8afef00e");
        employee2.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");
        employees[2] = employee2;
        List<Object[]> result = DASFacade.getAttributeValue(paths, employees);
        System.out.println(mapper.writeValueAsString(result));
    }

    @Test
    public void testGetAttrAsMap() throws DAOException, JsonProcessingException {

        String[] paths = new String[] {"fk_e_oid.o_name", "fk_e_oid.o_code", "e_name", "e_code", "e_code", "e_name"};
        Employee[] employees = new Employee[3];

        Employee employee = new Employee();
        employee.setE_id("05ffe037-3f02-4f3d-ae26-909d70954cf1");
        employee.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");
        employees[0] = employee;
        Employee employee1 = new Employee();
        employee1.setE_id("0bb2efab-cb03-4ff8-adb9-c4c7601f03fd");
        employee1.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");

        employees[1] = employee1;
        Employee employee2 = new Employee();
        employee2.setE_id("2224a6ba-d523-443a-9f46-bd1b8afef00e");
        employee2.setFk_e_oid("39a8afc9-a167-44f3-a2b9-6bf1da08548a");
        employees[2] = employee2;
        Map<String, Map<String, Object>> result = DASFacade.getAttributeValueAsPKMap(paths, employees);
        System.out.println(mapper.writeValueAsString(result));
    }
}
