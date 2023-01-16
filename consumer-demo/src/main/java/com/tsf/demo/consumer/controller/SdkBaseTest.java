package com.tsf.demo.consumer.controller;

import com.tencent.tsf.unit.TsfUnitContext;
import com.tsf.demo.consumer.proxy.MeshUserService;
import com.tsf.demo.consumer.proxy.ProviderDemoService;
import com.tsf.demo.consumer.proxy.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.tsf.faulttolerance.annotation.TsfFaultTolerance;
import org.springframework.cloud.tsf.faulttolerance.model.TsfFaultToleranceStragety;
import org.springframework.tsf.core.TsfContext;
import org.springframework.tsf.core.util.TsfSpringContextAware;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@RestController
public class SdkBaseTest {
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
    private static final Logger LOG = LoggerFactory.getLogger(SdkBaseTest.class);

    // 调用一次provider接口
    @RequestMapping(value = "/echo-once/{str}", method = RequestMethod.GET)
    public String restProvider(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        LOG.info("start call echo-once");
        String result = restTemplate.getForObject("http://provider-demo/echo/" + str, String.class);
        LOG.info("end call echo-once, the result is : " + result);
        return result;
    }

    // 调用多次provider接口，每秒5次
    @RequestMapping(value = "/echo-mul-times/{num}/{str}", method = RequestMethod.GET)
    public Integer feignProvider(@PathVariable Integer num, @PathVariable String str,
                                @RequestParam(required = false) String tagName,
                                @RequestParam(required = false) String tagValue) throws InterruptedException {
        LOG.info("start call echo-mul-times, total nums: " + num);
        String result = "";
        int normal_num = 0;
        for(int i = 0; i < num ; i++) {
            try {
                result = providerDemoService.echo(str);
                normal_num = normal_num + 1;
                LOG.info("for: call echo-once, the result is : " + result);
            }catch(Exception e){
                LOG.info("call failed");
            }

            Thread.sleep(200);
        }
        LOG.info("end call echo-mul-times");
        return normal_num;
    }

    // 调用一次provider的echo-error接口
    @RequestMapping(value = "/echo-error-once/{str}", method = RequestMethod.GET)
    public String errorProvider(@PathVariable String str,
                                @RequestParam(required = false) String tagName,
                                @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        LOG.info("start call echo-error-once");
        String result = restTemplate.getForObject("http://provider-demo/echo/error/" + str, String.class);
        LOG.info("end call echo-once, the result is : " + result);
        return result;
    }

    // 直接调用provider的error接口，报错
    @RequestMapping(value = "/error/{str}", method = RequestMethod.GET)
    public String error(@PathVariable String str) {
        LOG.info("start call error");
        String result = restTemplate.getForObject("http://provider-demo/error/" + str, String.class);
        LOG.info("end call error, the result is : " + result);
        return result;
    }

    // 调用provider的echo-error-mul接口，调用次数通过num指定
    @RequestMapping(value = "/echo-error-mul/{num}/{str}", method = RequestMethod.GET)
    public Integer errorProviderMul(@PathVariable Integer num, @PathVariable String str,
                                    @RequestParam(required = false) String tagName,
                                    @RequestParam(required = false) String tagValue) throws InterruptedException {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        LOG.info("start call echo-error-mul");
        LOG.info("nums: " + num);
        int error_num = 0;

        for (int i = 0; i < num ; i++) {
            try {
                String result = restTemplate.getForObject("http://provider-demo/echo/error/" + str, String.class);
                LOG.info("for: call echo-once, the result is : " + result);
            }catch(Exception e){
                error_num = error_num + 1;
            }
            Thread.sleep(330);
        }
        LOG.info("end call echo-error-mul");
        return error_num;
    }

    // 调用一次slow接口
    @RequestMapping(value = "/echo-slow-once/{str}", method = RequestMethod.GET)
    public String slowProvider(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        LOG.info("start call echo-slow-once");
        String result = restTemplate.getForObject("http://provider-demo/echo/slow/" + str, String.class);
        LOG.info("end call echo-once, the result is : " + result);
        return result;
    }

    // 调用多次slow（延时）接口
    @RequestMapping(value = "/echo-slow-mul/{num}/{str}", method = RequestMethod.GET)
    public Integer slowProviderMul(@PathVariable Integer num,@PathVariable String str,
                                   @RequestParam(required = false) String tagName,
                                   @RequestParam(required = false) String tagValue) throws InterruptedException {
        if (!StringUtils.isEmpty(tagName)) {
            TsfContext.putTag(tagName, tagValue);
//            TsfContext.putCustomMetadata(new CustomMetadata(tagName, tagValue));
        }
        LOG.info("start call echo-slow-once");
        int flow_num = 0;
        int error_num = 0;
        for(int i = 0; i < num; i++) {
            try{
                String result = restTemplate.getForObject("http://provider-demo/echo/slow/" + str, String.class);
                LOG.info("call echo slow, the result is : " + result);
                // 下发到时延provider接口上的数量
                if(result.indexOf(str) != -1 && result.indexOf("sleep") != -1)
                    flow_num = flow_num + 1;
            }catch(Exception e){
                error_num = error_num + 1;
            }
            Thread.sleep(100);
        }
        LOG.info("end call echo-once, the flow call num is : " + flow_num + ", error_num is: " + error_num);
        return error_num;
    }

    // 分布式配置，通过该函数来获取配置的值，以查看配置是否生效
    @RequestMapping(value = "/config/{path}/value", method = RequestMethod.GET)
    public String config(@PathVariable String path) {
        String result = TsfSpringContextAware.getProperties(path);;
        LOG.info("get distribution config value， the value is :" + result);
        return result;
    }

    // 直接失败
    @TsfFaultTolerance(strategy = TsfFaultToleranceStragety.FAIL_FAST,fallbackMethod = "restProviderFailfast")
    @RequestMapping(value = "/failfast/{str}", method = RequestMethod.GET)
    public String failFast(@PathVariable String str) {
        LOG.info("start call failfast:" + str);
        String result = restTemplate.getForObject("http://provider-demo/error/" + str, String.class);
        LOG.info("end call failfast-no-:" + str);
        return result;
    }

    public String restProviderFailfast(String str) {
        String result = "failfast-recall:" + str;
        LOG.info(result);
        return result;
    }

    // 回调函数restProviderFallback
    @TsfFaultTolerance(strategy = TsfFaultToleranceStragety.FAIL_FAST, fallbackMethod = "restProviderFallback")
    @RequestMapping(value = "/fallback/{str}", method = RequestMethod.GET)
    public String fallBack(@PathVariable String str) {
        LOG.info("start call fallback:" + str);
        String result = restTemplate.getForObject("http://provider-demo/error/" + str, String.class);
        LOG.info("end call fallback-no:" + result);
        return result;
    }

    public String restProviderFallback(String str) {
        String result = "fallback-recall:" + str;
        LOG.info(result);
        return result;
    }

    // 调用失败，容错，重试两次，总共调用三次
    @TsfFaultTolerance(strategy = TsfFaultToleranceStragety.FAIL_OVER, maxAttempts = 2, fallbackMethod = "restProviderFailover")
    @RequestMapping(value = "/failover/{str}", method = RequestMethod.GET)
    public String failOver(@PathVariable String str) {
        LOG.info("start call failover:" + str);
        String result = restTemplate.getForObject("http://provider-demo/error/" + str, String.class);
        LOG.info("end call failover-no:" + str);
        return result;
    }

    public String restProviderFailover(String str) {
        String result = "failover-recall:" + str;
        LOG.info(result);
        return result;
    }

    // 忽略RuntimeException异常，不触发容错，重试失效
    @TsfFaultTolerance(strategy = TsfFaultToleranceStragety.FAIL_OVER, maxAttempts = 2, ignoreExceptions = {RuntimeException.class}, fallbackMethod = "restProviderFailoverException")
    @RequestMapping(value = "/failover-exception/{str}", method = RequestMethod.GET)
    public String failOverException(@PathVariable String str) throws InterruptedException {
        String result = "";
        LOG.info("start call failover-exception:" + str);
        result = restTemplate.getForObject("http://provider-demo/error/" + str, String.class);
        LOG.info("end call failover with exception:" + str);
        return result;
    }

    public String restProviderFailoverException(String str) {
        String result = "failover-exception-recall:" + str;
        LOG.info(result);
        return result;
    }

    @RequestMapping(value = "/echo-rest-unit-mul/{str}", method = RequestMethod.GET)
    public String restUnitMul(@PathVariable String str,
                           @RequestParam(required = false) String tagName,
                           @RequestParam(required = false) String tagValue) {
        if (!StringUtils.isEmpty(tagName)) {
            String [] tag_list;
            String [] value_list;
            tag_list = tagName.split("-");
            value_list = tagValue.split("-");
            if (tag_list.length != value_list.length)
                throw new RuntimeException("tag和value个数对应不上，以-分割");

            for(int i = 0; i < tag_list.length; i++){
                LOG.info("单元化添加 key:" + tag_list[i] + ",value:" + value_list[i]);
                TsfUnitContext.putTag(tag_list[i], value_list[i]);
            }
        }
        return restTemplate.getForObject("http://provider-demo/echo/unit/" + str, String.class);
    }
}