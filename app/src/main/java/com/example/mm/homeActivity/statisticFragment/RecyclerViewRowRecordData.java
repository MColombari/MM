package com.example.mm.homeActivity.statisticFragment;


public class RecyclerViewRowRecordData implements Comparable<RecyclerViewRowRecordData> {
    int value;
    int date;

    public RecyclerViewRowRecordData(int value, int date) {
        this.value = value;
        this.date = date;
    }

    /* Getter */
    public int getValue() {
        return value;
    }
    public int getDate() {
        return date;
    }

    /* Setter */
    public void setValue(int value) {
        this.value = value;
    }
    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int compareTo(RecyclerViewRowRecordData o) {
        return this.getDate() - o.getDate();
    }
}
