package com.test;

public interface Sellable {
    double getPrice();

    default String symbol() {
        return "$";
    }
}
