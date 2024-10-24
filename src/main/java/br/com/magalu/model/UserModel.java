package br.com.magalu.model;

import java.util.HashSet;
import java.util.Set;

public class UserModel {

    private Integer userId;
    private String name;
    private Set<OrderModel> orders;

    public UserModel(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.orders = new HashSet<>();
    }

    public void addOrder(OrderModel order) {
        this.orders.add(order);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Set<OrderModel> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
