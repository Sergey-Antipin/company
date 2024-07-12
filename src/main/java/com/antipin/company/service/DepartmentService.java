package com.antipin.company.service;

import com.antipin.company.exception.EntityNotFoundException;
import com.antipin.company.model.Department;
import com.antipin.company.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public Department getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department save(Department department) {
        return repository.save(department);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
