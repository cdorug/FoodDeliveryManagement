package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic;

import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that acts as a service and handles access to users, menu items, orders.
 * @invariant isWellFormed()
 */
public class DeliveryService implements IDeliveryServiceProcessing {

    public ArrayList<User> users = new ArrayList<>();
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    public Map<Order, List<MenuItem>> orders = new HashMap<>();

    private int count = 0;
    @Override
    public List<BaseProduct> importItems() throws IOException {
        // read from .csv file
        Path path = Paths.get("C:\\Programming\\Programming Techniques\\PT2022_30423_Chete_Doru_Assignment_4\\products.csv");
        List<String[]> lines = Files.lines(path)
                .skip(1)
                .map(l -> l.split(","))
                .collect(Collectors.toList());
        List<BaseProduct> items = lines.stream()
                .distinct()
                .map(i -> new BaseProduct(0, i[0], Double.parseDouble(i[1]),
                        Integer.parseInt(i[2]), Integer.parseInt(i[3]), Integer.parseInt(i[4]), Integer.parseInt(i[5]),
                        Integer.parseInt(i[6])))
                .collect(Collectors.toList());
        ArrayList<BaseProduct> duplicates = new ArrayList<>();
        for(int i = 0; i < items.size(); i ++) {
            for(int j = i + 1; j < items.size(); j ++ ) {
                if(items.get(i).getTitle().equals(items.get(j).getTitle())) {
                    duplicates.add(items.get(j));
                }
            }
        }
        items.removeAll(duplicates);
        for(int i = 0; i < items.size(); i ++) {
            items.get(i).setId(++count);
        }
        return items;
    }

    @Override
    public void addMenu(CompositeProduct newMenu) {
        if(newMenu == null) {
            throw new NullPointerException();
        }
        menuItems.add(newMenu);
        assert menuItems.contains(newMenu);
    }

    @Override
    public boolean isWellFormed(int calories) {
        boolean ok = true;
        for(MenuItem menuItem: menuItems) {
            if (menuItem.getCalories() < 0) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    @Override
    public void addOrder(Order newOrder, List<MenuItem> orderedItems) {
        orders.put(newOrder, orderedItems);
    }

    @Override
    public void generateReport() {

    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Map<Order, List<MenuItem>> getOrders() {
        return orders;
    }

    public void setOrders(Map<Order, List<MenuItem>> orders) {
        this.orders = orders;
    }

    public Set<Order> getOnlyOrderDetails() {
        return orders.keySet();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
