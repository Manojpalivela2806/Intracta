package com.example7.ProjectIntranet.Controller;

import com.example7.ProjectIntranet.Service.ProjectService;
import com.example7.ProjectIntranet.dto.LeaveDto;
import com.example7.ProjectIntranet.dto.ProjectDto;
import com.example7.ProjectIntranet.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/Project")
@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @PostMapping("/create")
    public ResponseEntity<String> createProject(@Valid @RequestBody ProjectDto ProjectDto,@RequestParam String employeeEmail)
    {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.createProject(ProjectDto,employeeEmail));
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateProject(@Valid @RequestBody ProjectDto ProjectDto,@RequestParam String employeeEmail)
    {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(ProjectDto,employeeEmail));
    }
    @GetMapping("/getby")
    public ResponseEntity<ProjectDto> gettingEmployeesWhoAreAssignedToParticularProject(@RequestParam String projectName)
    {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.gettingEmployeesWhoAreAssignedToParticularProject(projectName));
    }
    @GetMapping("leavesInProject")
    public  List<Leave> getLeavesForProjectAndMonth( @RequestParam String projectName, @RequestParam int monthNo)
    {
        return projectService.getLeavesForProjectAndMonth(projectName,monthNo);
    }
}


