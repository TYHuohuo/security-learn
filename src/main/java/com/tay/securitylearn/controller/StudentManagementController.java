package com.tay.securitylearn.controller;

import com.google.common.collect.Lists;
import com.tay.securitylearn.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
@Slf4j
@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Lists.newArrayList(
            new Student(1, "Jack Jons"),
            new Student(2, "Steven Jobs"),
            new Student(3, "Marry Jan")
    );

    @GetMapping
    public List<Student> getStudents() {
        return STUDENTS;
    }

    @PostMapping
    public String addStudent(@RequestBody Student student) {
        log.info("student {}", student);

        return "success";
    }

    @DeleteMapping(path = "{studentId}")
    public String deleteStudent(@PathVariable("studentId") Integer studentId) {
        log.info("studentId {}", studentId);

        return "success";
    }

    @PutMapping(path = "{studentId}")
    public String updateStudent(@PathVariable("studentId")Integer studentId,
                                @RequestBody Student student) {
        log.info("studentId {}", studentId);
        log.info("student  {}", student);
        return "success";
    }
}
