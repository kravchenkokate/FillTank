package com.test;

import java.util.ArrayList;
import java.util.List;


public class Tank {

    // объём резервуара
    private int amount;
    // вёдра
    private List<Bucket> buckets = new ArrayList<>();
    // количество возможных вариантов заполнения резервуара
    private int varCount = 0;

    public Tank(int amount) {
        this.amount = amount;
        init();
    }

    private void init() {

        buckets.add(new Bucket(10, 0));
        buckets.add(new Bucket(5, 0));
        buckets.add(new Bucket(2, 0));
        buckets.add(new Bucket(1, 0));
    }

    public void fill() {

        System.out.println("Объём резервуара: " + amount + "(л)");
        System.out.println("Варианты заполнения:");

        for (int i = 0; i < buckets.size(); i++) {
            findVariant(amount, i);
        }

        System.out.println(String.format("Итого: при объёме %d(л) есть %d вариантов заполнения.", amount, varCount));
    }

    private void findVariant(int currAmount, int bucketIndex) {

        // остаток
        int rest = 0;

        // объём ведра
        int bucketAmount = buckets.get(bucketIndex).getAmount();
        // вычисляем максимальное количество вёдер, которое поместиться в резервуар
        int bucketCount = (int) Math.floor(currAmount / bucketAmount);
        buckets.get(bucketIndex).setCount(bucketCount);

        if (bucketIndex < buckets.size() - 1) {

            // если объём ведра неподходящий - переходим к следующему ведру
            if (bucketCount == 0) {
                findVariant(currAmount, bucketIndex + 1);
                return;
            }
            // постепенно уменьшаем количество вёдер текущего объёма и пересчитываем количество других вёдер
            while (bucketCount > 0) {

                rest = currAmount - bucketCount * bucketAmount;
                if (rest > 0) {
                    findVariant(rest, bucketIndex + 1);
                } else {
                    printVariant();
                }
                bucketCount--;
                buckets.get(bucketIndex).setCount(bucketCount);
            }
        } else {
            printVariant();
        }
    }

    private void printVariant() {

        varCount++;

        StringBuilder sb = new StringBuilder();
        for (Bucket bucket : buckets) {
            int count = bucket.getCount();
            int amount = bucket.getAmount();
            if (count > 0) {
                sb.append(count).append(" по ").append(amount).append("л").append(" + ");
            }
        }
        int length = sb.length();
        sb.setLength(length - 3);

        System.out.println(String.format("%d). %s;", varCount, sb.toString()));
    }
}
