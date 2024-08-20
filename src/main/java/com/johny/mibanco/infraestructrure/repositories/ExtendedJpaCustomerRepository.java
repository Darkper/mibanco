package com.johny.mibanco.infraestructrure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExtendedJpaCustomerRepository extends JpaRepository<JpaCustomer, UUID> {
}
