package com.example7.ProjectIntranet.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRole,String> {
    @Override
    public boolean isValid(String employeeRole , ConstraintValidatorContext context) {

        List<String> employeeRoles = Arrays.asList("Developer","Tester","Manager","Quality");
        return employeeRoles.contains(employeeRole);
    }
}
