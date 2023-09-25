package com.codeVirtus.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String gender;
    private String phoneNumber;
    private String address;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_education",
            joinColumns = @JoinColumn(
                    name = "member_id",
                    referencedColumnName = "memberId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "education_id",
                    referencedColumnName = "educationId"
            )
    )
    private Education education;
    private boolean approved;
    private LocalDateTime createdAt;

    public void setUser(User user) {
        this.user = user;
    }
}
