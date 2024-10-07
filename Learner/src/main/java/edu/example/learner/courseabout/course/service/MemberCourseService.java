package edu.example.learner.courseabout.course.service;

import edu.example.learner.courseabout.course.entity.MemberCourse;
import edu.example.learner.courseabout.course.repository.MemberCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCourseService {

    private final MemberCourseRepository memberCourseRepository;

    // 구매 여부 확인
    public boolean checkPurchase(Long courseId, Long memberId) {
        // 구매 기록이 있는지 확인
        MemberCourse purchase = memberCourseRepository.findByCourseIdAndMemberId(courseId, memberId);

        return purchase != null;
    }

}
