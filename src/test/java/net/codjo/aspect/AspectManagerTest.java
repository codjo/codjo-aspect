/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.test.common.LogString;
import junit.framework.TestCase;
/**
 * Classe de test de {@link AspectManager}.
 */
@SuppressWarnings({"OverlyCoupledClass"})
public class AspectManagerTest extends TestCase {
    private AspectManager manager;


    public void test_getBeforeAspect_report() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("control.dispatch", "Q_AP_FUND_PRICE");

        AspectContext context = launchAspect(aspect);
        assertEquals(
              "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() ",
              (String)context.get(AbstractTestAspect.CALL_HISTORY));
    }


    public void test_getBeforeAspect_compute() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("control.dispatch", "Q_AP_IDEE");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    public void test_getErrorAspect_compute() throws Exception {
        load();
        Aspect aspect = manager.getErrorAspect("with.error", "TOTO");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    public void test_getBeforeAspect_unknown() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("unknown", "unknownArg");
        AspectContext context = launchAspect(aspect);
        assertHistory(context, null);
    }


    public void test_getAfterAspect_no_argument() throws Exception {
        load();
        Aspect aspect = manager.getAfterAspect("securityReports", null);
        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    public void test_getAfterAspect_report() throws Exception {
        load();
        Aspect aspect = manager.getAfterAspect("handler.execute", "newVL");
        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    public void test_getAfterAspect_unknown() throws Exception {
        load();
        Aspect aspect = manager.getAfterAspect("unknown", "unknownArg");
        AspectContext context = launchAspect(aspect);
        assertHistory(context, null);
    }


    public void test_getAfterAspect_byAdd() throws Exception {
        JoinPoint point1 = new JoinPoint(JoinPoint.CALL_AFTER, "control.dispatch", "Q_AP_FUND_PRICE");
        JoinPoint point2 = new JoinPoint(JoinPoint.CALL_BEFORE, "control.dispatch", "Q_AP_IDEE");

        manager.addAspect("ReportId", new JoinPoint[]{point1, point2}, ReportAspect.class);
        Aspect aspect = manager.getAfterAspect("control.dispatch", "Q_AP_FUND_PRICE");
        AspectContext context = launchAspect(aspect);
        assertEquals(
              "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ReportAspect.run() net.codjo.aspect.ReportAspect.cleanUp() ",
              (String)context.get(AbstractTestAspect.CALL_HISTORY));
    }


    public void test_load_ko() throws Exception {
        manager = new AspectManager();
        try {
            manager.load(AspectManagerTest.class.getResourceAsStream("BadAspects.xml"));
            fail("Le chargement de ce fichier XML doit échouer.");
        }
        catch (AspectConfigException e) {
            assertNotNull(e.getCause());
            assertFalse(e.getCause() instanceof InternalLoadException);
            assertFalse(e.getCause() instanceof AspectConfigException);
        }
    }


    public void test_load_noRessource() throws Exception {
        manager = new AspectManager();
        manager.load(null);
    }


    public void test_multipleAspects_default_load()
          throws Exception {
        manager.load();
        Aspect aspect = manager.getBeforeAspect("multipoint", "Q_AP_OUTSTAND");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    public void test_multipleAspects() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("multipoint", "Q_AP_OUTSTAND");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() ");
    }


    private void load() throws AspectConfigException {
        manager.load(AspectManagerTest.class.getResourceAsStream("Aspects.xml"));
    }


    public void test_load_add() throws Exception {
        manager.addAspect("AspectAdd1", new JoinPoint[0], ComputeAspect.class);
        load();
        manager.addAspect("AspectAdd2", new JoinPoint[0], ComputeAspect.class);
        assertEquals(
              "[AspectAdd1, ReportAspectId, ComputeAspectId, ErrorAspectId, MainAspectId, SubAspectId, SubSecondAspectId, SubSubAspectId, CircularParentAspectId, CircularMiddleAspectId, CircularSonAspectId, OneShotAspectId, OneShotAspectTriggerAId, OneShotAspectTriggerBId, AbstractTestBasicSqlAspectId, AspectAdd2]",
              manager.getAspectConfigList().toString());
    }


    public void test_instanciation_exception() throws Exception {
        JoinPoint point1 = new JoinPoint(JoinPoint.CALL_AFTER, "control.dispatch", "Q_AP_FUND_PRICE");

        manager.addAspect("BadId", new JoinPoint[]{point1}, BadAspect.class);

        try {
            manager.getAfterAspect("control.dispatch", "Q_AP_FUND_PRICE");
            fail("L'instanciation doit échouer.");
        }
        catch (AspectInstanciationException e) {
            ; // Cas normal
        }
    }


    public void test_aspect() throws Exception {
        JoinPoint point1 = new JoinPoint(JoinPoint.CALL_AFTER, "control.dispatch", "Q_AP_FUND_PRICE");

        manager.addAspect("BugId", new JoinPoint[]{point1}, BugAspect.class);
        Aspect aspect = manager.getAfterAspect("control.dispatch", "Q_AP_FUND_PRICE");

        try {
            aspect.setUp(new AspectContext(), point1);
            fail("L'appel à setUp() doit échouer.");
        }
        catch (AspectException e) {
            assertSame(ClassNotFoundException.class, e.getCause().getClass());
        }
    }


    public void test_aspectHelper_multiArgument_setUp()
          throws Exception {
        String setupCallList =
              "net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ReportAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() net.codjo.aspect.ComputeAspect.setUp() ";
        String beforeCallList =
              "net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() ";
        String afterCallList =
              "net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() ";
        String errorCallList = "net.codjo.aspect.ComputeAspect.run() ";
        String cleanupCallList =
              "net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() net.codjo.aspect.ReportAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() net.codjo.aspect.ComputeAspect.cleanUp() ";

        load();
        AspectHelper helper =
              manager.createHelper("handler.execute",
                                   new String[]{"newVL", "deleteCarambar"},
                                   AspectFilter.ALL);
        AspectContext context = new AspectContext();
        helper.setUp(context);
        assertHistory(context, setupCallList);

        helper.runBefore(context);
        assertHistory(context, setupCallList + beforeCallList);

        helper.runAfter(context);
        assertHistory(context, setupCallList + beforeCallList + afterCallList);

        helper.runError(context);
        assertHistory(context,
                      setupCallList + beforeCallList + afterCallList + errorCallList);

        helper.cleanUp(context);
        assertHistory(context,
                      setupCallList + beforeCallList + afterCallList + errorCallList
                      + cleanupCallList);
    }


    public void test_aspectHelper_setUp() throws Exception {
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", AspectFilter.ALL);
        assertSetupExecution(helper,
                             "net.codjo.aspect.ReportAspect.setUp() "
                             + "net.codjo.aspect.ComputeAspect.setUp() "
                             + "net.codjo.aspect.ComputeAspect.setUp() "
                             + "net.codjo.aspect.ErrorAspect.setUp() ");
    }


    public void test_aspectHelper_filter() throws Exception {
        final LogString logString = new LogString();
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", new AspectFilter() {
            public boolean accept(JoinPoint joinPoint, String aspectId) {
                logString.call("accept", joinPoint, aspectId);
                return "ReportAspectId".equals(aspectId);
            }
        });
        assertSetupExecution(helper, "net.codjo.aspect.ReportAspect.setUp() ");
        logString.assertContent(
              "accept(JoinPoint{before, control.dispatch(Q_AP_FUND_PRICE), fork = false}, ReportAspectId)"
              + ", accept(JoinPoint{before, aspect(ReportAspectId), fork = false}, ComputeAspectId)"
              + ", accept(JoinPoint{after, control.dispatch(Q_AP_FUND_PRICE), fork = false}, ComputeAspectId)"
              + ", accept(JoinPoint{error, control.dispatch(Q_AP_FUND_PRICE), fork = false}, ErrorAspectId)");
    }


    public void test_aspectHelper_runBefore() throws Exception {
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", AspectFilter.ALL);
        AspectContext context = new AspectContext();
        helper.runBefore(context);
        assertHistory(context,
                      "net.codjo.aspect.ComputeAspect.run() net.codjo.aspect.ReportAspect.run() ");
    }


    public void test_aspectHelper_runAfter() throws Exception {
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", AspectFilter.ALL);
        AspectContext context = new AspectContext();
        helper.runAfter(context);
        assertHistory(context, "net.codjo.aspect.ComputeAspect.run() ");
    }


    public void test_aspectHelper_runError() throws Exception {
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", AspectFilter.ALL);
        AspectContext context = new AspectContext();
        helper.runError(context);
        assertHistory(context, "net.codjo.aspect.ErrorAspect.run() ");
    }


    public void test_aspectHelper_cleanUp() throws Exception {
        load();
        AspectHelper helper = manager.createHelper("control.dispatch", "Q_AP_FUND_PRICE", AspectFilter.ALL);
        AspectContext context = new AspectContext();
        helper.cleanUp(context);
        assertHistory(context,
                      "net.codjo.aspect.ReportAspect.cleanUp() "
                      + "net.codjo.aspect.ComputeAspect.cleanUp() "
                      + "net.codjo.aspect.ComputeAspect.cleanUp() "
                      + "net.codjo.aspect.ErrorAspect.cleanUp() ");
    }


    public void test_createHelper_forSpecificAspect() throws Exception {
        JoinPoint joinPoint = createJoinPoint();
        manager.addAspect("my-forked-aspect-id", list(joinPoint), MainAspect.class);

        AspectHelper helper = getAspects(AspectFilter.ALL, "my-forked-aspect-id", joinPoint);
        assertSetupExecution(helper, "net.codjo.aspect.MainAspect.setUp() ");
    }


    public void test_createHelper_forSpecificAspectUsingFilter() throws Exception {
        JoinPoint joinPoint = createJoinPoint();
        manager.addAspect("my-forked-aspect-id", list(joinPoint), MainAspect.class);

        AspectHelper helper = getAspects(AspectFilter.NONE, "my-forked-aspect-id", joinPoint);
        assertSetupExecution(helper, "net.codjo.aspect.MainAspect.setUp() ");
    }


    public void test_createHelper_withAspectOnAspect() throws Exception {
        JoinPoint joinPoint = createJoinPoint();
        JoinPoint joinPointOnAspect = new JoinPoint(JoinPoint.CALL_AFTER,
                                                    JoinPoint.ON_ASPECT,
                                                    "my-forked-aspect");

        manager.addAspect("not-executed-aspect", list(joinPoint), ErrorAspect.class);
        manager.addAspect("my-forked-aspect", list(joinPoint), MainAspect.class);
        manager.addAspect("attached-aspect", list(joinPointOnAspect), SubAspect.class);

        AspectHelper helper = manager.createHelper(joinPoint, "my-forked-aspect", AspectFilter.ALL);

        assertSetupExecution(helper, "net.codjo.aspect.MainAspect.setUp() "
                                     + "net.codjo.aspect.SubAspect.setUp() ");
    }


    public void test_createHelper_withAspectOnAspectUsingFilter() throws Exception {
        JoinPoint joinPoint = createJoinPoint();
        JoinPoint joinPointOnAspect = new JoinPoint(JoinPoint.CALL_AFTER,
                                                    JoinPoint.ON_ASPECT,
                                                    "my-forked-aspect");

        manager.addAspect("not-executed-aspect", list(joinPoint), ErrorAspect.class);
        manager.addAspect("my-forked-aspect", list(joinPoint), MainAspect.class);
        manager.addAspect("attached-aspect", list(joinPointOnAspect), SubAspect.class);

        AspectHelper helper = manager.createHelper(joinPoint, "my-forked-aspect", AspectFilter.NONE);

        assertSetupExecution(helper, "net.codjo.aspect.MainAspect.setUp() ");
    }


    public void test_createHelper_forSpecificAspect_unknwonAspect() throws Exception {
        try {
            manager.createHelper(new JoinPoint(JoinPoint.CALL_AFTER, "handler.execute", "updateIt"),
                                 "unknown-aspect-id", AspectFilter.ALL);
            fail();
        }
        catch (IllegalStateException ex) {
            assertEquals("Pas d'aspect 'unknown-aspect-id'"
                         + " au point de jointure 'JoinPoint{after, handler.execute(updateIt), fork = false}'",
                         ex.getMessage());
        }
    }


    private JoinPoint[] list(JoinPoint... joinPoints) {
        return joinPoints;
    }


    @Override
    protected void setUp() throws Exception {
        manager = new AspectManager();
    }


    private AspectHelper getAspects(AspectFilter filter,
                                    String aspectId, JoinPoint joinPoint) {
        return manager.createHelper(joinPoint, aspectId, filter);
    }


    private JoinPoint createJoinPoint() {
        return new JoinPoint(JoinPoint.CALL_AFTER, "handler.execute", "updateStuff");
    }


    private AspectContext launchAspect(Aspect aspect) throws AspectException {
        AspectContext context = new AspectContext();
        aspect.setUp(context, null);
        aspect.run(context);
        aspect.cleanUp(context);
        return context;
    }


    private void assertSetupExecution(AspectHelper helper, String expected) throws AspectException {
        AspectContext context = new AspectContext();
        helper.setUp(context);
        assertHistory(context, expected);
    }


    private void assertHistory(AspectContext context, String expected) {
        assertEquals(expected, (String)context.get(AbstractTestAspect.CALL_HISTORY));
    }
}
