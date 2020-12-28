package com.udacity.jdnd.course3.critter.pet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    PetService petService;
    CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        Long custmerId = petDTO.getOwnerId();
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        Pet newPet = petService.savePet(pet, custmerId);
        return copyPetToDTO(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        PetDTO petDTO = copyPetToDTO(pet);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> allPets = petService.getAllPets();
        return copyPetListToDTO(allPets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return copyPetListToDTO(petService.getPetsByOwner(ownerId));
    }


    private List<PetDTO> copyPetListToDTO(List<Pet> allPets) {
        List<PetDTO> allPetDTO = new ArrayList<PetDTO>();

        for (Pet pet: allPets ) {
            PetDTO petDTO= new PetDTO();
            petDTO = copyPetToDTO(pet);
            allPetDTO.add(petDTO);
        }

        return allPetDTO;
    }


    private PetDTO copyPetToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        petDTO.setOwnerId(pet.getOwnerId().getId());
        return petDTO;
    }

}
