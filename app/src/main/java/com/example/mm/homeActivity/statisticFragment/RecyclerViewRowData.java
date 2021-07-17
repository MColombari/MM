package com.example.mm.homeActivity.statisticFragment;

import java.util.ArrayList;

public class RecyclerViewRowData implements Comparable<RecyclerViewRowData>{
    String course;
    Float value;
    Integer imagesTrend;
    boolean isExpanded;

    public RecyclerViewRowData(String courses, Float values, Integer imagesTrends) {
        this.course = courses;
        this.value = values;
        this.imagesTrend = imagesTrends;
        this.isExpanded = false;
    }

    /* Getter */
    public String getCourse() {
        return course;
    }
    public Float getValue() {
        return value;
    }
    public Integer getImagesTrend() {
        return imagesTrend;
    }
    public boolean isExpanded() {
        return isExpanded;
    }

    /* Setter */
    public void setCourse(String courses) {
        this.course = courses;
    }
    public void setValue(Float values) {
        this.value = values;
    }
    public void setImagesTrend(Integer imagesTrends) {
        this.imagesTrend = imagesTrends;
    }
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public int compareTo(RecyclerViewRowData o) {
        if(o.getValue().equals(this.getValue())){
            return this.getCourse().compareTo(o.getCourse());
        }
        return o.getValue().compareTo(this.getValue());
    }
}
