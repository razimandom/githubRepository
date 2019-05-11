package com.raziman.petronas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ApiDocumentationController {

    @RequestMapping("")
    public String viewDocumentation() throws Exception{
        return "/api";
    }
	
}
