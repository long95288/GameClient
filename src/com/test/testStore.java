package com.test;

public class testStore {
    private static String status = "Init";


    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        testStore.status = status;
    }
}
