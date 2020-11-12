package com.tencent.tsf.msgw.zuul1.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import com.netflix.zuul.exception.ZuulException;
import com.tencent.tsf.gateway.core.annotation.TsfGatewayFilter;
import com.tencent.tsf.gateway.zuul1.filter.TsfGatewayZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Filter
 * @author seanlxliu
 * @since 2019/9/5
 */
@TsfGatewayFilter
public class TestFilter extends TsfGatewayZuulFilter {

    private Logger logger = LoggerFactory.getLogger(TestFilter.class);


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("hello world");
        logger.info("hello world");

        return null;
    }
}
