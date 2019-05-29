package com.test;

public class testStore2 {
    public static void main(String[] argv){
        String status = testStore.getStatus();
        System.out.println(status);

        testStore.setStatus("new Data");
        System.out.println(testStore.getStatus());
    }
}
