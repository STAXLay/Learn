package org.example;

public class Cash {

    public double applyDiscount(double price, int discount) {
        return price*(1 - (double)discount / 100);
    }

    public void printReceipt(Product product, int quantity) {
        System.out.printf("Чек%nВаша покупка: %d %s%nза %.2f", quantity, product.getName(), product.getPrice());
    }


}
