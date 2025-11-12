package com.smu.l03_jpa_join.repository;

import com.smu.l03_jpa_join.entity.Dept;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class DeptRepositoryTest {
    @Autowired
    private DeptRepository deptRepository;
    //부서의 다수를 조회할때는 Join처럼 select 진행
    //대리인 프록시 view와 service 에서만 초기화될때까지 기다려줌
    @Test
    @Transactional(readOnly = true) //롤백없이 조회만 있는 트랜잭션
    void findAll() throws Exception{
        //System.out.println(deptRepository.findAll());
        List<Dept> depts=deptRepository.findAll();
        for(Dept dept:depts){
            System.out.println(dept);
            System.out.println(dept.getDeptno()+"의 사원들");
            //dept.getEmps() 대리객체
            System.out.println(dept.getEmps());
        }

    }
    //부서 한개를 조회할때는 join
    @Test
    @Transactional(readOnly = true)
    void findById() throws Exception{
        Optional<Dept> deptOpt=deptRepository.findById(10);
        if(deptOpt.isPresent()){
            Dept dept=deptOpt.get();
            System.out.println(dept.toString());
            System.out.println("10번 부서의 사원들");
            System.out.println(dept.getEmps());
        }else{
            System.out.println("10번 부서 없음");
        }
    }
}