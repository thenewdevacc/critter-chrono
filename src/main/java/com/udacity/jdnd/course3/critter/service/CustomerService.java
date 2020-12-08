package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    
    @Autowired
    private CustomerRepository customerRepository;


    public Customer save(Customer customer){
        customerRepository.save(customer);
        return customer;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

}
