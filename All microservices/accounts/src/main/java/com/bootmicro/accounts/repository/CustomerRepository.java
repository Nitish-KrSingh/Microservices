package com.bootmicro.accounts.repository;

import com.bootmicro.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

   Optional<Customer> findByMobileNumber(String mobileNumber);
}
