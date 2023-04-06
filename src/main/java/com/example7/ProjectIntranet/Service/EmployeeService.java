package com.example7.ProjectIntranet.Service;

import com.example7.ProjectIntranet.Repository.AttendanceRepo;
import com.example7.ProjectIntranet.Repository.EmployeeRepo;
import com.example7.ProjectIntranet.Repository.ProjectRepo;
import com.example7.ProjectIntranet.dto.*;
import com.example7.ProjectIntranet.entity.*;
import com.example7.ProjectIntranet.exception.EmployeeNotFoundException;
import com.example7.ProjectIntranet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    AttendanceRepo attendanceRepo;

    public String createEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDto.getEmployeeName());
        employee.setEmployeeEmail(employeeDto.getEmployeeEmail());
        employee.setEmployeeRole(employeeDto.getEmployeeRole());
        employee.setEmployeeAdmin(employeeDto.isEmployeeAdmin());
        if(employeeDto.getEmployeeRole().equalsIgnoreCase("Manager"))
        {
            employee.setEmpProStatus(empProStatus.WORKING);
        }
        else {
            employee.setEmpProStatus(empProStatus.BENCH);
        }
        if (employee != null) {
            employeeRepo.save(employee);
            return "employee is saved";
        } else {
            return "employee is not saved";
        }
    }

    public List<ProjectDto> getEmployeesFromProject()
    {
        List<Project> p1 =projectRepo.findAll();
        List<ProjectDto>projectDtos2 = new ArrayList<>();
        if(!p1.isEmpty())
        {
            for(Project project:p1)
            {
                ProjectDto projectDto = new ProjectDto();
                projectDto.setProjectName(project.getProjectName());
                projectDto.setProjectStartDate(project.getProjectStartDate());
                projectDto.setProjectEndDate(project.getProjectEndDate());
                project.setProjectStatus(project.getProjectStatus());
                List<Employee> emp =project.getEmployee();
                List<EmployeeDto2> employeeDto2s = new ArrayList<>();
                for(Employee employee:emp)
                {
                    EmployeeDto employeeDto=new EmployeeDto();
                    employeeDto.setEmployeeName(employee.getEmployeeName());
                    employeeDto.setEmployeeRole(employee.getEmployeeRole());
                    employeeDto.setEmployeeEmail(employee.getEmployeeEmail());
                    employeeDto.setEmployeeAdmin(employee.isEmployeeAdmin());
                    Date date = new Date();
                    if(date.after(project.getProjectEndDate()))
                    {  employeeDto.setEmpProStatus(empProStatus.COMPLETED);}
                    else{   employeeDto.setEmpProStatus(empProStatus.WORKING);}
                }
                projectDto.setEmployee(employeeDto2s);
                projectDtos2.add(projectDto);
            }
            return projectDtos2;
        }
        else {
            throw new ResourceNotFoundException("No Projects found");
        }
    }
    public EmployeeDto getEmployeeByMail(String employeeMail) throws EmployeeNotFoundException {
        Employee employee = employeeRepo.findById(employeeMail).
                orElseThrow(()->new EmployeeNotFoundException("Employee not found with mail"+employeeMail));
        return mapEmployeeToDto(employee);
    }
    private EmployeeDto mapEmployeeToDto(Employee employee)
    {
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDto.setEmployeeName(employee.getEmployeeName());
        employeeDto.setEmployeeRole(employee.getEmployeeRole());
        employeeDto.setEmployeeAdmin(employee.isEmployeeAdmin());
        return employeeDto;
    }
    public List<ProjectDto2> getEmployeeProjectDetails(String employeeEmail) {
        Optional<Employee> byId = employeeRepo.findById(employeeEmail);
        if(byId.isPresent())
        {
            Employee employee = byId.get();
            List<Project> projects = employee.getProjects();
            List<ProjectDto2> projectDto2 = new ArrayList<>();
            for(Project project1:projects)
            {
                ProjectDto2 projectDto = new ProjectDto2();
                projectDto.setProjectName(project1.getProjectName());
                projectDto.setProjectStartDate(project1.getProjectStartDate());
                projectDto.setProjectEndDate(project1.getProjectEndDate());
                Date date = new Date();
                if(date.after(projectDto.getProjectEndDate()))
                {projectDto.setProjectStatus(ProjectStatus.COMPLETED);}
                else{projectDto.setProjectStatus(ProjectStatus.ONGOING);}
                if(projectDto.getProjectStatus().equals(ProjectStatus.COMPLETED))
                {projectDto.setEmpProStatus(empProStatus.COMPLETED);}
                else {projectDto.setEmpProStatus(empProStatus.WORKING);}
                projectDto2.add(projectDto);
            }
            return projectDto2;
        }
        else {
            throw new ResourceNotFoundException("Employee data not found");
        }
    }

//    public String addAttendanceToEmployee(EmployeeDto3 employeeDto3)
//    {
//        List<Attendance> attendances = new ArrayList<>();
//        Employee referenceById = employeeRepo.getReferenceById(employeeDto3.getEmployeeEmail());
//        if(referenceById!=null)
//        {
//            List<AttendanceDto> attendance2 = employeeDto3.getAttendance();
//            for(AttendanceDto attendance: attendance2)
//            {
//                Optional<Attendance> byId = attendanceRepo.findById(attendance.getAttendanceId());
//                if (byId.isPresent()) {
//                    Attendance attendance1 = byId.get();
//                    attendances.add(attendance1);
//                    referenceById.setAttendance(attendances);
//                }
//            }
//        }
//
//        employeeRepo.save(referenceById);
//        return "Employee attendance added";
//    }

    boolean isWithinRange(Date projectStartDate , Date projectEndDate ) {
        if (projectStartDate.before(projectEndDate)) {
            return true;
        } else {
            return false;}
    }
}
