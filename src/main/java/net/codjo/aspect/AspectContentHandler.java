/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.xml.fast.ClientContentHandler;
import java.util.Map;
/**
 * Classe permettant de charger un fichier XML décrivant un {@link Aspect}.
 *
 * @version $Revision: 1.4 $
 */
class AspectContentHandler implements ClientContentHandler {
    private AspectConfig config;

    AspectContentHandler(AspectConfig config) {
        this.config = config;
    }

    public void startElement(String name, Map attributes) {
        if (JoinPoint.ON_ASPECT.equals(name)) {
            config.setAspectId((String)attributes.get("id"));

            String className = (String)attributes.get("class");
            if (className == null) {
                throw new InternalLoadException(
                    "L'attribut classe est non renseigné sur l'aspect '"
                    + config.getAspectId() + "'");
            }
            config.setAspectClassName(className);
        }
        else if ("join-point".equals(name)) {
            JoinPoint joinPoint = new JoinPoint();
            joinPoint.setCall(toCallMethod((String)attributes.get("call")));
            joinPoint.setPoint((String)attributes.get("point"));
            joinPoint.setArgument((String)attributes.get("argument"));
            joinPoint.setFork(Boolean.valueOf((String)attributes.get("isFork")));
            config.addJoinPoint(joinPoint);
        }
    }


    private static int toCallMethod(String arg) {
        if ("before".equals(arg)) {
            return JoinPoint.CALL_BEFORE;
        }
        else if ("after".equals(arg)) {
            return JoinPoint.CALL_AFTER;
        }
        else if ("error".equals(arg)) {
            return JoinPoint.CALL_ERROR;
        }
        else {
            throw new IllegalArgumentException("Méthode d'appel invalide : " + arg);
        }
    }


    public void endElement(String name, String value) {}
}
