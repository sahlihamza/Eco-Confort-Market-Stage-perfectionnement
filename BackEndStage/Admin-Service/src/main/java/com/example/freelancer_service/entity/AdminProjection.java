package com.example.freelancer_service.entity;


import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name="full_freelancer",types = Admin.class)
public interface AdminProjection extends Projection {
    public long getId();
    public String getEmail();

}
