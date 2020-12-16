package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javafx.concurrent.ScheduledService;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule = convertScheduleDTOtoEntity(scheduleDTO);
        List<Pet> pets = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        scheduleDTO.getPetIds().forEach(petid -> pets.add(petService.getPet(petid)));
        scheduleDTO.getEmployeeIds().forEach(empid -> employees.add(employeeService.getEmployee(empid)));
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return convertEntityToScheduleDTO(scheduleService.createSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getAllSchedules().forEach(schedule -> scheduleDTOS.add(convertEntityToScheduleDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getScheduleForPet(petService.getPet(petId)).forEach(schedule -> scheduleDTOS.add(convertEntityToScheduleDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getScheduleForEmployee(employeeService.getEmployee(employeeId)).forEach(schedule -> scheduleDTOS.add(convertEntityToScheduleDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(customerId);
        pets.forEach(pet->scheduleService.getScheduleForPet(pet).forEach(schedule -> scheduleDTOS.add(convertEntityToScheduleDTO(schedule))));
        return scheduleDTOS;
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;  
    } 

    private Schedule convertScheduleDTOtoEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
