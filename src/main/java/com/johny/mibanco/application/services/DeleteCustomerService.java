package com.johny.mibanco.application.services;

import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerService {

    private final CustomerRepository customerRepository;

    public DeleteCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }


}
