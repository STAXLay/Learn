package org.example;

public class Cash {

    public double applyDiscount(double price, int discount) {
        return price*(1 - (double)discount / 100);
    }


}
