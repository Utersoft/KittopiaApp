package org.o7planning.kittopiaapp;

import android.app.Application;

public class MyApplication extends Application {
    private static String pseudo;
    private static String mdp;

    public MyApplication(){
        pseudo = "";
        mdp = "";
    }

    public static String getMdp() {
        return mdp;
    }

    public static void setMdp(String mdp) {
        MyApplication.mdp = mdp;
    }

    public static String getPseudo() {
        return pseudo;
    }

    public static void setPseudo(String pseudo) {
        MyApplication.pseudo = pseudo;
    }
}
