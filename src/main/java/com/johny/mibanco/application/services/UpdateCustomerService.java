package com.johny.mibanco.application.services;

import com.johny.mibanco.application.commands.UpdateCustomerCommand;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerService {

    private final CustomerRepository customerRepository;
    public UpdateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer updateCustomer(UpdateCustomerCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId());
        customer.updateInformation(command.getName(), command.getAddress(), command.getPhone());
        return customerRepository.save(customer);
    }
}
