package edu.example.learner.dto;

import edu.example.learner.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    private String profileImage;

    private String introduction;

    private LocalDateTime createDate;

    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
        this.createDate = member.getCreateDate();
        // 이미지를 Base64로 인코딩하여 저장
        if (member.getProfileImage() != null) {
            this.profileImage = Base64.encodeBase64String(member.getProfileImage());
        }
    }
}
