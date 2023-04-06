package com.example7.ProjectIntranet.Service;

import com.example7.ProjectIntranet.Repository.AttendanceRepo;
import com.example7.ProjectIntranet.Repository.EmployeeRepo;
import com.example7.ProjectIntranet.Repository.LeaveRepo;
import com.example7.ProjectIntranet.dto.LeaveDto;
import com.example7.ProjectIntranet.entity.*;
import com.example7.ProjectIntranet.exception.InvalidInputException;
import com.example7.ProjectIntranet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class LeaveService {
    @Autowired
    LeaveRepo leaveRepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    EmployeeRepo employeeRepo;

    public String applyForLeave(LeaveDto leaveDto) throws InvalidInputException {
        if (leaveDto.getLeaveEndDate().isBefore(leaveDto.getLeaveStartDate()))
        {
            throw new ResourceNotFoundException("End date should be after start date");
        }
        if (ChronoUnit.DAYS.between(leaveDto.getLeaveStartDate(),leaveDto.getLeaveEndDate()) > 2)
        {
            throw
            new InvalidInputException("Maximum leave duration is 2 days");
        }
        List<Leave> leaves = leaveRepo.findByLeaveStartDateBetween(leaveDto.getLeaveStartDate().minusDays(1),leaveDto.getLeaveEndDate().plusDays(1));
        if (leaves.size() > 2) {
            throw new InvalidInputException("Maximum 2 leaves allowed per month");
        }
        Leave leave = new Leave();
        Optional<Employee> byId = employeeRepo.findById(leaveDto.getEmployee().getEmployeeEmail());
        if(byId.isPresent())
        {
            Employee employee = byId.get();
            List<Attendance> attendance = employee.getAttendance();
            for(Attendance attendance1: attendance )
            {

                if (attendance1.getAttendanceDetails().keySet().contains(leaveDto.getLeaveStartDate()) && attendance1.getAttendanceDetails().keySet().contains(leaveDto.getLeaveStartDate())) {
                    leave.setEmployee(employee);
                    leave.setAttendance(attendance1);
                    leave.setLeaveStartDate(leaveDto.getLeaveStartDate());
                    leave.setLeaveEndDate(leaveDto.getLeaveEndDate());
                    leave.setLeaveReason(leaveDto.getLeaveReason());
                    leave.setLeaveStatus(LeaveStatus.PENDING);
                    leaveRepo.save(leave);
                    return "leaveSaved";
                }
            }

            return "AttendanceIdNotFound";
        }
       else{
           return "LeaveNotApplied employee Not found";
        }
    }

    public String ApproveLeave(LeaveDto leaveDto,String employeeEmail)
    {
        Optional<Employee> byId3 = employeeRepo.findById(employeeEmail);
        if(byId3.get().isEmployeeAdmin()==true && byId3.get().getEmployeeRole().equalsIgnoreCase("Manager"))
        {
            Optional<Employee> byId1 = employeeRepo.findById(leaveDto.getEmployee().getEmployeeEmail());
            if(byId1.isPresent())
            {
                Employee employee1 = byId1.get();
                List<Leave> leave = employee1.getLeave();
                for(Leave leave1 :leave)
                {
                    List<Attendance> attendance2 = employee1.getAttendance();
                    for (Attendance attendance : attendance2) {
                        if(leaveDto.getLeaveID()== leave1.getLeaveId()) {
                            Optional<Leave> byId2 = leaveRepo.findById(leaveDto.getLeaveID());
                            if (byId2.isPresent()) {
                                Leave leave2 = byId2.get();
                                while (leaveDto.getLeaveStatus().equals(LeaveStatus.APPROVED)) {
                                    Attendance attendance1 = leave2.getAttendance();
                                    leave2.setLeaveStatus(leaveDto.getLeaveStatus());
                                    LocalDate leaveStartDate = leave2.getLeaveStartDate();
                                    Map<LocalDate, AttendanceStatus> attendanceDetails = attendance1.getAttendanceDetails();
                                    LocalDate leaveEndDate = leave2.getLeaveEndDate();
                                    attendance1.getAttendanceDetails();
                                    while (!leaveStartDate.isAfter(leaveEndDate)) {
                                        attendanceDetails.put(leaveStartDate, AttendanceStatus.ABSENT);
                                        leaveStartDate = leaveStartDate.plusDays(1);
                                        attendanceRepo.save(attendance1);
                                        leaveRepo.save(leave2);
                                    }
                                    return "leave Approved and Updated";

                                }
                                while (leaveDto.getLeaveStatus().equals(LeaveStatus.CANCELED)) {
                                    Attendance attendance1 = leave2.getAttendance();
                                    leave2.setLeaveStatus(leaveDto.getLeaveStatus());
                                    LocalDate leaveStartDate = leave2.getLeaveStartDate();
                                    Map<LocalDate, AttendanceStatus> attendanceDetails = attendance1.getAttendanceDetails();
                                    LocalDate leaveEndDate = leave2.getLeaveEndDate();
                                    attendance1.getAttendanceDetails();
                                    while (!leaveStartDate.isAfter(leaveEndDate)) {
                                        attendanceDetails.put(leaveStartDate, AttendanceStatus.PRESENT);
                                        leaveStartDate = leaveStartDate.plusDays(1);
                                        attendanceRepo.save(attendance1);
                                        leaveRepo.save(leave2);
                                    }
                                    return "leave Cancelled and leave Updated";

                                }
                                while (leaveDto.getLeaveStatus().equals(LeaveStatus.PENDING)) {
                                    Attendance attendance1 = leave2.getAttendance();
                                    leave2.setLeaveStatus(leaveDto.getLeaveStatus());
                                    LocalDate leaveStartDate = leave2.getLeaveStartDate();
                                    Map<LocalDate, AttendanceStatus> attendanceDetails = attendance1.getAttendanceDetails();
                                    LocalDate leaveEndDate = leave2.getLeaveEndDate();
                                    attendance1.getAttendanceDetails();
                                    while (!leaveStartDate.isAfter(leaveEndDate)) {
                                        attendanceDetails.put(leaveStartDate, AttendanceStatus.PRESENT);
                                        leaveStartDate = leaveStartDate.plusDays(1);
                                        attendanceRepo.save(attendance1);
                                        leaveRepo.save(leave2);
                                    }
                                    return "leave in pending and Updated";

                                }

                            }
                        }
                    }
                }
            }
            return"employee email is not found";
        }
        else
        {
            return "employee not found to update the leave";
        }
    }
}




