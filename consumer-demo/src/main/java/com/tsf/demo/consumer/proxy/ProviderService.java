package com.tsf.demo.consumer.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试通过URL配置FeignClient
 * 使用时修改provider-ip:provider-port配置
 */
@FeignClient(name = "provider", url = "http://127.0.0.1:18081", fallback = FeignClientFallback.class)
public interface ProviderService {

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    String echo(@PathVariable("str") String str);

}

@Component
class FeignClientFallback implements ProviderService {
    @Override
    public String echo(String str) {
        return "tsf-fault-tolerance-" + str;
    }
}