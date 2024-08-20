package com.johny.mibanco.application.services;

import com.johny.mibanco.domain.AccountReport;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GenerateCustomerAccountReportService {

    private final CustomerRepository customerRepository;

    public GenerateCustomerAccountReportService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<AccountReport> generateReportByDateRange(UUID customerId, Date startDate, Date endDate) {
        Customer customer = customerRepository.findById(customerId);
        return customer.generateReportByDateRange(startDate, endDate);
    }

}
