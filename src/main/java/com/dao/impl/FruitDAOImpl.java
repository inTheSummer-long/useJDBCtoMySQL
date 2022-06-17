package com.dao.impl;

import com.dao.FruitDAO;
import com.pojo.Fruit;
import com.util.BeanJDBCUtils;

import java.util.List;

/**
 * @author long
 * @date 2022/6/15 15:04
 * @Description: 对数据库中t_fruit表的操作的类
 */
@SuppressWarnings("all")
public class FruitDAOImpl extends BeanJDBCUtils<Fruit> implements FruitDAO {

    @Override
    public List<Fruit> getAllFruit() {
        String sql = "select * from t_fruit";
        return query(sql);
    }

    @Override
    public Integer addFruit(Fruit fruit) {
        String sql = "insert into t_fruit(fname,price,fcount,remark) values(?,?,?,?)";
        return updata(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }

    @Override
    public Long getAllCount() {
        String sql = "select count(*) from t_fruit";
        return getElement(sql);
    }

    @Override
    public Integer getMaxPrice() {
        String sql = "select max(price) from t_fruit";
        return getElement(sql);
    }

    @Override
    public Integer getMinPrice() {
        String sql = "select min(price) from t_fruit";
        return getElement(sql);
    }

    @Override
    public Fruit getFruitById(Integer id) {
        String sql = "select * from t_fruit where fid = ?";
        return query(sql, Fruit.class, id);
    }

}
