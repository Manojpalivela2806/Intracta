package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.empProStatus;
import com.example7.ProjectIntranet.validation.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto2 {
    @NotBlank
    private String employeeName;
    @Email
    private String employeeEmail;
    @Enumerated(EnumType.STRING)

    private empProStatus empProStatus;

}
