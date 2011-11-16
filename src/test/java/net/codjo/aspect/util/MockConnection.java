/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;
/**
 * Mock de la connection pour tester les transactions.
 */
public class MockConnection implements Connection {
    private boolean autoCommit = true;
    private StringBuffer executeList = new StringBuffer();


    public Statement createStatement()
          throws SQLException {
        append(" create statement ");
        return new MockStatement(this.executeList);
    }


    public void setStringBuffer(StringBuffer sb) {
        this.executeList = sb;
    }


    public PreparedStatement prepareStatement(String sql,
                                              int resultSetType,
                                              int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        append(" prepare statement ");
        return null;
    }


    public CallableStatement prepareCall(String sql)
          throws SQLException {
        append(" prepare call : {" + sql + "} ");
        return new MockCallableStatement(this.executeList);
    }


    public String nativeSQL(String sql) throws SQLException {
        append(" native sql {" + sql + "} ");
        return null;
    }


    public void setAutoCommit(boolean newAutoCommit)
          throws SQLException {
        append("setAutoCommit(" + newAutoCommit + ")");
        autoCommit = newAutoCommit;
    }


    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }


    public void commit() throws SQLException {
        append("commit");
    }


    public void rollback(Savepoint savepoint) throws SQLException {
        append("rollback");
    }


    public void close() throws SQLException {
        append(" close ");
    }


    public boolean isClosed() throws SQLException {
        return false;
    }


    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }


    public void setReadOnly(boolean readOnly) throws SQLException {
    }


    public boolean isReadOnly() throws SQLException {
        return false;
    }


    public void setCatalog(String catalog) throws SQLException {
    }


    public String getCatalog() throws SQLException {
        return null;
    }


    public void setTransactionIsolation(int level)
          throws SQLException {
    }


    public int getTransactionIsolation() throws SQLException {
        return 0;
    }


    public SQLWarning getWarnings() throws SQLException {
        return null;
    }


    public void clearWarnings() throws SQLException {
        append(" clear warnings ");
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
          throws SQLException {
        append(" create statement {" + resultSetType + "," + resultSetConcurrency + "," + resultSetHoldability
               + "} ");
        return null;  // Todo
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency)
          throws SQLException {
        append(" create statement {" + resultSetType + "," + resultSetConcurrency + "} ");
        return null;
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType,
                                              int resultSetConcurrency) throws SQLException {
        append(" create perparedstatement {[" + sql + "]," + resultSetType + ","
               + resultSetConcurrency + "} ");
        return null;
    }


    public CallableStatement prepareCall(String sql, int resultSetType,
                                         int resultSetConcurrency) throws SQLException {
        append(" prepare call {[" + sql + "]," + resultSetType + ","
               + resultSetConcurrency + "} ");
        return null;
    }


    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }


    public String getExecuteList() {
        return executeList.toString();
    }


    private void append(String str) {
        if (executeList.length() > 0) {
            executeList.append(", ");
        }
        executeList.append(" CO ").append(str);
    }


    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;  // Todo
    }


    public void rollback() throws SQLException {
        append("rollback");
    }


    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        // Todo
    }


    public void setHoldability(int holdability) throws SQLException {
        // Todo
    }


    public int getHoldability() throws SQLException {
        return 0;  // Todo
    }


    public Savepoint setSavepoint() throws SQLException {
        return null;  // Todo
    }


    public Savepoint setSavepoint(String name) throws SQLException {
        return null;  // Todo
    }


    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        // Todo
    }


    public CallableStatement prepareCall(String sql,
                                         int resultSetType,
                                         int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        return null;  // Todo
    }


    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;  // Todo
    }


    public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
        return null;  // Todo
    }


    public PreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
        return null;  // Todo
    }
}
