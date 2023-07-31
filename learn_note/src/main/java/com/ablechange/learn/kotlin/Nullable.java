package com.ablechange.learn.kotlin;

import com.google.gson.Gson;

public class Nullable {

    String id = "dsad";

    public Nullable() {
    }

    public Nullable(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Nullable{" +
                "id='" + id + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String json = "{}";
        Nullable nullable = new Gson().fromJson(json, Nullable.class);
        System.out.println(nullable.toString());
    }
}

