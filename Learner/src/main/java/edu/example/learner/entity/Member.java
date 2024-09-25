package edu.example.learner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    
    private Long idx;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    private String profileImage;

    private String profileAddress;

    private String introduction;

    private LocalDateTime createDate;
}
