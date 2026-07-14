package com.corejava.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
        private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
        private static final String USER = "hr";
        private static final String PASSWORD = "hr";

        public static Connection getConnection() {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public static void main(String[] args) {
            Connection con = getConnection();
            if (con != null) {
                System.out.println("Connected to Oracle DB successfully!");
            } else {
                System.out.println("Connection failed.");
            }
        }
    }
