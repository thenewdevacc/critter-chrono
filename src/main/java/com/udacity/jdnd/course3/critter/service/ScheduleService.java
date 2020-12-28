package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setEmployeeIds(employees);
        schedule.setPetIds(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("No Employee for ID : " + employeeId));
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> getScheduleByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(()-> new ResourceNotFoundException("No Pet for ID : " + petId));
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getScheduleByCustomerId(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("No Customer with ID : " + customerId));
        List<Pet> customerPets = customer.getPets();
        return scheduleRepository.getAllByPetsIn(customerPets);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}