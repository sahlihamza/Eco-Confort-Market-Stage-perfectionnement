package com.example.freelancer_service.repository;

import com.example.freelancer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);
    @Query(value="select * from customer where email=:email",nativeQuery=true)
    Customer getByEmail(String email);
}
