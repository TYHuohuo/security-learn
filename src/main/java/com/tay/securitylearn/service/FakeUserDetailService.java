package com.tay.securitylearn.service;

import com.google.common.collect.Lists;
import com.tay.securitylearn.auth.User;
import com.tay.securitylearn.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static com.tay.securitylearn.enums.UserRole.*;

/**
 * @author huohuo
 * @createDate 2023-03-25
 * @email gang95@email.cn
 */
@Repository("fake")
public class FakeUserDetailService implements UserDao {
    @Autowired
    private PasswordEncoder passwordEncoder;


    public FakeUserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        return getUsers().stream().filter(a -> username.equals(a.getUsername())).findFirst();
    }

    public List<User> getUsers() {
        List<User> users = Lists.newArrayList(
                new User("anan",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true,
                        STUDENT.getGrantedAuthority()
                        ),
                new User("linda",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true,
                        ADMIN.getGrantedAuthority()
                ),
                new User("tom",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true,
                        ADMIN_TRAINER.getGrantedAuthority()
                )
        );
        return users;
    }
}
