/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Un {@link Aspect} qui provoque une erreur lors de son instanciation.
 *
 * @version $Revision: 1.1 $
 */
public class BadAspect implements Aspect {
    public BadAspect() {
        throw new RuntimeException();
    }

    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {}


    public void run(AspectContext context) throws AspectException {}


    public void cleanUp(AspectContext context) throws AspectException {}
}
