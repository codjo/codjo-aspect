/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
import java.util.List;
/**
 * Implémentation de SqlAspectBehaviour initialisée à partir de tableaux
 *
 * @version $Revision: 1.2 $
 */
public class DefaultSqlAspectBehaviour implements SqlAspectBehaviour {
    private AspectSqlData sqlData = new AspectSqlData();

    public String[] getSqlForSetUp() {
        List<String> allSqlForSetUp = sqlData.getAllSqlForSetUp();
        return allSqlForSetUp.toArray(new String[allSqlForSetUp.size()]);
    }


    public String[] getSqlCallForRun() {
        List<String> allSqlForRun = sqlData.getAllSqlForRun();
        return allSqlForRun.toArray(new String[allSqlForRun.size()]);
    }


    public String[] getSqlForCleanUp() {
        List<String> allSqlForCleanUp = sqlData.getAllSqlForCleanUp();
        return allSqlForCleanUp.toArray(new String[allSqlForCleanUp.size()]);
    }


    public void setTemporaryTables(Table[] tables) {
        for (Table table : tables) {
            sqlData.addTable(table);
        }
    }


    public void setSqlForSetUp(String[] sqls) {
        sqlData.setSqlForSetUp(sqls);
    }


    public void setSqlForRun(String[] sqls) {
        sqlData.setSqlForRun(sqls);
    }


    public void setSqlForCleanUp(String[] sqls) {
        sqlData.setSqlForCleanUp(sqls);
    }
}
