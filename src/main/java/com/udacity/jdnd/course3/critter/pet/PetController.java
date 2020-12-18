package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.minidev.json.writer.BeansMapper.Bean;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping()
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOtoEntity(petDTO);
        pet.setCustomer(customerService.getCustomer(petDTO.getOwnerId()));
        // pet.setCustomer(customerService.getCustomer(1L));
        return convertEntityToPetDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertEntityToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> petDTOs.add(convertEntityToPetDTO(pet)));
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> petDTOs.add(convertEntityToPetDTO(pet)));
        return petDTOs;
    }

    private PetDTO convertEntityToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;  
    } 

    private Pet convertPetDTOtoEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
