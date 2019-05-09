package com.dq.trm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DQController {

	@RequestMapping(value = "/")
    public String index() {
        return "/public/index.html";
    }
}
