import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HuYu
 * Date: 2021-06-25
 * Time: 13:54
 */
public class TestDemo {
    public static void main1(String[] args) throws ClassNotFoundException, SQLException {
       //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        String sql = "select * from user";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){//存放在结果集中
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
    }

    public static void main2(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        String sql = "select * from user";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){//存放在结果集中
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println(user);

        }
    }
    //根据条件查询
    public static void main3(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        //String sql = "select * from user where name ='bit' and password = '123'";
        String sql = "select * from user where name ='bit' and passord = '123'";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println(user);

        }
    }
    //查询语句定义成变量
    public static void main4(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        //String sql = "select * from user where name ='bit' and password = '123'";
        String uname = "bit";
        String upass = "123";
        String sql = "select * from user where name ='"+uname+" 'and passord = '"+upass+"'";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println(user);

        }
    }
     //sql注入
    public static void main6(String[] args) throws SQLException, ClassNotFoundException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        //String sql = "select * from user where name ='bit' and password = '123'";
        String uname = "bit";
        String upass = "1' or '1' = '1";
        String sql = "select * from user where name ='"+uname+" 'and passord = '"+upass+"'";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println(user);

        }
    }
    //新的获取连接的方式 Datasouce
    public static void main7(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.通过数据源获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
       // MysqlDataSource本身不具备连接池的概念;
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource)dataSource).setUrl(url);
        ((MysqlDataSource)dataSource).setUser(username);
        ((MysqlDataSource)dataSource).setPassword(password);

        Connection connection = dataSource.getConnection();

        //3.执行sql语句；
        //String sql = "select * from user where name ='bit' and password = '123'";
        String uname = "bit";
        String upass = "1' or '1' = '1";
        String sql = "select * from user where name = ? and passord = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,uname);
        preparedStatement.setString(2,upass);

        System.out.println("sql："+preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println(user);

        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
     //使用preparedstatement实现增删查改
    //增加
    public static void main8(String[] args) throws SQLException, ClassNotFoundException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.通过数据源获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        // MysqlDataSource本身不具备连接池的概念;
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource)dataSource).setUrl(url);
        ((MysqlDataSource)dataSource).setUser(username);
        ((MysqlDataSource)dataSource).setPassword(password);

        Connection connection = dataSource.getConnection();

        //3.执行sql语句；

        String sql = "insert into user (id,name ,passord) values (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,2);
        preparedStatement.setString(2,"gaobo");
        preparedStatement.setString(3,"666");

        System.out.println("sql："+preparedStatement.toString());
        //除了查询，其他都要用executeUpdate，返回值是受影响的行数
        int ret = preparedStatement.executeUpdate();
        if (ret!=0){
          System.out.println("插入成功！");
        }
        preparedStatement.close();
        connection.close();
    }
//更新时注意sql语句的顺序
    public static void main9(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.通过数据源获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        // MysqlDataSource本身不具备连接池的概念;
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource)dataSource).setUrl(url);
        ((MysqlDataSource)dataSource).setUser(username);
        ((MysqlDataSource)dataSource).setPassword(password);

        Connection connection = dataSource.getConnection();

        //3.执行sql语句；
         //找到id = 2，将信息修改成gaobo2
        //String sql = "update user set name = ? where id  =?";
        String sql = "update user set name=? where id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,"gaobo2");
        preparedStatement.setInt(2,2);

        System.out.println("sql："+preparedStatement.toString());
        //除了 查询 其他都要用executeUpdate  返回值是受影响的行数
        int ret  = preparedStatement.executeUpdate();//executeUpdate为插入语句，返回值为int、要用int接受

        if(ret != 0) {
            System.out.println("更新成功！");
        }
        preparedStatement.close();
        connection.close();
    }

    public static void main10(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.通过数据源获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        // MysqlDataSource本身不具备连接池的概念;
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource)dataSource).setUrl(url);
        ((MysqlDataSource)dataSource).setUser(username);
        ((MysqlDataSource)dataSource).setPassword(password);

        Connection connection = dataSource.getConnection();

        //3.执行sql语句；

        String sql = "delete  from user where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setInt(1,2);

        System.out.println("sql："+preparedStatement.toString());
        //除了 查询 其他都要用executeUpdate  返回值是受影响的行数
        int ret  = preparedStatement.executeUpdate();//executeUpdate为插入语句，返回值为int、要用int接受

        if(ret != 0) {
            System.out.println("删除成功！");
        }
        preparedStatement.close();
        connection.close();
    }
    //查询语句定义成变量
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/testjava17_18?useSSL=false";
        String username = "root";
        String password = "1111";
        Connection connection = DriverManager.getConnection(url,username,password);
        //3.执行sql语句；
        String sql = "select * from user";

        Statement statement = connection .createStatement();
        statement.executeQuery(sql);

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){//存放在结果集中
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            System.out.println("查询成功！");
            System.out.println(user);

        }
    }
}
