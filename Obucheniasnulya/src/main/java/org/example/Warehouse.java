package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Warehouse {

        private final Map<Product, Integer> stock = new HashMap<>();

        public double findProductPrice(String name) {
            for(Map.Entry<Product, Integer> entry : stock.entrySet()) {
                if(name.equals(entry.getKey().getName())) {
                    return entry.getKey().getPrice();
                }
            }
            return -1;
        }

        public void addProduct(String name, double price, int quantity) {
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

        public void sellProduct(String name, int quantity) {
            for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
                int currentQuantity = entry.getValue();

                if (currentQuantity < quantity) {
                    System.out.println("❌ Недостаточно товара на складе!");
                    break;
                }

                stock.put(entry.getKey(), currentQuantity - quantity);

                double totalPrice = entry.getKey().getPrice() * quantity;
                System.out.printf("✅ Продано %d шт. %s за %.2f руб.%n",
                        quantity, entry.getKey().getName(), totalPrice);

                if (stock.get(entry.getKey()) == 0) {
                    stock.remove(entry.getKey());
                    System.out.println("Товар закончился");
                }
            }

        }

}
