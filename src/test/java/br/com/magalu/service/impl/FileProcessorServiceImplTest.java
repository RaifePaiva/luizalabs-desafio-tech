package br.com.magalu.service.impl;

import br.com.magalu.model.UserModel;
import br.com.magalu.service.ProcessorService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FileProcessorServiceImplTest {

    @Inject
    private ProcessorService processorService;

    @Test
    void givenValidParams_whenCallsProcess_thenReturnUsers() {
        List<String> lines = Arrays.asList(
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308",
                "0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116"
        );

        Set<UserModel> users = processorService.process(lines, null, null, null);

        assertEquals(2, users.size());
        UserModel palmer = users.stream().filter(u -> u.getUserId() == 70).findFirst().orElse(null);
        UserModel bobbie = users.stream().filter(u -> u.getUserId() == 75).findFirst().orElse(null);

        assertNotNull(palmer);
        assertNotNull(bobbie);

        assertEquals(1, palmer.getOrders().size());
        assertEquals(1, bobbie.getOrders().size());
        assertEquals(1, palmer.getOrders().iterator().next().getProducts().size());
        assertEquals(1, bobbie.getOrders().iterator().next().getProducts().size());
    }

    @Test
    void givenValidOrder_whenCallProcess_shouldReturnOrderFiltered() {
        List<String> lines = Arrays.asList(
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308",
                "0000000070                              Palmer Prosacco00000007980000000002     2000.0020210308",
                "0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116"
        );

        Set<UserModel> users = processorService.process(lines, 753, null, null);

        assertEquals(1, users.size());
        UserModel palmer = users.stream().filter(u -> u.getUserId() == 70).findFirst().orElse(null);
        UserModel bobbie = users.stream().filter(u -> u.getUserId() == 75).findFirst().orElse(null);

        assertNull(bobbie); // Apenas o usuário 'Palmer' deve ser retornado
        assertNotNull(palmer);
        assertEquals(1, palmer.getOrders().size()); // Apenas o pedido correspondente a orderId 75
    }

    @Test
    void givenValidDateRange_whenCallProcess_shouldReturnPalmer() {
        List<String> lines = Arrays.asList(
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308",
                "0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116",
                "0000000070                              Palmer Prosacco00000007530000000003     2000.0020211208"
        );

        Set<UserModel> users = processorService.process(lines, null, "20211208", "20211208");

        assertEquals(1, users.size()); // Apenas o usuário 'Palmer' deve ser retornado
        assertEquals("Palmer Prosacco", users.iterator().next().getName());
    }

    @Test
    void givenEmptyLines_whenCallProcess_shouldReturnEmptySet() {
        List<String> lines = List.of(); // Lista vazia

        Set<UserModel> users = processorService.process(lines, null, null, null);

        assertTrue(users.isEmpty()); // O conjunto deve estar vazio
    }

}