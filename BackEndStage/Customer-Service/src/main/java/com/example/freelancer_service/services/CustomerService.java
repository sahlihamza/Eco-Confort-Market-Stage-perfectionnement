    package com.example.freelancer_service.services;

    //import com.example.freelancer_service.NodeSync.NodeSync;
    import com.example.freelancer_service.entity.Customer;
    import com.example.freelancer_service.repository.CustomerRepository;
    import com.example.freelancer_service.security.SecurityConfig;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import net.minidev.json.JSONObject;

    import java.util.List;

    @Service
    public class CustomerService {

        @Autowired
        SecurityConfig securityConfig;

        @Autowired
        private CustomerRepository customerRepository;

       // @Autowired
        //NodeSync nodeSync;
        public Customer createCustomer(Customer customer) {
            JSONObject jsonObject=new JSONObject();

            jsonObject.appendField("email", customer.getEmail());

            customer.setPassword(securityConfig.passwordEncoder().encode(customer.getPassword()));
            //nodeSync.addFreelancer(jsonObject);
            return customerRepository.save(customer);
        }
        public List<Customer> getAllCustomer(){
            return customerRepository.findAll();
        }
        public Customer getCustomerById(Long CustomerId){
            return customerRepository.findById(CustomerId).orElse(null);
        }
        public void deleteCustomer(Long CustomerId){
            customerRepository.deleteById(CustomerId);
        }
        public Customer updateCustomer(Long CustomerId, Customer updateCustomer){
            Customer existingCustomer = customerRepository.findById(CustomerId).orElse(null);
            if(existingCustomer != null){

                existingCustomer.setPassword(updateCustomer.getPassword());

                return customerRepository.save(existingCustomer);
            }
            return null;
        }

        public boolean getByEmail(String email) {
           return customerRepository.findByEmail(email) != null;
        }

    }

