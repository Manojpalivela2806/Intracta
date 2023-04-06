package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.Attendance;
import com.example7.ProjectIntranet.entity.Employee;
import com.example7.ProjectIntranet.entity.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {
    private Long leaveID;
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    @NotNull
    private LocalDate leaveStartDate;
    @NotNull
    private LocalDate leaveEndDate;
    @NotBlank
    private String leaveReason;
    private Attendance attendance;
    private Employee employee;

}
