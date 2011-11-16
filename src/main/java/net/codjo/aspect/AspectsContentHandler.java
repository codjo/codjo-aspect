/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.xml.fast.ClientContentHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * Classe permettant de charger un fichier XML listant les ressources à utiliser pour charger la configuration
 * des aspects.
 *
 * @version $Revision: 1.2 $
 */
class AspectsContentHandler implements ClientContentHandler {
    private List<AspectConfig> aspectConfigList = new ArrayList<AspectConfig>();


    public void startElement(String name, Map attributes) {
    }


    public void endElement(String name, String value) {
        if (JoinPoint.ON_ASPECT.equals(name)) {
            AspectConfig aspectConfig = new AspectConfig();
            try {
                aspectConfig.load(AspectsContentHandler.class.getResourceAsStream(value));
            }
            catch (AspectConfigException e) {
                throw new InternalLoadException(e.getMessage(), e);
            }

            aspectConfigList.add(aspectConfig);
        }
    }


    List<AspectConfig> getAspectConfigList() {
        return Collections.unmodifiableList(aspectConfigList);
    }
}
