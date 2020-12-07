package com.udacity.jdnd.course3.critter.entity;

import java.util.List;
import java.util.Set;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;


import javax.annotation.Generated;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> pets;
    @ManyToMany(targetEntity = Customer.class)
    private List<Customer> customers;
    @ElementCollection
    private Set<EmployeeSkill> activities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }    

}
