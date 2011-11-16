/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
/**
 * Interface fournissant toutes les requêtes SQL nécessaires au fonctionnement de {@link
 * AbstractBasicSqlAspect}
 *
 * @version $Revision: 1.2 $
 */
public interface SqlAspectBehaviour {
    /**
     * Retourne un tableau de requêtes SQL de création des tables temporaires. Si la
     * méthode renvoie null ou vide, on ne fait rien.
     *
     * @return
     */
    public String[] getSqlForSetUp();


    /**
     * Donne les requêtes à executer dans le run. Si la méthode renvoie null ou vide, on
     * ne fait rien. Si la requête commence par '{' alors exécute un appel de procédure
     * stockée.
     *
     * @return
     */
    public String[] getSqlCallForRun();


    /**
     * Retourne un tableau de requêtes SQL de destruction des tables temporaires. Si la
     * méthode renvoie null ou vide, on ne fait rien.
     *
     * @return
     */
    public String[] getSqlForCleanUp();
}
