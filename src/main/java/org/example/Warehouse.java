package org.example;

import java.util.HashMap;
import java.util.Map;


public class Warehouse {

        private final Map<Product, Integer> stock = new HashMap<>();
        Cash cash = new Cash();


        public Product findProduct(String name) {
            for(Map.Entry<Product, Integer> entry : stock.entrySet()) {
                if(name.equalsIgnoreCase(entry.getKey().getName())) {
                    return entry.getKey();
                }
            }
            throw new ProductNotFoundException("Товар '" + name + "' не найден на складе!");
        }

        public void addProduct(String name, double price, int quantity) {
            if(quantity<=0 || price <0) {
                throw new IllegalArgumentException("Цена или количество товара должна быть больше нуля!");
            }
            Product product = new Product(name, price);

            stock.put(product, stock.getOrDefault(product, 0) + quantity);
        }

        public void printInventory() {
            System.out.println("\n📦 Состояние склада:");
            for(Map.Entry<Product, Integer> entry : stock.entrySet()) {
                double totalForItem = entry.getKey().getPrice() * entry.getValue();
                System.out.printf("%s — %d шт. по %.2f руб. (Всего: %.2f руб.)%n",
                        entry.getKey().getName(),
                        entry.getValue(),
                        entry.getKey().getPrice(),
                        totalForItem);
            }
            System.out.printf("💰 Общая стоимость: %.2f руб.%n", getTotalValue());
        }

        public double getTotalValue() {
            double sum = 0;
            for(Map.Entry<Product, Integer> entry : stock.entrySet()) {
                sum += entry.getKey().getPrice() * entry.getValue();
            }
            return sum;
        }

        public void sellProduct(String name, int quantity, int discount) {

            Product product = findProduct(name);
            int currentQuantity = stock.get(product);

            if (quantity <= 0) {
                throw new IllegalArgumentException("Количество для продажи должно быть больше нуля!");
            }
            if (currentQuantity < quantity) {
                throw new NotEnoughProductException("Недостаточно товара '" + name + "' на складе!");
            }

            stock.put(product, currentQuantity - quantity);

            double totalPrice = cash.applyDiscount(product.getPrice(), discount) * quantity;
            System.out.printf("✅ Продано %d шт. %s за %.2f руб.%n",
                    quantity, product.getName(), totalPrice);

            if (stock.get(product) == 0) {
                stock.remove(product);
                System.out.println("⚠️ Товар '" + product.getName() + "' закончился на складе.");
            }
        }

}
