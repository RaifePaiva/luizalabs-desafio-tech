package br.com.magalu.service;

import br.com.magalu.model.UserModel;

import java.util.List;
import java.util.Set;

public interface ProcessorService {

    Set<UserModel> process(List<String> lines, Integer orderId, String startDate, String endDate);

}
