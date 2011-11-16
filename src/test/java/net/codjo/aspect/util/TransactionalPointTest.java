/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import net.codjo.aspect.Aspect;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.AspectException;
import net.codjo.aspect.AspectManager;
import net.codjo.aspect.JoinPoint;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TransactionalPointTest {
    private static final String POINT_ID = "pointId";
    private static final String CURRENT_ARG = "currentArgument";
    private static MyTransactionalManager txManager;
    private static StringBuffer globalExecuteList;
    private TransactionalPoint transactionalPoint;
    private AspectContext aspectContext;
    private MockConnection con;
    private AspectManager manager;
    private MyPointRunner myPointRunner;


    @Before
    public void setUp() throws Exception {
        globalExecuteList = new StringBuffer();

        txManager = new MyTransactionalManager();
        aspectContext = new AspectContext();
        con = new MockConnection();

        aspectContext.put(TransactionalPoint.CONNECTION, con);
        aspectContext.put(TransactionalPoint.ARGUMENT, CURRENT_ARG);

        manager = new AspectManager();
        transactionalPoint = new TransactionalPoint(POINT_ID, manager);

        myPointRunner = new MyPointRunner();
        FakeBeforeAspect.failInSetup = false;
        FakeBeforeAspect.failInRun = false;
    }


    @Test
    public void test_aspect_multipleJoinPoint() throws Exception {
        // Aspect
        manager.addAspect("aspectJPa",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, "argA"), FakeBeforeAspect.class);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, "argB"), FakeAfterAspect.class);

        // Appel
        aspectContext.put(TransactionalPoint.ARGUMENT, new String[]{"argA", "argB"});

        transactionalPoint.run(aspectContext, myPointRunner);

        // Verification
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeAfterAspect.executeList.toString());
    }


    @Test
    public void test_aspect_ok() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, CURRENT_ARG),
                          FakeAfterAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Verification
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeAfterAspect.executeList.toString());
        Assert.assertSame("Le bon contexte est passé aux Aspects", aspectContext,
                          FakeBeforeAspect.aspectCtxt);

        assertEquals("FakeBeforeAspect.run FakeAfterAspect.run ",
                     globalExecuteList.toString());
    }


    @Test
    public void test_transaction_ok() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Assert sur aspect
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertFalse("SetUp pas en transaction", FakeBeforeAspect.isSetupInTx);
        assertTrue("Run pas en transaction", FakeBeforeAspect.isRunInTx);
        assertFalse("CleanUp pas en transaction", FakeBeforeAspect.isCleanUpInTx);

        // Assert sur pointRunner
        assertTrue("Runner appelé", myPointRunner.runHasBeenCalled);
        assertTrue("Runner appelé en Tx", myPointRunner.runInTx);
    }


    @Test
    public void test_transaction_flush() throws Exception {
        // Aspect
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, CURRENT_ARG),
                          FakeAfterAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Assert sur aspect
        assertTrue("Run après le flush", FakeAfterAspect.isRunAfterFlush);
    }


    @Test
    public void test_transaction_userDefined_ok() throws Exception {
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);

        transactionalPoint.run(aspectContext, myPointRunner);

        assertEquals("begin, flush, commit, end", txManager.executeList.toString());
    }


    @Test
    public void test_argument_userNotDefined_ok() throws Exception {
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);

        aspectContext.put(TransactionalPoint.ARGUMENT, null);

        transactionalPoint.run(aspectContext, myPointRunner);

        assertEquals("begin, flush, commit, end", txManager.executeList.toString());
    }


    @Test
    public void test_context_connection_nok() throws Exception {
        // Appel
        AspectContext emptyContext = new AspectContext();
        try {
            transactionalPoint.run(emptyContext, myPointRunner);
            fail("Echec : context vide");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Objet manquant dans le context sous la clé : "
                         + TransactionalPoint.CONNECTION, e.getMessage());
        }
    }


    /**
     * Test le cas d'echec à l'init d'un aspect.
     *
     * @throws Exception erreur.
     */
    @Test
    public void test_fail_init() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        FakeBeforeAspect.failInSetup = true;
        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant l'init");
        }
        catch (AspectException e) {
            assertEquals("failInSetup", e.getMessage());
        }

        assertEquals("setUp, end", FakeBeforeAspect.executeList.toString());
        assertEquals("Rien n'est pas appelé sur la connection", "", con.getExecuteList());
    }


    /**
     * Test le cas d'echec durant l'execution d'un aspect.
     *
     * @throws Exception erreur.
     */
    @Test
    public void test_fail_run() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);
        manager.addAspect("aspectError",
                          newJoinPoint(JoinPoint.CALL_ERROR, POINT_ID, CURRENT_ARG),
                          FakeErrorAspect.class);

        // Appel
        FakeBeforeAspect.failInRun = true;
        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant le run");
        }
        catch (AspectException e) {
            assertEquals("failInRun", e.getMessage());
        }

        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeErrorAspect.executeList.toString());
        assertEquals(
              " CO setAutoCommit(false),  CO rollback,  CO setAutoCommit(true),  CO setAutoCommit(true)",
              con.getExecuteList());
    }


    /**
     * Test le cas d'echec durant l'execution d'un aspect.
     *
     * @throws Exception    erreur.
     * @throws SQLException erreur
     */
    @Test
    public void test_fail_runAndRollBack() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        FakeBeforeAspect.failInRun = true;
        con = new MockConnection() {
            @Override
            public void rollback() throws SQLException {
                super.rollback();
                throw new SQLException("Erreur rollback");
            }
        };
        aspectContext.put(TransactionalPoint.CONNECTION, con);

        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant l'init");
        }
        catch (TransactionException e) {
            assert (e.getMessage().contains("java.sql.SQLException: Erreur rollback"));
        }

        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals(" CO setAutoCommit(false),  CO rollback,  CO setAutoCommit(true)",
                     con.getExecuteList());
    }


    private JoinPoint[] newJoinPoint(int call, String point, String argument) {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setArgument(argument);
        joinPoint.setPoint(point);
        joinPoint.setCall(call);
        return new JoinPoint[]{joinPoint};
    }


    public static class FakeBeforeAspect implements Aspect {
        static StringBuffer executeList;
        static AspectContext aspectCtxt;
        static boolean isSetupInTx;
        static boolean isRunInTx;
        static boolean isCleanUpInTx;
        static boolean failInSetup = false;
        static boolean failInRun = false;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
            aspectCtxt = context;
            isSetupInTx = isInTx(context);
            if (failInSetup) {
                throw new AspectException("failInSetup");
            }
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
            isRunInTx = isInTx(context);
            globalExecuteList.append("FakeBeforeAspect.run ");
            if (failInRun) {
                throw new AspectException("failInRun");
            }
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
            isCleanUpInTx = isInTx(context);
        }


        private boolean isInTx(AspectContext context) {
            Connection con = (Connection)context.get(TransactionalPoint.CONNECTION);
            boolean autoCommit = false;
            try {
                autoCommit = con.getAutoCommit();
            }
            catch (SQLException e) {
                //
            }
            return !autoCommit;
        }
    }

    public static class FakeAfterAspect implements Aspect {
        static StringBuffer executeList;
        static boolean isRunAfterFlush = false;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
            globalExecuteList.append("FakeAfterAspect.run ");
            isRunAfterFlush = txManager.isFlushCalled();
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
        }
    }

    public static class FakeErrorAspect implements Aspect {
        static StringBuffer executeList;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
        }
    }

    public class MyPointRunner implements PointRunner {
        boolean runHasBeenCalled;
        boolean runInTx;


        public void run() throws PointRunnerException {
            runHasBeenCalled = true;
            try {
                runInTx = !con.getAutoCommit();
            }
            catch (SQLException e) {
                throw new InternalError("Erreur " + e);
            }
        }
    }

    public static class MyTransactionalManager implements TransactionalManager {
        StringBuffer executeList = new StringBuffer();


        public void begin(AspectContext ctxt) {
            executeList.append("begin");
        }


        public void flush(AspectContext ctxt) {
            executeList.append(", flush");
        }


        public void commit(AspectContext ctxt) {
            executeList.append(", commit");
        }


        public void rollback(AspectContext ctxt) {
            executeList.append(", rollback");
        }


        public void end(AspectContext ctxt) {
            executeList.append(", end");
        }


        public boolean isFlushCalled() {
            return executeList.toString().contains("flush");
        }
    }
}
