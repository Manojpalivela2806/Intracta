package com.example7.ProjectIntranet.Service;

import com.example7.ProjectIntranet.Repository.AttendanceRepo;
import com.example7.ProjectIntranet.Repository.EmployeeRepo;
import com.example7.ProjectIntranet.Repository.ProjectRepo;
import com.example7.ProjectIntranet.dto.*;
import com.example7.ProjectIntranet.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    ProjectRepo projectRepo;
    public String createAttendance(AttendanceDto3 attendanceDto3, String employeeEmail)
    {
        try{
            Optional<Employee> byId1 = employeeRepo.findById(employeeEmail);
            if(byId1.isPresent()) {
                Employee employee2 = byId1.get();
                if(employee2.getEmployeeRole().equalsIgnoreCase("Manager") && employee2.isEmployeeAdmin()==true) {
                    Attendance  attendance = new Attendance();
                    attendance.setAttendanceId(attendanceDto3.getAttendanceId());
                    attendance.setMonthNo(attendanceDto3.getMonthNo());
                    attendance.setAttendanceDetails(attendanceDto3.getAttendanceDetails());
                    Optional<Employee> byId2 = employeeRepo.findById(attendanceDto3.getEmployees().getEmployeeEmail());
                        if (byId2.isPresent()) {
                            Employee employee1 = byId2.get();
                            employee1.setEmployeeEmail(employee1.getEmployeeEmail());
                            employee1.setEmpProStatus(empProStatus.WORKING);
                            attendance.setEmployees(employee1);
                            attendanceRepo.save(attendance);
                            return "attendance saved";
                        }
                        else {
                            return "attendance not saved";
                        }
                }
                else{
                    return "employee is not a manager and not an admin to assign attendance";
                }
            }

            else{
                return "employee not found";
            }
        }
        catch (NullPointerException e)
        {
            return "passing Employee object is mandatory,passing employees is optional";
        }
    }

    public List<Attendance> getAttendanceByMonth(Integer monthNo) {
        return attendanceRepo.findByMonthNo(monthNo);
    }


    }
