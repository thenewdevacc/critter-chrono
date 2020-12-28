package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    private EmployeeService employeeService;
/*
    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }
 */

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = customerService.save(customer);
        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> allCustomers = customerService.findAll();
        List<CustomerDTO> allCustomerDTO = new ArrayList<>();
        for (Customer customer : allCustomers) {
            allCustomerDTO.add(convertCustomerToCustomerDTO(customer));
        }
        return allCustomerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getOwnerByPetId(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee empl = new Employee();
        BeanUtils.copyProperties(employeeDTO, empl);
        empl = employeeService.saveEmployee(empl);
        employeeDTO.setId(empl.getId());
        /*
        System.out.println("Name Set : " + empl.getName());
        System.out.println("ID Set : " + empl.getId() + " : " + employeeDTO.getId());
         */
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        //Employee empl = employeeService.getEmployeeByID(employeeId);
        //System.out.println("What we found : " + empl.getId() + " : " + empl.getName());
        BeanUtils.copyProperties(employeeService.getEmployeeByID(employeeId), employeeDTO);
        //System.out.println(employeeDTO.getName());
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesWithSkill = employeeService.getEmployeeBySkillAndDate(employeeDTO.getSkills(), employeeDTO.getDate());
        List<EmployeeDTO> emplDTO = new ArrayList<EmployeeDTO>();
        for(Employee empl : employeesWithSkill) {
            EmployeeDTO edto = new EmployeeDTO();
            BeanUtils.copyProperties(empl, edto);
            emplDTO.add(edto);
        }
        return emplDTO;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> petsOfCustomer =  customer.getPets();
        List<Long> customerDTOPetIds = new ArrayList<>();
        for(Pet pet : petsOfCustomer) {
            customerDTOPetIds.add(pet.getId());
        }
        customerDTO.setPetIds(customerDTOPetIds);
        return customerDTO;
    }

}