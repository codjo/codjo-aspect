/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import junit.framework.TestCase;
/**
 * Classe de test de {@link JoinPoint}
 *
 * @version $Revision: 1.4 $
 */
public class JoinPointTest extends TestCase {
    public void test_setPoint() throws Exception {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setPoint("job");
        assertEquals("job", joinPoint.getPoint());
    }


    public void test_setPoint_null() throws Exception {
        JoinPoint joinPoint = new JoinPoint();
        try {
            joinPoint.setPoint(null);
            fail("Doit échouer car le point 'null' est invalide.");
        }
        catch (Exception e) {
            // Cas normal
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Le point 'null' est interdit", e.getMessage());
        }
    }


    public void test_setCall() {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setCall(JoinPoint.CALL_AFTER);
        assertEquals(JoinPoint.CALL_AFTER, joinPoint.getCall());

        joinPoint.setCall(JoinPoint.CALL_BEFORE);
        assertEquals(JoinPoint.CALL_BEFORE, joinPoint.getCall());

        joinPoint.setCall(JoinPoint.CALL_ERROR);
        assertEquals(JoinPoint.CALL_ERROR, joinPoint.getCall());

        try {
            joinPoint.setCall(42);
            fail("Doit échouer car le call '42' est invalide.");
        }
        catch (Exception e) {
            // Cas normal
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Le call '42' est interdit", e.getMessage());
        }
    }


    public void test_constructor() {
        JoinPoint point = new JoinPoint(JoinPoint.CALL_AFTER, "job", "foo");
        assertEquals(JoinPoint.CALL_AFTER, point.getCall());
        assertEquals("job", point.getPoint());
        assertEquals("foo", point.getArgument());

        point = new JoinPoint(JoinPoint.CALL_BEFORE, "job", null);
        assertNull(point.getArgument());

        try {
            new JoinPoint(JoinPoint.CALL_ERROR, null, null);
            fail("Doit échouer car le point 'null' est invalide.");
        }
        catch (Exception e) {
            // Cas normal
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Le point 'null' est interdit", e.getMessage());
        }

        try {
            new JoinPoint(42, null, null);
            fail("Doit échouer car le call '42' est invalide.");
        }
        catch (Exception e) {
            // Cas normal
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Le call '42' est interdit", e.getMessage());
        }
    }
}
