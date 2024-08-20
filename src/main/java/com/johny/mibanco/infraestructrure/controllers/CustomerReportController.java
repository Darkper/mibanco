package com.johny.mibanco.infraestructrure.controllers;

import com.johny.mibanco.application.services.GenerateCustomerAccountReportService;
import com.johny.mibanco.domain.AccountReport;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate ) {
        return generateCustomerAccountReportService.generateReportByDateRange(customerId, startDate, endDate);
    }

}
