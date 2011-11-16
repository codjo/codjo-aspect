package net.codjo.aspect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Test;

public class AspectConfigTest {
    private AspectConfig config = new AspectConfig();


    @Test
    public void test_load() throws Exception {
        config.load(AspectManagerTest.class.getResourceAsStream("ReportAspect.xml"));
        assertEquals("ReportAspectId", config.getAspectId());
        assertSame(ReportAspect.class, config.getAspectClass());
    }


    @Test
    public void test_load_ko() throws Exception {
        try {
            config.load(AspectManagerTest.class.getResourceAsStream("BadAspect.xml"));
            fail("Le chargement de ce fichier XML doit échouer.");
        }
        catch (AspectConfigException e) {
            assertNotNull(e.getCause());
        }
    }


    @Test
    public void test_load_ko_badCall() throws Exception {
        try {
            config.load(AspectManagerTest.class.getResourceAsStream("BadAspectCall.xml"));
            fail("Le chargement de ce fichier XML doit échouer.");
        }
        catch (AspectConfigException e) {
            assertNotNull(e.getCause());
        }
    }


    @Test
    public void test_load_ko_badClass() throws Exception {
        config.load(AspectManagerTest.class.getResourceAsStream("BadAspectClass.xml"));
        try {
            config.getAspectClass();
            fail("La classe n'existe .");
        }
        catch (AspectConfigException e) {
            assertEquals("La classe 'net.codjo.aspect.AnUnknownClass' de l'aspect 'BadAspectId' est introuvable",
                         e.getMessage());
            assertSame(ClassNotFoundException.class, e.getCause().getClass());
        }
    }


    @Test
    public void test_load_ko_noClass() throws Exception {
        try {
            config.load(AspectManagerTest.class.getResourceAsStream(
                  "BadAspectClass_empty.xml"));
            fail("Le chargement de ce fichier XML doit échouer.");
        }
        catch (AspectConfigException e) {
            assertEquals("L'attribut classe est non renseigné sur l'aspect 'BadAspectId'",
                         e.getMessage());
        }
    }


    @Test
    public void test_toString() throws Exception {
        config.load(AspectManagerTest.class.getResourceAsStream("ReportAspect.xml"));
        assertEquals("ReportAspectId", config.toString());
    }
}
