package com.encrypt.demo.repo;

import com.encrypt.demo.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepo extends JpaRepository<Employee,Integer> {

    public List<Employee> findByDept(String dept);
}
