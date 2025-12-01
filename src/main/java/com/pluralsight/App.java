package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup24";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        String query = "select * from products;";

        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            String name = results.getString("ProductName");
            System.out.println(name);
        }

        connection.close();
    }
}
