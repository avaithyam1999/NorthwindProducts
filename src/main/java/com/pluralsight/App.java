package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        displayProductInfoSearch(url, username, password, scanner);
        displayCustomerInfoSearch(url, username, password, scanner);
    }

    private static void displayProductInfoSearch(String url, String username, String password, Scanner scanner) throws SQLException {
        String query = """
                SELECT productID, productName, unitPrice, unitsInStock
                FROM products
                WHERE productName LIKE ?;
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            System.out.println("What is the product name you are looking for?");
            String productName = scanner.nextLine().trim();

            statement.setString(1, "%" + productName + "%");

            try (ResultSet results = statement.executeQuery()) {
                boolean found = false;

                while (results.next()) {
                    found = true;
                    int productID = results.getInt("productID");
                    String name = results.getString("productName");
                    double unitPrice = results.getDouble("unitPrice");
                    int unitsInStock = results.getInt("unitsInStock");

                    System.out.printf("""
                            
                            Product ID: %d
                            Product Name: %s
                            Unit Price: $%.2f
                            Units in Stock: %d
                            
                            """, productID, name, unitPrice, unitsInStock);
                }

                if (!found) {
                    System.out.println("No products found matching: " + productName);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }
    private static void displayCustomerInfoSearch(String url, String username, String password, Scanner scanner) {
        String query = """
                SELECT customerID, companyName, contactName, city, country
                FROM customers
                WHERE country LIKE ?;
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            System.out.println("\nWhat country would you like to search for customers in?");
            String country = scanner.nextLine().trim();

            statement.setString(1, "%" + country + "%");

            try (ResultSet results = statement.executeQuery()) {
                boolean found = false;

                while (results.next()) {
                    found = true;
                    String customerID = results.getString("customerID");
                    String companyName = results.getString("companyName");
                    String contactName = results.getString("contactName");
                    String city = results.getString("city");
                    String customerCountry = results.getString("country");

                    System.out.printf("""
                            
                            Customer ID: %s
                            Company Name: %s
                            Contact Name: %s
                            City: %s
                            Country: %s
                            
                            """, customerID, companyName, contactName, city, customerCountry);
                }

                if (!found) {
                    System.out.println("No customers found in: " + country);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }
}
