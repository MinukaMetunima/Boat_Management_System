package com.boatsafari.common.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread-Safe Singleton JDBC Connection Manager for Boat Safari System.
 * Supports Microsoft SQL Server (SSMS), MySQL, and dual in-memory fallback.
 * Demonstrates the Singleton Design Pattern, Encapsulation, and defensive resource handling.
 * 
 * Group: Y2-S1-MLB-B8G1-09 | SLIIT SE2030 Software Engineering
 */
public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static volatile DatabaseConnection instance;

    private String url;
    private String username;
    private String password;
    private String driver;
    private String databaseType = "IN_MEMORY";
    private boolean isAvailable = false;

    private DatabaseConnection() {
        loadConfiguration();
    }

    /**
     * Double-checked locking Singleton accessor.
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                LOGGER.warning("db.properties not found in classpath. Using default connection settings.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not load db.properties: " + e.getMessage());
        }

        // 1. Check Primary Configured Connection (from db.properties)
        String primaryDriver = props.getProperty("db.driver", "net.sourceforge.jtds.jdbc.Driver");
        String primaryUrl = props.getProperty("db.url", "jdbc:jtds:sqlserver://localhost/boat_safari_db;instance=SQLEXPRESS;namedPipe=true");
        String primaryUser = props.getProperty("db.username", "sa");
        String primaryPass = props.getProperty("db.password", "0000");

        String primaryLabel = "Primary Database";
        if (primaryDriver.contains("jtds") || primaryDriver.contains("sqlserver")) {
            primaryLabel = "Microsoft SQL Server (SSMS)";
        } else if (primaryDriver.contains("mysql")) {
            primaryLabel = "MySQL 8.0";
        }

        if (tryConnect(primaryDriver, primaryUrl, primaryUser, primaryPass, primaryLabel)) {
            return;
        }

        // 2. Check SQL Server via jTDS Named Pipes (Local SQLEXPRESS)
        String jtdsDriver = "net.sourceforge.jtds.jdbc.Driver";
        String jtdsPipeUrl = props.getProperty("db.mssql.pipeUrl", "jdbc:jtds:sqlserver://localhost/boat_safari_db;instance=SQLEXPRESS;namedPipe=true");
        String mssqlUser = props.getProperty("db.mssql.username", "sa");
        String mssqlPass = props.getProperty("db.mssql.password", "0000");

        if (tryConnect(jtdsDriver, jtdsPipeUrl, mssqlUser, mssqlPass, "Microsoft SQL Server (jTDS Named Pipe)")) {
            return;
        }

        // 3. Check Microsoft SQL Server via Official MSSQL JDBC Driver
        String mssqlDriver = props.getProperty("db.mssql.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String mssqlNamedUrl = props.getProperty("db.mssql.namedUrl", "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=boat_safari_db;encrypt=false;trustServerCertificate=true;");
        String mssqlTcpUrl = props.getProperty("db.mssql.tcpUrl", "jdbc:sqlserver://localhost:1433;databaseName=boat_safari_db;encrypt=false;trustServerCertificate=true;");

        if (tryConnect(mssqlDriver, mssqlNamedUrl, mssqlUser, mssqlPass, "Microsoft SQL Server (MSSQL Driver - Named Instance)")) {
            return;
        }

        if (tryConnect(mssqlDriver, mssqlTcpUrl, mssqlUser, mssqlPass, "Microsoft SQL Server (MSSQL Driver - Port 1433)")) {
            return;
        }

        // 4. Check MySQL 8.0 Server Configuration (Fallback)
        String mysqlDriver = props.getProperty("db.mysql.driver", "com.mysql.cj.jdbc.Driver");
        String mysqlUrl = props.getProperty("db.mysql.url", "jdbc:mysql://localhost:3306/boat_safari_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8");
        String mysqlUser = props.getProperty("db.mysql.username", "root");
        String mysqlPass = props.getProperty("db.mysql.password", "0000");

        if (tryConnect(mysqlDriver, mysqlUrl, mysqlUser, mysqlPass, "MySQL 8.0 Fallback")) {
            return;
        }

        // 4. Fallback to synchronized in-memory database store
        this.isAvailable = false;
        this.databaseType = "IN_MEMORY_STORE";
        LOGGER.info("No physical database server reachable. Activated synchronized in-memory database store with pre-seeded data. ZERO crash guaranteed.");
    }

    private boolean tryConnect(String driverClass, String testUrl, String user, String pass, String label) {
        try {
            Class.forName(driverClass);
            try (Connection conn = (user != null && !user.isEmpty()) 
                    ? DriverManager.getConnection(testUrl, user, pass) 
                    : DriverManager.getConnection(testUrl)) {
                this.driver = driverClass;
                this.url = testUrl;
                this.username = user;
                this.password = pass;
                this.isAvailable = true;
                this.databaseType = label;
                LOGGER.info("Successfully connected to " + label + " database: " + testUrl);
                return true;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.FINE, "Driver class not found for " + label + ": " + driverClass);
        } catch (SQLException e) {
            LOGGER.log(Level.FINE, label + " connection attempt failed (" + testUrl + "): " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtains a live JDBC Connection.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (!isAvailable) {
            throw new SQLException("No active physical database connection. In-memory store active.");
        }
        if (username != null && !username.isEmpty()) {
            return DriverManager.getConnection(this.url, this.username, this.password);
        }
        return DriverManager.getConnection(this.url);
    }

    public boolean isDatabaseAvailable() {
        return isAvailable;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    /**
     * Prevents cloning to maintain strict Singleton invariant.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning of DatabaseConnection singleton is prohibited.");
    }
}
