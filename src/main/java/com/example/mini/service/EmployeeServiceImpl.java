

package com.example.mini.service;

import com.example.mini.exception.EmployeeNotFoundException;
import com.example.mini.mode.entity.Employee;
import com.example.mini.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {

        log.info("Getting all employees");

        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {

        log.info("Getting employee with id: {}", id);

        return employeeRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Employee not found with id: {}", id);

                    return new EmployeeNotFoundException(
                            "Employee not found with id: " + id
                    );
                });
    }

    @Override
    public Employee addEmployee(Employee employee) {

        log.info("Adding employee: {}", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(
            Long id,
            Employee employee
    ) {

        Employee oldEmployee =
                employeeRepository.findById(id)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Employee not found with id: {}",
                                    id
                            );

                            return new EmployeeNotFoundException(
                                    "Employee not found with id: " + id
                            );
                        });

        oldEmployee.setFullName(employee.getFullName());
        oldEmployee.setDepartment(employee.getDepartment());
        oldEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(oldEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Employee not found with id: {}",
                                    id
                            );

                            return new EmployeeNotFoundException(
                                    "Employee not found with id: " + id
                            );
                        });

        employeeRepository.delete(employee);
    }
}