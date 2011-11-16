/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import junit.framework.TestCase;
/**
 * Classe de test de {@link AspectManager}.
 */
public class AspectOnAspectTest extends TestCase {
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
        Aspect aspect = manager.getBeforeAspect("control.dispatch", "Q_CASCADE");

        AspectContext context = launchAspect(aspect);
        assertHistory(context,
                      "net.codjo.aspect.MainAspect.setUp() net.codjo.aspect.SubSecondAspect.setUp() net.codjo.aspect.SubAspect.setUp() net.codjo.aspect.SubSubAspect.setUp() net.codjo.aspect.SubSecondAspect.run() net.codjo.aspect.MainAspect.run() net.codjo.aspect.SubSubAspect.run() net.codjo.aspect.SubAspect.run() net.codjo.aspect.MainAspect.cleanUp() net.codjo.aspect.SubSecondAspect.cleanUp() net.codjo.aspect.SubAspect.cleanUp() net.codjo.aspect.SubSubAspect.cleanUp() ");
    }


    public void test_circular() throws Exception {
        try {
            load();
            manager.getBeforeAspect("handler.execute", "testCircular");
            fail("[ERRRO] la réference circulaire n'a pas été détéctée !");
        }
        catch (AspectInstanciationException ex) {
            //cas normal
            System.out.println("[OK] Reference circulaire detectée :" + ex.getMessage());
        }
    }


    private void load() throws AspectConfigException {
        manager.load(AspectOnAspectTest.class.getResourceAsStream("Aspects.xml"));
    }


    @Override
    protected void setUp() throws Exception {
        manager = new AspectManager();
    }
}
