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
     * @param db_Host IP
     * @param db_Name name
     * @param db_User user
     * @param db_Passwd password
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
     * @throws SQLException if stmt can not be created
     */
    private static PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    /**
     * Disconnect from database
     * @throws SQLException if disconnecting fails
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
     * @throws SQLException if creation fails
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

    /**
     * Getter for SQL connection
     * @return connection
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * Insert method for sql
     * @param table name
     * @param fields field array
     * @param values object array
     * @throws SQLException if insert fails
     */
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

    /**
     * Delete method with param
     * @param table name
     * @param where param
     * @param values object array
     * @throws SQLException if delete fails
     */
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

    /**
     * Delete method
     * @param table name
     * @throws SQLException if table drop fails
     */
    public static void delete(String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("DELETE FROM " + table);
        stmt.execute();
    }

    /**
     * Select method without param
     * @param fields field array
     * @param table name
     * @return ResultSet
     * @throws SQLException if select fails
     */
    public static ResultSet select(String[] fields, String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + Arrays.toString(fields)
                .replace("[", "").replace("]", "") + " FROM " + table);
        return stmt.executeQuery();
    }

    /**
     * Single select method without param
     * @param field field
     * @param table name
     * @return ResultSet
     * @throws SQLException if select fails
     */
    public static ResultSet select(String field, String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + field + " FROM " + table);
        return stmt.executeQuery();
    }

    /**
     * Select method with params
     * @param fields field array
     * @param table name
     * @param where param
     * @param values object array
     * @return ResultSet
     * @throws SQLException if select fails
     */
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

    /**
     * Select all method with param
     * @param table name
     * @param where param
     * @param values object array
     * @return ResultSet
     * @throws NumberFormatException if sql statement can not be parsed
     * @throws SQLException if select fails
     */
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

    /**
     * Select all method
     * @param table name
     * @return ResultSet
     * @throws SQLException if select fails
     */
    public static ResultSet selectAll(String table) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT * FROM " + table);
        return stmt.executeQuery();
    }

    /**
     * Select method with order type
     * @param fields field array
     * @param table name
     * @param order ASC or DESC etc.
     * @param type Upwards or downwards
     * @return ResultSet
     * @throws SQLException if select fails
     */
    public static ResultSet selectOrderBy(String[] fields, String table, String order, String type) throws SQLException {
        PreparedStatement stmt = prepareStatement("SELECT " + Arrays.toString(fields).replace("[", "")
                .replace("]", "") + " FROM " + table + " ORDER BY " + order + " " + type);
        return stmt.executeQuery();
    }

    /**
     * Update method with param
     * @param fields field array
     * @param table name
     * @param where param
     * @param values object array
     * @throws SQLException if update fails
     */
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
