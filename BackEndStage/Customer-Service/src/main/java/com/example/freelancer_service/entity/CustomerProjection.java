package com.example.freelancer_service.entity;


import org.springframework.data.rest.core.config.Projection;

@Projection(name="full_customer",types = Customer.class)
public interface CustomerProjection extends Projection {
    public long getId();
    public String getEmail();

}
