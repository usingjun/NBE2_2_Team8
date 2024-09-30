package edu.example.learner.member.service;

import edu.example.learner.member.entity.Member;
import edu.example.learner.member.exception.LoginException;
import edu.example.learner.member.exception.MemberException;
import edu.example.learner.member.dto.MemberDTO;
import edu.example.learner.member.repository.MemberRepository;
import edu.example.learner.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    //회원가입
    public MemberDTO register(MemberDTO memberDTO) {
        try{
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
                    .nickname(memberDTO.getNickname())
                    .introduction(memberDTO.getIntroduction())
                    .phoneNumber(memberDTO.getPhoneNumber())
                    .build();

            memberRepository.save(member);
            return new MemberDTO(member);
        }catch (Exception e){
            log.error("회원가입 도중 오류 발생",e);
            throw MemberException.MEMBER_NOT_REGISTERED.getMemberTaskException();
        }
    }

    //사진 업로드 및 수정
    public MemberDTO uploadImage(MultipartFile file, Long memberId) {
        try{
            Member member = memberRepository.getMemberInfo(memberId);
            if(member == null){
                throw MemberException.MEMBER_NOT_FOUND.getMemberTaskException();
            }
            member.changeProfileImage(file.getBytes());
            member.changeImageType(file.getContentType());

            return new MemberDTO(memberRepository.save(member));
        }catch (Exception e){
            log.error(e);
            throw MemberException.NOT_UPLOAD_IMAGE.getMemberTaskException();
        }
    }

    //회원 정보 조회
    public MemberDTO getMemberInfo(Long memberId) {
        try {
            Member member = memberRepository.getMemberInfo(memberId);
            MemberDTO memberDTO= new MemberDTO(member);

            // 이미지가 null인지 확인
            if (memberDTO.getProfileImage() == null || memberDTO.getProfileImage().isEmpty()) {
                // 기본 이미지 경로 설정
                String defaultImagePath = "/images/default_profile.png";
                memberDTO.setProfileImage(defaultImagePath);
            }
            return memberDTO;
        }catch (Exception e){
            log.error(e);
            throw MemberException.MEMBER_NOT_FOUND.getMemberTaskException();
        }
    }

    //회원 정보 수정
    public MemberDTO updateMemberInfo(Long memberId, MemberDTO memberDTO) {
        Member member = memberRepository.getMemberInfo(memberId);
        if(member == null){
            throw MemberException.MEMBER_NOT_FOUND.getMemberTaskException();
        }

        try{
            member.changeNickname(memberDTO.getNickname());
            member.changeIntroduction(memberDTO.getIntroduction());
            member.changePassword(passwordEncoder.encode(memberDTO.getPassword()));
            member.changeEmail(memberDTO.getEmail());

            MemberDTO modifyMemberDTO = new MemberDTO(memberRepository.save(member));

            // 이미지가 null인지 확인
            if (modifyMemberDTO.getProfileImage() == null || modifyMemberDTO.getProfileImage().isEmpty()) {
                // 기본 이미지 경로 설정
                String defaultImagePath = "/images/default_profile.png";
                modifyMemberDTO.setProfileImage(defaultImagePath);
            }

            return modifyMemberDTO;
        }catch (Exception e){
            log.error(e);
            throw MemberException.MEMBER_NOT_MODIFIED.getMemberTaskException();
        }
    }

    //회원 탈퇴
    public void deleteMember(Long memberId) {
        Member member = memberRepository.getMemberInfo(memberId);
        if(member == null){
            throw MemberException.MEMBER_NOT_FOUND.getMemberTaskException();
        }

        try{
            memberRepository.delete(member);
        } catch (Exception e){
            log.error(e);
            throw MemberException.MEMBER_NOT_DELETE.getMemberTaskException();
        }
    }

    //로그인
    public String login(String email, String password) {
        Member member = memberRepository.getMemberByEmail(email).orElseThrow(LoginException.NOT_FOUND_EMAIL::getMemberTaskException);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw LoginException.PASSWORD_DISAGREEMENT.getMemberTaskException();
        }

        // JWT 생성 및 반환
        return jwtUtil.createToken(Map.of("mid", member.getMemberId(), "role", member.getRole()), 30);
    }
}
