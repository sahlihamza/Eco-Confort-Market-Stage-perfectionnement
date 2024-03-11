package com.example.freelancer_service.controllers;

import com.example.freelancer_service.Payload.Credentials;
import com.example.freelancer_service.entity.Admin;
import com.example.freelancer_service.services.AdminService;
import com.example.freelancer_service.jwt.JwtTokenUtil;
import com.example.freelancer_service.repository.AdminRepository;
import com.example.freelancer_service.security.UserDetailsImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsImpl userservice;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createAdmin = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin updateAdmin) {
        Admin admin = adminService.updateAdmin(id, updateAdmin);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmin(){

        List<Admin> admins = adminService.getAllAdmin();
        if(!admins.isEmpty()){
            return ResponseEntity.ok(admins);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Credentials parametre){
        try {
            if(!adminService.getByEmail(parametre.getEmail())) {
                return new ResponseEntity<String>("User Not Found",HttpStatus.NOT_FOUND);
            }
            Authentication authsuser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(parametre.getEmail(), parametre.getPassword()));
            UserDetails user_detailts=userservice.loadUserByUsername(parametre.getEmail());
            String token=jwtTokenUtil.generateToken(user_detailts);
            JSONObject json=new JSONObject();
            json.appendField("user", adminRepository.getByEmail(parametre.getEmail()));
            json.appendField("token",token);
            return ResponseEntity.ok(json);
        }catch(BadCredentialsException e) {
            return new ResponseEntity<String>("Incorrect email or password",HttpStatus.NOT_FOUND);
        }
    }

}