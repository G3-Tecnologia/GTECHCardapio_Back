package com.example.cardapio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDbTest {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://10.0.1.26:3306/gpp_fulano?zeroDateTimeBehavior=convertToNull";
        try (Connection conn = DriverManager.getConnection(url, "g3_informatica", "#g31nf#");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("DESCRIBE view_cheff_produto")) {
            while (rs.next()) {
                System.out.println("COLUMN: " + rs.getString("Field"));
            }
        }
    }
}
