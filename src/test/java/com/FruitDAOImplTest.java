package com;

import com.dao.FruitDAO;
import com.dao.impl.FruitDAOImpl;
import com.pojo.Fruit;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author long
 * @date 2022/6/15 15:16
 */
public class FruitDAOImplTest {

    @Test
    public void test() throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        System.out.println("获取所有水果信息：");
        fruitDAO.getAllFruit().forEach(System.out::println);
        System.out.println("总水果种类数"+fruitDAO.getAllCount());
        System.out.println("最大价格"+fruitDAO.getMaxPrice());
        System.out.println("最低价格"+fruitDAO.getMinPrice());
        System.out.println("添加水果信息:"+fruitDAO.addFruit(new Fruit("香蕉",21,10,"")));
        System.out.println("通过fid获取水果信息：");
        System.out.println(fruitDAO.getFruitById(70));
        System.out.println("获取所有水果信息：");
        fruitDAO.getAllFruit().forEach(System.out::println);
    }
}
