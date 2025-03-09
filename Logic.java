/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package week3_2025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author davewirjoatmodjo
 */
public class Logic {

    User admin, newUser, currentUser;
    Order newOrder;
    Menu menu1, menu2, menu3, newMenu;
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Menu> menuList = new ArrayList<Menu>();
    String yesno;
    int menuChoiceChangeMenu;

    public void setUp() {
        admin = new User("master", "12345678", "admin");
        menu1 = new Menu("Nasi goreng", "Nasi goreng yang dimasak pakai hati<3", 25000);
        menu2 = new Menu("Ayam bakar keju pake telor", "Telornya didadar diatas ayam", 22000);
        menu3 = new Menu("Tahu telor", "Tahu digoreng dalam telor ditaruh bumbu kacang", 17000);
        userList.add(admin);
        Collections.addAll(menuList, menu1, menu2, menu3);
    }

    public void authenticationMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Aaron's Restaurant");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose menu: ");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void register() {

        Scanner scan = new Scanner(System.in);
        String password;
        System.out.print("Username: ");
        String username = scan.next() + scan.nextLine();
        do {
            System.out.print("Password: ");
            password = scan.next();
        } while (!password.matches(".{8,}"));

        newUser = new User(username, password, "customer");

        userList.add(newUser); //  add customer ke list

        authenticationMenu();
    }

    public void login() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scan.next() + scan.nextLine();
        System.out.print("Password: ");
        String password = scan.next();

        for (int i = 0; i < userList.size(); i++) {
            if (username.equalsIgnoreCase(userList.get(i).getUsername()) && password.equalsIgnoreCase(userList.get(i).getPassword())) {
                currentUser = userList.get(i);
                if (currentUser.getRole().equalsIgnoreCase("admin")) {
                    adminStart();
                } else if (currentUser.getRole().equalsIgnoreCase("customer")) {
                    customerStart();
                }
                return;
            }
        }

        System.out.println("Invalid username or password");
        authenticationMenu();
    }

    public void customerStart() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello, " + currentUser.getUsername());
        System.out.println("1. Order");
        System.out.println("2. Check all order");
        System.out.println("3. Remove order");
        System.out.println("4. Log out");
        System.out.print("Choose: ");
        int choose = scan.nextInt();
        switch (choose) {
            case 1:
                orderMenu();
                break;
            case 2:
                checkAllOrder();
                break;
            case 3:
                removeOrder();
                break;
            case 4:
                authenticationMenu();
                break;
            default:
                System.out.println("Please input the correct number");
                customerStart();
                break;
        }
    }

    public void orderMenu() {
        int totalPrice = 0;
        Scanner scan = new Scanner(System.in);
        System.out.print("Table number: ");
        int tableNumber = scan.nextInt();

        do {
            for (int i = 0; i < menuList.size(); i++) {
                System.out.println((i + 1) + ". " + menuList.get(i).getName() + " - " + menuList.get(i).getDescription() + " - " + menuList.get(i).getPrice());
            }
            System.out.print("Choose Menu: ");
            int choose = scan.nextInt();            
            totalPrice += menuList.get(choose - 1).getPrice();            
            newOrder = new Order(menuList.get(choose - 1).getName(), totalPrice, tableNumber);  
            newOrder.setPrice(menuList.get(choose - 1).getPrice());
            currentUser.getOrderedList().add(newOrder);

            System.out.println("Do you want to continue ordering? (y/n) : ");
            yesno = scan.next();

        } while (yesno.equalsIgnoreCase("y"));

        customerStart();
    }

    public void checkAllOrder() {
        if (currentUser.getOrderedList().isEmpty()) {
            System.out.println("Please input your order first.");
            customerStart();
        }
        System.out.println("Your total order: ");
        for (int i = 0; i < currentUser.getOrderedList().size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.getOrderedList().get(i).getName());
        }
        System.out.println("Total cost: " + newOrder.getTotalPrice());
        customerStart();
    }

    public void removeOrder() {
        Scanner scanner = new Scanner(System.in);

        if (currentUser.getOrderedList().isEmpty()) {
            System.out.println("No orders to remove.");
            customerStart();
            return;
        }

        System.out.println("Your total order: ");
        for (int i = 0; i < currentUser.getOrderedList().size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.getOrderedList().get(i).getName());
        }

        System.out.print("Which one do you want to remove? ");
        int choose = scanner.nextInt();

        if (choose < 1 || choose > currentUser.getOrderedList().size()) {
            System.out.println("Invalid choice.");
            customerStart();
            return;
        }

        Order selectedOrder = currentUser.getOrderedList().get(choose - 1);
        System.out.println("Removing " + selectedOrder.getName() + " from your order list.");
        currentUser.getOrderedList().remove(choose - 1);
        newOrder.setTotalPrice(newOrder.getTotalPrice() - selectedOrder.getTotalPrice());

        System.out.println("Updated total cost: " + newOrder.getTotalPrice());

        customerStart();
    }

    public void adminStart() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello, " + admin.getUsername());
        System.out.println("1. Menu items");
        System.out.println("2. Check customer orders");
        System.out.println("3. Log out");
        System.out.print("Choose: ");
        int choose = scan.nextInt();
        switch (choose) {
            case 1:
                adminMenuItems();
                break;
            case 2:
                checkAllCustomerOrders();
                break;
            case 3:
                authenticationMenu();
                break;
            default:
                System.out.println("Please input the correct number");
                adminStart();
                break;
        }
    }

    public void adminMenuItems() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Change Menu Items");
        System.out.println("2. Display Menu Items");
        System.out.println("3. Add New Menu Items");
        System.out.println("4. Back");
        System.out.print("Choose menu - ");
        int choose = scan.nextInt();
        switch (choose) {
            case 1:
                changeMenuItems();
                break;
            case 2:
                displayMenuItems();
                break;
            case 3:
                addNewMenuItem();
                break;
            case 4:
                adminStart();
                break;
            default:
                System.out.println("Please input the right number");
                adminMenuItems();
                break;
        }
    }

    public void changeMenuItems() {
        System.out.println("Change your menu!");
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println((i + 1) + ". " + menuList.get(i).getName());
        }
        System.out.print("Choose: ");
        menuChoiceChangeMenu = scan.nextInt();

        System.out.println("1. Change Menu Name");
        System.out.println("2. Change Description");
        System.out.println("3. Change price");
        System.out.println("4. Back");

        System.out.print("Choose: ");
        int choose = scan.nextInt();

        switch (choose) {
            case 1:
                changeMenuName();
                break;
            case 2:
                changeMenuDescription();
                break;
            case 3:
                changeMenuPrice();
                break;
            case 4:
                adminMenuItems();
                break;
            default:
                System.out.println("Please input the right number");
                changeMenuItems();
                break;

        }
        adminMenuItems();
    }

    public void changeMenuName() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Previous menu name:" + menuList.get(menuChoiceChangeMenu - 1).getName());
        System.out.print("New name: ");
        String name = scan.next() + scan.nextLine();
        menuList.get(menuChoiceChangeMenu - 1).setName(name);
    }

    public void changeMenuDescription() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Previous menu description:" + menuList.get(menuChoiceChangeMenu - 1).getDescription());
        System.out.print("New Description: ");
        String description = scan.next() + scan.nextLine();
        menuList.get(menuChoiceChangeMenu - 1).setDescription(description);
    }

    public void changeMenuPrice() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Previous menu price:" + menuList.get(menuChoiceChangeMenu - 1).getPrice());
        System.out.print("New price: ");
        int harga = scan.nextInt();
        menuList.get(menuChoiceChangeMenu - 1).setPrice(harga);
    }

    public void displayMenuItems() {
        System.out.println("List of menu in Aaron's Restaurant:");
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println((i + 1) + ". " + menuList.get(i).getName() + " - " + menuList.get(i).getDescription() + " - " + menuList.get(i).getPrice());
        }
        adminMenuItems();
    }

    public void addNewMenuItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Menu name: ");
        String name = scan.next() + scan.nextLine();
        System.out.print("Menu description: ");
        String description = scan.next() + scan.nextLine();
        System.out.print("Price: ");
        int price = scan.nextInt();
        newMenu = new Menu(name, description, price);
        menuList.add(newMenu);
        System.out.println("New Menu Added");
        adminMenuItems();
    }

   public void checkAllCustomerOrders() {
    System.out.println("=== All Customer Orders ===");

    boolean hasOrders = false;
    int grandTotal = 0; 

    for (User user : userList) {
        if (!user.getOrderedList().isEmpty()) {
            hasOrders = true; 
            
            System.out.println("Orders for user: " + user.getUsername());
            
            int userTotalPrice = 0; 
            for (Order order : user.getOrderedList()) {
                System.out.println("- " + order.getName() + " | Table: " + order.getTableNumber() + " | Price: " + order.getPrice());
                
                
                userTotalPrice += order.getPrice();
            }
            
            System.out.println("Total Price for " + user.getUsername() + ": " + userTotalPrice);
            System.out.println("----------------------------");

            
            grandTotal += userTotalPrice;
        }
    }

    if (!hasOrders) {
        System.out.println("No orders available.");
    } else {
        System.out.println("Grand Total for All Orders: " + grandTotal);
    }

    adminStart();
}


}
