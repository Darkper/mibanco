package com.johny.mibanco.application.services;

import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCustomerService {

    private final CustomerRepository customerRepository;

    public GetAllCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }


}
