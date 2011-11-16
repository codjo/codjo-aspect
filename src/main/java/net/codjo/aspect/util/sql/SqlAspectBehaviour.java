/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
/**
 * Interface fournissant toutes les requ�tes SQL n�cessaires au fonctionnement de {@link
 * AbstractBasicSqlAspect}
 *
 * @version $Revision: 1.2 $
 */
public interface SqlAspectBehaviour {
    /**
     * Retourne un tableau de requ�tes SQL de cr�ation des tables temporaires. Si la
     * m�thode renvoie null ou vide, on ne fait rien.
     *
     * @return
     */
    public String[] getSqlForSetUp();


    /**
     * Donne les requ�tes � executer dans le run. Si la m�thode renvoie null ou vide, on
     * ne fait rien. Si la requ�te commence par '{' alors ex�cute un appel de proc�dure
     * stock�e.
     *
     * @return
     */
    public String[] getSqlCallForRun();


    /**
     * Retourne un tableau de requ�tes SQL de destruction des tables temporaires. Si la
     * m�thode renvoie null ou vide, on ne fait rien.
     *
     * @return
     */
    public String[] getSqlForCleanUp();
}
