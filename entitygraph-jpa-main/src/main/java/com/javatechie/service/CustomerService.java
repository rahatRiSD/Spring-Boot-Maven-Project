package com.javatechie.service;

import com.javatechie.entity.Customer;
import com.javatechie.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        if (customer.getAddresses() != null) {
            customer.getAddresses()
                    .forEach(address ->
                            address.setCustomer(customer));
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Exception occurred while fetching customer : " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.fetchCustomersWithAddress();
    }
}
