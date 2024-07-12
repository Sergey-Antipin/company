package com.antipin.company.controller;

import com.antipin.company.model.Employee;
import com.antipin.company.repository.EmployeeProjection;
import com.antipin.company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}/projection")
    public ResponseEntity<EmployeeProjection> getProjection(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProjectionById(id));
    }

    @GetMapping("/projection")
    public ResponseEntity<List<EmployeeProjection>> getAllProjection() {
        return ResponseEntity.ok(service.getAllProjection());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = service.save(employee);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.save(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
