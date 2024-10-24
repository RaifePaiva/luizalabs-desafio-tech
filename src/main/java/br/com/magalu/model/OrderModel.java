package br.com.magalu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class OrderModel {

    private Integer orderId;
    private Double total;
    private String date;
    private Set<ProductModel> products;

    public OrderModel(int orderId, String date) {
        this.orderId = orderId;
        this.date = date;
        this.products = new HashSet<>();
    }

    public void addProduct(ProductModel product) {
        this.products.add(product);
    }

    public Double getTotal() {
        var total = products.stream()
                .map(ProductModel::getValue)
                .reduce(0.0, Double::sum);

        return BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return parsedDate.format(outputFormatter);
    }

    public Set<ProductModel> getProducts() {
        return products;
    }
}
