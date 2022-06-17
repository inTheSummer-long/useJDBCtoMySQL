package com;

import com.util.JDBCUtils;
import org.junit.Test;

/**
 * @author long
 * @date 2022/6/17 11:14
 */
public class JDBCUtilsTest {
    JDBCUtils jdbcUtils;

    @Test
    public void test(){
        jdbcUtils = new JDBCUtils();
        System.out.println(jdbcUtils.getConnection());
    }

}
