package edu.example.learner.member.service.find;

import edu.example.learner.member.entity.Member;
import edu.example.learner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    // 전화번호로 이메일(아이디) 찾기
    public List<String> findEmailsByPhoneNumber(String phoneNumber) {
        List<Member> membersByPhoneNumber = memberRepository.findByPhoneNumber(phoneNumber);
        return membersByPhoneNumber.stream()
                .map(Member::getEmail)
                .collect(Collectors.toList());
    }

    // 비밀번호 찾기

}
