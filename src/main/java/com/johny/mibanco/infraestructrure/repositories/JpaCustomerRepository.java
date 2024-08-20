package com.johny.mibanco.infraestructrure.repositories;

import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.CustomerRepository;
import com.johny.mibanco.domain.exceptions.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JpaCustomerRepository implements CustomerRepository {

    private final ExtendedJpaCustomerRepository extendedJpaCustomerRepository;

    public JpaCustomerRepository(ExtendedJpaCustomerRepository extendedJpaCustomerRepository) {
        this.extendedJpaCustomerRepository = extendedJpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        JpaCustomer jpaCustomer = extendedJpaCustomerRepository.save(new JpaCustomer(customer));
        return jpaCustomer.toCustomer();
    }



    @Override
    public Customer findById(UUID id) {
        Optional<JpaCustomer> jpaCustomer = extendedJpaCustomerRepository.findById(id);
        if (jpaCustomer.isPresent()) {
            return jpaCustomer.get().toCustomer();
        }
        throw new CustomerNotFoundException();
    }
    @Override
    public void deleteById(UUID id) {
        Optional<JpaCustomer> jpaCustomer = extendedJpaCustomerRepository.findById(id);
        if (jpaCustomer.isPresent()) {
            extendedJpaCustomerRepository.deleteById(id);
        }else {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> getAll() {
        return extendedJpaCustomerRepository.findAll().stream().map(JpaCustomer::toCustomer).collect(Collectors.toList());
    }
}
