/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import junit.framework.TestCase;
/**
 * @version $Revision: 1.2 $
 */
public class OneShotAspectTest extends TestCase {
    private AspectManager manager;

    private AspectContext launchAspect(Aspect aspect)
            throws AspectException {
        AspectContext context = new AspectContext();
        aspect.setUp(context, null);
        aspect.run(context);
        aspect.cleanUp(context);
        return context;
    }


    private void assertHistory(AspectContext context, String expected) {
        assertEquals(expected, (String)context.get(AbstractTestAspect.CALL_HISTORY));
    }


    public void test_cascade() throws Exception {
        load();
        Aspect aspect = manager.getBeforeAspect("handler.execute", "testONESHOTASPECT");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
            "net.codjo.aspect.OneShotAspectTriggerA.setUp() net.codjo.aspect.OneShotAspect.setUp() net.codjo.aspect.OneShotAspectTriggerB.setUp() net.codjo.aspect.OneShotAspectTriggerA.run() net.codjo.aspect.OneShotAspect.run() net.codjo.aspect.OneShotAspectTriggerB.run() net.codjo.aspect.OneShotAspectTriggerA.cleanUp() net.codjo.aspect.OneShotAspectTriggerB.cleanUp() net.codjo.aspect.OneShotAspect.cleanUp() ");
    }


    private void load() throws AspectConfigException {
        manager.load(AspectOnAspectTest.class.getResourceAsStream("Aspects.xml"));
    }


    @Override
    protected void setUp() throws Exception {
        manager = new AspectManager();
    }
}
