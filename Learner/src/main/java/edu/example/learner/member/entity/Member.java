package edu.example.learner.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "member", indexes = @Index(columnList = "email"))
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Hibernate 프록시 필드를 무시
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false , unique = true )
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false , unique = true )
    private String nickname;

    @Column(nullable = true)
    private String phoneNumber;

    @Lob  // BLOB 타입으로 처리됨
    private byte[] profileImage;

    private String imageType;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String introduction;

    @CreatedDate
    private LocalDateTime createDate;



    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void changeProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void changeImageType(String imageType) {
        this.imageType = imageType;
    }
}
