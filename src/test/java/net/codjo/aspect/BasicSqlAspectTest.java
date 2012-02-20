/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import junit.framework.TestCase;
import net.codjo.aspect.util.TransactionalPoint;
import net.codjo.test.common.LogString;
import net.codjo.test.common.mock.CallableStatementMock;
import net.codjo.test.common.mock.ConnectionMock;
/**
 * Test de {@link AbstractTestBasicSqlAspect}
 *
 * @version $Revision: 1.3 $
 */
public class BasicSqlAspectTest extends TestCase {
    private AspectManager manager;
    private LogString log = new LogString();


    private AspectContext launchAspect(Aspect aspect)
          throws AspectException {
        AspectContext context = new AspectContext();
        StringBuffer sb = new StringBuffer();
        context.put(AbstractTestBasicSqlAspect.CALL_HISTORY, log);
        ConnectionMock connection = new ConnectionMock(new LogString("connection", log));
        connection.mockCreateStatement(new CallableStatementMock(log));
        context.put("control.table", "#TABLE_TEST");
        context.put(TransactionalPoint.CONNECTION, connection);
        aspect.setUp(context, null);
        aspect.run(context);
        aspect.cleanUp(context);
        return context;
    }


    public void test_getBeforeAspect_compute() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("handler.execute", "testSql");

        launchAspect(aspect);

        log.assertContent("connection.createStatement(), statement.executeUpdate(create table A (id int)), statement.close()"
                          + ", connection.createStatement(), statement.executeUpdate(create table B (id int)), statement.close()"
                          + ", connection.createStatement(), statement.executeUpdate(select * from #TABLE_TEST), statement.close()"

                          + ", net.codjo.aspect.AbstractTestBasicSqlAspect. doSetUp ()"

                          + ", net.codjo.aspect.AbstractTestBasicSqlAspect. run before Sql ()"
                          + ", connection.prepareCall({call sp_test}), statement.executeUpdate(), statement.close()"
                          + ", net.codjo.aspect.AbstractTestBasicSqlAspect. run after Sql ()"

                          + ", net.codjo.aspect.AbstractTestBasicSqlAspect. doCleanUp ()"

                          + ", connection.createStatement(), statement.executeUpdate(drop table A), statement.close()"
                          + ", connection.createStatement(), statement.executeUpdate(drop table B), statement.close()");
    }


    private void load() throws AspectConfigException {
        manager.load(AspectManagerTest.class.getResourceAsStream("Aspects.xml"));
    }


    @Override
    protected void setUp() throws Exception {
        manager = new AspectManager();
    }
}
