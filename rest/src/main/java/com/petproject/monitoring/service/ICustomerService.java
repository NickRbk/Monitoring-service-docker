package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.web.dto.request.CustomerReqDTO;

public interface ICustomerService {
    Customer getById(Long customerId);
    void signUp(CustomerReqDTO customerReqDTO);
    void update(Long customerId, CustomerReqDTO customerReqDTO);
    void delete(Long customerId);
}
