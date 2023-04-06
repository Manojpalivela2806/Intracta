package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.AttendanceStatus;
import com.example7.ProjectIntranet.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto3 {

    private Long attendanceId;
    private int monthNo;
    @Enumerated(EnumType.STRING)
    private Map<LocalDate, AttendanceStatus> attendanceDetails = new HashMap<>();
    private EmployeeDto employees;
}
