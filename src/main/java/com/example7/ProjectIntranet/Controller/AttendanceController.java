package com.example7.ProjectIntranet.Controller;

import com.example7.ProjectIntranet.Service.AttendanceService;
import com.example7.ProjectIntranet.dto.AttendanceDto;
import com.example7.ProjectIntranet.dto.AttendanceDto2;
import com.example7.ProjectIntranet.dto.AttendanceDto3;
import com.example7.ProjectIntranet.dto.ProjectDto;
import com.example7.ProjectIntranet.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @PostMapping("/create")
    public ResponseEntity<String> createProject(@Valid @RequestBody AttendanceDto3 attendanceDto3, @RequestParam String employeeEmail)
    {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.createAttendance(attendanceDto3,employeeEmail));
    }

    @GetMapping("/getMonth")
    public ResponseEntity<List<Attendance>> getAttendanceByMonth(@RequestParam Integer monthNo)
    {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendanceByMonth(monthNo));
    }

}
