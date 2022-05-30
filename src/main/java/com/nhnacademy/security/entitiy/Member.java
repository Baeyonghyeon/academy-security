package com.nhnacademy.security.entitiy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "Members")
public class Member {

    @Id
    @Column(name = "member_id")
    String id;

    @Column
    String name;

    @Column
    String pwd;
}
