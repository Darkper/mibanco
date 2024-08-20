package com.johny.mibanco.infraestructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johny.mibanco.application.commands.CreateNewCustomerWithAccountCommand;
import com.johny.mibanco.application.commands.MakeAccountTransactionCommand;
import com.johny.mibanco.application.commands.UpdateCustomerAccountCommand;
import com.johny.mibanco.application.commands.UpdateCustomerCommand;
import com.johny.mibanco.application.services.*;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.exceptions.CustomerAccountNotFoundException;
import com.johny.mibanco.infraestructrure.controllers.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(CustomerController.class)
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetAllCustomerService getAllCustomerService;

    @MockBean
    private CreateNewCustomerWithAccountService createNewCustomerWithAccountService;

    @MockBean
    private CreateNewAccountService createNewAccountService;

    @MockBean
    private UpdateCustomerService updateCustomerService;

    @MockBean
    private UpdateCustomerAccountService updateCustomerAccountService;

    @MockBean
    private MakeAccountDepositService makeAccountDepositService;

    @MockBean
    private MakeAccountWithdrawService makeAccountWithdrawService;

    @MockBean
    private DeleteCustomerAccountService deleteCustomerAccountService;

    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
    }

    @Test
    void getAll_shouldReturnListOfCustomers_whenCustomersExist() throws Exception {
        // Arrange
        Customer customer1 = new Customer(UUID.randomUUID(), "John Doe", "123 Main St", "555-1234");
        Customer customer2 = new Customer(UUID.randomUUID(), "Jane Doe", "456 Elm St", "555-5678");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        Mockito.when(getAllCustomerService.getAll()).thenReturn(customers);

        // Act & Assert
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoCustomersExist() throws Exception {
        // Arrange
        Mockito.when(getAllCustomerService.getAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createCustomerWithAccount_shouldReturnCustomer_whenValidRequest() throws Exception {
        CreateNewCustomerWithAccountCommand command = new CreateNewCustomerWithAccountCommand();
        command.setName("John Doe");
        command.setAddress("123 Main St");
        command.setPhone("555-1234");

        Customer customer = new Customer(customerId, command.getName(), command.getAddress(), command.getPhone());
        Mockito.when(createNewCustomerWithAccountService.createNewCustomerWithAccountService(any())).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone").value("555-1234"));
    }

    @Test
    void createNewAccount_shouldReturnCustomerWithNewAccount_whenValidRequest() throws Exception {
        Customer customer = new Customer(customerId, "John Doe", "123 Main St", "555-1234");
        Mockito.when(createNewAccountService.createNewAccount(customerId)).thenReturn(customer);

        mockMvc.perform(post("/customers/{id}/accounts", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer_whenValidRequest() throws Exception {
        UpdateCustomerCommand command = new UpdateCustomerCommand();
        command.setCustomerId(customerId);
        command.setName("Jane Doe");
        command.setAddress("456 Elm St");
        command.setPhone("555-5678");

        Customer updatedCustomer = new Customer(customerId, command.getName(), command.getAddress(), command.getPhone());
        Mockito.when(updateCustomerService.updateCustomer(any())).thenReturn(updatedCustomer);

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("456 Elm St"))
                .andExpect(jsonPath("$.phone").value("555-5678"));
    }

    @Test
    void updateCustomerAccount_shouldReturnUpdatedCustomer_whenValidRequest() throws Exception {
        UpdateCustomerAccountCommand command = new UpdateCustomerAccountCommand();
        command.setCustomerId(customerId);
        command.setAccountId(1L);
        command.setBalance(500.0);

        Customer customer = new Customer(customerId, "John Doe", "123 Main St", "555-1234");
        Mockito.when(updateCustomerAccountService.updateCustomerAccount(any())).thenReturn(customer);

        mockMvc.perform(put("/customers/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    void makeAccountDeposit_shouldReturnUpdatedCustomer_whenValidRequest() throws Exception {
        MakeAccountTransactionCommand command = new MakeAccountTransactionCommand();
        command.setCustomerId(customerId);
        command.setAccountId(1L);
        command.setAmount(200.0);

        Customer customer = new Customer(customerId, "John Doe", "123 Main St", "555-1234");
        Mockito.when(makeAccountDepositService.makeAccountDeposit(any())).thenReturn(customer);

        mockMvc.perform(post("/customers/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    void makeAccountWithdraw_shouldReturnUpdatedCustomer_whenValidRequest() throws Exception {
        MakeAccountTransactionCommand command = new MakeAccountTransactionCommand();
        command.setCustomerId(customerId);
        command.setAccountId(1L);
        command.setAmount(200.0);

        Customer customer = new Customer(customerId, "John Doe", "123 Main St", "555-1234");
        Mockito.when(makeAccountWithdrawService.makeAccountWithdraw(any())).thenReturn(customer);

        mockMvc.perform(post("/customers/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    void deleteAccount_shouldReturnNoContent_whenAccountDeletedSuccessfully() throws Exception {
        long accountId = 1L;

        Mockito.when(deleteCustomerAccountService.deleteAccount(customerId, accountId))
                .thenReturn(new Customer(customerId, "John Doe", "123 Main St", "555-1234"));

        mockMvc.perform(delete("/customers/{customerId}/accounts/{id}", customerId, accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    void deleteAccount_shouldReturnNotFound_whenAccountDoesNotExist() throws Exception {
        long accountId = 1L;

        Mockito.when(deleteCustomerAccountService.deleteAccount(customerId, accountId))
                .thenThrow(new CustomerAccountNotFoundException());

        mockMvc.perform(delete("/customers/{customerId}/accounts/{id}", customerId, accountId))
                .andExpect(status().isNotFound());
    }

}

