package com.codeVirtus.demo.controllers;

import com.codeVirtus.demo.models.Education;
import com.codeVirtus.demo.models.Member;
import com.codeVirtus.demo.service.MemberService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/create-profile")
    public ResponseEntity<String> createProfile(@RequestBody Member member) {
        return memberService.createProfile(member);
    }
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> viewMemberProfile(@PathVariable("memberId") Long memberId) {
        return memberService.viewMemberProfile(memberId);
    }

    @GetMapping("/educationLevels")
    public ResponseEntity<List<Education>> getAllEducation() {
        return  memberService.getAllEducation();
    }

    @GetMapping("/lastToBeAdded")
    public ResponseEntity<Member> lastToBeAdded() {
        return  memberService.lastToBeAdded();
    }
}
