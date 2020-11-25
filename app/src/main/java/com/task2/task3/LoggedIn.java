package com.task2.task3;

import android.icu.lang.UProperty;

public class LoggedIn {
    boolean isLoggedI = false;

    public static String usernameG = "";
    public static String passwordG = "";
    //PUT THE USERNAME PASSWORD IN GLOBAL
    public LoggedIn() { }

    public boolean isLoggedI() {
        return isLoggedI;
    }

    public void setLoggedI(boolean loggedI) {
        isLoggedI = loggedI;
    }

    public void setUsername(String loggedI) {
        usernameG = loggedI;
    }
    public String getUsername() {
        return usernameG;
    }

    public void setPassword(String loggedI) {
        passwordG = loggedI;
    }
    public String getPassword() {
        return passwordG;
    }

}
