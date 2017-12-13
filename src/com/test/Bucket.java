package com.test;


/**
 * Ведро
 */
public class Bucket {

    //объём ведра
    private int amount;
    //количество вёдер, необходимых для заполнения бака
    private int count;

    public Bucket(int amount, int count) {
        this.amount = amount;
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
