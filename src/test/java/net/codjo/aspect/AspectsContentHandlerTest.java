/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.xml.fast.XmlParser;
import java.util.List;
import junit.framework.TestCase;
import org.xml.sax.InputSource;
/**
 * Test de la classe {@link AspectsContentHandler}
 *
 * @version $Revision: 1.3 $
 */
public class AspectsContentHandlerTest extends TestCase {

    public void test_getAspectConfigList() throws Exception {
        XmlParser xmlParser = new XmlParser();
        AspectsContentHandler aspectsContentHandler = new AspectsContentHandler();
        xmlParser.parse(new InputSource(
              AspectsContentHandlerTest.class.getResourceAsStream("Aspects.xml")),
                        aspectsContentHandler);
        assertEquals(
              "[ReportAspectId, ComputeAspectId, ErrorAspectId, MainAspectId, SubAspectId, SubSecondAspectId, SubSubAspectId, CircularParentAspectId, CircularMiddleAspectId, CircularSonAspectId, OneShotAspectId, OneShotAspectTriggerAId, OneShotAspectTriggerBId, AbstractTestBasicSqlAspectId]",
              aspectsContentHandler.getAspectConfigList().toString());

        List<AspectConfig> configList = aspectsContentHandler.getAspectConfigList();
        assertEquals(
              new AspectConfigBuilder("ReportAspectId", "net.codjo.aspect.ReportAspect")
                    .addJoinPoint(JoinPoint.CALL_BEFORE, "control.dispatch", "Q_AP_FUND_PRICE")
                    .addJoinPoint(JoinPoint.CALL_BEFORE, "control.dispatch", null)
                    .addJoinPoint(JoinPoint.CALL_AFTER, "handler.execute", "newVL", true)
                    .addJoinPoint(JoinPoint.CALL_BEFORE, "handler.execute", "newVL", false)
                    .addJoinPoint(JoinPoint.CALL_BEFORE, "multipoint", "Q_AP_OUTSTAND")
                    .get(),
              configList.get(0));
    }
}
