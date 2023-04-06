package com.example7.ProjectIntranet.Repository;

import com.example7.ProjectIntranet.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo  extends JpaRepository<Attendance, Long> {
    List<Attendance> findByMonthNo(Integer monthNo);
    ;
}
