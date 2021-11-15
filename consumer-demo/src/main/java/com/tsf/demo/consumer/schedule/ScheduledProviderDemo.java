package com.tsf.demo.consumer.schedule;

import com.tsf.demo.consumer.entity.CustomMetadata;
import com.tsf.demo.consumer.proxy.ProviderDemoService;
import com.tsf.demo.consumer.proxy.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.tsf.faulttolerance.annotation.TsfFaultTolerance;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.tsf.core.TsfContext;

import com.tsf.demo.consumer.proxy.ProviderDemoService;

@EnableScheduling
@Service
public class ScheduledProviderDemo {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledProviderDemo.class);

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderDemoService providerDemoService;

    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:500}")
    public void doWork() throws InterruptedException {
        TsfContext.putTag("test", "123");
        TsfContext.putCustomMetadata(new CustomMetadata("test", "123"));
        String response = providerDemoService.echo("auto-test");
        LOG.info("consumer-demo auto test, response: [" + response + "]");
    }

    // 异常熔断测试，需要测试熔断时打开注释
//    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:1000}")
//    @TsfFaultTolerance(fallbackMethod = "doWorkFallback")
//    public void doWorkWithError() throws InterruptedException {
//        String response = providerDemoService.echoError("auto-test-error");
//        LOG.info("consumer-demo auto test, response: [" + response + "]");
//    }

    // 慢请求熔断测试，需要测试熔断时打开注释
//    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:100}")
//    @TsfFaultTolerance(fallbackMethod = "doWorkFallback")
//    public void doWorkWithError() throws InterruptedException {
//        String response = providerDemoService.echoSlow("auto-test-slow");
//        LOG.info("consumer-demo auto test, response: [" + response + "]");
//    }

    public void doWorkFallback() {
        LOG.info("consumer-demo auto test fallback.");
    }

    //测试URL配置FeignClient时启用
    /*@Scheduled(fixedDelayString = "${consumer.auto.test.interval:10000}")
    public void doUrlTest() throws InterruptedException {
        TsfContext.putTag("test", "123");
        String response = providerService.echo("auto-test");
        LOG.info("consumer-demo auto test, response: [" + response + "]");
    }*/

    //trace信息获取注入时打开
//    @Scheduled(fixedDelayString = "${consumer.auto.test.interval:1000}")
//    public void doWorkTracer() throws InterruptedException {
//        TsfContext.putTag("test", "123");
//        TsfContext.putCustomMetadata(new CustomMetadata("test", "123"));
//        String response = providerDemoService.echoTracer("auto-test");
//        LOG.info("consumer-demo auto test, response: [" + response + "]");
//    }
}