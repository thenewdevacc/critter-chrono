package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule,Long>{
    
    public List<Schedule> findAllByPet(Pet pet);

    public List<Schedule> findAllByEmployee(Employee employee);
}
