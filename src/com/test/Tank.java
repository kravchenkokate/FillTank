package com.test;

import java.util.ArrayList;
import java.util.List;


/**
 * Резервуар
 */
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

    /**
     * Начальная инициализация - задаём параметры вёдер
     */
    private void init() {

        buckets.add(new Bucket(10, 0));
        buckets.add(new Bucket(5, 0));
        buckets.add(new Bucket(2, 0));
        buckets.add(new Bucket(1, 0));
    }

    /**
     * Заполнить резервуар - вывести на консоль все возможные варианты
     */
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

    /**
     * Найти начальный вариант заполнения
     */
    private void findFirstVariant() {
        calcBucketCount(amount, 0);
    }

    /**
     * Найти различные варианты заполнения резервуара
     * @param currBucketIndex - индекс текущего ведра
     */
    private void findOtherVariants(int currBucketIndex) {

        Bucket currBucket = buckets.get(currBucketIndex);
        int currBucketCount = currBucket.getCount();

        if (currBucketCount > 0) {
            // уменьшаем количество вёдер текущего объёма на 1
            currBucketCount--;
            currBucket.setCount(currBucketCount);

            // для вёдер меньшего объёма - пересчитываем количество
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
        else {
            // если объём ведра неподходящий
            if (currBucketIndex > 0) {
                // переходим к ведру большего объёма
                findOtherVariants(currBucketIndex - 1);
            } else {
                // заполнить резервуар только 1л вёдрами
                calcBucketCount(amount, buckets.size() - 1);
            }
        }
    }

    /**
     * Посчитать количество вёдер определённого объёма (10л, 5л, 2л, 1л),
     * необходимых для заполнения указанного объёма в резервуаре
     * @param currAmount - объём в резервуаре, который нужно заполнить
     * @param bucketIndex - индекс ведра
     */
    private void calcBucketCount(int currAmount, int bucketIndex) {

        // объём ведра
        int bucketAmount = buckets.get(bucketIndex).getAmount();
        // вычисляем максимальное количество вёдер, которое поместиться в заданном объёме
        int bucketCount = (int) Math.floor(currAmount / bucketAmount);
        buckets.get(bucketIndex).setCount(bucketCount);

        if (bucketCount > 0) {
            // вычисляем остаток
            int rest = currAmount - bucketCount * bucketAmount;
            if (rest > 0) {
                // если объёма ведра недостаточно - переходим к следующему ведру
                calcBucketCount(rest, bucketIndex + 1);
            } else {
                // всё заполнили - печатаем
                printVariant();
            }
        } else {
            // если объём ведра неподходящий - переходим к следующему ведру
            calcBucketCount(currAmount, bucketIndex + 1);
        }
    }

    /**
     * Вывести вариант заполнения резервуара на консоль
     */
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

    /**
     * Проверить, нужно ли продолжать поиск новых вариантов
     * @return
     */
    private boolean enough() {

        boolean result = true;
        // поиск можно прекратить, если резервуар можно заполнить только вёдрами 1л
        for (int i = 0; i < buckets.size() - 1; i++) {
            if (buckets.get(i).getCount() > 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}
