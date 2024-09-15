package test;

import java.util.Scanner;

import controller.Manager;
import models.Product;

public class Main {
    public static void Menu(){
        System.out.println("1. Add a product.\r\n" + //
                        "2. Search product by product name\r\n" + //
                        "3. Update product.\r\n" + //
                        "4. Delete product.\r\n" + //
                        "5. Save products to file.\r\n" + //
                        "6. Print list products from the file.\r\n"+ //
                        "7. Quit");// );
    }
    public static void main(String[] args) {
        Manager m = new Manager();

        Scanner sc = new Scanner(System.in);
        int choice ;
        do{
            Menu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = 0;
            }
            switch (choice) {
                case 1:
                    m.createProduct();

                    break;
                case 2:
                    System.out.print("Enter name to search: ");
                    String name = sc.nextLine();
                    for (Product p : m.searchProduct(name)) {
                        System.out.println(p.toString());
                    }
                    break;
                case 3:
                    System.out.print("Enter id to update: ");
                    String idToUpdate = sc.nextLine();
                    m.updateProduct(idToUpdate);
                    break;
                case 4:
                    System.out.print("Enter id to delete: ");
                    String id = sc.nextLine();
                    m.deleteProduct(id);

                    break;
                case 5:
                    String path = "output.txt";
                    m.saveToFile(path);
                    System.out.println("Success!");
                    break;
                case 6:
                    m.printListProduct();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Enter again!");
                    break;
            }
        }while(choice != 7);
    }
}
