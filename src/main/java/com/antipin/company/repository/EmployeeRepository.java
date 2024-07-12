package com.antipin.company.repository;

import com.antipin.company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    EmployeeProjection getProjectionById(Long id);

    List<EmployeeProjection> getAllProjectionBy();
}
