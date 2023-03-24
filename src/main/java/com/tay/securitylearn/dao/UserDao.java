package com.tay.securitylearn.dao;

import com.tay.securitylearn.auth.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
public interface UserDao {
    /**
     * 根据用户名查找用户
     * @return
     */
    Optional<User> selectUserByUsername(String username);
}
