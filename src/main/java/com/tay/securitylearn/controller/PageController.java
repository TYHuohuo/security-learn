package com.tay.securitylearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 页面请求
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses";
    }
}
