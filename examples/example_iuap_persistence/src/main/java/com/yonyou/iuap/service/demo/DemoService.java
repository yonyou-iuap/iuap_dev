package com.yonyou.iuap.service.demo;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.yonyou.iuap.entity.demo.DemoEntity;
import com.yonyou.iuap.repository.demo.DemoEntityJpaDao;

@Service
public class DemoService {

    @Autowired
    private DemoEntityJpaDao dao;
    
	@Autowired
	private JdbcTemplate jt;

    public DemoEntity getDemoById(String id) {
        return dao.findOne(id);
    }

    public DemoEntity getDemoBySql(String id) {
        return dao.getDemoByNativeSql(id);
    }
    
    @Transactional
    public void deleteById(String id) {
    	dao.delete(id);
    }

    @Transactional
    public void deleteDemoByIdUseSql(String id) {
        dao.deleteDemoByIdWithNativeSql(id);
    }

    public DemoEntity saveEntity(DemoEntity entity) throws SQLException {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(UUID.randomUUID().toString());
        }
        entity = dao.save(entity);
        return entity;
    }

    public Page<DemoEntity> getDemoPage(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<DemoEntity> spec = buildSpecification(searchParams);
        return dao.findAll(spec, pageRequest);
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<DemoEntity> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<DemoEntity> spec = DynamicSpecifications.bySearchFilter(filters.values(), DemoEntity.class);
        return spec;
    }
}
