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
@Table(name = "Authoroties")
public class Authorotie {

    @Id
    @Column
    String id;

    @Column
    String authority;
}
