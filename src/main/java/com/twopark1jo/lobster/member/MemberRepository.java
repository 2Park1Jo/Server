package com.twopark1jo.lobster.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
    //로그인 확인
    @Query(value = "SELECT EXISTS(SELECT email FROM member " +
            "WHERE email=:email and password=:password)", nativeQuery = true)
    public int checkLogin(@Param("email") String email, @Param("password") String password);

}
