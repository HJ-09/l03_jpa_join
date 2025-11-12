package com.smu.l03_jpa_join.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "EMP")
public class Emp {
    @Id
    @Column(name = "EMPNO", nullable = false)
    private Integer empno;

    @Column(name = "ENAME", length = 10)
    private String ename;

    @Column(name = "JOB", length = 9)
    private String job;

    @Column(name = "MGR")
    private Integer mgr;

    @Column(name = "HIREDATE")
    private LocalDate hiredate;

    @Column(name = "SAL", precision = 7, scale = 2)
    private BigDecimal sal;

    @Column(name = "COMM", precision = 7, scale = 2)
    private BigDecimal comm;

    @Column(name = "DEPTNO", insertable = false, updatable = false) //fk
    private  Integer deptno;

    @ManyToOne(fetch = FetchType.LAZY)//즉시 or 지연
    @JoinColumn( name = "DEPTNO") //조인하는 부모키(table기반)
    //@OnDelete(action = OnDeleteAction.SET_NULL)
    @ToString.Exclude
    @JsonIgnore
    private Dept dept;

    //mgr 상사를 조인(지연조회)
    //상사 1 : 사원 N



//    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.RESTRICT)
//    @JoinColumn(name = "DEPTNO")
//    private Dept deptno;

}