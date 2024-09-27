package edu.example.learner.controller;

import edu.example.learner.dto.MemberDTO;
import edu.example.learner.entity.Member;
import edu.example.learner.security.auth.CustomUserPrincipal;
import edu.example.learner.service.MemberService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Log4j2
public class MemberRestController {
    private final MemberService memberService;
    //이미지 업로드
    @PostMapping("/upload_image")
    public ResponseEntity<String> memberUploadImage(@RequestParam("file") MultipartFile file,
                                                    @AuthenticationPrincipal CustomUserPrincipal principal) {
        log.info("--- memberUploadImage()");
        //파일 크기 제한
        if (!file.isEmpty() && file.getSize() > 2097152) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일 크기가 너무 큽니다.");
        }

        //이미지 파일인지 확인
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지 파일만 업로드 가능해요👻");
        }

        try {
            memberService.uploadImage(file, Long.parseLong(principal.getUsername()));
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            log.error("Error uploading image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }
    //회원 정보 조회
    @GetMapping("/read")
    public ResponseEntity<MemberDTO> memberRead(@AuthenticationPrincipal CustomUserPrincipal principal) {
        log.info("--- memberRead()");
        log.info(principal.getUsername());
        return ResponseEntity.ok(memberService.getMemberInfo(Long.parseLong(principal.getUsername())));
    }

    //회원 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<MemberDTO> memberModify(@RequestBody @Validated MemberDTO memberDTO,
                                                  @AuthenticationPrincipal CustomUserPrincipal principal) {
        log.info("--- memberModify()");

        return ResponseEntity.ok(memberService.updateMemberInfo(Long.parseLong(principal.getUsername()),memberDTO));
    }
    //회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> memberDelete(@AuthenticationPrincipal CustomUserPrincipal principal) {
        log.info("--- memberDelete()");

        memberService.deleteMember(Long.parseLong(principal.getUsername()));

        return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
    }
}
