package com.johny.mibanco.application.services;

import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateNewAccountService {

    private final CustomerRepository customerRepository;

    public CreateNewAccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createNewAccount(UUID customerId) {
        Customer customer = customerRepository.findById(customerId);
        customer.createNewAccount();
        return customerRepository.save(customer);
    }


}
