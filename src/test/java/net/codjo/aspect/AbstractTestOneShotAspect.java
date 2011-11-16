/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * TODO.
 *
 * @version $Revision: 1.2 $
 */
public class AbstractTestOneShotAspect extends AbstractTestAspect {
    //permet de dire que l'on ne passe qu'une fois dans un aspect par appel
    @Override
    protected boolean isOneShotAspect() {
        return true;
    }
}
