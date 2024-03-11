package com.example.freelancer_service.services;

//import com.example.freelancer_service.NodeSync.NodeSync;
import com.example.freelancer_service.entity.Admin;
import com.example.freelancer_service.repository.AdminRepository;
import com.example.freelancer_service.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.minidev.json.JSONObject;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    private AdminRepository adminRepository;

   // @Autowired
    //NodeSync nodeSync;
    public Admin createAdmin(Admin admin) {
        JSONObject jsonObject=new JSONObject();

        jsonObject.appendField("email", admin.getEmail());

        admin.setPassword(securityConfig.passwordEncoder().encode(admin.getPassword()));
        //nodeSync.addFreelancer(jsonObject);
        return adminRepository.save(admin);
    }
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
    public Admin getAdminById(Long AdminId){
        return adminRepository.findById(AdminId).orElse(null);
    }
    public void deleteAdmin(Long AdminId){
        adminRepository.deleteById(AdminId);
    }
    public Admin updateAdmin(Long AdminId, Admin updateAdmin){
        Admin existingAdmin = adminRepository.findById(AdminId).orElse(null);
        if(existingAdmin != null){

            existingAdmin.setPassword(updateAdmin.getPassword());

            return adminRepository.save(existingAdmin);
        }
        return null;
    }

    public boolean getByEmail(String email) {
       return adminRepository.findByEmail(email) != null;
    }

}

