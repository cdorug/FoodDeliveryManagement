package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Data;

import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic.MenuItem;
import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic.Order;
import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that handles serialization and deserialization of user, products and orders data.
 */
public class Serializator {

    /**
     * Method that deserializes all users from the user file.
     * @return A list of User objects
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<User> deserializeUsers() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("users.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<User> users;
        try {
            users = (ArrayList<User>) ois.readObject();
            return users;
        } catch (EOFException e) {
            System.out.println("No users");
            ArrayList<User> emptyUserList = new ArrayList<>();
            return emptyUserList= new ArrayList<>();
        } finally {
            fis.close();
            fis.close();
        }
    }

    /**
     * Method that serializes all users into the file with the user data.
     * @param users All users
     * @throws IOException
     */
    public void serializeUsers(ArrayList<User> users) throws IOException {
        FileOutputStream fos = new FileOutputStream("users.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(users);
        oos.close();
        fos.close();
    }

    /**
     * Method that deserializes all menu items from the file with menu item data.
     * @return A list of MenuItem objects
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<MenuItem> deserializeMenuItems() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("menuItems.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<MenuItem> menuItems;
        try {
            menuItems = (ArrayList<MenuItem>) ois.readObject();
            return menuItems;
        } catch (EOFException e) {
            System.out.println("No menu items");
            ArrayList<MenuItem> emptyMenuItemList = new ArrayList<>();
            return emptyMenuItemList;
        } finally {
            fis.close();
            fis.close();
        }
    }

    /**
     * A method that serializes all MenuItem objects into the file with menu items data.
     * @param items All current items in the menu.
     * @throws IOException
     */
    public void serializeMenuItems(ArrayList<MenuItem> items) throws IOException {
        FileOutputStream fos = new FileOutputStream("menuItems.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(items);
        oos.close();
        fos.close();
    }

    /**
     * A method that serializes all orders into the file with orders data.
     * @param orders All current orders
     * @throws IOException
     */
    public void serializeOrders(HashMap<Order, List<MenuItem>> orders) throws IOException {
        FileOutputStream fos = new FileOutputStream("orders.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(orders);
        oos.close();
        fos.close();
    }

    /**
     * Method that deserializes all users from the file with user data.
     * @return A Map of Order and List of ordered items pair.
     * @throws IOException
     */
    public HashMap<Order, List<MenuItem>> deserializeOrders() throws IOException {

        FileInputStream fis = new FileInputStream("orders.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        HashMap<Order, List<MenuItem>> orders;
        try {
            orders = (HashMap<Order, List<MenuItem>>) ois.readObject();
            return orders;
        } catch (EOFException | ClassNotFoundException e) {
            HashMap<Order, List<MenuItem>> emptyOrders = new HashMap<Order, List<MenuItem>>();
            return emptyOrders;
        } finally {
            fis.close();
            fis.close();
        }
    }

    /**
     * Method that serializes username of currently logged in user.
     * @param username Username of currently logged in user.
     * @throws IOException
     */
    public void serializeCurrentlyLoggedUsername(String username) throws IOException {
        FileOutputStream fos = new FileOutputStream("loggedInUsername.ser", false);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(username);
        oos.close();
        fos.close();
    }


    /**
     * Method that serializes role of currently logged in user.
     * @param role Role of the currently logged in user.
     * @throws IOException
     */
    public void serializeCurrentlyLoggedRole(String role) throws IOException {
        FileOutputStream fos = new FileOutputStream("loggedInRole.ser", false);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(role);
        oos.close();
        fos.close();
    }

    /**
     * Method that deserializes username of currently logged in user.
     * @return Username of currently logged in user.
     * @throws IOException
     */
    public String deserializeCurrentlyLoggedUsername() throws IOException {
        FileInputStream fis = new FileInputStream("loggedInUsername.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            return (String) ois.readObject();
        } catch (EOFException | ClassNotFoundException e) {
            return null;
        } finally {
            fis.close();
            fis.close();
        }
    }

    /**
     * Method that deserializes role of currently logged in user.
     * @return Role of currently logged in user.
     * @throws IOException
     */
    public String deserializeCurrentlyLoggedRole() throws IOException {
        FileInputStream fis = new FileInputStream("loggedInRole.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            return (String) ois.readObject();
        } catch (EOFException | ClassNotFoundException e) {
            return null;
        } finally {
            fis.close();
            fis.close();
        }
    }
}
