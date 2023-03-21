package com.tay.securitylearn.controller;

import com.google.common.collect.Lists;
import com.tay.securitylearn.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
@RequestMapping("/api/v1/student")
@RestController
public class StudentController {
    private static final List<Student> STUDENTS = Lists.newArrayList(
            new Student(1, "Jack Jons"),
            new Student(2, "Steven Jobs"),
            new Student(3, "Marry Jan")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
        return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("用户" + studentId + "不存在"));
    }
}
