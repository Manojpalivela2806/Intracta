package com.example7.ProjectIntranet.Repository;

import com.example7.ProjectIntranet.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepo extends JpaRepository <Project,String>
{

}
