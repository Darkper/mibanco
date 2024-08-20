package com.johny.mibanco.application.services;

import com.johny.mibanco.application.commands.UpdateCustomerAccountCommand;
import com.johny.mibanco.domain.Account;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerAccountService {

    private final CustomerRepository customerRepository;
    public UpdateCustomerAccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer updateCustomerAccount(UpdateCustomerAccountCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId());
        Account account = customer.getAccount(command.getAccountId());
        account.updateBalance(command.getBalance());
        return customerRepository.save(customer);
    }
}
