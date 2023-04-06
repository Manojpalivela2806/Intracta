package com.example7.ProjectIntranet.Repository;

import com.example7.ProjectIntranet.entity.Employee;
import com.example7.ProjectIntranet.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepo extends JpaRepository<Leave,Long> {
    List<Leave> findByLeaveStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Leave> findByLeaveStartDateBetweenOrLeaveEndDateBetween(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);

}
