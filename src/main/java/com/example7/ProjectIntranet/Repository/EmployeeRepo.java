package com.example7.ProjectIntranet.Repository;

import com.example7.ProjectIntranet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface EmployeeRepo extends JpaRepository<Employee,String> {
}
