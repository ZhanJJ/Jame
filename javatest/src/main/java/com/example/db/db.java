package com.example.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

/**
 * Created by Kin on 2017/7/5.
 */

public class db {
    public static void main(String[] args) {
        try {
            //获取数据库链接
            String dbSql = "jdbc:sqlite://F:/database/test.db"; //直接指定数据库test.db在电脑中的位置
//            String dbSql = "jdbc:sqlite:jdbc:test.db"; //不指定数据库test.db在电脑中的位置，这是位置就是所在Java项目根目录下
            Class.forName("org.sqlite.JDBC");

            //建立数据库连接
            //连接到一个现有的数据库。如果数据库不存在，那么它就会被创建，最后将返回一个数据库对象。
            Connection connection = getConnection(dbSql);
            System.out.println("Opened database successfully");

            Statement st = connection.createStatement();

            st.executeUpdate("drop table if exists company;");
            String sql = "create table if not exists company(" +
                    "id int primary key not null," +
                    "age int not null," +
                    "name text not null," +
                    "salary real default 0.00" +
                    ")";
            st.executeUpdate(sql);
            insert(st);
//            update(st);
//            delete(st);
//            updateTable(statement);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Statement st) throws SQLException {
        st.executeUpdate("insert into company values(1,20,'Jack',5000)");
        st.executeUpdate("insert into company values(2,19,'Kobe',8000)");
        st.executeUpdate("insert into company values(3,30,'James',20000)");
        st.executeUpdate("insert into company values(4,22,'Alvin',10000)");
        System.out.println("insert data success!");
        query(st);
    }

    private static void update(Statement st) throws SQLException {
        st.executeUpdate("update company set salary=28000 where name='James'");
        System.out.println("update data success!");
        query(st);
    }

    private static void delete(Statement st) throws SQLException {
        String sql = "delete from company where id = 1";
        st.executeUpdate(sql);
        System.out.println("delete data success!");
        query(st);
    }

    private static void query(Statement st) throws SQLException {
        ResultSet resultSet = st.executeQuery("select * from company");
        while (resultSet.next()) {
            System.out.print("id=" + resultSet.getInt("id"));
            System.out.print(" age=" + resultSet.getInt("age"));
            System.out.print(" name=" + resultSet.getString("name"));
            System.out.print(" salary=" + resultSet.getDouble("salary"));
            System.out.println();
        }
        resultSet.close();
    }

    //改变表结构
    private static void updateTable(Statement statement) throws SQLException {
        statement.executeUpdate("alter table company add column salary int default 0");
        statement.executeUpdate("insert into company values(3,0,'James',5000)");
        ResultSet rs = statement.executeQuery("select * from company");
        System.out.println("改变表结构之后");
        while (rs.next()) {
            System.out.print("id=" + rs.getInt("id"));
            System.out.print(" age=" + rs.getInt("age"));
            System.out.print(" name=" + rs.getString("name"));
            System.out.print(" salary=" + rs.getInt("salary"));
            System.out.println("");
        }
        rs.close();
    }
}
