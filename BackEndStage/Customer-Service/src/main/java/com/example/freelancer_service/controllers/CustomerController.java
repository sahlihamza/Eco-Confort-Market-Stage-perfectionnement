package com.example.freelancer_service.controllers;

import com.example.freelancer_service.Payload.Credentials;
import com.example.freelancer_service.entity.Customer;
import com.example.freelancer_service.services.CustomerService;
import com.example.freelancer_service.jwt.JwtTokenUtil;
import com.example.freelancer_service.repository.CustomerRepository;
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
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsImpl userservice;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updateCustomer) {
        Customer customer = customerService.updateCustomer(id, updateCustomer);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomer(){

        List<Customer> customers = customerService.getAllCustomer();
        if(!customers.isEmpty()){
            return ResponseEntity.ok(customers);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Credentials parametre){
        try {
            if(!customerService.getByEmail(parametre.getEmail())) {
                return new ResponseEntity<String>("User Not Found",HttpStatus.NOT_FOUND);
            }
            Authentication authsuser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(parametre.getEmail(), parametre.getPassword()));
            UserDetails user_detailts=userservice.loadUserByUsername(parametre.getEmail());
            String token=jwtTokenUtil.generateToken(user_detailts);
            JSONObject json=new JSONObject();
            json.appendField("user", customerRepository.getByEmail(parametre.getEmail()));
            json.appendField("token",token);
            return ResponseEntity.ok(json);
        }catch(BadCredentialsException e) {
            return new ResponseEntity<String>("Incorrect email or password",HttpStatus.NOT_FOUND);
        }
    }

}