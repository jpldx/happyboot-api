package org.happykit.happyboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chen.xudong
 * @date 2020/7/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void test1() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("连接：" + connection);
    }
}
