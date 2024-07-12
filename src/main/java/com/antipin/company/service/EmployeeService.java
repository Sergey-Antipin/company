package com.antipin.company.service;

import com.antipin.company.exception.EntityNotFoundException;
import com.antipin.company.model.Employee;
import com.antipin.company.repository.EmployeeProjection;
import com.antipin.company.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EmployeeProjection getProjectionById(Long id) {
        return repository.getProjectionById(id);
    }

    public List<EmployeeProjection> getAllProjection() {
        return repository.getAllProjectionBy();
    }
}
