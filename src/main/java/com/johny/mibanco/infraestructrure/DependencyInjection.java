package com.johny.mibanco.infraestructrure;

import com.johny.mibanco.domain.CustomerRepository;
import com.johny.mibanco.infraestructrure.repositories.ExtendedJpaCustomerRepository;
import com.johny.mibanco.infraestructrure.repositories.JpaCustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjection {

    @Bean
    public CustomerRepository CustomerRepository(ExtendedJpaCustomerRepository extendedJpaCustomerRepository) {
        return new JpaCustomerRepository(extendedJpaCustomerRepository);
    }
}
