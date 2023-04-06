package com.example7.ProjectIntranet.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmployeeRoleValidator.class)
public @interface EmployeeRole  {
    public String message() default "Invalid employeeRole it should be Developer,Tester,Manager or Admin";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
