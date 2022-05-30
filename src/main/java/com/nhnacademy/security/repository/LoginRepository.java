package com.nhnacademy.security.repository;

import com.nhnacademy.security.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Member, String> {

    Iterable<Object> findAllById(String id);

}
