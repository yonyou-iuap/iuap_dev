package com.yonyou.login.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.yonyou.login.entity.User;
import com.yonyou.login.repository.UserDao;


/**
 *
 *管理用户信息
 * @author taomk 2016年3月18日 下午4:15:04
 *
 */
@Service
@Transactional
public class AccountService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    
    @Autowired
    private UserDao userDao;

    private Clock clock = Clock.DEFAULT;

    public List<User> getAllUser() {
        return (List<User>) userDao.findAll();
    }

    public User getUser(Long id) {
        return userDao.findOne(id);
    }

    public User findUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    public User findUserByName(String username) {
        return userDao.findByName(username);
    }

    public void registerUser(User user) {
        entryptPassword(user); 
//        user.setRoles("user");
        user.setRegisterDate(clock.getCurrentDate());
        if (user.getId() == null  || 0 == user.getId()  ) {
        	user.setId(userDao.getNextId());
        }
        userDao.save(user);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));
        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<User> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<User> user = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
        return user;
    }

    public Page<User> getAccountPage(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<User> spec = buildSpecification(searchParams);
        return userDao.findAll(spec, pageRequest);
    }

    @Transactional
    public User saveEntity(User entity) throws Exception {
        entryptPassword(entity);
        if (0 == entity.getId()) {
            entity.setId(userDao.getNextId());
            entity.setRegisterDate(clock.getCurrentDate());
        }
        return userDao.save(entity);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
