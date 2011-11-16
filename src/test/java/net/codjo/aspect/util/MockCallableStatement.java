/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.ParameterMetaData;
import java.util.Calendar;
import java.util.Map;
import java.net.URL;
/**
 * TODO.
 *
 * @version $Revision: 1.2 $
 */
public class MockCallableStatement implements CallableStatement {
    private StringBuffer executeList;

    public MockCallableStatement(StringBuffer s) {
        this.executeList = s;
    }

    private void append(String str) {
        if (executeList.length() > 0) {
            executeList.append(", ");
        }
        executeList.append(str);
    }


    public void registerOutParameter(int i, int i1)
            throws SQLException {}


    public void registerOutParameter(int i, int i1, int i2)
            throws SQLException {}


    public boolean wasNull() throws SQLException {
        return false;
    }


    public String getString(int i) throws SQLException {
        return null;
    }


    public boolean getBoolean(int i) throws SQLException {
        return false;
    }


    public byte getByte(int i) throws SQLException {
        return 0;
    }


    public short getShort(int i) throws SQLException {
        return 0;
    }


    public int getInt(int i) throws SQLException {
        return 0;
    }


    public long getLong(int i) throws SQLException {
        return 0;
    }


    public float getFloat(int i) throws SQLException {
        return 0;
    }


    public double getDouble(int i) throws SQLException {
        return 0;
    }


    public BigDecimal getBigDecimal(int i, int i1)
            throws SQLException {
        return null;
    }


    public byte[] getBytes(int i) throws SQLException {
        return new byte[0];
    }


    public Date getDate(int i) throws SQLException {
        return null;
    }


    public Time getTime(int i) throws SQLException {
        return null;
    }


    public Timestamp getTimestamp(int i) throws SQLException {
        return null;
    }


    public Object getObject(int i) throws SQLException {
        return null;
    }


    public BigDecimal getBigDecimal(int i) throws SQLException {
        return null;
    }

    public Ref getRef(int i) throws SQLException {
        return null;
    }


    public Blob getBlob(int i) throws SQLException {
        return null;
    }


    public Clob getClob(int i) throws SQLException {
        return null;
    }


    public Array getArray(int i) throws SQLException {
        return null;
    }


    public Date getDate(int i, Calendar calendar)
            throws SQLException {
        return null;
    }


    public Time getTime(int i, Calendar calendar)
            throws SQLException {
        return null;
    }


    public Timestamp getTimestamp(int i, Calendar calendar)
            throws SQLException {
        return null;
    }


    public void registerOutParameter(int i, int i1, String s)
            throws SQLException {}


    public ResultSet executeQuery() throws SQLException {
        append(" execute query ");
        return new MockResultSet(this.executeList);
    }


    public int executeUpdate() throws SQLException {
        append(" execute update ");
        return 0;
    }


    public void setNull(int i, int i1) throws SQLException {}


    public void setBoolean(int i, boolean b) throws SQLException {}


    public void setByte(int i, byte b) throws SQLException {}


    public void setShort(int i, short i1) throws SQLException {}


    public void setInt(int i, int i1) throws SQLException {}


    public void setLong(int i, long l) throws SQLException {}


    public void setFloat(int i, float v) throws SQLException {}


    public void setDouble(int i, double v) throws SQLException {}


    public void setBigDecimal(int i, BigDecimal bigDecimal)
            throws SQLException {}


    public void setString(int i, String s) throws SQLException {}


    public void setBytes(int i, byte[] bytes) throws SQLException {}


    public void setDate(int i, Date date) throws SQLException {}


    public void setTime(int i, Time time) throws SQLException {}


    public void setTimestamp(int i, Timestamp timestamp)
            throws SQLException {}


    public void setAsciiStream(int i, InputStream inputStream, int i1)
            throws SQLException {}


    public void setUnicodeStream(int i, InputStream inputStream, int i1)
            throws SQLException {}


    public void setBinaryStream(int i, InputStream inputStream, int i1)
            throws SQLException {}


    public void clearParameters() throws SQLException {}


    public void setObject(int i, Object o, int i1, int i2)
            throws SQLException {}


    public void setObject(int i, Object o, int i1)
            throws SQLException {}


    public void setObject(int i, Object o) throws SQLException {}


    public boolean execute() throws SQLException {
        return false;
    }


    public void addBatch() throws SQLException {}


    public void setCharacterStream(int i, Reader reader, int i1)
            throws SQLException {}


    public void setRef(int i, Ref ref) throws SQLException {}


    public void setBlob(int i, Blob blob) throws SQLException {}


    public void setClob(int i, Clob clob) throws SQLException {}


    public void setArray(int i, Array array) throws SQLException {}


    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }


    public void setDate(int i, Date date, Calendar calendar)
            throws SQLException {}


    public void setTime(int i, Time time, Calendar calendar)
            throws SQLException {}


    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar)
            throws SQLException {}


    public void setNull(int i, int i1, String s) throws SQLException {}


    public ResultSet executeQuery(String s) throws SQLException {
        return null;
    }


    public int executeUpdate(String s) throws SQLException {
        return 0;
    }


    public void close() throws SQLException {}


    public int getMaxFieldSize() throws SQLException {
        return 0;
    }


    public void setMaxFieldSize(int i) throws SQLException {}


    public int getMaxRows() throws SQLException {
        return 0;
    }


    public void setMaxRows(int i) throws SQLException {}


    public void setEscapeProcessing(boolean b) throws SQLException {}


    public int getQueryTimeout() throws SQLException {
        return 0;
    }


    public void setQueryTimeout(int i) throws SQLException {}


    public void cancel() throws SQLException {}


    public SQLWarning getWarnings() throws SQLException {
        return null;
    }


    public void clearWarnings() throws SQLException {}


    public void setCursorName(String s) throws SQLException {}


    public boolean execute(String s) throws SQLException {
        return false;
    }


    public ResultSet getResultSet() throws SQLException {
        return null;
    }


    public int getUpdateCount() throws SQLException {
        return 0;
    }


    public boolean getMoreResults() throws SQLException {
        return false;
    }


    public void setFetchDirection(int i) throws SQLException {}


    public int getFetchDirection() throws SQLException {
        return 0;
    }


    public void setFetchSize(int i) throws SQLException {}


    public int getFetchSize() throws SQLException {
        return 0;
    }


    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }


    public int getResultSetType() throws SQLException {
        return 0;
    }


    public void addBatch(String s) throws SQLException {}


    public void clearBatch() throws SQLException {}


    public int[] executeBatch() throws SQLException {
        return new int[0];
    }


    public Connection getConnection() throws SQLException {
        return null;
    }


    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return null;  // Todo
    }


    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        // Todo
    }


    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        // Todo
    }


    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        // Todo
    }


    public URL getURL(int parameterIndex) throws SQLException {
        return null;  // Todo
    }


    public void setURL(String parameterName, URL val) throws SQLException {
        // Todo
    }


    public void setNull(String parameterName, int sqlType) throws SQLException {
        // Todo
    }


    public void setBoolean(String parameterName, boolean x) throws SQLException {
        // Todo
    }


    public void setByte(String parameterName, byte x) throws SQLException {
        // Todo
    }


    public void setShort(String parameterName, short x) throws SQLException {
        // Todo
    }


    public void setInt(String parameterName, int x) throws SQLException {
        // Todo
    }


    public void setLong(String parameterName, long x) throws SQLException {
        // Todo
    }


    public void setFloat(String parameterName, float x) throws SQLException {
        // Todo
    }


    public void setDouble(String parameterName, double x) throws SQLException {
        // Todo
    }


    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        // Todo
    }


    public void setString(String parameterName, String x) throws SQLException {
        // Todo
    }


    public void setBytes(String parameterName, byte x[]) throws SQLException {
        // Todo
    }


    public void setDate(String parameterName, Date x) throws SQLException {
        // Todo
    }


    public void setTime(String parameterName, Time x) throws SQLException {
        // Todo
    }


    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        // Todo
    }


    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        // Todo
    }


    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        // Todo
    }


    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        // Todo
    }


    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        // Todo
    }


    public void setObject(String parameterName, Object x) throws SQLException {
        // Todo
    }


    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        // Todo
    }


    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        // Todo
    }


    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        // Todo
    }


    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        // Todo
    }


    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        // Todo
    }


    public String getString(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public boolean getBoolean(String parameterName) throws SQLException {
        return false;  // Todo
    }


    public byte getByte(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public short getShort(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public int getInt(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public long getLong(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public float getFloat(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public double getDouble(String parameterName) throws SQLException {
        return 0;  // Todo
    }


    public byte[] getBytes(String parameterName) throws SQLException {
        return new byte[0];  // Todo
    }


    public Date getDate(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Time getTime(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Object getObject(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return null;  // Todo
    }


    public Ref getRef(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Blob getBlob(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Clob getClob(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Array getArray(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return null;  // Todo
    }


    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return null;  // Todo
    }


    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return null;  // Todo
    }


    public URL getURL(String parameterName) throws SQLException {
        return null;  // Todo
    }


    public void setURL(int parameterIndex, URL x) throws SQLException {
        // Todo
    }


    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;  // Todo
    }


    public boolean getMoreResults(int current) throws SQLException {
        return false;  // Todo
    }


    public ResultSet getGeneratedKeys() throws SQLException {
        return null;  // Todo
    }


    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;  // Todo
    }


    public int executeUpdate(String sql, int columnIndexes[]) throws SQLException {
        return 0;  // Todo
    }


    public int executeUpdate(String sql, String columnNames[]) throws SQLException {
        return 0;  // Todo
    }


    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;  // Todo
    }


    public boolean execute(String sql, int columnIndexes[]) throws SQLException {
        return false;  // Todo
    }


    public boolean execute(String sql, String columnNames[]) throws SQLException {
        return false;  // Todo
    }


    public int getResultSetHoldability() throws SQLException {
        return 0;  // Todo
    }
}
