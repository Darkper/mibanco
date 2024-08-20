package com.johny.mibanco.domain;


import java.util.List;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findById(UUID id);
    void deleteById(UUID id);

    List<Customer> getAll();
}
