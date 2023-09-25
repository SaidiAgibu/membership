package com.codeVirtus.demo.service;

import com.codeVirtus.demo.models.Member;
import com.codeVirtus.demo.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    public ResponseEntity<List<Member>> viewMembers();

    public ResponseEntity<List<User>> viewUsers();

    public ResponseEntity<Member> approveMembers(Long memberId);
}
