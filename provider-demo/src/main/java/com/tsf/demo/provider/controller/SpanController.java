package com.tsf.demo.provider.controller;

import brave.Span;
import brave.Tracing;
import brave.handler.MutableSpan;
import brave.propagation.CurrentTraceContext;
import brave.propagation.TraceContext;
import com.tsf.demo.provider.config.ProviderNameConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;

@RestController
public class SpanController {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderController.class);
    @Autowired
    private ProviderNameConfig providerNameConfig;
    @Autowired
    private Tracing tracing;

    //获取span信息时打开
    @RequestMapping(value = "/getSpan/{param}", method = RequestMethod.GET)
    public String getSpan(@PathVariable String param) throws Exception {
        TraceContext traceContext = tracing.tracer().currentSpan().context();

        //获取traceid，spanid
        long traceId = traceContext.traceId();
        long spanId = traceContext.spanId();
        LOG.info("traceId:{},spanId:{}",traceId, spanId);

        //获取tags
        Span span = tracing.tracer().currentSpan();
        Field state = span.getClass().getDeclaredField("state");
        state.setAccessible(true);
        MutableSpan mutableSpan = (MutableSpan)state.get(span);

        Field tags = mutableSpan.getClass().getDeclaredField("tags");
        tags.setAccessible(true);
        ArrayList<String> list = (ArrayList<String>) tags.get(mutableSpan);
        String key =null, value =null;
        for (int i = 0, length = list.size(); i < length; i += 2) {
            key =  list.get(i);
            value = list.get(i + 1);

            LOG.info("tags key: {}, value: {}", key, value);
        }

        String result = "request param: " + param + ", response from " + providerNameConfig.getName();

        return result;
    }

    //设置span信息时打开
    @RequestMapping(value = "/addSpan/{param}", method = RequestMethod.GET)
    public void addSpan(@PathVariable String param) throws Exception{
        TraceContext traceContext = tracing.tracer().currentSpan().context();

        TraceContext context = traceContext.toBuilder().traceId(2035873338086630653L).spanId(-2035873338086630653L).build();

        CurrentTraceContext currentTraceContext = tracing.currentTraceContext();
        currentTraceContext.newScope(context);

        Span spanCur = tracing.tracer().currentSpan();


        long traceId1 = spanCur.context().traceId();
        long spanId1 = spanCur.context().spanId();
        LOG.info("traceId1:{},spanId1:{}",traceId1, spanId1);


        //添加tag
        spanCur.tag("qx-test1","value");
        spanCur.tag("qx-test2","value");
        spanCur.tag("qx-test1","value");

        //获取tag
        Field state1 = spanCur.getClass().getDeclaredField("state");
        state1.setAccessible(true);
        MutableSpan mutableSpan1 = (MutableSpan)state1.get(spanCur);
        Field tags1 = mutableSpan1.getClass().getDeclaredField("tags");
        tags1.setAccessible(true);
        ArrayList<String> list1 = (ArrayList<String>) tags1.get(mutableSpan1);
        String key1 =null, value1 =null;
        for (int i = 0, length = list1.size(); i < length; i += 2) {
            key1 =  list1.get(i);
            value1 = list1.get(i + 1);

            LOG.info("tags key1: {}, value1: {}", key1, value1);
        }

        //获取traceid， spanid
        TraceContext traceContext1 = spanCur.context();
        long traceId2 = traceContext1.traceId();
        long spanId2 = traceContext1.spanId();
        LOG.info("traceId1:{},spanId1:{}",traceId2, spanId2);
    }
}
