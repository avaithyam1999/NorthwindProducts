package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        Connection connection = DriverManager.getConnection(url, username, password);

        String query = """
                select productID, productName, unitPrice, UnitsInStock
                from products
                where productName like ?;
                """;

        PreparedStatement statement = connection.prepareStatement(query);

        System.out.println("What is the product name you are looking for?");
        String productName = scanner.nextLine().trim();

//        System.out.println("What is the supplier ID(1-29)");
//        int supplierID = scanner.nextInt();
//        scanner.nextLine();

        statement.setString(1, "%" + productName + "%");
//        statement.setString(2, "%" + supplierID + "%");
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
