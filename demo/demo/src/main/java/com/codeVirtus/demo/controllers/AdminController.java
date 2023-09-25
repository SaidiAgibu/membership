package com.codeVirtus.demo.controllers;

import com.codeVirtus.demo.models.Member;
import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.service.AdminService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> viewMembers() {
        return adminService.viewMembers();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> viewUsers() {
        return adminService.viewUsers();
    }

    @PostMapping("/{memberId}/approve")
    public ResponseEntity<Member> approveMembers(@PathVariable("memberId") Long memberId) {
        return adminService.approveMembers(memberId);
    }


}
