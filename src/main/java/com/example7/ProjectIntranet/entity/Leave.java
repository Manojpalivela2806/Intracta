package com.example7.ProjectIntranet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String leaveReason;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "attendanceId")
    private Attendance attendance;
    @ManyToOne
    @JsonIgnore
    @JoinColumn
        private Employee employee;
}
