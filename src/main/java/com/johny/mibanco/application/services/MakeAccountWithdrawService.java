package com.johny.mibanco.application.services;

import com.johny.mibanco.application.commands.MakeAccountTransactionCommand;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class MakeAccountWithdrawService {

    private final CustomerRepository customerRepository;

    public MakeAccountWithdrawService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer makeAccountWithdraw(MakeAccountTransactionCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId());
        customer.makeAccountWithdraw(command.getAccountId(), command.getAmount());
        return customerRepository.save(customer);
    }
}
