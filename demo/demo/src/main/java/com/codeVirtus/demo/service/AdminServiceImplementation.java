package com.codeVirtus.demo.service;

import com.codeVirtus.demo.models.Member;
import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.repository.MemberRepository;
import com.codeVirtus.demo.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class AdminServiceImplementation implements AdminService{

    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminServiceImplementation(MemberRepository memberRepository,UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Member>> viewMembers() {
        return new ResponseEntity<>(memberRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> viewUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Member> approveMembers(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        if(!member.isApproved()) {
            member.setApproved(true);
            return new ResponseEntity<>(memberRepository.save(member), HttpStatus.OK);
        }
        return null;
    }
}
