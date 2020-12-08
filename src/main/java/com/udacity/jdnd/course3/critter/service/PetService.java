package com.udacity.jdnd.course3.critter.service;

import java.util.Optional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;

    public Customer getOwnerByPet(Long id){
        return petRepository.findById(id).get().getCustomer();
    }
}
