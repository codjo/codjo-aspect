/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Contexte relatif � l'ex�cution d'un aspect.
 *
 * @version $Revision: 1.2 $
 */
public class AspectContext {
    private Map<String, Object> map = new HashMap<String, Object>();


    /**
     * Positionne un param�tre dans le contexte.
     *
     * @param key   Nom du param�tre
     * @param value Valeur du param�tre
     */
    public void put(String key, Object value) {
        map.put(key, value);
    }


    /**
     * R�cup�re un param�tre dans le contexte.
     *
     * @param key Nom du param�tre
     *
     * @return Valeur du param�tre
     */
    public Object get(String key) {
        return map.get(key);
    }


    /**
     * Retourne le contenu du contexte sous forme de map
     */
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(map);
    }
}
