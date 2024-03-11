package com.example.freelancer_service.repository;

import com.example.freelancer_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByEmail(String email);
    @Query(value="select * from admin where email=:email",nativeQuery=true)
    Admin getByEmail(String email);
}
