package br.com.magalu.model;

public class ProductModel {

    private Integer productId;
    private Double value;

    public ProductModel(Integer productId, Double value) {
        this.productId = productId;
        this.value = value;
    }

    public Integer getProductId() {
        return productId;
    }

    public Double getValue() {
        return value;
    }
}
