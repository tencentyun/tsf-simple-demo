package com.tsf.demo.consumer.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user")
public interface MeshUserService {
    @RequestMapping(value = "/api/v6/user/create", method = RequestMethod.GET)
    String create();
}
