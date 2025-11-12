package com.smu.l03_jpa_join.repository;

import com.smu.l03_jpa_join.entity.Dept;
import com.smu.l03_jpa_join.entity.Emp;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 스프링 빈 팩토리를 생성해서 테스트
class EmpRepositoryTest {
    //Component : 스프링 빈 팩토리에서 관리하는 객체
    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EntityManager em; //영속성컨텍스트(조회한 내역을 저장하는 저장소)


    @Test
    //┌─ 이거 잇스면 지연조회 대상이 됨.
    @Transactional(readOnly = true) //dml이 없다는 의미
    //Spring에서는 Transactional이 서비스 단위임.
    void findAll() throws Exception{
//        System.out.println(empRepository.findAll());
        for (Emp emp : empRepository.findAll()) {
            System.out.println(emp.toString());
            System.out.println(emp.getDept()); //지연조회 트리거 dept 호출

            //영속성컨텍스트에 저장된 내역
            //① 사원전체
            //② 사원이 참조하는 부서
            System.out.println("▽▽▽▽▽ 영속성컨텍스트에 저장된 부서 10 조회 ▽▽▽▽▽");
            System.out.println(em.find(Dept.class,10));
        }
    }

    @Test
    @Transactional(readOnly = true)
    void findById() throws Exception{
        int empno=7788;
        Optional<Emp> empOpt=empRepository.findById(empno);
        //Optional null 일수 있는 데이터는 null 처리하라
//        if(emp.isPresent()){}
        empOpt.ifPresentOrElse((emp)->{
            System.out.println(emp);
            System.out.println(empno+"의 부서");
            System.out.println(emp.getDept().getClass()); //대리객체(=proxy)
            //Dept$HibernateProxy 지연조회가 완료될때까지 Dept인척하면서 기다림
            System.out.println(emp.getDept());
        },()->{
            System.out.println(empno+"은 없습니다.");
        });
    }

    @Test
//    @Transactional(readOnly = true)
    void findByEname() {
        List<Emp> emps=empRepository.findByEname("SCOTT");
        //System.out.println(emps);
        for(Emp emp:emps){
            System.out.println(emp);
            //System.out.println(emp.getDept().getClass());
            //class com.smu.l03_jpa_join.entity.Dept  강제조인이기 때문에 프록시가 아님
            System.out.println(emp.getDept());
        }
    }

    @Test
    void getByDeptno() {
        for(Emp e:empRepository.getByDeptno(10)){
            System.out.println(e);
            //System.out.println(e.getDept());
        }
    }

    @Test
    void findAllWithDept() { //EntityGraph는 '무조건' join!
        for(Emp emp:empRepository.findAllWithDept()){
            System.out.println(emp);
            System.out.println(emp.getDept());
            //1+N 문제를 해결하면 지연조회 사용 x
        }
    }

    @Test
    void findWithDeptQuery() {
        for (Emp e:empRepository.findWithDeptQuery()) {
            System.out.println(e);
            System.out.println(e.getDept());
        }
    }

    @Test
    @Transactional
    void findWithFetchDept() {
        for (Emp emp:empRepository.findWithFetchDept()) {
            System.out.println(emp);
            System.out.println(" ,"+emp.getDept());

            System.out.println("***** 영속성컨텍스트에 저장된 부서 10 조회 *****");
            System.out.println(em.find(Dept.class,10));
        }
    }
}