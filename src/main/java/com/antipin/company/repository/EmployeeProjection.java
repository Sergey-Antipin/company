package com.antipin.company.repository;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {

    @Value("#{target.firstname + ' ' + target.lastname}")
    String getFullName();

    String getPosition();

    @Value("#{target.department.name}")
    String getDepartmentName();
}
