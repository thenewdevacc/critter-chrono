package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private CustomerService customerService;

    public Customer getOwnerByPet(Long id){
        return petRepository.findById(id).get().getCustomer();
    }

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPet(Long id){
        return petRepository.findById(id).get();
    }

    public List<Pet> getPets(){
        List<Pet> pets = new ArrayList<>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    public List<Pet> getPetsByOwner(Long id){
        return petRepository.findAllByCustomer(customerService.getCustomer(id));
    }
}
