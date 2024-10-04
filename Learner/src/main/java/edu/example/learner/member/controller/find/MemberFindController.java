package edu.example.learner.member.controller.find;

import edu.example.learner.member.service.find.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/find")
@Tag(name = "회원 찾기", description = "회원을 찾는 API")
public class MemberFindController {
    private final LoginService loginService;

    // 전화번호로 이메일(아이디) 찾기
    @GetMapping("/emails")
    @Operation(summary = "전화번호로 이메일(아이디) 찾기", description = "전화번호로 가입된 회원의 이메일(아이디)을 찾습니다.")
    public ResponseEntity<List<String>> findEmailByPhoneNumber(@RequestParam String phoneNumber) {

        List<String> emails = loginService.findEmailsByPhoneNumber(phoneNumber);
        if (emails.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 응답
        }
        return ResponseEntity.ok(emails); // 200 응답
    }

}
