package com.tay.securitylearn.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.tay.securitylearn.enums.UserPermission.*;

/**
 * <p>
 *   角色枚举
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
public enum UserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_READ, STUDENT_READ, STUDENT_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
