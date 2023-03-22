package com.tay.securitylearn.enums;

/**
 * <p>
 *  权限枚举
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
public enum UserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:writer");

    private final String permission;


    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
