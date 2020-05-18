package com.utsav.simplerssexample;

import java.util.ArrayList;

public class Apiresult {
    private Integer total_count;
    private boolean incomplete_results;
    private ArrayList<newsitem>items;


    public ArrayList<newsitem> getItems() {
        return items;
    }

    public void setItems(ArrayList<newsitem> items) {
        this.items = items;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete(boolean incomplete) {
        this.incomplete_results = incomplete;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }
}
