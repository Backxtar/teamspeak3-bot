package de.backxtar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLManager {
    private static final Logger log = LoggerFactory.getLogger(SQLManager.class);
    private static Connection conn = null;
    private static String dbName;

    /**
     * Create a connection to mysql database
     * @param db_Host
     * @param db_Name
     * @param db_User
     * @param db_Passwd
     * @throws ClassNotFoundException if drivers not found
     * @throws SQLException if connection fails
     */

    public static void connect(String db_Host, String db_Name, String db_User, String db_Passwd) throws ClassNotFoundException, SQLException {
        dbName = db_Name;
        if (conn != null) disconnect();

        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        Class.forName(jdbcDriver);
        conn = DriverManager.getConnection("jdbc:mysql://" + db_Host + ":3306/" + db_Name, db_User, db_Passwd);
        log.info("Connected to database - " + db_Name);
        createTables();
    }

    /**
     * PS for sql security
     * @param sql string
     * @return a prepareStatement
     * @throws SQLException
     */

    private static PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    /**
     * Disconnect from database
     * @throws SQLException
     */

    public static void disconnect() throws SQLException{
        if (conn != null) {
            conn.close();
            conn = null;
            log.info("Disconnected from database - " + dbName);
        }
    }

    /**
     * Create database if not exist
     * @throws SQLException
     */

    private static void createTables() throws SQLException {
        String[] sqlStmts = {
                "CREATE TABLE IF NOT EXISTS API_Keys(" +
                        "id bigint(20) AUTO_INCREMENT," +
                        "clientIdentity varchar(255)," +
                        "GW2_Key varchar(255)," +
                        "accountName varchar(255))",

                "CREATE TABLE IF NOT EXISTS Timers(" +
                        "id bigint(20) AUTO_INCREMENT," +
                        "clientIdentity varchar(255)," +
                        "timeStamp timestamp," +
                        "name varchar(255))"
        };
        List<PreparedStatement> stmts = new ArrayList<>() {{
            add(prepareStatement(sqlStmts[0]));
            add(prepareStatement(sqlStmts[1]));
        }};
        stmts.forEach(statement -> {
            try {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /* Return connection */
    public static Connection getConn() {
        return conn;
    }

    /* Insert into database */
    public static void insert(String table, String[] fields, Object[] values) throws SQLException {
        StringBuilder stmtString = new StringBuilder("INSERT INTO " + table + Arrays.toString(fields)
                .replace("[", "(").replace("]", ")") + " VALUES(");

        for (Object ignored : values)
            stmtString.append("?,");
        stmtString = new StringBuilder(stmtString.substring(0, stmtString.length() - 1) + ") ");
        PreparedStatement stmt = prepareStatement(stmtString.toString());

        int i = 1;
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String"))
                stmt.setString(i++, (String) value);
            else stmt.setLong(i++, (Long) value);
        }
        stmt.execute();
    }

    /* Delete from database */
    public static void delete(String table, String where, Object[] values) throws SQLException {
        PreparedStatement stmt = prepareStatement("DELETE FROM " + table + " WHERE " + where);

        int i = 1;
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String"))
                stmt.setString(i++, (String) value);
            else stmt.setLong(i++, (Long) value);
        }
        stmt.execute();
    }

    /* Delete table */
    public static void delete(String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("DELETE FROM " + table);
        stmt.execute();
    }

    /* Select fields from table */
    public static ResultSet select(String[] fields, String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + Arrays.toString(fields)
                .replace("[", "").replace("]", "") + " FROM " + table);
        return stmt.executeQuery();
    }

    /* Select field from table */
    public static ResultSet select(String field, String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + field + " FROM " + table);
        return stmt.executeQuery();
    }

    /* Select fields from table with value */
    public static ResultSet select(String[] fields, String table, String where, Object[] values) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + Arrays.toString(fields)
                .replace("[", "").replace("]", "") + " FROM " + table + " WHERE " + where);

        int i = 1;
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String"))
                stmt.setString(i++, (String) value);
            else stmt.setLong(i++, (Long) value);
        }
        return stmt.executeQuery();
    }

    /* Select all from table with value */
    public static ResultSet selectAll(String table, String where, Object[] values) throws NumberFormatException, SQLException {
        PreparedStatement stmt = prepareStatement("SELECT * FROM " + table + " WHERE " + where);

        int i = 1;
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String"))
                stmt.setString(i++, (String) value);
            else stmt.setLong(i++, (Long) value);
        }
        return stmt.executeQuery();
    }

    /* Select all from table */
    public static ResultSet selectAll(String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT * FROM " + table);
        return stmt.executeQuery();
    }

    /* Select fields from table with order */
    public static ResultSet selectOrderBy(String[] fields, String table, String order, String type) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + Arrays.toString(fields).replace("[", "")
                .replace("]", "") + " FROM " + table + " ORDER BY " + order + " " + type);
        return stmt.executeQuery();
    }

    /* Update fields in table with value */
    public static void update(String[] fields, String table, String where, Object[] values) throws SQLException {
        PreparedStatement stmt = prepareStatement("UPDATE " + table + " SET " + Arrays.toString(fields)
                .replace("[", "").replace("]", " = ?").replace(",", "= ? ,") + " WHERE " + where);

        int i = 1;
        for (Object value : values) {
            if (value.getClass().getName().equals("java.lang.String"))
                stmt.setString(i++, (String) value);
            else stmt.setLong(i++, (Long) value);
        }
        stmt.execute();
    }
}
