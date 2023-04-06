package com.example7.ProjectIntranet.Service;

import com.example7.ProjectIntranet.Repository.AttendanceRepo;
import com.example7.ProjectIntranet.Repository.EmployeeRepo;
import com.example7.ProjectIntranet.Repository.ProjectRepo;
import com.example7.ProjectIntranet.dto.*;
import com.example7.ProjectIntranet.entity.*;
import com.example7.ProjectIntranet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class ProjectService {
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    public String createProject(ProjectDto projectDto, String employeeEmail) {
        try {
            Optional<Employee> byId1 = employeeRepo.findById(employeeEmail);
            if (byId1.isPresent()) {
                Employee employee2 = byId1.get();
                if (employee2.getEmployeeRole().equalsIgnoreCase("Manager") && employee2.isEmployeeAdmin() == true) {
                    Project project = new Project();
                    Date date = new Date();
                    if (date.after(projectDto.getProjectEndDate())) {
                        project.setProjectStatus(ProjectStatus.COMPLETED);
                    } else {
                        project.setProjectStatus(ProjectStatus.ONGOING);
                    }
                    project.setProjectName(projectDto.getProjectName());
                    project.setProjectStartDate(projectDto.getProjectStartDate());
                    boolean withinRange = isWithinRange(projectDto.getProjectStartDate(), projectDto.getProjectEndDate());
                    project.setProjectStatus(projectDto.getProjectStatus());
                    project.setProjectEndDate(projectDto.getProjectEndDate());
                    List<EmployeeDto2> employeeDto2s = projectDto.getEmployee();
                    List<Employee> employees = new ArrayList<>();
                    for (EmployeeDto2 employee : employeeDto2s) {
                        Optional<Employee> byId = employeeRepo.findById(employee.getEmployeeEmail());
                        if (byId.isPresent()) {
                            Employee employee1 = byId.get();
                            employee1.setEmployeeEmail(employee.getEmployeeEmail());
                            employee1.setEmpProStatus(empProStatus.WORKING);
                            employees.add(employee1);
                        }
                    }
                    if (project != null && withinRange == true) {
                        project.setEmployee(employees);
                        projectRepo.save(project);
                        return "project is created";
                    } else {
                        return "project is not saved may be because of un proper endDate ";
                    }
                } else {
                    return "employee is not a manager not  not an admin to assign project";
                }
            } else {
                return "employee not found";
            }
        } catch (NullPointerException e) {
            return "passing Employee object is mandatory,passing employees is optional";
        }
    }
    public String updateProject(ProjectDto projectDto, String employeeEmail) {

        Optional<Employee> byId1 = employeeRepo.findById(employeeEmail);
        if (byId1.isPresent()) {
            Employee employee2 = byId1.get();
            if (employee2.isEmployeeAdmin() == true && employee2.getEmployeeRole().equalsIgnoreCase("Manager")) {
                Optional<Project> optionalProject = projectRepo.findById(projectDto.getProjectName());
                if (optionalProject.isPresent()) {
                    Project project = optionalProject.get();
                    project.setProjectName(projectDto.getProjectName());
                    project.setProjectStartDate(projectDto.getProjectStartDate());
                    boolean withinRange = isWithinRange(projectDto.getProjectStartDate(), projectDto.getProjectEndDate());
                    project.setProjectEndDate(projectDto.getProjectEndDate());
                    Date date = new Date();
                    if (date.after(projectDto.getProjectEndDate())) {
                        project.setProjectStatus(ProjectStatus.COMPLETED);
                    } else {
                        project.setProjectStatus(ProjectStatus.ONGOING);
                    }
                    List<EmployeeDto2> employeeDto2s = projectDto.getEmployee();
                    List<Employee> employees = new ArrayList<>();
                    for (EmployeeDto2 employee : employeeDto2s) {
                        Optional<Employee> byId = employeeRepo.findById(employee.getEmployeeEmail());
                        if (byId.isPresent()) {
                            Employee employee1 = byId.get();
                            employee1.setEmployeeEmail(employee.getEmployeeEmail());
                            employee1.setEmpProStatus(empProStatus.WORKING);
                            employees.add(employee1);
                        }
                    }
                    if (withinRange == true) {
                        project.setEmployee(employees);
                        projectRepo.save(project);
                    } else {
                        return "project not updated check whether given project date is correct or not ";
                    }
                    return "project Updated";
                } else {
                    return "project is not present to update  ";
                }
            } else {
                return "only manager has the authority to update project";
            }
        } else {
            return "email not present";
        }
    }
    public ProjectDto gettingEmployeesWhoAreAssignedToParticularProject(String projectName) {
        Optional<Project> project = projectRepo.findById(projectName);
        if (project.isPresent()) {
            Project project1 = project.get();
            ProjectDto projectDto = new ProjectDto();
            projectDto.setProjectName(project1.getProjectName());
            projectDto.setProjectStartDate(project1.getProjectStartDate());
            projectDto.setProjectEndDate(project1.getProjectEndDate());
            Date date = new Date();
            if (date.after(projectDto.getProjectEndDate())) {
                projectDto.setProjectStatus(ProjectStatus.COMPLETED);
            } else {
                projectDto.setProjectStatus(ProjectStatus.ONGOING);
            }
            List<Employee> employees = project1.getEmployee();
            List<EmployeeDto2> employeeDto2s = new ArrayList<>();
            for (Employee employee : employees) {
                EmployeeDto2 employeeDto2 = new EmployeeDto2();
                employeeDto2.setEmployeeEmail(employee.getEmployeeEmail());
                employeeDto2.setEmployeeName(employee.getEmployeeName());
                if (projectDto.getProjectStatus().equals(ProjectStatus.COMPLETED)) {
                    employeeDto2.setEmpProStatus(empProStatus.COMPLETED);
                } else {
                    employeeDto2.setEmpProStatus(empProStatus.WORKING);
                }

                employeeDto2s.add(employeeDto2);
            }
            projectDto.setEmployee(employeeDto2s);
            return projectDto;
        } else {
            throw new ResourceNotFoundException("Project name not found");
        }
    }

    boolean isWithinRange(Date projectStartDate, Date projectEndDate) {
        if (projectStartDate.before(projectEndDate)) {
            return true;
        } else {
            return false;
        }
    }
    public List<Leave> getLeavesForProjectAndMonth(String projectName, int monthNo)  {
        try {
            List<Leave> commonLeaves = new ArrayList<>();
            List<Leave> leaves1 = getLeaveOnProject(projectName);
            if (leaves1 == null) {
                throw new NullPointerException("Leaves not found on project: " + projectName);
            }
            List<Leave> leaves2 = getLeavesByMonth(monthNo);
            if (leaves2 == null) {
                throw new NullPointerException("Leaves not found on month: " + monthNo);
            }
            for (Leave leave : leaves1) {
                for (Leave leave1 : leaves2) {
                    if (leave == leave1) {
                        commonLeaves.add(leave1);
                        break;
                    }
                }
            }
            return commonLeaves;
        }
        catch (NullPointerException nullPointerException)
        {
            nullPointerException.getMessage();
        }
        throw new ResourceNotFoundException("leaves not Found Or for the above month project is not available");
    }
    public List<Leave> getLeaveOnProject(String projectName) {
        Optional<Project> byId = projectRepo.findById(projectName);
        if(byId.isPresent())

        {
            Project project = byId.get();
            List<Employee> employeeList = project.getEmployee();
            List<Leave> approvedLeaves = new ArrayList<>();
            for (Employee employee : employeeList) {
                List<Attendance> attendanceList = employee.getAttendance();
                for (Attendance attendance : attendanceList) {
                    List<Leave> leaveList = attendance.getLeave();
                    Iterator<Leave> leaveIterator = leaveList.iterator();
                    while (leaveIterator.hasNext()) {
                        Leave leave = leaveIterator.next();
                        if (leave.getLeaveStatus().equals(LeaveStatus.APPROVED)) {
                            approvedLeaves.add(leave);
                        } else {
                            leaveIterator.remove();
                        }
                    }
                }
            }
            return approvedLeaves;
        }
        return Collections.emptyList();
    }

    public List<Leave> getLeavesByMonth(int monthNo) {
        List<Leave> leaves1 = new ArrayList<>();
        List<Attendance> byMonthNo = attendanceRepo.findByMonthNo(monthNo);
        for (Attendance attendance : byMonthNo) {
            int monthNo1 = attendance.getMonthNo();
            Set<LocalDate> localDates = attendance.getAttendanceDetails().keySet();
            for (LocalDate date : localDates) {
                String s = String.valueOf(monthNo);
                Month of = Month.of(monthNo);
                if (of == date.getMonth()) {
                    Map<LocalDate, AttendanceStatus> attendanceDetails = attendance.getAttendanceDetails();
                    for (Map.Entry<LocalDate, AttendanceStatus> entry : attendanceDetails.entrySet()) {
                        String date1 = String.valueOf(entry.getKey());
                        String status = String.valueOf(entry.getValue());
                        if ("ABSENT".equals(status)) {
                            List<Leave> leave = attendance.getLeave();
                            return leave;
                        }
                    }
                }
            }
        }
        return null;
    }
}









