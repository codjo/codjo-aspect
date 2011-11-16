/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
public class Table {
    private String name;
    private String fields;


    public Table(String name, String fields) {
        this.name = name;
        this.fields = fields;
    }


    public String getName() {
        return name;
    }


    public String buildSqlCreate() {
        return new StringBuilder("create table ").append(name).append(" (").append(fields).append(")")
              .toString();
    }


    public String buildSqlDrop() {
        return "drop table " + name;
    }
}
