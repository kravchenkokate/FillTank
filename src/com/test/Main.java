package com.test;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

    private static final int MIN = 10;
    private static final int MAX = 200;

    public static void main(String[] args) {

        //int v = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);

        System.out.println("Введите объем резервуара:");
        System.out.println("V=");
        Scanner sc = new Scanner(System.in);
        String vStr =  sc.nextLine();
        int v = Integer.parseInt(vStr);

        new Tank(v).fill();
    }
}
