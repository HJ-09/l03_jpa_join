package com.smu.l03_jpa_join.repository;

import com.smu.l03_jpa_join.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept,Integer> {


}
