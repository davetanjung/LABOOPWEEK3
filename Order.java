/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package week3_2025;

import java.util.ArrayList;

/**
 *
 * @author davewirjoatmodjo
 */
public class Order {
    
    private String name;
    private int totalPrice, tableNumber; 
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    private ArrayList<Menu> menuList = new ArrayList();

    public Order(String name, int totalPrice, int tableNumber) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.tableNumber = tableNumber;
    }    
    
    public ArrayList<Menu> getMenuList() {
        return menuList;
    }
    
    public void setAddItem(Menu menu) {
        menuList.add(menu);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
        
}
