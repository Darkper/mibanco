package com.johny.mibanco.application.services;

import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerAccountService {

    private final CustomerRepository customerRepository;

    public DeleteCustomerAccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer deleteAccount(UUID customerId, long accountId) {
        Customer customer = customerRepository.findById(customerId);
        customer.deleteAccount(accountId);
        return customerRepository.save(customer);
    }


}
