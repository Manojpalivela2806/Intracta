package com.example7.ProjectIntranet.entity;

import com.example7.ProjectIntranet.validation.EmployeeRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    private String employeeName;
    @Id
    private String employeeEmail;
    private String employeeRole;
    @JsonProperty
    private boolean isEmployeeAdmin;

    @Enumerated(EnumType.STRING)
    private empProStatus empProStatus;

    @ManyToMany(mappedBy ="employee")
    private List<Project> projects;
    @OneToMany(mappedBy = "employees")
    private List<Attendance> attendance;

    @OneToMany(mappedBy = "employee")
    private List<Leave> leave;
}
