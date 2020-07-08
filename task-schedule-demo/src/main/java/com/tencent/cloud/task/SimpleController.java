package com.tencent.cloud.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {


    @RequestMapping(value = "/echo")
    public String echo(@RequestParam String key) {
        return key;
    }
}
