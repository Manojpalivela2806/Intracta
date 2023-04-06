package com.example7.ProjectIntranet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;
    private int monthNo;
    @ElementCollection
    @CollectionTable(name = "attendance_details")
    @MapKeyColumn(name = "attendance_date")
    @Column(name = "attendance_status")
    @Enumerated(EnumType.STRING)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Map<LocalDate, AttendanceStatus> attendanceDetails = new HashMap<>();
    @OneToMany(mappedBy = "attendance")
    private List<Leave> leave;
    @ManyToOne
    @JsonIgnore
    private  Employee employees;


}
