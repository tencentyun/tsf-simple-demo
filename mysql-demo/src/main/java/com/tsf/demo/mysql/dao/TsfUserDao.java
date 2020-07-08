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
public class TsfUserDao {

    private static final Logger LOG = LoggerFactory.getLogger(TsfUserDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(final String userName) throws Exception {

        String sql = "INSERT INTO `tsf_user` (`user_name`, `user_token`, `creation_time`) VALUES (?, ?, CURRENT_TIMESTAMP) " +
                "ON DUPLICATE KEY UPDATE `creation_time` = CURRENT_TIMESTAMP";

        try {
            String userToken = "token:" + userName;
            LOG.info("insert into table tsf_user, user_name [{}] user token [{}]", userName, userToken);
            return jdbcTemplate.update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1, userName, Types.VARCHAR);
                    ps.setObject(2, userToken, Types.VARCHAR);
                }
            });
        } catch (DataAccessException dae) {
            // 数据库连接异常
            LOG.error("insert into table tsf_user failed, errinfo : {}", dae.getMessage());
            throw new Exception("insert into table tsf_user failed", dae);
        }
    }

    public String query(final String userName) throws Exception {
        try {
            String sql = "SELECT `user_token` FROM `tsf_user` WHERE `user_name` = '" + userName + "'";

            LOG.info("query from table tsf_user, user_name [{}]", userName);
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (DataAccessException dae) {
            // 数据库连接异常
            LOG.error("query from table tsf_user failed, errinfo : {}", dae.getMessage());
            throw new Exception("query from table tsf_user failed", dae);
        }
    }
}