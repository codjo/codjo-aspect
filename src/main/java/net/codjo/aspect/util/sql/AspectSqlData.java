/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Données utilisées par un {@link net.codjo.aspect.util.sql.AbstractBasicSqlAspect}
 *
 * @version $Revision: 1.1 $
 */
class AspectSqlData {
    private List<Table> tables = new ArrayList<Table>();
    private List<String> sqlForSetUp = new ArrayList<String>();
    private List<String> sqlForRun = new ArrayList<String>();
    private List<String> sqlForCleanUp = new ArrayList<String>();


    void addTable(Table table) {
        tables.add(table);
    }


    void addSqlForSetUp(String sql) {
        sqlForSetUp.add(sql);
    }


    void addSqlForRun(String sql) {
        sqlForRun.add(sql);
    }


    void addSqlForCleanUp(String sql) {
        sqlForCleanUp.add(sql);
    }


    List<String> getAllSqlForSetUp() {
        List<String> list = new ArrayList<String>();

        for (Table table : tables) {
            list.add(table.buildSqlCreate());
        }

        list.addAll(sqlForSetUp);

        return Collections.unmodifiableList(list);
    }


    List<String> getAllSqlForRun() {
        return Collections.unmodifiableList(sqlForRun);
    }


    List<String> getAllSqlForCleanUp() {
        List<String> list = new ArrayList<String>();

        list.addAll(sqlForCleanUp);

        for (Table table : tables) {
            list.add(table.buildSqlDrop());
        }

        return Collections.unmodifiableList(list);
    }


    void setSqlForSetUp(String[] sqls) {
        sqlForSetUp.clear();
        for (String sql : sqls) {
            addSqlForSetUp(sql);
        }
    }


    void setSqlForRun(String[] sqls) {
        sqlForRun.clear();
        for (String sql : sqls) {
            addSqlForRun(sql);
        }
    }


    void setSqlForCleanUp(String[] sqls) {
        sqlForCleanUp.clear();
        for (String sql : sqls) {
            addSqlForCleanUp(sql);
        }
    }
}
