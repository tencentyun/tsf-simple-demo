package com.tsf.demo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.tsf.core.TsfContext;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.tencent.tsf.unit.TsfUnitContext;
import com.tsf.demo.consumer.entity.CustomMetadata;
import com.tsf.demo.consumer.proxy.MeshUserService;
import com.tsf.demo.consumer.proxy.ProviderDemoService;
import com.tsf.demo.consumer.proxy.ProviderService;

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
            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
		}
		return restTemplate.getForObject("http://provider-demo/echo/" + str, String.class);
	}

	@RequestMapping(value = "/echo-async-rest/{str}", method = RequestMethod.GET)
	public String asyncRestProvider(@PathVariable String str,
									@RequestParam(required = false) String tagName,
									@RequestParam(required = false) String tagValue) throws Exception {
		if (!StringUtils.isEmpty(tagName)) {
			TsfContext.putTag(tagName, tagValue);
            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
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
            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
		}
		return providerDemoService.echo(str);
	}

    @RequestMapping(value = "/echo-feign-url/{str}", method = RequestMethod.GET)
    public String feignUrlProvider(@PathVariable String str,
                                   @RequestParam(required = false) String tagName,
                                   @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        return providerService.echo(str);
    }

	@RequestMapping(value = "/user-feign", method = RequestMethod.GET)
	public String feignMeshUser(@RequestParam(required = false) String tagName,
								@RequestParam(required = false) String tagValue) {
		if (!StringUtils.isEmpty(tagName)) {
			TsfContext.putTag(tagName, tagValue);
            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
		}
		return meshUserService.create();
	}
	
	@RequestMapping(value = "/echo-feign-unit/{str}", method = RequestMethod.GET)
    public String feignUnit(@PathVariable String str,
                            @RequestParam(required = false) String tagName,
                            @RequestParam(required = false) String tagValue) {
        // 在feign方法上添加@TsfUnitRule注解
        return providerDemoService.echoUnit(str);
    }
    
    @RequestMapping(value = "/echo-rest-unit/{str}", method = RequestMethod.GET)
    public String restUnit(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            // 添加单元化规则tag key和value
            TsfUnitContext.putTag(tagName, tagValue);
        }
        return restTemplate.getForObject("http://provider-demo/echo/unit/" + str, String.class);
    }
}