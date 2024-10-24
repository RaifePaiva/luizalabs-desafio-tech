package br.com.magalu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserModelTest {

    @Test
    void givenValidParams_whenCallNewIntance_thenCreateNewUserModel() {
        final var expectedUserId = 1;
        final var expectedName = "John Doe";

        final var userModel = new UserModel(expectedUserId, expectedName);

        assertEquals(expectedUserId, userModel.getUserId());
        assertEquals(expectedName, userModel.getName());
    }

}