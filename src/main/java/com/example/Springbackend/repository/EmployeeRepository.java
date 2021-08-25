package com.example.Springbackend.repository;

import com.example.Springbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee,Long> {
}
