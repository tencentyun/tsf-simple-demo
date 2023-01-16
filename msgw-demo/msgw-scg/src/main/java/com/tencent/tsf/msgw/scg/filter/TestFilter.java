package com.tencent.tsf.msgw.scg.filter;

import com.tencent.tsf.gateway.core.annotation.TsfGatewayFilter;
import com.tencent.tsf.gateway.scg.AbstractTsfGlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author seanlxliu
 */
@TsfGatewayFilter
public class TestFilter extends AbstractTsfGlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return true;
    }

    @Override
    public Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("hello world");
        logger.info("hello world");
        return chain.filter(exchange);
    }
}