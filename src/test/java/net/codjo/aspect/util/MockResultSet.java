/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
/**
 * TODO.
 *
 * @version $Revision: 1.1 $
 */
public class MockResultSet implements ResultSet {
    private StringBuffer executeList;


    public MockResultSet(StringBuffer buf) {
        this.executeList = buf;
    }


    public boolean next() throws SQLException {
        append(" next ");
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void close() throws SQLException {
        append(" close ");
    }


    public boolean wasNull() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getString(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean getBoolean(int i) throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public byte getByte(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public short getShort(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getInt(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public long getLong(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public float getFloat(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public double getDouble(int i) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public BigDecimal getBigDecimal(int i, int i1)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public byte[] getBytes(int i) throws SQLException {
        return new byte[0]; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Date getDate(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Time getTime(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Timestamp getTimestamp(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getAsciiStream(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getUnicodeStream(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getBinaryStream(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getString(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean getBoolean(String s) throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public byte getByte(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public short getShort(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getInt(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public long getLong(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public float getFloat(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public double getDouble(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public BigDecimal getBigDecimal(String s, int i)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public byte[] getBytes(String s) throws SQLException {
        return new byte[0]; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Date getDate(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Time getTime(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Timestamp getTimestamp(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getAsciiStream(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getUnicodeStream(String s)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public InputStream getBinaryStream(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public SQLWarning getWarnings() throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void clearWarnings() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getCursorName() throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Object getObject(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Object getObject(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int findColumn(String s) throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Reader getCharacterStream(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Reader getCharacterStream(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public BigDecimal getBigDecimal(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public BigDecimal getBigDecimal(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isBeforeFirst() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isAfterLast() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isFirst() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isLast() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void beforeFirst() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void afterLast() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean first() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean last() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getRow() throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean absolute(int i) throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean relative(int i) throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean previous() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void setFetchDirection(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getFetchDirection() throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void setFetchSize(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getFetchSize() throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getType() throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getConcurrency() throws SQLException {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean rowUpdated() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean rowInserted() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean rowDeleted() throws SQLException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateNull(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBoolean(int i, boolean b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateByte(int i, byte b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateShort(int i, short i1) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateInt(int i, int i1) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateLong(int i, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateFloat(int i, float v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateDouble(int i, double v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBigDecimal(int i, BigDecimal bigDecimal)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateString(int i, String s) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBytes(int i, byte[] bytes) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateDate(int i, Date date) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateTime(int i, Time time) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateTimestamp(int i, Timestamp timestamp)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateAsciiStream(int i, InputStream inputStream, int i1)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBinaryStream(int i, InputStream inputStream, int i1)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateCharacterStream(int i, Reader reader, int i1)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateObject(int i, Object o, int i1)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateObject(int i, Object o) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateNull(String s) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBoolean(String s, boolean b)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateByte(String s, byte b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateShort(String s, short i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateInt(String s, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateLong(String s, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateFloat(String s, float v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateDouble(String s, double v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBigDecimal(String s, BigDecimal bigDecimal)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateString(String s, String s1)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBytes(String s, byte[] bytes)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateDate(String s, Date date) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateTime(String s, Time time) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateTimestamp(String s, Timestamp timestamp)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateAsciiStream(String s, InputStream inputStream, int i)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateBinaryStream(String s, InputStream inputStream, int i)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateCharacterStream(String s, Reader reader, int i)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateObject(String s, Object o, int i)
          throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateObject(String s, Object o) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void insertRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void deleteRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void refreshRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void cancelRowUpdates() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void moveToInsertRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void moveToCurrentRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public Statement getStatement() throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Ref getRef(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Blob getBlob(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Clob getClob(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Array getArray(int i) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Ref getRef(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Blob getBlob(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Clob getClob(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Array getArray(String s) throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Date getDate(int i, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Date getDate(String s, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Time getTime(int i, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Time getTime(String s, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Timestamp getTimestamp(int i, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    public Timestamp getTimestamp(String s, Calendar calendar)
          throws SQLException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }


    private void append(String str) {
        if (executeList.length() > 0) {
            executeList.append(", ");
        }
        executeList.append(" RS ").append(str);
    }


    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return null;  // Todo
    }


    public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
        return null;  // Todo
    }


    public URL getURL(int columnIndex) throws SQLException {
        return null;  // Todo
    }


    public URL getURL(String columnName) throws SQLException {
        return null;  // Todo
    }


    public void updateRef(int columnIndex, Ref x) throws SQLException {
        // Todo
    }


    public void updateRef(String columnName, Ref x) throws SQLException {
        // Todo
    }


    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        // Todo
    }


    public void updateBlob(String columnName, Blob x) throws SQLException {
        // Todo
    }


    public void updateClob(int columnIndex, Clob x) throws SQLException {
        // Todo
    }


    public void updateClob(String columnName, Clob x) throws SQLException {
        // Todo
    }


    public void updateArray(int columnIndex, Array x) throws SQLException {
        // Todo
    }


    public void updateArray(String columnName, Array x) throws SQLException {
        // Todo
    }
}
