package com.jjh.ecommerce.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;//**jackson 것 import 안하면 @JsonIgnore 안먹음
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    //json data return 할 때, states 정보는 보내지 않는다. 예) http://localhost:8181/api/countries/ 이거 검색했을 때 states 정보 보이지 않게 됨
    private List<State> states;
}
