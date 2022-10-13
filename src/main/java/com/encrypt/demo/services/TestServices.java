package com.encrypt.demo.services;

import com.encrypt.demo.exceptions.EmpNotFoundException;
import com.encrypt.demo.pojo.Employee;
import com.encrypt.demo.repo.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class TestServices {

    @Autowired
    EmpRepo repo;

    public List<Employee> getAllEmp() {
        List<Employee> list = repo.findAll();
        return list;

    }

    public Employee saveEmp(Employee employee) {

        Employee employeeSaved = repo.save(employee);
        return employeeSaved;

    }

    public Employee getById(int id) throws EmpNotFoundException {
        Employee employee = repo.findById(id).orElseThrow(() -> new EmpNotFoundException("Employee Not found"));
        return employee;
    }

    public List<Employee> getByDept(String dept) {

        return repo.findByDept(dept);
    }

    public Employee updateEmp(int id, Employee employee) {

        Optional<Employee> empOptional = repo.findById(id);

        return repo.findById(id)
                .map(emp -> {
                    emp.setName(employee.getName());
                    emp.setDept(employee.getDept());
                    emp.setSalery(employee.getSalery());
                    return repo.save(emp);
                })
                .orElseThrow(() -> new EmpNotFoundException("Employee Not found with id " + id));

    }

    public String deleteEmp(int id) throws EmpNotFoundException {

        Optional<Employee> empOptional = repo.findById(id);

        if (empOptional.isPresent()) {
            repo.deleteById(id);
            return "Deleted";
        } else
            throw new EmpNotFoundException("Employee not found with id" + id);

    }

}
