package com.yonyou.iuap.service.quotation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.repository.quotation.IpuQuotationMapper;

@Service
public class QuotationService {

    @Autowired
    private IpuQuotationMapper mapper;

    @Autowired
    private JdbcTemplate jt;

    @Transactional
    public int complexQuotationOperate(IpuQuotation quotation) {
        int result = 0;

        IpuQuotation q = new IpuQuotation();
        q.setIpuquotaionid("1b2d669f-a80a-4334-af17-b8333aa08d5f");
        q.setDescription("test_transaction888");

        mapper.updateByPrimaryKey(q);

        updateByJdbcTemplate();

        result = mapper.insert(quotation);

        // Integer.parseInt("test");

        return result;

    }

    private void updateByJdbcTemplate() {
        String sql = "update mgr_function set func_code='test_transaction888' where id = 100 ";
        jt.execute(sql);
    }

    public Page<IpuQuotation> retrievePage(PageRequest pageRequest, String subject) {
        return mapper.retrievePage(pageRequest, subject).getPage();
    }
}
