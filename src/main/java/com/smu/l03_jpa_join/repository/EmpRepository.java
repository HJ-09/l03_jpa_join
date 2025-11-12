package com.smu.l03_jpa_join.repository;

import com.smu.l03_jpa_join.entity.Emp;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Emp,Integer> {
    //findAll,findById,deleteById,save


    //┌─ 영속성컨텍스트에 사원과 부서가 따로 저장·관리
    @EntityGraph(attributePaths = {"dept"})
//    List<Emp> getAll();
    //★★★ 엔터티 그래프는 쿼리메소드에서만 ! 근데 get은 쿼리에 업슴 ^^..
    @Query("SELECT e FROM Emp e") //여기 Emp는 테이블 아니고 클래스.
    List<Emp> findAllWithDept(); //WithDept가 메서드 규칙에 어긋나서 @Query 붙임.


    //┌─ 이렇게 직접 조인쿼리를 써버리면 영속성컨텍스트에 조인된 결과로 저장. 그래서 관리가 안됨. (1+N 문제를 효과적으로 해결하지 못함)
    @Query("SELECT e, d FROM Emp e LEFT JOIN Dept d ON e.deptno=d.deptno")
    List<Emp> findWithDeptQuery();

    //fetch join 해볼게 ~
    //JPQL 문법 ^-^
    @Query("SELECT e FROM Emp e JOIN FETCH e.dept") //fetch join은 entity관계로 조인하는거라서 ON절 생략해도 됨! 와우
    List<Emp> findWithFetchDept();
    //★☆★☆★☆ 쿼리로 join을 해야한다면, 무조건 join fetch를 사용 ☆★☆★☆★



    //엔터티 그래프로 메소드를 호출할 때 무조건 join 하게 하는 방법 (entity간의 관계를 lazy로 정의)=> json
    @EntityGraph(attributePaths = {"dept"})
    //private Dept dept (Entity 기반으로 조인)
    List<Emp> findByEname(String ename);
    //@Query로 조인쿼리 작성

    @Query("SELECT e,d FROM Emp e LEFT JOIN Dept d ON e.deptno=d.deptno WHERE e.deptno=:deptno")
    List<Emp> getByDeptno(Integer deptno);

}
