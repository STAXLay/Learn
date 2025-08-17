package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();
        Cash cash = new Cash();
        int counter = 1;
        while(true) {
            System.out.println("\n1 — Добавить товар");
            System.out.println("2 — Показать склад");
            System.out.println("3 — Продать товар");
            System.out.println("0 — Выход");
            String choice = scanner.next();

            if(choice.equals("0")) {
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
                    if(discount > 0) {
                        cash.applyDiscount(warehouse.findProduct(nameProduct).getPrice(), discount);
                    }
                    warehouse.sellProduct(nameProduct, quantity);
                    cash.printReceipt(warehouse.findProduct(nameProduct), quantity);
                default:
                    System.out.println("Неизвестная команда");
            }
        }
        /*
        while(true) {
            //Checks if user wants to continue the process
            System.out.println("Продолжить?");
            String willNext = scanner.next();
            if (willNext.equalsIgnoreCase("no")) {
                break;
            }

            System.out.printf("Вы покупатель под номером %d%n", counter);
            counter++;
            //Fetches user`s data
            System.out.println("Введите название товара");
            String choice = scanner.next();
            System.out.println("Введите цену");
            String priceStr = scanner.next().replace(",", ".");
            double price = Double.parseDouble(priceStr);
            System.out.println("Есть скидка?");
            String discount = scanner.next();
            //discount logic
            if (discount.equalsIgnoreCase("Да")||discount.equalsIgnoreCase("Yes")) {
                price = price * 0.9;
                System.out.printf("Ваша покупка %s за %.2f со скидкой.%n",
                        choice, price);
            } else System.out.printf("Ваша покупка %s за %.2f без скидки.%n",
                    choice, price);
        }
         */
    }
}