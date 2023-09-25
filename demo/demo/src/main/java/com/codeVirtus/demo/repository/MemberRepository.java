package com.codeVirtus.demo.repository;

import com.codeVirtus.demo.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query
    Member findTopByOrderByCreatedAtDesc();
}
