package io.controller;
import data.QueryRouter;

import java.util.logging.Level;

public class main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        try {
            QueryRouter router = new QueryRouter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
