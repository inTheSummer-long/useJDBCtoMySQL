# useJDBCtoMySQL

# JDBC连接MySQL数据库及自定义JDBCUtils
# 前言
`JDBC 指 Java 数据库连接，是一种标准Java应用编程接口（ JAVA API），用来连接 Java 编程语言和广泛的数据库。`
本文使用的是JDBC连接MySQL数据库
MySQL版本为8.0.28
# 一、JDBC连接数据库
## 1. 引入依赖
使用maven引入依赖包
`如果MySQL版本为8.x.xx`
```\
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>8.0.29</version>
</dependency>
```
`如果MySQL版本为 5.x.xx`
则引入可用
```\
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.45</version>
</dependency>
```
## 2. 连接数据库
使用JDBC连接数据库条件
- 需要指定连接的路径`url = "jdbc:mysql://localhost:3306/fruitdb"`
- 需要指定用户名`userName = "root"`
- 需要指定用户的密码`password = "xxxxxxx"`
- 需要MySQL驱动`diver = com.mysql.cj.jdbc.Driver`

**url 格式为：jdbc:mysql://地址或主机名:端口号/数据库名**

**driver**
- 如果MySQL版本为8.x.xx为  **com.mysql.cj.jdbc.Driver**
- 如果MySQL版本为5.x.xx为 **com.mysql.jdbc.Driver**

代码如下：
```java
public class JDBCUtils {

    private static final String url;
    private static final String userName;
    private static final String password;
    private static final String driver;

	/*静态代码块,给url,userName,password,driver赋值*/
    static {

        Properties properties = new Properties();
        /*读取jdbc.properties*/
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(is);
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
            //注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

	/*获取数据库的连接*/
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("无法连接到数据库\n");
        }
    }
}
```
jdbc.properties文件的路径我放在资源文件下的
![](https://img-blog.csdnimg.cn/c51b8fb312c645878d315654e281b572.png)

jdbc.properties文件的内容：
>url=jdbc:mysql://localhost:3306/fruitdb

>userName=root

>password=2043602397l

>driver=com.mysql.cj.jdbc.Driver

测试连接是否成功：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f93b5570033f48c0aa74c449a89061f2.png)
# 二、自定义JDBCUtils
我采用的是：
- `JDBCUtils来连接和关闭数据库相关资源`。
- 再写一个`类(BeanJDBCUtils)来进行数据库的基本操作`。
- 最后写一个类来实现具体的操作。
## 1. JDBCUtils
代码如下：
```java
/**
 * @author long
 * @date 2022/6/15 14:00
 * @Description: 连接数据库的类
 */

@SuppressWarnings("all")
public class JDBCUtils {

    private static final String url;
    private static final String userName;
    private static final String password;
    private static final String driver;

    static {

        Properties properties = new Properties();
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(is);
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            //注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @Description 获取数据库的连接
     * @return
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("无法连接到数据库\n");
        }
    }

    /**
     * @Description 关闭连接
     * @param connection
     */
    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description 关闭连接
     * @param connection
     */
    public void close(Connection connection, ResultSet resultSet) {
        try {
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```
## 2. BeanJDBCUtils
代码如下：
```java

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

```

## 3. 使用自定义JDBC类操作数据库
表名如下
![在这里插入图片描述](https://img-blog.csdnimg.cn/18b96d26cbb442b498a18bb1fcee0a5d.png)
表中内容如下
![在这里插入图片描述](https://img-blog.csdnimg.cn/84801ed5835b4a038f4804dad8f99d4b.png)

创建一个与数据库表t_fruit一一对应的Fruit类
```java

import lombok.*;

/**
 * @author long
 * @date 2022/6/15 14:58
 * @Description: 与数据库中的t_fruit表一一对应的水果类
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Fruit {
    private Integer fid;
    private String fname;
    private Integer price;
    private Integer fcount;
    private String remark;

    public Fruit(String fname, Integer price, Integer fcount, String remark) {
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }
}

```
创建一个接口（FruitDAO）来声明对改数据库的表中的操作的方法。
```java
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

```
对上述接口(FruitDAO)的实现类(FruitDAOImpl)
```java
/**
 * @author long
 * @date 2022/6/15 15:04
 * @Description: 对数据库中t_fruit表的操作的类
 */
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
```
测试结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f9c8a585dc1b4f15bf4dc5b6e45b34a8.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/f09cae442fd74a9b83732d1c17c69ab6.png)
