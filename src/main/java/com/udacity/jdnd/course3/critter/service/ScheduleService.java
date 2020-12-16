package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        List<Schedule> schedules = new ArrayList<>();
        scheduleRepository.findAll().forEach(schedules::add);;
        return schedules;
    }

    public List<Schedule> getScheduleForPet(Pet pet){ 
        return scheduleRepository.findAllByPet(pet);
    }

    public List<Schedule> getScheduleForEmployee(Employee employee){
        return scheduleRepository.findAllByEmployee(employee);
    }
}
