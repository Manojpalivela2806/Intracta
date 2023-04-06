package com.example7.ProjectIntranet.Controller;

import com.example7.ProjectIntranet.Service.LeaveService;
import com.example7.ProjectIntranet.dto.LeaveDto;
import com.example7.ProjectIntranet.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Leave")
@RestController
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @PostMapping("applyByAdvance")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveDto leaveDto) throws InvalidInputException {
        return ResponseEntity.status(HttpStatus.OK).body(leaveService.applyForLeave(leaveDto));
    }
    @PutMapping("ApproveLeave")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveDto leaveDto,@RequestParam String employeeEmail)  {
        return ResponseEntity.status(HttpStatus.OK).body(leaveService.ApproveLeave(leaveDto,employeeEmail));
    }

}
