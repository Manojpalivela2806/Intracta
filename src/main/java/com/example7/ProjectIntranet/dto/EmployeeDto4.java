package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto4 {
    private String employeeName;
    private String employeeEmail;
    private List<LeaveDto2> leave;
}
