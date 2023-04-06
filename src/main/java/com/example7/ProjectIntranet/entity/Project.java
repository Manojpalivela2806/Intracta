package com.example7.ProjectIntranet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    private String projectName;
    private Date projectStartDate;
    private Date projectEndDate;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    @ManyToMany
    @JsonIgnore
    private List<Employee> employee;
}
