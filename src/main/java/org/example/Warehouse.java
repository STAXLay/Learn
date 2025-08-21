package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Warehouse {

        private final Map<Product, Integer> stock = new HashMap<>();
        Cash cash = new Cash();

        public void loadFromFile(String filename) {
            Path path = Paths.get(filename);

            if(!Files.exists(path)) {
                System.out.println("Файл склада не найден, создаем новый.");
                return;
            }

            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                int lineNo = 0;
                stock.clear();

                while((line = reader.readLine()) != null) {
                    lineNo++;
                    line = line.trim();

                    if(line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    String[] parts = line.split(";");
                    if(parts.length != 3) {
                        System.out.printf("⚠️ Строка %d пропущена: ожидаю 3 колонки%n", lineNo);
                        continue;
                    }
                    String name = parts[0].trim();
                    String priceStr = parts[1].trim();
                    String quantityStr = parts[2].trim();

                    double price;
                    int quantity;

                    try {
                        price = Double.parseDouble(priceStr);
                    } catch (NumberFormatException e) {
                        price = Double.parseDouble(priceStr.replace(",", "."));
                    }
                    try {
                        quantity = Integer.parseInt(quantityStr);
                    } catch (NumberFormatException e) {
                        System.out.printf("⚠️ Строка %d пропущена: неверное количество '%s'%n", lineNo, quantityStr);
                        continue;
                    }
                    addProduct(name, price, quantity);
                }
                System.out.println("✅ Склад успешно загружен из файла.");
            } catch (IOException e) {
                System.out.println("Ошибка при загрузке склада: " + e.getMessage());
            }
        }

        public void saveToFile(String filename) {
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
                    Paths.get(filename), StandardCharsets.UTF_8))) {
                for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
                    writer.printf(Locale.US,"%s;%.2f;%d%n",
                            entry.getKey().getName(),
                            entry.getKey().getPrice(),
                            entry.getValue());
                }
                System.out.println("💾 Склад сохранен в файл.");
            } catch (IOException e) {
                System.out.println("Ошибка при сохранении склада: " + e.getMessage());
            }
        }



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
