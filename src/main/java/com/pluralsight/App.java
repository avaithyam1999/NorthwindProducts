package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        String query = """
                select productID, productName, unitPrice, UnitsInStock
                from products
                where productName like ? or supplierID like ?;
                """;

        PreparedStatement statement = connection.prepareStatement(query);

        String productName = "%hi%";
        int supplierID = 4;

        statement.setString(1, productName);
        statement.setInt(2, supplierID);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            String name = results.getString(1);
            productName = results.getString(2);
            int unitPrice = results.getInt(3);
            int unitsInStock = results.getInt(4);
            System.out.printf("""
                    
                    Product ID: %s
                    Product Name: %s
                    Unit Price: %d
                    Units in Stock: %d
                    
                    """, name, productName, unitPrice, unitsInStock);
        }

        results.close();
        statement.close();
        connection.close();
    }
}
