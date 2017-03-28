package persistence.mybatis;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.service.quotation.QuotationService;

public class QuotationServiceTest {
    public static ApplicationContext context;

    private QuotationService service;


    @Before
    public void setUp() throws Exception {
        context =
                new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml",
                        "classpath:applicationContext-persistence.xml"});
        service = context.getBean(QuotationService.class);
    }

    @Test
    public void testComplexService() throws Exception {
        boolean flag = false;
        IpuQuotation entity = new IpuQuotation();
        entity.setIpuquotaionid(UUID.randomUUID().toString());
        entity.setDescription("test");
        entity.setBuyoffertime(new Date(System.currentTimeMillis()));
        int s = service.complexQuotationOperate(entity);
        if (s != 0) {
            flag = true;
        }
        Assert.isTrue(flag);

    }

    @Test
    public void testPage() throws Exception {
        Page<IpuQuotation> page =
                service.retrievePage(new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.DESC, "ts"))), "");
        if (page != null) {
            System.out.println(page.getContent());
            System.out.println(page.getTotalElements());
        }

    }

}
