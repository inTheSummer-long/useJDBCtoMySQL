package com.dao;

import com.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    /*获取所有水果信息*/
    List<Fruit> getAllFruit();

    /*添加水果信息*/
    Integer addFruit(Fruit fruit);

    /*获取水果的种类的数量*/
    Long getAllCount();

    /*获取最大价格*/
    Integer getMaxPrice();

    /*获取最小价格*/
    Integer getMinPrice();

    Fruit getFruitById(Integer id);

}
