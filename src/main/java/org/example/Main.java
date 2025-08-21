package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();
        Cash cash = new Cash();
        warehouse.loadFromFile("warehouse.txt");
        while (true) {

            try {
                System.out.println("\n1 — Добавить товар");
                System.out.println("2 — Показать склад");
                System.out.println("3 — Продать товар");
                System.out.println("0 — Выход");
                String choice = scanner.next();

                if (choice.equals("0")) {
                    warehouse.saveToFile("warehouse.txt");
                    break;
                }

                switch (choice) {
                    case "1":
                        System.out.println("Введите название товара:");
                        String name = scanner.next();
                        System.out.println("Введите цену:");
                        double price = Double.parseDouble(scanner.next().replace(",", "."));
                        System.out.println("Введите количество:");
                        int qty = scanner.nextInt();
                        warehouse.addProduct(name, price, qty);
                        break;
                    case "2":
                        warehouse.printInventory();
                        break;
                    case "3":
                        System.out.println("Введите название товара:");
                        String nameProduct = scanner.next();
                        System.out.println("Введите количество:");
                        int quantity = scanner.nextInt();
                        System.out.println("Какая скидка?");
                        int discount = scanner.nextInt();
                        Product product = warehouse.findProduct(nameProduct);
                        try {
                            warehouse.sellProduct(nameProduct, quantity, discount);
                            cash.printReceipt(product, quantity, discount);
                        } catch (ProductNotFoundException e) {
                            throw new ProductNotFoundException(e.getMessage());
                        }

                        break;
                    default:
                        System.out.println("Неизвестная команда");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("❌ Ошибка ввода: нужно ввести число!");
                scanner.nextLine(); // очищаем ввод
            } catch (ProductNotFoundException | NotEnoughProductException e) {
                System.out.println("⚠️ " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Неверные данные: " + e.getMessage());
            }
        }
    }
}