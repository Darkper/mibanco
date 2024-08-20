package com.johny.mibanco.application.commands;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GenerateCustomerAccountReportCommand {
    private UUID customerId;
    private Date startDate;
    private Date endDate;
}
