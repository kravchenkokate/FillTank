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

        // найти первый вариант
        findFirstVariant();
        // найти другие варианты
        do {
            findOtherVariants(buckets.size() - 2);
        } while (!enough());

        System.out.println(String.format("Итого: при объёме %d(л) есть %d вариантов заполнения.", amount, varCount));
    }

    private void findFirstVariant() {
        calcBucketCount(amount, 0);
    }

    private void findOtherVariants(int currBucketIndex) {

        Bucket currBucket = buckets.get(currBucketIndex);
        int currBucketCount = currBucket.getCount();

        if (currBucketCount > 0) {
            currBucketCount--;
            currBucket.setCount(currBucketCount);

            int rest = amount;
            for (Bucket bucket : buckets) {
                int bucketAmount = bucket.getAmount();
                int bucketCount = bucket.getCount();
                if(bucketAmount < currBucket.getAmount()) {
                    bucketCount = 0;
                    bucket.setCount(0);
                }
                rest -= bucketCount * bucketAmount;
            }

            calcBucketCount(rest, currBucketIndex + 1);
        }
        // если объём ведра неподходящий
        else {
            if (currBucketIndex > 0) {
                // переходим к ведру большего объёма
                findOtherVariants(currBucketIndex - 1);
            } else {
                // посчитать только 1л вёдрами
                calcBucketCount(amount, buckets.size() - 1);
            }
        }
    }

    private void calcBucketCount(int currAmount, int bucketIndex) {

        // объём ведра
        int bucketAmount = buckets.get(bucketIndex).getAmount();
        // вычисляем максимальное количество вёдер, которое поместиться в резервуар
        int bucketCount = (int) Math.floor(currAmount / bucketAmount);
        buckets.get(bucketIndex).setCount(bucketCount);

        if (bucketCount > 0) {
            // вычисляем остаток
            int rest = currAmount - bucketCount * bucketAmount;
            // если объёма ведра недостаточно - переходим к следующему ведру
            if (rest > 0) {
                calcBucketCount(rest, bucketIndex + 1);
            } else {
                printVariant();
            }
        } else {
            // если объём ведра неподходящий - переходим к следующему ведру
            calcBucketCount(currAmount, bucketIndex + 1);
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

    private boolean enough() {

        boolean result = true;
        for (int i = 0; i < buckets.size() - 1; i++) {
            if (buckets.get(i).getCount() > 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}
