package com.example7.ProjectIntranet.Controller;

import com.example7.ProjectIntranet.Service.EmployeeService;
import com.example7.ProjectIntranet.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/Employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
//2
    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto)
    {

        return  ResponseEntity.status(HttpStatus.OK).body(employeeService.createEmployee(employeeDto));
    }
    @GetMapping("/getfromproject")
    public ResponseEntity<List<ProjectDto>> getEmployeesFromProject()
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeesFromProject());
    }
    @GetMapping("/getby")
    public ResponseEntity<List<ProjectDto2>> gettingEmployeeProjectDetails(@Valid @RequestParam String employeeEmail)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeProjectDetails(employeeEmail));
    }
//    @PostMapping("/addAttendance")
//    public ResponseEntity<String> addingAttendanceToEmployee(@RequestBody EmployeeDto3 employeeDto3)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(employeeService.addAttendanceToEmployee(employeeDto3));
//    }
}
