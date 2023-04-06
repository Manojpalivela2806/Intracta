package com.example7.ProjectIntranet.dto;

import com.example7.ProjectIntranet.entity.Employee;
import com.example7.ProjectIntranet.entity.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NotBlank
    private String projectName;
    @NotNull
    private Date projectStartDate;
    @NotNull
    private Date projectEndDate;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    private List<EmployeeDto2> employee;
}
