package com.tsf.demo.mysql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by chazling on 2019/5/26.
 */
@Component
public class TsfCountDao {

    private static final Logger LOG = LoggerFactory.getLogger(TsfCountDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert() throws Exception {

        String sql = "INSERT INTO `tsf_count` (`insertion_time`) VALUES (CURRENT_TIMESTAMP) ";

        try {
            LOG.info("insert into table tsf_count");
            return jdbcTemplate.update(sql);
        } catch (DataAccessException dae) {
            // 数据库连接异常
            LOG.error("insert into table tsf_count failed, errinfo : {}", dae.getMessage());
            throw new Exception("insert into table tsf_count failed", dae);
        }
    }
}