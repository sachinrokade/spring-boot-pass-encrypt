package com.encrypt.demo.controller;

import com.encrypt.demo.exceptions.EmpNotFoundException;
import com.encrypt.demo.pojo.Employee;
import com.encrypt.demo.services.TestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestServices testServices;


    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllData() {
        try {
            return new ResponseEntity<>(testServices.getAllEmp(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addEmp")
    public ResponseEntity<Employee> addEmp(@Valid @RequestBody Employee employee) {

        try {
            return new ResponseEntity<>(testServices.saveEmp(employee), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") int id) throws EmpNotFoundException{
        try {
            return new ResponseEntity<>(testServices.getById(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByDept/{dept}")
    public ResponseEntity<List<Employee>> getByDept(@PathVariable("dept") String dept) {

        try {
            return new ResponseEntity<>(testServices.getByDept(dept), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmp(@PathVariable("id") int id, @RequestBody Employee employee) throws EmpNotFoundException {

        try {
            return new ResponseEntity<>(testServices.updateEmp(id, employee), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmp(@PathVariable("id") int id) throws EmpNotFoundException {

        try {
            return new ResponseEntity<>(testServices.deleteEmp(id), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
