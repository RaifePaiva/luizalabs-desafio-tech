package br.com.magalu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductModelTest {

    @Test
    void givenValidParams_whenCreateProductModel_thenInstanceCreated() {
        final var expectedProductId = 1;
        final var expectedValue = 10.0;

        final var productModel = new ProductModel(expectedProductId, expectedValue);

        assertNotNull(productModel);
        assertEquals(expectedProductId, productModel.getProductId());
        assertEquals(expectedValue, productModel.getValue());
    }

}