package com.cjx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/video")
public class VideoController {
    @GetMapping("/")
    public String de() {
        return "hello world";
    }
}
