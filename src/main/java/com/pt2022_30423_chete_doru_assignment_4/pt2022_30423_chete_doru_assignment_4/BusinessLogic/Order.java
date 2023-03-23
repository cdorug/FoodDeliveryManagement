package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic;

import java.io.Serializable;
import java.util.Date;

/**
 * Class that models an order.
 */
public class Order implements Serializable {
    private int orderID;
    private String client_username;
    private Date orderDate;

    public int hashCode() {
        return 1;
    }

    public Order(int orderID, String client_username, Date orderDate) {
        this.orderID = orderID;
        this.client_username = client_username;
        this.orderDate = orderDate;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
