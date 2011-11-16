/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.aspect.util.MockConnection;
import net.codjo.aspect.util.TransactionalPoint;
import junit.framework.TestCase;
/**
 * Test de {@link AbstractTestBasicSqlAspect}
 *
 * @version $Revision: 1.3 $
 */
public class BasicSqlAspectTest extends TestCase {
    private AspectManager manager;


    private AspectContext launchAspect(Aspect aspect)
          throws AspectException {
        AspectContext context = new AspectContext();
        StringBuffer sb = new StringBuffer();
        context.put(AbstractTestBasicSqlAspect.CALL_HISTORY, sb);
        MockConnection connection = new MockConnection();
        connection.setStringBuffer(sb);
        context.put("control.table", "#TABLE_TEST");
        context.put(TransactionalPoint.CONNECTION, connection);
        aspect.setUp(context, null);
        aspect.run(context);
        aspect.cleanUp(context);
        return context;
    }


    private void assertHistory(AspectContext context, String expected) {
        assertEquals(expected,
                     context.get(AbstractTestBasicSqlAspect.CALL_HISTORY).toString());
    }


    public void test_getBeforeAspect_compute() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("handler.execute", "testSql");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      " CO  create statement ,  ST  execute update {create table A (id int)} ,  ST  close ,  CO  create statement ,  ST  execute update {create table B (id int)} ,  ST  close ,  CO  create statement ,  ST  execute update {select * from #TABLE_TEST} ,  ST  close net.codjo.aspect.AbstractTestBasicSqlAspect. doSetUp () net.codjo.aspect.AbstractTestBasicSqlAspect. run before Sql () ,  CO  prepare call : {{call sp_test}} ,  execute update net.codjo.aspect.AbstractTestBasicSqlAspect. run after Sql () net.codjo.aspect.AbstractTestBasicSqlAspect. doCleanUp () ,  CO  create statement ,  ST  execute update {drop table A} ,  ST  close ,  CO  create statement ,  ST  execute update {drop table B} ,  ST  close ");
    }


    private void load() throws AspectConfigException {
        manager.load(AspectManagerTest.class.getResourceAsStream("Aspects.xml"));
    }


    @Override
    protected void setUp() throws Exception {
        manager = new AspectManager();
    }
}
