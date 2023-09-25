package com.codeVirtus.demo.service;

import com.codeVirtus.demo.models.Education;
import com.codeVirtus.demo.models.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    public ResponseEntity<String> createProfile(Member member);

    public ResponseEntity<Member> viewMemberProfile(Long memberId);

    public ResponseEntity<List<Education>> getAllEducation();

    public ResponseEntity<Member> lastToBeAdded();
}
