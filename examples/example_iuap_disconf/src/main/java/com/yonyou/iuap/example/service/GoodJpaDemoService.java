package com.yonyou.iuap.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.yonyou.iuap.example.entity.GoodJpaDemo;
import com.yonyou.iuap.example.repository.GoodJpaDemoDao;

@Service
public class GoodJpaDemoService {

    @Autowired
    private GoodJpaDemoDao dao;

    public GoodJpaDemo getEntityById(String id) {
        return dao.findOne(id);
    }

    public void deleteById(String id) {
        dao.delete(id);
    }
    
    public void batchDelete(List<String> ids) {
		List<GoodJpaDemo> deleteVos = new ArrayList<GoodJpaDemo>();
		for (int i = 0; i < ids.size(); i++) {
			GoodJpaDemo goodDemo = new GoodJpaDemo();
			goodDemo.setProductid(ids.get(i));
			deleteVos.add(goodDemo);
		}
		if (deleteVos.size() > 0) {
			dao.delete(deleteVos);
		}
    }

    public GoodJpaDemo saveEntity(GoodJpaDemo entity) {
        if (StringUtils.isBlank(entity.getProductid())) {
            entity.setProductid(UUID.randomUUID().toString());
        }
        return dao.save(entity);
    }

    public Page<GoodJpaDemo> getDemoPage(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<GoodJpaDemo> spec = buildSpecification(searchParams);
        return dao.findAll(spec, pageRequest);
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<GoodJpaDemo> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        //默认以and方式合并条件查询，如果自定义合并方式，扩展DynamicSpecifications.bySearchFilter
        Specification<GoodJpaDemo> spec = DynamicSpecifications.bySearchFilter(filters.values(), GoodJpaDemo.class);
        return spec;
    }
}
