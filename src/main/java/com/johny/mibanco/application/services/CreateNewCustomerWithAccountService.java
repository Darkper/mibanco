package com.johny.mibanco.application.services;

import com.johny.mibanco.application.commands.CreateNewCustomerWithAccountCommand;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateNewCustomerWithAccountService {

    private final CustomerRepository customerRepository;
    public CreateNewCustomerWithAccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createNewCustomerWithAccountService(CreateNewCustomerWithAccountCommand command) {
        Customer newCustomer = new Customer(UUID.randomUUID(), command.getName(), command.getAddress(), command.getPhone());
        newCustomer.createNewAccount();
        return customerRepository.save(newCustomer);
    }
}
