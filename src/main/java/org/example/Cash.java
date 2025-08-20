package org.example;

public class Cash {

    public double applyDiscount(double price, int discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть от 0 до 100%");
        }
        return price*(1 - (double)discount / 100);
    }

    public void printReceipt(Product product, int quantity, int discount) {
        double finalPrice = applyDiscount(product.getPrice(), discount) * quantity;
        System.out.printf("Чек%nВаша покупка: %d %s%nСкидка: %d%%%nИтого: %.2f руб.%n",
                quantity, product.getName(), discount, finalPrice);
    }


}
