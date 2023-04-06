package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto3 {
    private String employeeEmail;
    private List<AttendanceDto> attendance;
}
