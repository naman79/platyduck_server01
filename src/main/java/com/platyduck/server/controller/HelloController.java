package com.platyduck.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

        @GetMapping("hello")
        public String index() {

            System.out.println("HelloController");

            return "hello.html";
        }
}
