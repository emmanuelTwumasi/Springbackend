package com.example.Springbackend.controller;

import com.example.Springbackend.exception.ResourceNotFoundException;
import com.example.Springbackend.model.Employee;
import com.example.Springbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localHost:3000")
@RestController
@RequestMapping("/api/v1.0")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employee
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
 //create employee
    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
     return employeeRepository.save(employee);
    }
    //get Employee Id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
       Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found "+employeeId));
       return ResponseEntity.ok().body(employee);
    }

    //update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId
    , @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found "+employeeId));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable(value = "id")long employeeId)throws ResourceNotFoundException {
        Employee employee  = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found "+employeeId));
        employeeRepository.delete(employee);
        Map<String,Boolean> res = new HashMap<String,Boolean>();
        res.put("Deleted",Boolean.TRUE);
        return ResponseEntity.ok(res);
    }
}
