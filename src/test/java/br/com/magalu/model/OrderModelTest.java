package br.com.magalu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderModelTest {

    @Test
    void givenValidParams_whenCallNewIntance_thenCreateNewOrder() {
        final var expectedOrderId = 1;
        final var expectedDate = "20200522";

        final var order = new OrderModel(expectedOrderId, expectedDate);

        assertEquals(expectedOrderId, order.getOrderId());
        assertEquals("2020-05-22", order.getDate());
    }

    @Test
    void givenValidOrder_whenCallTotal_thenReturnTotal() {
        final var order = new OrderModel(1, "20200522");
        final var product1 = new ProductModel(1, 10.99);
        final var product2 = new ProductModel(2, 20.99);
        order.addProduct(product1);
        order.addProduct(product2);

        assertEquals(2, order.getProducts().size());
        assertEquals("2020-05-22", order.getDate());
        assertEquals(31.98, order.getTotal());
    }

}