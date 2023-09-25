package com.codeVirtus.demo.service;

import com.codeVirtus.demo.models.Education;
import com.codeVirtus.demo.models.Member;
import com.codeVirtus.demo.repository.EducationRepository;
import com.codeVirtus.demo.repository.MemberRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Service
public class MemberServiceImplementation implements MemberService{

    private MemberRepository memberRepository;
    private EducationRepository educationRepository;

    public MemberServiceImplementation(MemberRepository memberRepository, EducationRepository educationRepository) {
        this.memberRepository = memberRepository;
        this.educationRepository = educationRepository;
    }

    @Override
    public ResponseEntity<String> createProfile(Member member) {

        Member newMember = new Member();
        newMember.setAddress(member.getAddress());
        newMember.setGender(member.getGender());
        newMember.setPhoneNumber(member.getPhoneNumber());
        newMember.setApproved(false);

        Education educationLevels = educationRepository.findById(member.getEducation().getEducationId()).orElse(null);
        newMember.setEducation(educationLevels);

        memberRepository.save(newMember);
        return new ResponseEntity<>("Request send successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Member> viewMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        if(member.isApproved()) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Education>> getAllEducation() {
        return new ResponseEntity<>(educationRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Member> lastToBeAdded() {
        return new ResponseEntity<>(memberRepository.findTopByOrderByCreatedAtDesc(), HttpStatus.OK);
    }


}
