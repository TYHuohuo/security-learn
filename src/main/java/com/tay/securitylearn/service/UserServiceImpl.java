package com.tay.securitylearn.service;

import com.tay.securitylearn.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
*
 * @author tay
 * @since 2022-05-15
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("fake") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userDao.selectUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
    }
}
