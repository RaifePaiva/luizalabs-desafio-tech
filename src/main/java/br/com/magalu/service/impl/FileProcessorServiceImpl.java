package br.com.magalu.service.impl;

import br.com.magalu.model.OrderModel;
import br.com.magalu.model.ProductModel;
import br.com.magalu.model.UserModel;
import br.com.magalu.service.ProcessorService;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ApplicationScoped
public class FileProcessorServiceImpl implements ProcessorService {


    @Override
    public Set<UserModel> process(List<String> lines, Integer orderId, String startDate, String endDate) {
        Map<String, UserModel> userMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate start = startDate != null ? LocalDate.parse(startDate, formatter) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate, formatter) : null;

        for (String line : lines) {
            String userId = line.substring(0, 10).trim();
            String userName = line.substring(10, 55).trim();
            String orderIdStr = line.substring(55, 65).trim();
            String productId = line.substring(65, 75).trim();
            String value = line.substring(75, 85).trim();
            String dateStr = line.substring(87).trim();
            LocalDate date = LocalDate.parse(dateStr, formatter);

            if ((orderId == null || orderId.equals(Integer.parseInt(orderIdStr))) &&
                    (start == null || !date.isBefore(start)) &&
                    (end == null || !date.isAfter(end))) {

                UserModel user = userMap.computeIfAbsent(userId, k -> new UserModel(Integer.parseInt(userId), userName));

                OrderModel order = user.getOrders().stream()
                        .filter(o -> o.getOrderId().equals(Integer.parseInt(orderIdStr)))
                        .findFirst()
                        .orElseGet(() -> {
                            OrderModel newOrder = new OrderModel(Integer.parseInt(orderIdStr), dateStr);
                            user.addOrder(newOrder);
                            return newOrder;
                        });

                order.addProduct(new ProductModel(Integer.parseInt(productId), Double.parseDouble(value)));
            }
        }

        return new HashSet<>(userMap.values());
    }
}
