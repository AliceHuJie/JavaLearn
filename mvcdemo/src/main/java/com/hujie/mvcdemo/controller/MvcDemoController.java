package com.hujie.mvcdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mvc-demo")
public class MvcDemoController {

    @GetMapping(value = "/test")
    public String test() {
        return "sucess";
    }
}
