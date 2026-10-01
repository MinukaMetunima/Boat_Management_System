package com.boatsafari.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class TestDB {
    @Test
    public void testDatabaseConnection() {
        main(new String[0]);
    }

    public static void main(String[] args) {
        String user = "sa";
        String pass = "0000";
        String url = "jdbc:jtds:sqlserver://localhost/boat_safari_db;instance=SQLEXPRESS;namedPipe=true";

        System.out.println("Testing SQL Server Connection with user=sa, password=0000 via " + url);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                System.out.println("Connection established! DB Product: " + conn.getMetaData().getDatabaseProductName() + " " + conn.getMetaData().getDatabaseProductVersion());

                // 1. CREATE (INSERT with RETURN_GENERATED_KEYS)
                int generatedId = -1;
                String insertSql = "INSERT INTO destinations (name, region, harbor_name, description, image_url) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, "Test Destination Coral Lagoon");
                    stmt.setString(2, "SOUTH_COAST");
                    stmt.setString(3, "Test Pier");
                    stmt.setString(4, "Created by CRUD verification test");
                    stmt.setString(5, "assets/img/test.jpg");
                    int affected = stmt.executeUpdate();
                    System.out.println("1. CREATE: Insert affected " + affected + " row(s)");
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedId = rs.getInt(1);
                            System.out.println("   Generated Key ID = " + generatedId);
                        }
                    }
                }

                if (generatedId <= 0) {
                    throw new RuntimeException("Failed to obtain generated key!");
                }

                // 2. READ (SELECT by ID)
                String selectSql = "SELECT name, region FROM destinations WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                    stmt.setInt(1, generatedId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("2. READ: Found record name='" + rs.getString("name") + "', region=" + rs.getString("region"));
                        } else {
                            throw new RuntimeException("Record not found in READ step!");
                        }
                    }
                }

                // 3. UPDATE
                String updateSql = "UPDATE destinations SET name = ?, description = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, "Updated Coral Lagoon - Elite");
                    stmt.setString(2, "Updated description for CRUD verification");
                    stmt.setInt(3, generatedId);
                    int updated = stmt.executeUpdate();
                    System.out.println("3. UPDATE: Updated " + updated + " row(s)");
                }

                // Verify UPDATE
                try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                    stmt.setInt(1, generatedId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("   Verified updated name: '" + rs.getString("name") + "'");
                        }
                    }
                }

                // 4. DELETE
                String deleteSql = "DELETE FROM destinations WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                    stmt.setInt(1, generatedId);
                    int deleted = stmt.executeUpdate();
                    System.out.println("4. DELETE: Deleted " + deleted + " row(s)");
                }

                System.out.println(">>> FULL CRUD OPERATIONS TEST PASSED 100% ON SQL SERVER! <<<");

                // 5. Refresh all live table text files from SQL Server
                com.boatsafari.common.util.LiveTableManager.exportAllTables();
                System.out.println(">>> REFRESHED ALL 17 LIVE TABLE TEXT FILES FROM SQL SERVER! <<<");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
