/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Classe représentant un point de jointure.
 *
 * <p> Un point de jointure est défini par trois paramètres :
 *
 * <ul> <li> <b>Call</b> : Méthode d'appel (obligatoire). Les valeurs possibles sont {@link #CALL_BEFORE} ou {@link #CALL_AFTER}. </li> <li> <b>Point</b> : Type de point de jointure (obligatoire). Exemple : "control.dispatch" </li> <li> <b>Argument</b> : Identifiant du point de
 * jointure (facultatif).  Exemple : "Q_AP_FUND_PRICE" </li> </ul> </p>
 *
 * @version $Revision: 1.9 $
 */
public class JoinPoint {
    public static final int CALL_BEFORE = 1;
    public static final int CALL_AFTER = 2;
    public static final int CALL_ERROR = 3;
    public static final String ON_ASPECT = "aspect";

    private int call;
    private String point;
    private String argument;
    private boolean fork = false;


    public JoinPoint() {
    }


    public JoinPoint(int call, String point, String argument) {
        this.setCall(call);
        this.setPoint(point);
        this.setArgument(argument);
    }


    public JoinPoint(int call, String point, String argument, boolean fork) {
        this(call, point, argument);
        this.fork = fork;
    }


    public int getCall() {
        return call;
    }


    public void setCall(int call) {
        if (CALL_BEFORE != call && CALL_AFTER != call && CALL_ERROR != call) {
            throw new IllegalArgumentException("Le call '" + call + "' est interdit");
        }

        this.call = call;
    }


    public String getPoint() {
        return point;
    }


    public void setPoint(String point) {
        if (point == null) {
            throw new IllegalArgumentException("Le point 'null' est interdit");
        }

        this.point = point;
    }


    public String getArgument() {
        return argument;
    }


    public void setArgument(String argument) {
        this.argument = argument;
    }


    public void setFork(boolean fork) {
        this.fork = fork;
    }


    public boolean isFork() {
        return this.fork;
    }


    @Override
    public String toString() {
        return "JoinPoint{" + callToString() + ", " + point + "(" + "" + argument + ")"
               + ", fork = " + fork
               + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JoinPoint joinPoint = (JoinPoint)o;

        if (call != joinPoint.call) {
            return false;
        }
        if (fork != joinPoint.fork) {
            return false;
        }
        if (argument != null ? !argument.equals(joinPoint.argument) : joinPoint.argument != null) {
            return false;
        }
        if (point != null ? !point.equals(joinPoint.point) : joinPoint.point != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = call;
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + (argument != null ? argument.hashCode() : 0);
        result = 31 * result + (fork ? 1 : 0);
        return result;
    }


    private String callToString() {
        switch (call) {
            case CALL_AFTER:
                return "after";
            case CALL_BEFORE:
                return "before";
            case CALL_ERROR:
                return "error";
            default:
                return "???";
        }
    }
}
