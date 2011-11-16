/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.util.ArrayList;
import java.util.List;
/**
 * Composite de {@link AspectHelper}.
 */
class MultiAspectHelper implements AspectHelper {
    private List<AspectHelper> helperList = new ArrayList<AspectHelper>();


    public void add(AspectHelper helper) {
        helperList.add(helper);
    }


    public void setUp(AspectContext context) throws AspectException {
        for (AspectHelper aspectHelper : helperList) {
            aspectHelper.setUp(context);
        }
    }


    public void runBefore(AspectContext context) throws AspectException {
        for (AspectHelper aspectHelper : helperList) {
            aspectHelper.runBefore(context);
        }
    }


    public void runAfter(AspectContext context) throws AspectException {
        for (AspectHelper aspectHelper : helperList) {
            aspectHelper.runAfter(context);
        }
    }


    public void runError(AspectContext context) throws AspectException {
        for (AspectHelper aspectHelper : helperList) {
            aspectHelper.runError(context);
        }
    }


    public void cleanUp(AspectContext context) throws AspectException {
        for (AspectHelper aspectHelper : helperList) {
            aspectHelper.cleanUp(context);
        }
    }
}
