package com.smu.l03_jpa_join.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "DEPT")
public class Dept {
    @Id
    @Column(name = "DEPTNO", nullable = false)
    private Integer deptno;

    @Column(name = "DNAME", length = 14)
    private String dname;

    @Column(name = "LOC", length = 13)
    private String loc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dept")
    @ToString.Exclude
    @JsonIgnore
    List<Emp> emps=new ArrayList<>();
    //List,Set

}