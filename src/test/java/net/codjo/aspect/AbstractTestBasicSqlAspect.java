/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.sql.Connection;
import net.codjo.aspect.util.sql.AbstractBasicSqlAspect;
import net.codjo.aspect.util.sql.DefaultSqlAspectBehaviour;
import net.codjo.aspect.util.sql.SqlAspectBehaviour;
import net.codjo.aspect.util.sql.Table;
import net.codjo.test.common.LogString;
/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.4 $
 */
public class AbstractTestBasicSqlAspect extends AbstractBasicSqlAspect {
    static final String CALL_HISTORY = "callHistory";
    private DefaultSqlAspectBehaviour testSqlAspectBehaviour;


    @Override
    protected SqlAspectBehaviour getSqlBehaviour() {
        if (testSqlAspectBehaviour == null) {
            testSqlAspectBehaviour = new DefaultSqlAspectBehaviour();
            testSqlAspectBehaviour.setTemporaryTables(new Table[]{
                  new Table("A", "id int"), new Table("B", "id int"),
            });

            testSqlAspectBehaviour.setSqlForSetUp(new String[]{
                  "select * from $control.table$"
            });
            testSqlAspectBehaviour.setSqlForRun(new String[]{"{call sp_test}"});
        }
        return testSqlAspectBehaviour;
    }


    private void logCall(AspectContext context, String methodName) {
        Object logger = context.get(CALL_HISTORY);
        ((LogString)logger).call(getClass().getName() + "." + methodName);
    }


    @Override
    protected void doSetUp(AspectContext context, JoinPoint joinPoint, Connection con)
          throws AspectException {
        logCall(context, " doSetUp ");
    }


    @Override
    protected void doRunBeforeSql(AspectContext context, Connection con) throws AspectException {
        logCall(context, " run before Sql ");
    }


    @Override
    protected void doRunAfterSql(AspectContext context, Connection con) throws AspectException {
        logCall(context, " run after Sql ");
    }


    @Override
    protected void doCleanUp(AspectContext context, Connection con) {
        logCall(context, " doCleanUp ");
    }


    //permet de dire que l'on ne passe qu'une fois dans un aspect par appel
    @Override
    protected boolean isOneShotAspect() {
        return false;
    }
}
