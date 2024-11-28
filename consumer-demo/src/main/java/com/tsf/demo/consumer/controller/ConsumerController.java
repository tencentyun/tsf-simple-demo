package com.tsf.demo.consumer.controller;

import com.tsf.demo.consumer.proxy.MeshUserService;
import com.tsf.demo.consumer.proxy.ProviderDemoService;
import com.tsf.demo.consumer.proxy.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.tsf.core.TsfContext;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private ProviderDemoService providerDemoService;
    @Autowired
    private MeshUserService meshUserService;

    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String restProvider(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//			TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        return restTemplate.getForObject("http://provider-demo/echo/" + str, String.class);
    }

    @RequestMapping(value = "/echo-async-rest/{str}", method = RequestMethod.GET)
    public String asyncRestProvider(@PathVariable String str,
                                    @RequestParam(required = false) String tagName,
                                    @RequestParam(required = false) String tagValue) throws Exception {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//			TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate
                .getForEntity("http://provider-demo/echo/" + str, String.class);
        return future.get().getBody();
    }

    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feignProvider(@PathVariable String str,
                                @RequestParam(required = false) String tagName,
                                @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//			TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        return providerDemoService.echo(str);
    }

    @RequestMapping(value = "/echo-feign-url/{str}", method = RequestMethod.GET)
    public String feignUrlProvider(@PathVariable String str,
                                   @RequestParam(required = false) String tagName,
                                   @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//			TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        return providerService.echo(str);
    }

    @RequestMapping(value = "/user-feign", method = RequestMethod.GET)
    public String feignMeshUser(@RequestParam(required = false) String tagName,
                                @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//			TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        return meshUserService.create();
    }
}