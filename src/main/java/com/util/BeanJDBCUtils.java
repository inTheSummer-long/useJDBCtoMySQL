package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author long
 * @date 2022/6/15 15:23
 * @Description: 对数据的操作的基本类
 */

@SuppressWarnings("all")
public class BeanJDBCUtils<T> extends JDBCUtils {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private Class entityClass;

    /**
     * 构造方法,为了获取到泛型的对象的类，将其赋值给entityClass
     */
    public BeanJDBCUtils() {
        //getClass() 获取Class对象，当前我们执行的是new FruitDAOImpl() , 创建的是FruitDAOImpl的实例
        //那么子类构造方法内部首先会调用父类（BaseDAO）的无参构造方法
        //因此此处的getClass()会被执行，但是getClass获取的是FruitDAOImpl的Class
        //所以getGenericSuperclass()获取到的是BaseJDBCUtils的Class

        //System.out.println("获取父类对象：" + clazz.getSuperclass());
        Type genericType = getClass().getGenericSuperclass();
        //ParameterizedType 参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //获取到的<T>中的T的真实的类型
        Type actualType = actualTypeArguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sql
     * @param args
     * @return
     * @Description: 查找数据库中多行数据
     */
    public List<T> query(String sql, Object... args) {
        List<T> list = new ArrayList<>();
        connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            /*填充占位符*/
            for (int i = 0; i < args.length; ++i)
                preparedStatement.setObject(i + 1, args[i]);
            preparedStatement.execute();
            /*获取返回结果*/
            resultSet = preparedStatement.getResultSet();
            /*获取返回结果的数据*/
            ResultSetMetaData metaData = resultSet.getMetaData();
            /*获取数据的行数*/
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                /*初始化泛型*/
                T entity = (T) entityClass.newInstance();
                Object object = new Object();
                for (int i = 0; i < columnCount; ++i) {
                    /*获取列名的别名，如果没有别名则直接获取列名*/
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    object = resultSet.getObject(i + 1);
                    /*使用反射给泛型变量赋值*/
                    Field field = entity.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(entity, object);
                }
                list.add(entity);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, resultSet);
        }
    }


    /**
     *
     * @param sql
     * @param clazz
     * @param args
     * @return
     * @Description: 查找数据库中单行数据
     */
    public T query(String sql, Class clazz, Object... args) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; ++i)
                preparedStatement.setObject(i + 1, args[i]);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            T t = (T) entityClass.newInstance();
            if (resultSet.next()) {
                Object object = new Object();
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    object = resultSet.getObject(columnLabel);
                    field.set(t, object);
                }
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, resultSet);
        }
    }

    /**
     * @param sql
     * @param args
     * @return 影响的行数
     * @Description: 增删改
     */
    public Integer updata(String sql, Object... args) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; ++i)
                preparedStatement.setObject(i + 1, args[i]);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
    }

    /**
     * @param sql
     * @param args
     * @param <E>
     * @return
     * @Description: 查找特定的值，并返回单一基本类型
     */
    public <E> E getElement(String sql, Object... args) {
        connection = getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            Object object = null;
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }
            return (E) object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, resultSet);
        }
    }

}
