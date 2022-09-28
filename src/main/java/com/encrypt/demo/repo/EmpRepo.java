package com.encrypt.demo.repo;

import com.encrypt.demo.pojo.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepo extends JpaRepository<Emp,Integer> {

}
