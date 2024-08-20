package com.johny.mibanco.application.services;

import com.johny.mibanco.application.commands.MakeAccountTransactionCommand;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class MakeAccountDepositService {

    private final CustomerRepository customerRepository;

    public MakeAccountDepositService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer makeAccountDeposit(MakeAccountTransactionCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId());
        customer.makeAccountDeposit(command.getAccountId(), command.getAmount());
        return customerRepository.save(customer);
    }
}
