package com.tsf.demo.mysql.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * 测试自定义DruidDataSource配置
 */
@Configuration
public class DruidConfiguration {
    // 需要时开启，并定义CustomFilter进行测试
    /*
    @Inject
    DruidConfiguration(DataSource dataSource)
    {
        //CustomFilter filter = new CustomFilter();
        DruidDataSource druidDataSource = (DruidDataSource)dataSource;
        List<Filter> list = Lists.newArrayList();
        //list.add(filter);
        druidDataSource.setProxyFilters(list);
    }*/
}