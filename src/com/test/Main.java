package com.test;

import java.util.concurrent.ThreadLocalRandom;


public class Main {

    private static final int MIN = 10;
    private static final int MAX = 200;

    public static void main(String[] args) {

        int v = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        new Tank(v).fill();
    }
}
