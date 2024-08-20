package com.johny.mibanco.infraestructrure.controllers;

import com.johny.mibanco.application.services.GenerateCustomerAccountReportService;
import com.johny.mibanco.domain.AccountReport;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerReportController {

    private final GenerateCustomerAccountReportService generateCustomerAccountReportService;

    public CustomerReportController(GenerateCustomerAccountReportService generateCustomerAccountReportService) {
        this.generateCustomerAccountReportService = generateCustomerAccountReportService;
    }

    @GetMapping("/{customerId}/report")
    public List<AccountReport> generateCustomerReport(@PathVariable UUID customerId,
                                                      @RequestParam Date startDate,
                                                      @RequestParam Date endDate ) {
        return generateCustomerAccountReportService.generateReportByDateRange(customerId, startDate, endDate);
    }

}
