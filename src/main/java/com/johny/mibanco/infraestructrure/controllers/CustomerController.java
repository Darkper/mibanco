package com.johny.mibanco.infraestructrure.controllers;

import com.johny.mibanco.application.commands.CreateNewCustomerWithAccountCommand;
import com.johny.mibanco.application.commands.MakeAccountTransactionCommand;
import com.johny.mibanco.application.commands.UpdateCustomerAccountCommand;
import com.johny.mibanco.application.commands.UpdateCustomerCommand;
import com.johny.mibanco.application.services.*;

import com.johny.mibanco.domain.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CreateNewCustomerWithAccountService createNewCustomerWithAccountService;
    private final CreateNewAccountService createNewAccountService;
    private final UpdateCustomerService updateCustomerService;
    private final UpdateCustomerAccountService updateCustomerAccountService;
    private final MakeAccountDepositService makeAccountDepositService;
    private final MakeAccountWithdrawService makeAccountWithdrawService;
    private final DeleteCustomerAccountService deleteCustomerAccountService;
    private final DeleteCustomerService deleteCustomerService;
    private final GetAllCustomerService getAllCustomerService;

    public CustomerController(CreateNewCustomerWithAccountService createNewCustomerWithAccountService, CreateNewAccountService createNewAccountService, UpdateCustomerService updateCustomerService, UpdateCustomerAccountService updateCustomerAccountService, MakeAccountDepositService makeAccountDepositService, MakeAccountWithdrawService makeAccountWithdrawService, DeleteCustomerAccountService deleteCustomerAccountService, DeleteCustomerService deleteCustomerService, GetAllCustomerService getAllCustomerService) {
        this.createNewCustomerWithAccountService = createNewCustomerWithAccountService;
        this.createNewAccountService = createNewAccountService;
        this.updateCustomerService = updateCustomerService;
        this.updateCustomerAccountService = updateCustomerAccountService;
        this.makeAccountDepositService = makeAccountDepositService;
        this.makeAccountWithdrawService = makeAccountWithdrawService;
        this.deleteCustomerAccountService = deleteCustomerAccountService;
        this.deleteCustomerService = deleteCustomerService;
        this.getAllCustomerService = getAllCustomerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return getAllCustomerService.getAll();
    }
    @PostMapping
    public Customer createCustomerWithAccount(@RequestBody CreateNewCustomerWithAccountCommand command) {
        return createNewCustomerWithAccountService.createNewCustomerWithAccountService(command);
    }

    @PostMapping("{id}/accounts")
    public Customer createNewAccount(@PathVariable UUID id) {
        return createNewAccountService.createNewAccount(id);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody UpdateCustomerCommand command) {
        return updateCustomerService.updateCustomer(command);
    }

    @PutMapping("/accounts")
    public Customer updateCustomerAccount(@RequestBody UpdateCustomerAccountCommand command) {
        return updateCustomerAccountService.updateCustomerAccount(command);
    }

    @PostMapping("/accounts/deposit")
    public Customer makeAccountDeposit(@RequestBody MakeAccountTransactionCommand command) {
        return makeAccountDepositService.makeAccountDeposit(command);
    }

    @PostMapping("/accounts/withdraw")
    public Customer makeAccountWithdraw(@RequestBody MakeAccountTransactionCommand command) {
        return makeAccountWithdrawService.makeAccountWithdraw(command);
    }

    @DeleteMapping("{customerId}/accounts/{id}")
    public Customer deleteAccount(@PathVariable UUID customerId, @PathVariable long id) {
        return deleteCustomerAccountService.deleteAccount(customerId, id);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable UUID customerId) {
        deleteCustomerService.deleteCustomer(customerId);
    }

}
