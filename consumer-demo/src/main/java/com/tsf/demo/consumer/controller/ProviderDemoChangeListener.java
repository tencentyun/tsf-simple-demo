//package com.tsf.demo.consumer.controller;
//
//
//import com.ecwid.consul.v1.health.model.HealthService;
//import com.tencent.tsf.serviceregistry.watch.ConsulServiceChangeCallback;
//import com.tencent.tsf.serviceregistry.watch.ConsulServiceChangeListener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@ConsulServiceChangeListener(serviceName = "provider-demo", global = false)
//public class ProviderDemoChangeListener implements ConsulServiceChangeCallback {
//
//    private static final Logger log = LoggerFactory.getLogger(ProviderDemoChangeListener.class);
//
//    @Override
//    public void callback(List<HealthService> currentServices, List<HealthService> addServices, List<HealthService> deleteServices) {
//        log.info("current size:{}, add size:{}, delete list:{}", currentServices.size(), addServices.size(), deleteServices.size());
//        log.info("current list:{}, add list:{}, delete list:{}", currentServices, addServices, deleteServices);
//    }
//}
