package com.logistics.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gateway")
public class GateWayController {

    @GetMapping("/")
    public String gate(){
        return "/gateway";
    }
}
